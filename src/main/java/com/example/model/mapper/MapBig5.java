package com.example.model.mapper;

import com.example.model.table.Big5;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MapBig5 implements RowMapper<Big5> {

    @Override
    public Big5 mapRow(ResultSet rs, int rowNum) throws SQLException {
        Big5 table = new Big5();
        table.setId(rs.getLong("id"));
        table.setOpenness(rs.getDouble("openness"));
        table.setConscientiousness(rs.getDouble("conscientiousness"));
        table.setExtroversion(rs.getDouble("extroversion"));
        table.setAgreeableness(rs.getDouble("agreeableness"));
        table.setNeuroticism(rs.getDouble("neuroticism"));
        return table;
    }
}
