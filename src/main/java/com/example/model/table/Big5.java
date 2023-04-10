package com.example.model.table;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("big5")
public class Big5 {
    @Id
    private Long id;
    private Double openness;
    private Double conscientiousness;
    private Double extroversion;
    private Double agreeableness;
    private Double neuroticism;
}
