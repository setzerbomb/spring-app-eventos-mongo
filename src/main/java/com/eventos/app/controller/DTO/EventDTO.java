package com.eventos.app.controller.DTO;

import com.eventos.app.common.components.Formatter;
import com.eventos.app.controller.DTO.common.BaseDTO;
import com.eventos.app.model.domain.Event;
import com.eventos.app.model.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventDTO extends BaseDTO {

    @NotEmpty(message = "{event.name.error.empty}")
    @Length(max = 255, message = "{event.name.error.max}")
    private String nome;
    @NotNull(message = "{event.date.error.null}")
    private Date data;
    @Length(max = 255, message = "{event.user.error.max}")
    private String user;

    public EventDTO(Event event){
        this.id = event.getId();
        this.nome = event.getNome();
        this.data = event.getData();
        this.user = event.getUser();
    }

    public void setData(String date) {
        this.data = Formatter.StrToDate(date);
    }
}
