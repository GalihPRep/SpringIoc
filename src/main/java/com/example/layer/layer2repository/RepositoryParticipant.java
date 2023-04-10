package com.example.layer.layer2repository;

import com.example.model.dto.ReturnParticipantTyped;
import com.example.model.mapper.MapParticipant;
import com.example.model.mapper.MapParticipantTyped;
import com.example.model.table.Participant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class RepositoryParticipant implements RepositoryBase<Participant> {
    private JdbcTemplate jdbcTemplate;
    private final String type = "CASE WHEN c.openness > 1/3 THEN 'O' WHEN c.openness < 1/3 THEN 'F' ELSE 'S' END || "+
            "CASE WHEN c.conscientiousness > 1/3 THEN 'C' WHEN c.conscientiousness < 1/3 THEN 'L' ELSE 'M' END || "+
            "CASE WHEN c.extroversion > 1/3 THEN 'E' WHEN c.extroversion < 1/3 THEN 'I' ELSE 'U' END || "+
            "CASE WHEN c.agreeableness > 1/3 THEN 'A' WHEN c.agreeableness < 1/3 THEN 'R' ELSE 'G' END || "+
            "CASE WHEN c.neuroticism > 1/3 THEN 'N' WHEN c.neuroticism < 1/3 THEN 'T' ELSE 'Y' END AS type";
    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Participant save(Participant object) {
        String SQL = "INSERT INTO participants (name,birthdate,big5_id) VALUES (?,?,?) RETURNING id";
        Long id = jdbcTemplate.queryForObject(SQL, Long.class,object.getName(),object.getBirthdate(),object.getBig5().getId());
        return (Participant) findById(id,false).orElse(null);
    }

    @Override
    public List<Participant> saveAll(List<Participant> objects) {
        List<Participant> result = new ArrayList<>();
        for(Participant i: objects){
            result.add(save(i));
        }
        return result;
    }
    @Override
    public Participant update(Participant object, Long id) {
        String SQL = "UPDATE participants SET name = ?, birthdate = ?, big5_id = ? WHERE id = ? RETURNING id";
        Long idUpdated = jdbcTemplate.queryForObject(SQL, Long.class,object.getName(),object.getBirthdate(),object.getBig5().getId(),id);
        return (Participant) findById(idUpdated,false).orElse(null);
    }
    @Override
    public int deleteById(Long id) {
        String SQL = "DELETE FROM participants WHERE id = ?";
        return jdbcTemplate.update(SQL,id);
    }





    public List<?> findAll(boolean withType) {
        String SQL = "SELECT * FROM participants";
        List<?> result = jdbcTemplate.query(SQL, new MapParticipant(jdbcTemplate))
                .stream()
                .sorted(Comparator.comparing(Participant::getName))
                .collect(Collectors.toList());
        if (withType) {
            SQL = "SELECT a.id, a.name, a.birthdate, b.type FROM participants AS a INNER JOIN ("+
                    "SELECT c.id, "+type+" FROM big5 c) AS b ON a.big5_id = b.id";
            result = jdbcTemplate.query(SQL,new MapParticipantTyped())
                    .stream()
                    .sorted(Comparator.comparing(ReturnParticipantTyped::getName))
                    .collect(Collectors.toList());
        }
        return result;
    }

    public Optional<?> findById(Long id, boolean withType) {
        String SQL = "SELECT * FROM participants WHERE id = ?";
        Optional<?> result = Optional.ofNullable(jdbcTemplate.queryForObject(SQL,new MapParticipant(jdbcTemplate),id));
        if(withType){
            SQL = "SELECT a.id, a.name, a.birthdate, b.type FROM participants AS a INNER JOIN ("+
                    "SELECT c.id, "+type+" FROM big5 c) AS b ON a.big5_id = b.id WHERE a.id = ?";
            result = Optional.ofNullable(jdbcTemplate.queryForObject(SQL,new MapParticipantTyped(),id));
        }
        return result;
    }

    public Optional<List<?>> findByNameContaining(String name, boolean withType) {
        String SQL = "SELECT * FROM participants WHERE name ILIKE ?";
        Optional<List<?>> result = Optional.of(
                jdbcTemplate.query(SQL, new MapParticipant(jdbcTemplate), "%"+name+"%")
                        .stream()
                        .sorted(Comparator.comparing(Participant::getName))
                        .collect(Collectors.toList())
        );
        if(withType){
            SQL = "SELECT a.id, a.name, a.birthdate, b.type FROM participants AS a INNER JOIN ("+
                    "SELECT c.id, "+type+" FROM big5 c) AS b ON a.big5_id = b.id WHERE a.name ILIKE ?";
            result = Optional.of(
                    jdbcTemplate.query(SQL, new MapParticipantTyped(), "%"+name+"%")
                            .stream()
                            .sorted(Comparator.comparing(ReturnParticipantTyped::getName))
                            .collect(Collectors.toList())
            );
        }
        return result;
    }
    public Optional<List<Participant>> findByBig5Scores(String columnBig5, Double scoreBig5, String operation){
        String SQL = "SELECT a.id, a.name, a.birthdate, a.big5_id FROM participants a " +
                "INNER JOIN big5 b ON a.big5_id = b.id WHERE "+columnBig5+" "+operation+" ?";
        return Optional.of(
                jdbcTemplate.query(SQL,new MapParticipant(jdbcTemplate),scoreBig5)
                        .stream()
                        .sorted(Comparator.comparing(Participant::getName))
                        .collect(Collectors.toList())
        );
    }
    public Optional<List<ReturnParticipantTyped>> findByBig5Type(String typeBig5, String operation){
        String SQL = "SELECT a.id, a.name, a.birthdate, b.type FROM participants AS a INNER JOIN ("+
                "SELECT c.id, "+type+" FROM big5 c) AS b ON a.big5_id = b.id WHERE b.type "+operation+" ?";
        return Optional.of(
                jdbcTemplate.query(SQL,new MapParticipantTyped(),"%"+typeBig5+"%")
                        .stream()
                        .sorted(Comparator.comparing(ReturnParticipantTyped::getName))
                        .collect(Collectors.toList())
        );
    }
}
