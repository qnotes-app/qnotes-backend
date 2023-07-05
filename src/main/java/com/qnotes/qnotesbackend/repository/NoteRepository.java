package com.qnotes.qnotesbackend.repository;

import com.qnotes.qnotesbackend.models.Group;
import com.qnotes.qnotesbackend.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NoteRepository extends JpaRepository<Note, UUID> {
    Optional<Note> findFirstByGroupOrderByOrder(Group group);
    List<Note> findNotesByGroupOrderByOrder(Group group);
}
