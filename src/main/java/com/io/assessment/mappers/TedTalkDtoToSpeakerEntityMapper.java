package com.io.assessment.mappers;

import com.io.assessment.models.dtos.TedTalkDto;
import com.io.assessment.models.entities.SpeakerEntity;
import com.io.assessment.models.entities.TedTalkEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TedTalkDtoToSpeakerEntityMapper {

    private final TedTalkDtoToEntityMapper tedTalkDtoToEntityMapper;

    @Autowired
    public TedTalkDtoToSpeakerEntityMapper(TedTalkDtoToEntityMapper tedTalkDtoToEntityMapper) {
        this.tedTalkDtoToEntityMapper = tedTalkDtoToEntityMapper;
    }

    public List<SpeakerEntity> map(final TedTalkDto tedTalkDto) {
        return tedTalkDto.authors()
                .stream()
                .map(author -> map(author, tedTalkDto))
                .toList();
    }

    protected SpeakerEntity map(final String speakerName,
                                final TedTalkDto tedTalkDto) {
        final Map<String, TedTalkEntity> talks = new HashMap<>();
        talks.put(tedTalkDto.title(), tedTalkDtoToEntityMapper.map(tedTalkDto));

        return new SpeakerEntity(
                speakerName,
                talks
        );
    }
}
