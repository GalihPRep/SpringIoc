package com.example.layer.layer0controller;

import com.example.layer.layer1service.ServiceBig5;
import com.example.model.response.Success;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/big5")
public class ControllerBig5 {
    private ServiceBig5 serviceBig5;

    @Autowired
    public void setServiceBig5(ServiceBig5 serviceBig5) {
        this.serviceBig5 = serviceBig5;
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(new Success<>("data has been retrieved!",serviceBig5.findAll(true)));
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        return ResponseEntity.ok(new Success<>("data has been retrieved!",serviceBig5.findById(id,true)));
    }
    @GetMapping("/average")
    public ResponseEntity<?> getAverage(){
        return ResponseEntity.ok(new Success<>("data has been retrieved!",serviceBig5.findAverage(true)));
    }
}
