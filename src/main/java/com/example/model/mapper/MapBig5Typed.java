package com.example.model.mapper;

import com.example.model.dto.ReturnBig5Typed;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MapBig5Typed implements RowMapper<ReturnBig5Typed> {

    @Override
    public ReturnBig5Typed mapRow(ResultSet rs, int rowNum) throws SQLException {
        ReturnBig5Typed table = new ReturnBig5Typed();
        table.setId(rs.getLong("id"));
        table.setOpenness(rs.getDouble("openness"));
        table.setConscientiousness(rs.getDouble("conscientiousness"));
        table.setExtroversion(rs.getDouble("extroversion"));
        table.setAgreeableness(rs.getDouble("agreeableness"));
        table.setNeuroticism(rs.getDouble("neuroticism"));
        table.setType(rs.getString("type"));
        return table;
    }
}
