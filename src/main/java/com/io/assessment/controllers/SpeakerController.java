package com.io.assessment.controllers;

import com.io.assessment.models.dtos.SpeakerDto;
import com.io.assessment.services.SpeakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/api/speakers")
public class SpeakerController {

    private final SpeakerService speakerService;

    @Autowired
    public SpeakerController(SpeakerService speakerService) {
        this.speakerService = speakerService;
    }

    @GetMapping
    public ResponseEntity<List<SpeakerDto>> findAll(@RequestParam(value = "topN", required = false) Integer topN) {
        if (topN == null) {
            return ResponseEntity.ok(speakerService.findAll());
        }
        return ResponseEntity.ok(speakerService.getTopNSpeakers(topN));
    }

    @GetMapping("/{name}")
    public ResponseEntity<SpeakerDto> findByName(@PathVariable String name) {
        return ResponseEntity.ok(speakerService.findByName(name));
    }
}
