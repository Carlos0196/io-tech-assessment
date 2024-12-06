package com.io.assessment.mappers;

import com.io.assessment.models.dtos.SpeakerDto;
import com.io.assessment.models.entities.SpeakerEntity;
import com.io.assessment.models.entities.TedTalkEntity;
import org.springframework.stereotype.Component;

@Component
public class SpeakerEntityToDtoMapper {

    public SpeakerEntityToDtoMapper() {
    }

    public SpeakerDto map(final SpeakerEntity speakerEntity) {
        return new SpeakerDto(
                speakerEntity.name(),
                speakerEntity.views(),
                speakerEntity.likes(),
                speakerEntity.influenceScore(),
                speakerEntity.talks()
                        .values()
                        .stream()
                        .map(TedTalkEntity::title)
                        .toList()
        );
    }
}
