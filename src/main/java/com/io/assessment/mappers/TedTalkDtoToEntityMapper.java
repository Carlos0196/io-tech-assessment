package com.io.assessment.mappers;

import com.io.assessment.models.dtos.TedTalkDto;
import com.io.assessment.models.entities.TedTalkEntity;
import org.springframework.stereotype.Component;

@Component
public class TedTalkDtoToEntityMapper {

    public TedTalkDtoToEntityMapper() {
    }

    public TedTalkEntity map(final TedTalkDto tedTalkDto) {
        return new TedTalkEntity(
                tedTalkDto.title(),
                tedTalkDto.year(),
                tedTalkDto.views(),
                tedTalkDto.likes(),
                tedTalkDto.link(),
                tedTalkDto.authors()
        );
    }
}
