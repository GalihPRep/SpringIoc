package com.example.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ReturnParticipantTyped {
    private Long id;
    private String name;
    private Date birthdate;
    private String type;
}
