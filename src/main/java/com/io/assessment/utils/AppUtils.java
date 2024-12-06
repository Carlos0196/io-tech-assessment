package com.io.assessment.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class AppUtils {

    private AppUtils() {
    }

    public static Double calculateInfluenceScore(final Long views,
                                                 final Long likes) {
        // This helps to moderate the influence of speakers
        // with a very large number of views but relatively low engagement
        // The +1 ensures you don’t take the logarithm of zero views
        final var score = likes.doubleValue() / (Math.log(views.doubleValue()) + 1);
        return BigDecimal.valueOf(score)
                .setScale(4, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
