package com.io.assessment.models.entities;

import com.io.assessment.utils.AppUtils;

import java.util.List;

public record TedTalkEntity(
        String title,
        Integer year,
        Long views,
        Long likes,
        String link,
        List<String> authors
) {
    public Double influenceScore() {
        return AppUtils.calculateInfluenceScore(this.views(), this.likes());
    }
}
