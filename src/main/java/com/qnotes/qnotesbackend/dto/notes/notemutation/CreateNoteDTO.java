package com.qnotes.qnotesbackend.dto.notes.notemutation;

import com.qnotes.qnotesbackend.dto.notes.BulkNoteMutationSingleDTO;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateNoteDTO implements NoteMutationDTO {
    private String content;
    private UUID previousNoteId;
    private UUID groupId;

    public CreateNoteDTO(BulkNoteMutationSingleDTO bulk) {
        this.content = bulk.getContent();
        this.previousNoteId = bulk.getPreviousNoteId();
        this.groupId = bulk.getGroupId();
    }
}
