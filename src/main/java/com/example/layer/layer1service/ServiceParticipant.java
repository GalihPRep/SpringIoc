package com.example.layer.layer1service;

import com.example.layer.layer2repository.RepositoryParticipant;
import com.example.model.dto.ReturnParticipantTyped;
import com.example.model.table.Participant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Qualifier("participant")
public class ServiceParticipant implements ServiceBase<Participant> {
    private RepositoryParticipant repositoryParticipant;
    @Autowired
    public void setRepositoryParticipant(RepositoryParticipant repositoryParticipant) {
        this.repositoryParticipant = repositoryParticipant;
    }



    @Override
    public Participant save(Participant object) {
        Participant result = repositoryParticipant.save(object);
        if(result == null){
            throw new RuntimeException("data fails to be saved");
        }
        return result;
    }
    @Override
    public List<Participant> saveAll(List<Participant> objects) {return repositoryParticipant.saveAll(objects);}
    @Override
    public Participant update(Participant object, Long id) {
        Participant result = repositoryParticipant.update(object, id);
        if(result == null){
            throw new RuntimeException("data fails to be saved");
        }
        return result;
    }
    @Override
    public void deleteById(Long id) {
        int result = repositoryParticipant.deleteById(id);
        if(result <= 0){
            throw new RuntimeException("data fails to be saved");
        }
    }








    public List<?> findAll(boolean withType) {
        return repositoryParticipant.findAll(withType);
    }
    public Optional<?> findById(Long id, boolean withType) {return repositoryParticipant.findById(id, withType);}
    public Optional<List<?>> findByNameContaining(String name, boolean withType) {return repositoryParticipant.findByNameContaining(name,withType);}
    public Optional<List<Participant>> findByBig5Scores(String columnBig5, Double scoreBig5, String operation){
        return repositoryParticipant.findByBig5Scores(columnBig5, scoreBig5, operation);
    }
    public Optional<List<ReturnParticipantTyped>> findByBig5Type(String typeBig5, String operation){
        return repositoryParticipant.findByBig5Type(typeBig5,operation);
    }
}
