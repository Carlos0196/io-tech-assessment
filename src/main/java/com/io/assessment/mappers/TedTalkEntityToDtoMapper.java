package com.io.assessment.mappers;

import com.io.assessment.models.dtos.TedTalkDto;
import com.io.assessment.models.entities.TedTalkEntity;
import org.springframework.stereotype.Component;

@Component
public class TedTalkEntityToDtoMapper {

    public TedTalkDto map(TedTalkEntity entity) {
        return new TedTalkDto(
                entity.title(),
                entity.year(),
                entity.views(),
                entity.likes(),
                entity.link(),
                entity.authors()
        );
    }
}
