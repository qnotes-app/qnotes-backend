package com.qnotes.qnotesbackend.controller;

import com.qnotes.qnotesbackend.dto.NoteDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RestController
@RequestMapping("/notes")
@Tag(name = "Notes")
public class NotesController {
    @Operation(summary = "Get all notes")
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<NoteDTO>> getAllNotes() {
        return ResponseEntity.ok(getDummyNotes());
    }

    private List<NoteDTO> getDummyNotes() {
        List<NoteDTO> dummyNotes = new ArrayList<>();
        dummyNotes.add(NoteDTO.builder()
                .id("1")
                .content("Testing")
                .createdAt(new Date())
                .updatedAt(new Date())
                .build()
        );
        dummyNotes.add(NoteDTO.builder()
                .id("2")
                .content("Testongz")
                .createdAt(new Date())
                .updatedAt(new Date())
                .build()
        );
        return dummyNotes;
    }
}
