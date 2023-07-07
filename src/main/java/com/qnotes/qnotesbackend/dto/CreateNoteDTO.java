package com.qnotes.qnotesbackend.dto;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateNoteDTO {
    private String content;
    private UUID previousNoteId;
    private UUID groupId;
}
