package com.io.assessment.mappers;

import com.io.assessment.models.dtos.TedTalkRecord;
import com.io.assessment.models.entities.SpeakerEntity;
import com.io.assessment.models.entities.TedTalkEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TedTalkRecordToSpeakersMapper {

    private final TedTalkRecordToTedTalkMapper tedTalkRecordToTedTalkMapper;

    public TedTalkRecordToSpeakersMapper(final TedTalkRecordToTedTalkMapper tedTalkRecordToTedTalkMapper) {
        this.tedTalkRecordToTedTalkMapper = tedTalkRecordToTedTalkMapper;
    }

    public List<SpeakerEntity> map(final TedTalkRecord record) {
        return record.authors()
                .stream()
                .map(author -> map(author, record))
                .toList();
    }

    protected SpeakerEntity map(final String speakerName,
                                final TedTalkRecord record) {
        final Map<String, TedTalkEntity> talks = new HashMap<>();
        talks.put(record.title(), tedTalkRecordToTedTalkMapper.map(record));

        return new SpeakerEntity(
                speakerName,
                talks
        );
    }
}
