package com.io.assessment.repositories;

import com.io.assessment.models.entities.SpeakerEntity;
import com.io.assessment.models.entities.TedTalkEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SpeakerRepositoryTest {

    private SpeakerRepository speakerRepository;

    @BeforeEach
    void setUp() {
        speakerRepository = new SpeakerRepository();
    }

    @Test
    void shouldSaveSpeaker() {
        // Arrange
        var talk1 = mock(TedTalkEntity.class);
        var talk2 = mock(TedTalkEntity.class);
        var talks = new HashMap<String, TedTalkEntity>();
        talks.put("talk1", talk1);
        talks.put("talk2", talk2);

        var speaker = new SpeakerEntity("name", talks);

        // Act
        speakerRepository.saveOrUpdate(speaker);
        var result = speakerRepository.findByName("name");

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.name()).isEqualTo("name");
        assertThat(result.talks().values().size()).isEqualTo(2);
        assertThat(result.talks().values()).containsExactly(talk1, talk2);
    }

    @Test
    void shouldUpdateSpeaker() {
        // Arrange
        var talk1 = mock(TedTalkEntity.class);
        var talks = new HashMap<String, TedTalkEntity>();
        talks.put("talk1", talk1);
        var speaker = new SpeakerEntity("name", talks);

        speakerRepository.saveOrUpdate(speaker);
        var result = speakerRepository.findByName("name");
        assertThat(result).isNotNull();
        assertThat(result.name()).isEqualTo("name");
        assertThat(result.talks().values().size()).isEqualTo(1);
        assertThat(result.talks().values()).containsExactly(talk1);

        // Act
        var talk2 = mock(TedTalkEntity.class);
        talks.put("talk2", talk2);
        var updatedSpeaker = new SpeakerEntity("name", talks);

        // Assert
        speakerRepository.saveOrUpdate(updatedSpeaker);
        result = speakerRepository.findByName("name");
        assertThat(result).isNotNull();
        assertThat(result.name()).isEqualTo("name");
        assertThat(result.talks().values().size()).isEqualTo(2);
        assertThat(result.talks().values()).containsExactly(talk1, talk2);
    }

    @Test
    void shouldUpdateInfluenceMap() {
        // Arrange
        var speaker = mock(SpeakerEntity.class);
        when(speaker.name()).thenReturn("name");
        when(speaker.influenceScore()).thenReturn(5d, 5d, 10d);
        when(speaker.merge(speaker)).thenReturn(speaker);

        // Act
        speakerRepository.saveOrUpdate(speaker);
        speakerRepository.saveOrUpdate(speaker);
        var result = speakerRepository.getTopNSpeakers(1);

        // Assert
        assertThat(result.isEmpty()).isFalse();
        assertThat(result.get(0).name()).isEqualTo("name");
        assertThat(result.get(0).influenceScore()).isEqualTo(10d);
    }
}
