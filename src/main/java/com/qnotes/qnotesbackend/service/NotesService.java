package com.qnotes.qnotesbackend.service;

import com.qnotes.qnotesbackend.dto.CreateNoteDTO;
import com.qnotes.qnotesbackend.dto.PatchNoteDTO;
import com.qnotes.qnotesbackend.exceptions.NotFoundException;
import com.qnotes.qnotesbackend.models.Group;
import com.qnotes.qnotesbackend.models.Note;
import com.qnotes.qnotesbackend.repository.GroupRepository;
import com.qnotes.qnotesbackend.repository.NoteRepository;
import com.qnotes.qnotesbackend.utils.TimeUtils;
import com.qnotes.qnotesbackend.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service
public class NotesService {
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private GroupRepository groupRepository;

    public List<Note> getAllNotes(String groupIdString) {
        var groupId = UUIDUtils.fromString(groupIdString);
        var group = groupRepository.findById(groupId);
        if (group.isEmpty()) {
            throw new NotFoundException("Couldn't find group with id " + groupId);
        }

        return noteRepository.findNotesByGroupOrderByOrder(group.get());
    }

    public Note createNote(CreateNoteDTO request) {
        var groupId = UUIDUtils.fromString(request.getGroupId());
        var group = groupRepository.findById(groupId);
        if (group.isEmpty()) {
            throw new NotFoundException("Couldn't find group with id " + groupId);
        }

        var prevNoteId = UUIDUtils.fromString(request.getPreviousNote());
        int order = getOrder(prevNoteId, group.get());

        Timestamp curTime = TimeUtils.getCurrentTimestamp();
        Note note = Note.builder()
                .content(request.getContent())
                .createdAt(curTime)
                .updatedAt(curTime)
                .group(group.get())
                .order(order)
                .build();
        return noteRepository.save(note);
    }

    public Note patchNote(PatchNoteDTO request) {
        var noteId = UUIDUtils.fromString(request.getId());
        var noteOpt = noteRepository.findById(noteId);
        if (noteOpt.isEmpty()) {
            throw new NotFoundException("Couldn't find note with id " + noteId);
        }
        Note note = noteOpt.get();

        if (request.getContent() != null) {
            note.setContent(request.getContent());
        }

        if (request.getPreviousNote() != null || request.isFirst()) {
            var prevNoteId = UUIDUtils.fromString(request.getPreviousNote());
            int order = getOrder(prevNoteId, note.getGroup());
            note.setOrder(order);
        }

        return noteRepository.save(note);
    }

    public void deleteNote(String noteIdString) {
        var noteId = UUIDUtils.fromString(noteIdString);
        noteRepository.deleteById(noteId);
    }

    private int getOrder(UUID prevNoteId, Group group) {
        int order;
        if (prevNoteId == null) {
            var firstNote = noteRepository.findFirstByGroupOrderByOrder(group);
            order = firstNote.map(note -> getOrderMinusOne(note, group)).orElse(0);
        } else {
            var prevNote = noteRepository.findById(prevNoteId);
            if (prevNote.isEmpty()) {
                throw new NotFoundException("Couldn't find note with id " + prevNoteId);
            }
            order = getOrderPlusOne(prevNote.get(), group);
        }
        return order;
    }

    private int getOrderMinusOne(Note note, Group group) {
        int order = note.getOrder();
        if ((order - 1) % Note.INITIAL_ORDER_GAP == 0) {
            return reorder(group, note.getId()) - 1;
        }
        return order - 1;
    }

    private int getOrderPlusOne(Note note, Group group) {
        int order = note.getOrder();
        if ((order + 1) % Note.INITIAL_ORDER_GAP == 0) {
            return reorder(group, note.getId()) + 1;
        }
        return order + 1;
    }

    private int reorder(Group group, UUID returnedNoteId) {
        boolean flag = false;
        int ret = -1;
        List<Note> notes = noteRepository.findNotesByGroupOrderByOrder(group);
        int index = 0;
        for (Note note: notes) {
            note.setOrder(index * Note.INITIAL_ORDER_GAP);
            if (note.getId().equals(returnedNoteId)) {
                ret = note.getOrder();
                flag = true;
            }
            index++;
        }
        noteRepository.saveAll(notes);
        if (flag) {
            return ret;
        } else {
            throw new NotFoundException("Couldn't find note with id " + returnedNoteId);
        }
    }
}
