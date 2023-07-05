package com.qnotes.qnotesbackend.dto;

import lombok.*;

import java.util.Date;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
public class NoteDTO {
    private String id;
    private String content;
    private Date createdAt;
    private Date updatedAt;
}
