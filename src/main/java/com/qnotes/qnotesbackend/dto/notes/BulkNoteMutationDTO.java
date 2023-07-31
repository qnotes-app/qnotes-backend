package com.qnotes.qnotesbackend.dto.notes;

import com.qnotes.qnotesbackend.dto.notes.notemutation.CreateNoteDTO;
import com.qnotes.qnotesbackend.dto.notes.notemutation.DeleteNoteDTO;
import com.qnotes.qnotesbackend.dto.notes.notemutation.NoteMutationDTO;
import com.qnotes.qnotesbackend.dto.notes.notemutation.PatchNoteDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
    @Valid
    @NotNull
    private List<BulkNoteMutationSingleDTO> mutations;

    public List<NoteMutationDTO> convertMutations() {
        List<NoteMutationDTO> newMutations = new ArrayList<>();
        for (var mutation: mutations) {
            if (mutation.getType().equals(BulkNoteMutationType.CREATE)) {
                newMutations.add(new CreateNoteDTO(mutation));
            } else if (mutation.getType().equals(BulkNoteMutationType.PATCH)) {
                newMutations.add(new PatchNoteDTO(mutation));
            } else if (mutation.getType().equals(BulkNoteMutationType.DELETE)) {
                newMutations.add(new DeleteNoteDTO(mutation));
            }
        }
        return newMutations;
    }
}
