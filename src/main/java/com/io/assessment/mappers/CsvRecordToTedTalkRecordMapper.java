package com.io.assessment.mappers;

import com.io.assessment.models.dtos.TedTalkRecord;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Component
public class CsvRecordToTedTalkRecordMapper {

    public CsvRecordToTedTalkRecordMapper() {
    }

    public TedTalkRecord map(final CSVRecord record) {
        String title = record.get("title");
        String authors = record.get("author");
        String date = record.get("date");
        long views = Long.parseLong(record.get("views"));
        long likes = Long.parseLong(record.get("likes"));
        String link = record.get("link");

        if (views < 0 || likes < 0 || views < likes) {
            throw new IllegalArgumentException("Invalid views or likes");
        }

        return new TedTalkRecord(
                title,
                splitAuthors(authors),
                parseDate(date),
                views,
                likes,
                link
        );
    }

    protected List<String> splitAuthors(final String authors) {
        if (authors == null || authors.isBlank()) {
            return new ArrayList<>();
        }

        // Replace "with" and "and" with commas to create a uniform delimiter
        String normalized = authors
                .replace(" and ", ",")
                .replace(" with ", ",");

        // Split the normalized string by commas, trim spaces, and collect into a list
        return Arrays.stream(normalized.split(","))
                .map(String::trim)
                .filter(author -> !author.isBlank()) // Remove any empty strings
                .toList();
    }

    protected LocalDate parseDate(final String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy dd", Locale.ENGLISH);
        return LocalDate.parse(date + " 01", formatter);
    }

}
