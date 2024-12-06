package com.io.assessment.services;

import com.io.assessment.mappers.SpeakerEntityToDtoMapper;
import com.io.assessment.models.dtos.SpeakerDto;
import com.io.assessment.repositories.SpeakerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpeakerService {

    private final SpeakerRepository speakerRepository;
    private final SpeakerEntityToDtoMapper speakerEntityToDtoMapper;

    @Autowired
    public SpeakerService(final SpeakerRepository speakerRepository,
                          final SpeakerEntityToDtoMapper speakerEntityToDtoMapper) {
        this.speakerRepository = speakerRepository;
        this.speakerEntityToDtoMapper = speakerEntityToDtoMapper;
    }

    public List<SpeakerDto> findAll() {
        return speakerRepository.findAll()
                .stream()
                .map(speakerEntityToDtoMapper::map)
                .toList();
    }

    public SpeakerDto findByName(final String name) {
        return speakerEntityToDtoMapper.map(speakerRepository.findByName(name));
    }

    public List<SpeakerDto> getTopNSpeakers(final int n) {
        return speakerRepository.getTopNSpeakers(n)
                .stream()
                .map(speakerEntityToDtoMapper::map)
                .toList();
    }
}
