package com.example.model.dto;

import com.example.model.table.Big5;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class RequestParticipant {
    @NotBlank(message = "name is required!")
    @Size(max = 60, message = "too long!")
    private String name;
    private Date birthdate;
    private Big5 big5;
}
