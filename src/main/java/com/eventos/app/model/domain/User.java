package com.eventos.app.model.domain;

import com.eventos.app.controller.DTO.UserDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class User {

    @Id
    private String id;
    private String nome;
    @JsonIgnore
    @Indexed(name="email_index" ,unique=true, direction = IndexDirection.ASCENDING)
    private String email;
    private @JsonIgnore String senha;
    @JsonIgnore
    @Version
    private Long version;

    public User(UserDTO userDTO) {
        this.id = userDTO.getId();
        this.senha = new BCryptPasswordEncoder().encode(userDTO.getSenha());
        this.nome = userDTO.getNome();
        this.email = userDTO.getEmail();
    }
}
