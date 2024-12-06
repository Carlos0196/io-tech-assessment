package com.io.assessment.models.entities;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SpeakerEntityTest {

    @Test
    void shouldCalculateViewsProperly() {
        // Arrange
        var talk1 = mock(TedTalkEntity.class);
        when(talk1.views()).thenReturn(20L);

        var talk2 = mock(TedTalkEntity.class);
        when(talk2.views()).thenReturn(30L);

        var talks = new HashMap<String, TedTalkEntity>();
        talks.put("talk1", talk1);
        talks.put("talk2", talk2);

        var speaker = new SpeakerEntity("name", talks);

        // Act
        var result = speaker.views();

        // Assert
        assertThat(result).isEqualTo(50L);
    }

    @Test
    void shouldCalculateLikesProperly() {
        // Arrange
        var talk1 = mock(TedTalkEntity.class);
        when(talk1.likes()).thenReturn(20L);

        var talk2 = mock(TedTalkEntity.class);
        when(talk2.likes()).thenReturn(30L);

        var talks = new HashMap<String, TedTalkEntity>();
        talks.put("talk1", talk1);
        talks.put("talk2", talk2);

        var speaker = new SpeakerEntity("name", talks);

        // Act
        var result = speaker.likes();

        // Assert
        assertThat(result).isEqualTo(50L);
    }

    @Test
    void shouldMergeProperly() {
        // Arrange
        var talk1 = mock(TedTalkEntity.class);
        var talk2 = mock(TedTalkEntity.class);

        var talks1 = new HashMap<String, TedTalkEntity>();
        talks1.put("talk1", talk1);

        var talks2 = new HashMap<String, TedTalkEntity>();
        talks2.put("talk2", talk2);

        var speaker1 = new SpeakerEntity("name", talks1);
        var speaker2 = new SpeakerEntity("name", talks2);

        // Act
        var result = speaker1.merge(speaker2);

        // Assert
        assertThat(result.name()).isEqualTo("name");
        assertThat(result.talks().values().size()).isEqualTo(2);
        assertThat(result.talks().values()).containsExactly(talk1, talk2);
    }
}
