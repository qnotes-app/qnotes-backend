package com.qnotes.qnotesbackend.dto;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
public class PatchNoteDTO {
    private String id;
    private String content;
    private String previousNote;
    private boolean isFirst;
}
