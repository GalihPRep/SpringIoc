package com.example.model.mapper;

import com.example.model.dto.ReturnParticipantTyped;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MapParticipantTyped implements RowMapper<ReturnParticipantTyped> {

    public MapParticipantTyped() {
    }

    @Override
    public ReturnParticipantTyped mapRow(ResultSet rs, int rowNum) throws SQLException {
        ReturnParticipantTyped table = new ReturnParticipantTyped();
        table.setId(rs.getLong("id"));
        table.setName(rs.getString("name"));
        table.setBirthdate(rs.getDate("birthdate"));
        table.setType(rs.getString("type"));
        return table;
    }
}
