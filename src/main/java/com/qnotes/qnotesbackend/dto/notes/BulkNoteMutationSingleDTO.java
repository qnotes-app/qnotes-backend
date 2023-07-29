package com.qnotes.qnotesbackend.dto.notes;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BulkNoteMutationSingleDTO {
    BulkNoteMutationType type;
    private UUID id;
    private UUID groupId;
    private String content;
    private UUID previousNoteId;
    private Boolean isFirst;
}
