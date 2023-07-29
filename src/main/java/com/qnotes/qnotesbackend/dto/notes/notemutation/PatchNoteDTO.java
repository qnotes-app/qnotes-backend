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
public class PatchNoteDTO implements NoteMutationDTO {
    private UUID id;
    private String content;
    private UUID previousNoteId;
    private Boolean isFirst;

    public PatchNoteDTO(BulkNoteMutationSingleDTO bulk) {
        this.id = bulk.getId();
        this.content = bulk.getContent();
        this.previousNoteId = bulk.getPreviousNoteId();
        this.isFirst = bulk.getIsFirst();
    }
}
