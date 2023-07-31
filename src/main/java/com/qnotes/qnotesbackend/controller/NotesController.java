package com.qnotes.qnotesbackend.controller;

import com.qnotes.qnotesbackend.dto.MessageDTO;
import com.qnotes.qnotesbackend.dto.notes.BulkNoteMutationDTO;
import com.qnotes.qnotesbackend.dto.notes.NoteDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

    @Operation(summary = "Bulk update notes")
    @PostMapping(produces = "application/json")
    public ResponseEntity<BulkNoteMutationDTO> bulkUpdateNotes(@Valid @RequestBody BulkNoteMutationDTO request) {
        return ResponseEntity.ok(request);
    }

    private List<NoteDTO> getDummyNotes() {
        List<NoteDTO> dummyNotes = new ArrayList<>();
        dummyNotes.add(NoteDTO.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"))
                .content("Testing")
                .createdAt(new Date())
                .updatedAt(new Date())
                .build()
        );
        dummyNotes.add(NoteDTO.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440001"))
                .content("Testongz")
                .createdAt(new Date())
                .updatedAt(new Date())
                .build()
        );
        return dummyNotes;
    }
}
