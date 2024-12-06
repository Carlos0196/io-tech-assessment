package com.io.assessment.controllers;

import com.io.assessment.models.dtos.TedTalkDto;
import com.io.assessment.services.ImportService;
import com.io.assessment.services.TedTalkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/api/ted-talks")
public class TedTalkController {

    private final ImportService importService;
    private final TedTalkService tedTalkService;

    @Autowired
    public TedTalkController(final ImportService importService,
                             final TedTalkService tedTalkService) {
        this.importService = importService;
        this.tedTalkService = tedTalkService;
    }

    @GetMapping
    public ResponseEntity<List<TedTalkDto>> getInfluentialTedTalks() {
        return ResponseEntity.ok(tedTalkService.getInfluentialTalks());
    }

    @PutMapping
    public ResponseEntity<TedTalkDto> updateTedTalk(@RequestBody TedTalkDto updateSpeakerDto) {
        return ResponseEntity.ok(tedTalkService.update(updateSpeakerDto));
    }

    @PostMapping("/import")
    public ResponseEntity<String> uploadCsv(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty() ||
                file.getOriginalFilename() == null ||
                !file.getOriginalFilename().endsWith(".csv")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload a valid CSV file.");
        }

        try {
            importService.importData(file);

            return ResponseEntity.ok("File processed successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error reading the file: " + e.getMessage());
        }
    }
}
