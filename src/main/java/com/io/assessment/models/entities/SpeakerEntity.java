package com.io.assessment.models.entities;

import com.io.assessment.utils.AppUtils;

import java.util.HashMap;
import java.util.Map;

public record SpeakerEntity(
        String name,
        Map<String, TedTalkEntity> talks) {

    public Long views() {
        return talks.values().stream().map(TedTalkEntity::views).reduce(0L, Long::sum);
    }

    public Long likes() {
        return talks.values().stream().map(TedTalkEntity::likes).reduce(0L, Long::sum);
    }

    public Double influenceScore() {
        return AppUtils.calculateInfluenceScore(this.views(), this.likes());
    }

    public SpeakerEntity merge(final SpeakerEntity other) {
        final var talks = new HashMap<String, TedTalkEntity>();
        talks.putAll(this.talks);
        talks.putAll(other.talks);

        return new SpeakerEntity(
                this.name,
                talks
        );
    }
}
