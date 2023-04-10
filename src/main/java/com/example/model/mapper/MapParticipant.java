package com.example.model.mapper;

import com.example.model.table.Big5;
import com.example.model.table.Participant;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class MapParticipant implements RowMapper<Participant> {
    private final JdbcTemplate jdbcTemplate;

    public MapParticipant(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Participant mapRow(ResultSet rs, int rowNum) throws SQLException {
        Participant table = new Participant();
        table.setId(rs.getLong("id"));
        table.setName(rs.getString("name"));
        table.setBirthdate(rs.getDate("birthdate"));
        long big5Id = rs.getLong("big5_id");
        if (big5Id > 0) {
            String SQL = "SELECT * FROM big5 WHERE id = ?";
            Big5 big5 = jdbcTemplate.queryForObject(SQL, new Object[]{big5Id}, new MapBig5());
            table.setBig5(big5);
        }
        return table;
    }
}
