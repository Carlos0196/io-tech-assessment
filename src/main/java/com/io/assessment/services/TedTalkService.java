package com.io.assessment.services;

import com.io.assessment.mappers.TedTalkDtoToEntityMapper;
import com.io.assessment.mappers.TedTalkDtoToSpeakerEntityMapper;
import com.io.assessment.mappers.TedTalkEntityToDtoMapper;
import com.io.assessment.models.dtos.TedTalkDto;
import com.io.assessment.repositories.SpeakerRepository;
import com.io.assessment.repositories.TedTalkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TedTalkService {

    private final TedTalkRepository tedTalkRepository;
    private final SpeakerRepository speakerRepository;
    private final TedTalkDtoToEntityMapper tedTalkDtoToEntityMapper;
    private final TedTalkDtoToSpeakerEntityMapper tedTalkDtoToSpeakerEntityMapper;
    private final TedTalkEntityToDtoMapper tedTalkEntityToDtoMapper;

    @Autowired
    public TedTalkService(final TedTalkRepository tedTalkRepository,
                          final SpeakerRepository speakerRepository,
                          final TedTalkDtoToEntityMapper tedTalkDtoToEntityMapper,
                          final TedTalkDtoToSpeakerEntityMapper tedTalkDtoToSpeakerEntityMapper,
                          final TedTalkEntityToDtoMapper tedTalkEntityToDtoMapper) {
        this.tedTalkRepository = tedTalkRepository;
        this.speakerRepository = speakerRepository;
        this.tedTalkDtoToEntityMapper = tedTalkDtoToEntityMapper;
        this.tedTalkDtoToSpeakerEntityMapper = tedTalkDtoToSpeakerEntityMapper;
        this.tedTalkEntityToDtoMapper = tedTalkEntityToDtoMapper;
    }

    public List<TedTalkDto> getInfluentialTalks() {
        return tedTalkRepository.getInfluentialTalks()
                .stream()
                .map(tedTalkEntityToDtoMapper::map)
                .toList();
    }

    public TedTalkDto update(final TedTalkDto tedTalkDto) {
        final var speakers = tedTalkDtoToSpeakerEntityMapper.map(tedTalkDto);
        final var tedTalkEntity = tedTalkDtoToEntityMapper.map(tedTalkDto);

        tedTalkRepository.saveInfluentialTalks(tedTalkEntity);
        speakerRepository.saveOrUpdate(speakers);

        return tedTalkDto;
    }
}
