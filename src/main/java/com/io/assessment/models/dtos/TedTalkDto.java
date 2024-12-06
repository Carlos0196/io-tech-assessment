package com.io.assessment.models.dtos;

import java.util.List;

public record TedTalkDto(
        String title,
        Integer year,
        Long views,
        Long likes,
        String link,
        List<String> authors
) {
}
