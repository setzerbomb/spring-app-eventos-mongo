package com.eventos.app.controller.DTO;

import com.eventos.app.controller.DTO.common.BaseDTO;
import com.eventos.app.model.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO extends BaseDTO {

    @NotEmpty(message = "{user.name.error.empty}")
    @Length(max = 255, message = "{user.name.error.max}")
    private String nome;
    @Email(message = "{user.email.error.valid}")
    @NotEmpty(message = "{user.email.error.empty}")
    @Length(max = 128, message = "{user.email.error.max}")
    private String email;
    @NotEmpty(message = "{user.password.error.empty}")
    @Length(max = 255, message = "{user.password.error.max}")
    private String senha;
    @NotEmpty(message = "{user.passwordConfirmation.error.empty}")
    @Length(max = 255, message = "{user.passwordConfirmation.error.max}")
    private String confirmacaoSenha;

    public UserDTO(User user){
        this.id = user.getId();
        this.nome = user.getNome();
        this.email = user.getEmail();
    }
}
