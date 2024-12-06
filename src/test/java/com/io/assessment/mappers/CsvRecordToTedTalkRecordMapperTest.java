package com.io.assessment.mappers;


import com.io.assessment.models.dtos.TedTalkRecord;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CsvRecordToTedTalkRecordMapperTest {

    private CsvRecordToTedTalkRecordMapper csvRecordToTedTalkRecordMapper;

    @BeforeEach
    void setUp() {
        csvRecordToTedTalkRecordMapper = new CsvRecordToTedTalkRecordMapper();
    }

    @Test
    void shouldMapCorrectly() {
        // Arrange
        CSVRecord record = mock(CSVRecord.class);
        when(record.get("title")).thenReturn("Title");
        when(record.get("author")).thenReturn("Author");
        when(record.get("date")).thenReturn("December 2024");
        when(record.get("views")).thenReturn("20");
        when(record.get("likes")).thenReturn("10");
        when(record.get("link")).thenReturn("link.com/test");

        // Act
        TedTalkRecord result = csvRecordToTedTalkRecordMapper.map(record);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.title()).isEqualTo("Title");
        assertThat(result.authors().size()).isEqualTo(1);
        assertThat(result.authors().get(0)).isEqualTo("Author");
        assertThat(result.date().getYear()).isEqualTo(2024);
        assertThat(result.views()).isEqualTo(20);
        assertThat(result.likes()).isEqualTo(10);
        assertThat(result.link()).isEqualTo("link.com/test");
    }

    @Test
    void shouldThrowIfInvalidViews() {
        // Arrange
        CSVRecord record = mock(CSVRecord.class);
        when(record.get("title")).thenReturn("Title");
        when(record.get("author")).thenReturn("Author");
        when(record.get("date")).thenReturn("December 2024");
        when(record.get("views")).thenReturn("-20");
        when(record.get("likes")).thenReturn("10");
        when(record.get("link")).thenReturn("link.com/test");

        // Act - Assert
        assertThatThrownBy(() -> csvRecordToTedTalkRecordMapper.map(record));
    }

    @Test
    void shouldThrowIfInvalidLikes() {
        // Arrange
        CSVRecord record = mock(CSVRecord.class);
        when(record.get("title")).thenReturn("Title");
        when(record.get("author")).thenReturn("Author");
        when(record.get("date")).thenReturn("December 2024");
        when(record.get("views")).thenReturn("20");
        when(record.get("likes")).thenReturn("-10");
        when(record.get("link")).thenReturn("link.com/test");

        // Act - Assert
        assertThatThrownBy(() -> csvRecordToTedTalkRecordMapper.map(record));
    }

    @Test
    void shouldThrowIfInvalidViewsAndLikes() {
        // Arrange
        CSVRecord record = mock(CSVRecord.class);
        when(record.get("title")).thenReturn("Title");
        when(record.get("author")).thenReturn("Author");
        when(record.get("date")).thenReturn("December 2024");
        when(record.get("views")).thenReturn("10");
        when(record.get("likes")).thenReturn("20");
        when(record.get("link")).thenReturn("link.com/test");

        // Act - Assert
        assertThatThrownBy(() -> csvRecordToTedTalkRecordMapper.map(record));
    }

    @Test
    void shouldSliptActorsCorrectly() {
        // Arrange
        CSVRecord record = mock(CSVRecord.class);
        when(record.get("title")).thenReturn("Title");
        when(record.get("date")).thenReturn("December 2024");
        when(record.get("views")).thenReturn("20");
        when(record.get("likes")).thenReturn("10");
        when(record.get("link")).thenReturn("link.com/test");
        when(record.get("author")).thenReturn("Author1, Author2 and Author3 with Author4");

        // Act
        TedTalkRecord result = csvRecordToTedTalkRecordMapper.map(record);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.authors().size()).isEqualTo(4);
        assertThat(result.authors().contains("Author1")).isTrue();
        assertThat(result.authors().contains("Author2")).isTrue();
        assertThat(result.authors().contains("Author3")).isTrue();
        assertThat(result.authors().contains("Author4")).isTrue();
    }
}
