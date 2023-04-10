package com.example.layer.layer1service;

import com.example.layer.layer2repository.RepositoryBig5;
import com.example.model.table.Big5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Qualifier("big5")
public class ServiceBig5 implements ServiceBase<Big5> {
    private RepositoryBig5 repositoryBig5;
    @Autowired
    public void setRepositoryBig5(RepositoryBig5 repositoryBig5) {
        this.repositoryBig5 = repositoryBig5;
    }



    @Override
    public Big5 save(Big5 object) {
        Big5 result = repositoryBig5.save(object);
        if(result == null){
            throw new RuntimeException("data fails to be saved!");
        }
        return result;
    }

    @Override
    public List<Big5> saveAll(List<Big5> objects) {return repositoryBig5.saveAll(objects);}
    @Override
    public Big5 update(Big5 object, Long id) {
        Big5 result = repositoryBig5.update(object,id);
        if(result == null){
            throw new RuntimeException("data fails to be saved!");
        }
        return result;
    }

    @Override
    public void deleteById(Long id) {
        int result = repositoryBig5.deleteById(id);
        if(result <= 0){
            throw new RuntimeException("data fails to be deleted!");
        }
    }


    public List<?> findAll(boolean withType) {return repositoryBig5.findAll(withType);}
    public Optional<?> findById(Long id, boolean withType) {return repositoryBig5.findById(id,withType);}



    public Optional<?> findAverage(boolean withType) {
        return repositoryBig5.findAverage(withType);
    }
}
