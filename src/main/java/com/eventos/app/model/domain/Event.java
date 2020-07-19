package com.eventos.app.model.domain;

import com.eventos.app.controller.DTO.EventDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    @Id
    private String id;
    private String nome;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date data;
    private String user;
    @Version private Long version;

    public Event(EventDTO eventDTO){
        this.id = eventDTO.getId();
        this.nome = eventDTO.getNome();
        this.data = eventDTO.getData();
        this.user = eventDTO.getUser();
    }
}
