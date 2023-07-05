package com.qnotes.qnotesbackend.dto;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
public class CreateNoteDTO {
    private String content;
    private String previousNote;
    private String groupId;
}
