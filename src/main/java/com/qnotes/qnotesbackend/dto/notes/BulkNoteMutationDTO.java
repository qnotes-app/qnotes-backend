package com.qnotes.qnotesbackend.dto.notes;

import com.qnotes.qnotesbackend.dto.notes.notemutation.CreateNoteDTO;
import com.qnotes.qnotesbackend.dto.notes.notemutation.DeleteNoteDTO;
import com.qnotes.qnotesbackend.dto.notes.notemutation.NoteMutationDTO;
import com.qnotes.qnotesbackend.dto.notes.notemutation.PatchNoteDTO;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BulkNoteMutationDTO {
    private List<BulkNoteMutationSingleDTO> mutations;

    public List<NoteMutationDTO> convertMutations() {
        List<NoteMutationDTO> newMutations = new ArrayList<>();
        for (var mutation: mutations) {
            if (mutation.type.equals(BulkNoteMutationType.CREATE)) {
                newMutations.add(new CreateNoteDTO(mutation));
            } else if (mutation.type.equals(BulkNoteMutationType.PATCH)) {
                newMutations.add(new PatchNoteDTO(mutation));
            } else if (mutation.type.equals(BulkNoteMutationType.DELETE)) {
                newMutations.add(new DeleteNoteDTO(mutation));
            }
        }
        return newMutations;
    }
}
