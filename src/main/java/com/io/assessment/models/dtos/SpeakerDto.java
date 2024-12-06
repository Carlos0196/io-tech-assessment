package com.io.assessment.models.dtos;

import java.util.List;

public record SpeakerDto(
        String name,
        Long views,
        Long likes,
        Double influenceScore,
        List<String> talks
) {
}
