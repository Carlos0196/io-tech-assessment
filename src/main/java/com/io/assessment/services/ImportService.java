package com.io.assessment.services;

import com.io.assessment.mappers.CsvRecordToTedTalkRecordMapper;
import com.io.assessment.mappers.TedTalkRecordToSpeakersMapper;
import com.io.assessment.mappers.TedTalkRecordToTedTalkMapper;
import com.io.assessment.repositories.SpeakerRepository;
import com.io.assessment.repositories.TedTalkRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class ImportService {

    private static final Logger logger = LoggerFactory.getLogger(ImportService.class);

    private final SpeakerRepository speakerRepository;
    private final TedTalkRepository tedTalkRepository;
    private final CsvRecordToTedTalkRecordMapper recordMapper;
    private final TedTalkRecordToSpeakersMapper speakersMapper;
    private final TedTalkRecordToTedTalkMapper tedTalkMapper;

    @Autowired
    public ImportService(final SpeakerRepository speakerRepository,
                         final TedTalkRepository tedTalkRepository,
                         final CsvRecordToTedTalkRecordMapper recordMapper,
                         final TedTalkRecordToSpeakersMapper speakersMapper,
                         final TedTalkRecordToTedTalkMapper tedTalkMapper) {
        this.speakerRepository = speakerRepository;
        this.tedTalkRepository = tedTalkRepository;
        this.recordMapper = recordMapper;
        this.speakersMapper = speakersMapper;
        this.tedTalkMapper = tedTalkMapper;
    }

    public void importData(final MultipartFile file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                     .builder()
                     .setHeader("title", "author", "date", "views", "likes", "link")
                     .setSkipHeaderRecord(true)
                     .build())) {

            for (CSVRecord csvRecord : csvParser) {
                try {
                    final var record = recordMapper.map(csvRecord);

                    final var speakers = speakersMapper.map(record);
                    speakerRepository.saveOrUpdate(speakers);

                    final var tedTalks = tedTalkMapper.map(record);
                    tedTalkRepository.saveInfluentialTalks(tedTalks);
                } catch (Exception e) {
                    logger.warn("Ignoring invalid record :: {} :: {}", e.getMessage(), csvRecord);
                }
            }

        }
    }
}
