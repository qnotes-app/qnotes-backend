package com.qnotes.qnotesbackend.dto;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatchNoteDTO {
    private UUID id;
    private String content;
    private UUID previousNoteId;
    private boolean isFirst;
}
