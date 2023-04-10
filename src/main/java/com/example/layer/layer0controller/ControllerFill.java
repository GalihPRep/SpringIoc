package com.example.layer.layer0controller;

import com.example.layer.layer1service.ServiceBase;
import com.example.model.dto.RequestFill;
import com.example.model.response.Success;
import com.example.model.table.Big5;
import com.example.model.table.Participant;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fill")
public class ControllerFill {
    private ServiceBase<Big5> serviceBig5;
    private ServiceBase<Participant> serviceParticipant;
    private ModelMapper modelMapper;

    @Autowired
    @Qualifier("big5")
    public void setServiceBig5(ServiceBase<Big5> serviceBig5) {
        this.serviceBig5 = serviceBig5;
    }
    @Autowired
    @Qualifier("participant")
    public void setServiceParticipant(ServiceBase<Participant> serviceParticipant) {
        this.serviceParticipant = serviceParticipant;
    }
    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }




    @PostMapping
    public ResponseEntity<?> post(@Valid @RequestBody RequestFill request){
        Participant participant = modelMapper.map(request,Participant.class);
        Big5 big5 = modelMapper.map(request, Big5.class);
        Big5 big5Id = serviceBig5.save(big5);
        participant.setBig5(big5Id);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Success<>("data has been added!", serviceParticipant.save(participant)));
    }
    @PostMapping(path = "/bulk", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> post(@RequestBody List<@Valid RequestFill> request){
        List<Participant> participants = request.stream().map(
                element -> {
                    Participant participant = modelMapper.map(element,Participant.class);
                    Big5 big5 = modelMapper.map(element, Big5.class);
                    Big5 big5Id = serviceBig5.save(big5);
                    participant.setBig5(big5Id);
                    return participant;
                }
        ).toList();
        return ResponseEntity.status(HttpStatus.CREATED).body(new Success<>("data has been added!", serviceParticipant.saveAll(participants)));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> put(@Valid @RequestBody RequestFill request, @PathVariable Long id){
        Participant participant = modelMapper.map(request,Participant.class);
        Big5 big5 = modelMapper.map(request, Big5.class);
        Big5 big5Id = serviceBig5.save(big5);
        participant.setBig5(big5Id);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Success<>("data has been added!", serviceParticipant.update(participant,id)));
    }
}
