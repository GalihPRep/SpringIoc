package com.example.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class RequestFill {
    @NotBlank(message = "name is required!")
    @Size(max = 60, message = "too long!")
    private String name;
    private Date birthdate;
    @NotNull(message = "openness is missing!")
    private Double openness;
    @NotNull(message = "conscientiousness is missing!")
    private Double conscientiousness;
    @NotNull(message = "extroversion is missing!")
    private Double extroversion;
    @NotNull(message = "agreeableness is missing!")
    private Double agreeableness;
    @NotNull(message = "neuroticism is missing!")
    private Double neuroticism;
}
