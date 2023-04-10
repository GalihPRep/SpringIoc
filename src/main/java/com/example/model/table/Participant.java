package com.example.model.table;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

@Data
@Table("participants")
public class Participant {
    @Id
    private Long id;
    private String name;
    private Date birthdate;
    private Big5 big5;
}
