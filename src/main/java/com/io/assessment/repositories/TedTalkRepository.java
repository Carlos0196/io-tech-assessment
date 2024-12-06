package com.io.assessment.repositories;

import com.io.assessment.models.entities.TedTalkEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class TedTalkRepository {

    private final Map<Integer, TedTalkEntity> tedTalks;

    public TedTalkRepository() {
        this.tedTalks = new ConcurrentHashMap<>();
    }

    public List<TedTalkEntity> getInfluentialTalks() {
        return new ArrayList<>(tedTalks.values());
    }

    public void saveInfluentialTalks(final TedTalkEntity tedTalkEntity) {
        if (tedTalks.containsKey(tedTalkEntity.year())) {
            final var previousTalk = tedTalks.get(tedTalkEntity.year());

            if (tedTalkEntity.influenceScore() > previousTalk.influenceScore()) {
                tedTalks.put(tedTalkEntity.year(), tedTalkEntity);
            }
        } else {
            tedTalks.put(tedTalkEntity.year(), tedTalkEntity);
        }
    }

}
