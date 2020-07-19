package com.eventos.app.controller.DTO.common;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;

@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseDTO {
    @Max(message = "Id não pode passar de 128 carácteres",value = 128)
    protected String id;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

}
