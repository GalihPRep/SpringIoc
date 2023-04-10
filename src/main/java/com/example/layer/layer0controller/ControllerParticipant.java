package com.example.layer.layer0controller;

import com.example.layer.layer1service.ServiceParticipant;
import com.example.model.response.Success;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/participants")
public class ControllerParticipant {
    private ServiceParticipant serviceParticipant;
    @Autowired
    public void setServiceParticipant(ServiceParticipant serviceParticipant) {
        this.serviceParticipant = serviceParticipant;
    }





    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        serviceParticipant.deleteById(id);
        return ResponseEntity.ok(new Success<>("data has been deleted!",null));
    }




    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(new Success<>("data has been retrieved!",serviceParticipant.findAll(true)));
    }
    @GetMapping("/scores")
    public ResponseEntity<?> getAllWithScores(){
        return ResponseEntity.ok(new Success<>("data has been retrieved!",serviceParticipant.findAll(false)));
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        return ResponseEntity.ok(new Success<>("data has been retrieved!",serviceParticipant.findById(id,true)));
    }
    @GetMapping("/id/{id}/scores")
    public ResponseEntity<?> getByIdWithScores(@PathVariable Long id){
        return ResponseEntity.ok(new Success<>("data has been retrieved!",serviceParticipant.findById(id,false)));
    }
    @GetMapping("/name/{name}")
    public ResponseEntity<?> getByNameContaining(@PathVariable String name){
        return ResponseEntity.ok(new Success<>("data has been retrieved!",serviceParticipant.findByNameContaining(name,true)));
    }
    @GetMapping("/name/{name}/scores")
    public ResponseEntity<?> getByNameContainingWithScores(@PathVariable String name){
        return ResponseEntity.ok(new Success<>("data has been retrieved!",serviceParticipant.findByNameContaining(name,false)));
    }
    @GetMapping(value = "/big5", params = {"columnBig5", "scoreBig5", "operation"})
    public ResponseEntity<?> getByBig5Scores(@RequestParam String columnBig5, @RequestParam Double scoreBig5, @RequestParam String operation){
        return ResponseEntity.ok(new Success<>("data has been retrieved!",serviceParticipant.findByBig5Scores(columnBig5,scoreBig5,operation)));
    }
    @GetMapping(value = "/big5", params = {"typeBig5", "operation"})
    public ResponseEntity<?> getByBig5Type(@RequestParam String typeBig5, @RequestParam String operation){
        return ResponseEntity.ok(new Success<>("data has been retrieved!",serviceParticipant.findByBig5Type(typeBig5,operation)));
    }


}
