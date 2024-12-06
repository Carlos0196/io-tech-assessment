package com.io.assessment.models.dtos;

import java.time.LocalDate;
import java.util.List;

public record TedTalkRecord(
        String title,
        List<String> authors,
        LocalDate date,
        Long views,
        Long likes,
        String link
) {
}
