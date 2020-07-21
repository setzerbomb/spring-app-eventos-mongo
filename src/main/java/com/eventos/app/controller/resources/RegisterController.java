package com.eventos.app.controller.resources;

import com.eventos.app.common.components.MessageByLocaleService;
import com.eventos.app.common.exceptions.DataException;
import com.eventos.app.controller.DTO.UserDTO;
import com.eventos.app.model.service.UsersService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
public class RegisterController {

    private UsersService usersService;
    private MessageByLocaleService messageByLocaleService;

    @Autowired
    public RegisterController(UsersService usersService, MessageByLocaleService messageByLocaleService) {
        this.usersService = usersService;
        this.messageByLocaleService = messageByLocaleService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> create(@Valid @RequestBody UserDTO userDTO, BindingResult error)  throws ResponseStatusException {
        try{
            if (!error.hasErrors()) {
                usersService.insert(userDTO);
                return new ResponseEntity<String>(messageByLocaleService.getMessage("user.create.success"), HttpStatus.OK);
            }
            return new ResponseEntity<String>(error.getFieldError().getDefaultMessage(),HttpStatus.BAD_REQUEST);
        } catch (DuplicateKeyException dpke){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,messageByLocaleService.getMessage("user.error.duplicated.key"));
        }
        catch (DataException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,messageByLocaleService.getMessage(e.getMessage()));
        }
    }

}
