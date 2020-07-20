package com.eventos.app.model.domain;

import com.eventos.app.controller.DTO.EventDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Event {

    @Id
    private String id;
    private String nome;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date data;
    private String user;
    @JsonIgnore
    @Version private Long version;

    public Event(EventDTO eventDTO){
        this.id = eventDTO.getId();
        this.nome = eventDTO.getNome();
        this.data = eventDTO.getData();
        this.user = eventDTO.getUser();
    }
}
