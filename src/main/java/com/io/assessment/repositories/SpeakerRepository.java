package com.io.assessment.repositories;

import com.io.assessment.models.entities.SpeakerEntity;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class SpeakerRepository {

    private final Map<String, SpeakerEntity> speakers;
    private final Map<Double, Map<String, SpeakerEntity>> influenceMap;

    public SpeakerRepository() {
        this.speakers = new ConcurrentHashMap<>();
        this.influenceMap = Collections.synchronizedMap(new TreeMap<>(Collections.reverseOrder()));
    }

    public List<SpeakerEntity> findAll() {
        return new ArrayList<>(speakers.values());
    }

    public SpeakerEntity findByName(final String name) {
        return speakers.get(name);
    }

    public List<SpeakerEntity> getTopNSpeakers(int n) {
        List<SpeakerEntity> result = new ArrayList<>();
        for (var entry : influenceMap.entrySet()) {
            for (SpeakerEntity speaker : entry.getValue().values()) {
                if (result.size() < n) {
                    result.add(speaker);
                } else {
                    return result;
                }
            }
        }
        return result;
    }

    public void saveOrUpdate(final List<SpeakerEntity> speakers) {
        speakers.forEach(this::saveOrUpdate);
    }

    public void saveOrUpdate(final SpeakerEntity speaker) {
        if (speakers.containsKey(speaker.name())) {
            this.update(speaker);
        } else {
            this.save(speaker);
        }
    }

    private void save(final SpeakerEntity speaker) {
        speakers.put(speaker.name(), speaker);
        influenceMap.computeIfAbsent(speaker.influenceScore(), k -> new ConcurrentHashMap<>())
                .put(speaker.name(), speaker);
    }

    private void update(final SpeakerEntity speaker) {
        SpeakerEntity oldSpeaker = speakers.get(speaker.name());

        // Remove the speaker from the old score
        final var oldScore = oldSpeaker.influenceScore();
        influenceMap.get(oldScore).remove(oldSpeaker.name());
        if (influenceMap.get(oldScore).isEmpty()) {
            influenceMap.remove(oldScore);
        }

        // Merge speaker entities
        final var newSpeaker = oldSpeaker.merge(speaker);

        // Add the speaker to the new score
        double newScore = newSpeaker.influenceScore();
        influenceMap.computeIfAbsent(newScore, k -> new ConcurrentHashMap<>())
                .put(newSpeaker.name(), newSpeaker);
        speakers.put(newSpeaker.name(), newSpeaker);
    }
}
