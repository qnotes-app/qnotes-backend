package com.qnotes.qnotesbackend.dto;

import lombok.*;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NoteDTO {
    private UUID id;
    private String content;
    private Date createdAt;
    private Date updatedAt;
}
