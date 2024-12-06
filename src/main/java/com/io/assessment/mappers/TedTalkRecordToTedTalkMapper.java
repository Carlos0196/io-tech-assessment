package com.io.assessment.mappers;

import com.io.assessment.models.dtos.TedTalkRecord;
import com.io.assessment.models.entities.TedTalkEntity;
import org.springframework.stereotype.Component;

@Component
public class TedTalkRecordToTedTalkMapper {

    public TedTalkRecordToTedTalkMapper() {
    }

    public TedTalkEntity map(final TedTalkRecord record) {
        return new TedTalkEntity(
                record.title(),
                record.date().getYear(),
                record.views(),
                record.likes(),
                record.link(),
                record.authors()
        );
    }
}
