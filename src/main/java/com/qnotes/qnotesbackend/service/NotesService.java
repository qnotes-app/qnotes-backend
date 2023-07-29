package com.qnotes.qnotesbackend.service;

import com.qnotes.qnotesbackend.dto.notes.BulkNoteMutationDTO;
import com.qnotes.qnotesbackend.dto.notes.notemutation.CreateNoteDTO;
import com.qnotes.qnotesbackend.dto.notes.notemutation.DeleteNoteDTO;
import com.qnotes.qnotesbackend.dto.notes.notemutation.NoteMutationDTO;
import com.qnotes.qnotesbackend.dto.notes.notemutation.PatchNoteDTO;
import com.qnotes.qnotesbackend.exceptions.NotFoundException;
import com.qnotes.qnotesbackend.models.Group;
import com.qnotes.qnotesbackend.models.Note;
import com.qnotes.qnotesbackend.repository.GroupRepository;
import com.qnotes.qnotesbackend.repository.NoteRepository;
import com.qnotes.qnotesbackend.utils.TimeUtils;
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

    public List<Note> getAllNotes(UUID groupId) {
        Group group = getGroupOrNotFound(groupId);
        return noteRepository.findNotesByGroupOrderByOrder(group);
    }

    public void updateNotes(BulkNoteMutationDTO request) {
        var mutations = request.convertMutations();
        for (NoteMutationDTO mutation: mutations) {
            if (mutation instanceof CreateNoteDTO) {
                createNote((CreateNoteDTO) mutation);
            } else if (mutation instanceof PatchNoteDTO) {
                patchNote((PatchNoteDTO) mutation);
            } else if  (mutation instanceof DeleteNoteDTO) {
                deleteNote((DeleteNoteDTO) mutation);
            }
        }
    }

    private void createNote(CreateNoteDTO request) {
        Group group = getGroupOrNotFound(request.getGroupId());

        int order = getOrder(request.getPreviousNoteId(), group);

        Timestamp curTime = TimeUtils.getCurrentTimestamp();
        Note note = Note.builder()
                .content(request.getContent())
                .createdAt(curTime)
                .updatedAt(curTime)
                .group(group)
                .order(order)
                .build();
        noteRepository.save(note);
    }

    private void patchNote(PatchNoteDTO request) {
        Note note = getNoteOrNotFound(request.getId());

        if (request.getContent() != null) {
            note.setContent(request.getContent());
        }

        if (request.getPreviousNoteId() != null || request.getIsFirst()) {
            int order = getOrder(request.getPreviousNoteId(), note.getGroup());
            note.setOrder(order);
        }

        noteRepository.save(note);
    }

    private void deleteNote(DeleteNoteDTO request) {
        noteRepository.deleteById(request.getId());
    }

    private int getOrder(UUID prevNoteId, Group group) {
        int order;
        if (prevNoteId == null) {
            var firstNote = noteRepository.findFirstByGroupOrderByOrder(group);
            order = firstNote.map(note -> getOrderMinusOne(note, group)).orElse(0);
        } else {
            Note prevNote = getNoteOrNotFound(prevNoteId);
            order = getOrderPlusOne(prevNote, group);
        }
        return order;
    }

    private Note getNoteOrNotFound(UUID id) {
        var note = noteRepository.findById(id);
        if (note.isEmpty()) {
            throw new NotFoundException("Couldn't find note with id " + id);
        }
        return note.get();
    }

    private Group getGroupOrNotFound(UUID id) {
        var group = groupRepository.findById(id);
        if (group.isEmpty()) {
            throw new NotFoundException("Couldn't find group with id " + id);
        }
        return group.get();
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
