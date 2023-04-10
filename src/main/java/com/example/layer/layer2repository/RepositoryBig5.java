package com.example.layer.layer2repository;

import com.example.model.dto.RequestBig5;
import com.example.model.mapper.MapBig5;
import com.example.model.mapper.MapBig5Typed;
import com.example.model.table.Big5;
import com.example.model.dto.ReturnBig5Typed;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class RepositoryBig5 implements RepositoryBase<Big5> {
    private JdbcTemplate jdbcTemplate;
    private ModelMapper modelMapper;
    private final String type = "CASE WHEN c.openness > 1/3 THEN 'O' WHEN c.openness < 1/3 THEN 'F' ELSE 'S' END || "+
            "CASE WHEN c.conscientiousness > 1/3 THEN 'C' WHEN c.conscientiousness < 1/3 THEN 'L' ELSE 'M' END || "+
            "CASE WHEN c.extroversion > 1/3 THEN 'E' WHEN c.extroversion < 1/3 THEN 'I' ELSE 'U' END || "+
            "CASE WHEN c.agreeableness > 1/3 THEN 'A' WHEN c.agreeableness < 1/3 THEN 'R' ELSE 'G' END || "+
            "CASE WHEN c.neuroticism > 1/3 THEN 'N' WHEN c.neuroticism < 1/3 THEN 'T' ELSE 'Y' END AS type";
    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }





    @Override
    public Big5 save(Big5 object) {
        Optional<Big5> objectExisting = findByScores(object);
        if (objectExisting.isPresent()) {
            return objectExisting.get();
        }
        String SQL = "INSERT INTO " +
                "big5 (openness, conscientiousness, extroversion, agreeableness, neuroticism) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING id";
        Long id = jdbcTemplate.queryForObject(SQL, Long.class,
                object.getOpenness(), object.getConscientiousness(),
                object.getExtroversion(), object.getAgreeableness(),
                object.getNeuroticism());
        return (Big5) findById(id,false).orElse(null);
    }

    @Override
    public List<Big5> saveAll(List<Big5> objects) {
        List<Big5> result = new ArrayList<>();
        for(Big5 i: objects){
            result.add(save(i));
        }
        return result;
    }
    @Override
    public Big5 update(Big5 object, Long id) {
        String SQL = "UPDATE big5 " +
                "SET openness = ?, conscientiousness = ?, extroversion = ?, agreeableness = ?, neuroticism = ? " +
                "WHERE id = ? RETURNING id";
        id = jdbcTemplate.queryForObject(
                SQL, Long.class,
                object.getOpenness(),
                object.getConscientiousness(),
                object.getExtroversion(),
                object.getAgreeableness(),
                object.getNeuroticism(),
                id
        );
        return (Big5) findById(id,false).orElse(null);
    }
    @Override
    public int deleteById(Long id) {
        String SQL = "DELETE FROM big5 WHERE id = ?";
        return jdbcTemplate.update(SQL,id);
    }









   public List<?> findAll(boolean withType) {
        String SQL = "SELECT * FROM big5";
        List <?> result = jdbcTemplate.query(SQL,new MapBig5());
        if(withType){
            SQL = "SELECT *, "+type+" FROM big5 c";
            result = jdbcTemplate.query(SQL,new MapBig5Typed())
                    .stream()
                    .sorted(
                            Comparator.comparing(
                                    ReturnBig5Typed::getOpenness)
                                    .thenComparing(ReturnBig5Typed::getConscientiousness)
                                    .thenComparing(ReturnBig5Typed::getExtroversion)
                                    .thenComparing(ReturnBig5Typed::getAgreeableness)
                                    .thenComparing(ReturnBig5Typed::getNeuroticism)
                    )
                    .collect(Collectors.toList());
        }
        return result;
    }
    public Optional<?> findById(Long id, boolean withType) {
        String SQL = "SELECT * FROM big5 WHERE id = ?";
        Optional<?> result = Optional.ofNullable(jdbcTemplate.queryForObject(SQL,new MapBig5(),id));
        if(withType){
            SQL = "SELECT *, "+type+" FROM big5 c WHERE id = ?";
            result = Optional.ofNullable(jdbcTemplate.queryForObject(SQL,new MapBig5Typed(),id));
        }
        return result;
    }
    public Optional<Big5> findByScores(Big5 object) {
        String SQL = "SELECT * FROM big5 WHERE " +
                "openness = ? AND " +
                "conscientiousness = ? AND " +
                "extroversion = ? AND " +
                "agreeableness = ? AND " +
                "neuroticism = ?";
        List<Big5> result = jdbcTemplate.query(SQL, new MapBig5(),
                object.getOpenness(),
                object.getConscientiousness(),
                object.getExtroversion(),
                object.getAgreeableness(),
                object.getNeuroticism()
        );
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }









    public Optional<?> findAverage(boolean withType){
        String SQL = "SELECT "+
                "AVG(a.openness) AS openness, "+
                "AVG(a.conscientiousness) AS conscientiousness, "+
                "AVG(a.extroversion) AS extroversion, "+
                "AVG(a.agreeableness) AS agreeableness, "+
                "AVG(a.neuroticism) AS neuroticism "+
                "FROM big5 AS a "+
                "INNER JOIN participants AS b "+
                "ON a.id = b.big5_id";
        Optional<?> result = Optional.ofNullable(
                modelMapper.map(
                        jdbcTemplate.queryForObject(SQL, new BeanPropertyRowMapper<>(RequestBig5.class)),
                        Big5.class
                )
        );
        if(withType){
            SQL = "SELECT *, "+
                    type+" "+
                    "FROM ("+
                    "SELECT "+
                    "AVG(a.openness) AS openness, "+
                    "AVG(a.conscientiousness) AS conscientiousness, "+
                    "AVG(a.extroversion) AS extroversion, "+
                    "AVG(a.agreeableness) AS agreeableness, "+
                    "AVG(a.neuroticism) AS neuroticism "+
                    "FROM big5 AS a "+
                    "INNER JOIN participants AS b "+
                    "ON a.id = b.big5_id" +
                    ") AS c";
            result = Optional.ofNullable(
                    modelMapper.map(
                            jdbcTemplate.queryForObject(SQL, new BeanPropertyRowMapper<>(ReturnBig5Typed.class)),
                            ReturnBig5Typed.class
                    )
            );
        }
        return result;
    }

}
