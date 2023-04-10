package com.example.model.dto;

import lombok.Data;

@Data
public class ReturnBig5Typed {
    private Long id;
    private Double openness;
    private Double conscientiousness;
    private Double extroversion;
    private Double agreeableness;
    private Double neuroticism;
    private String type;
}
