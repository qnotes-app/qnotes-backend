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
public class DeleteNoteDTO implements NoteMutationDTO {
    private UUID id;

    public DeleteNoteDTO(BulkNoteMutationSingleDTO bulk) {
        this.id = bulk.getId();
    }
}
