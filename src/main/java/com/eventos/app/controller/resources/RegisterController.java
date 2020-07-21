package com.eventos.app.controller.resources;

import com.eventos.app.common.components.MessageByLocaleService;
import com.eventos.app.common.exceptions.DataException;
import com.eventos.app.controller.DTO.UserDTO;
import com.eventos.app.model.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<String> create(@Valid @RequestBody UserDTO userDTO, BindingResult error){
        try{
            if (!error.hasErrors()) {
                usersService.insert(userDTO);
                return new ResponseEntity<String>(messageByLocaleService.getMessage("user.create.success"), HttpStatus.OK);
            }
            return new ResponseEntity<String>(error.getFieldError().getDefaultMessage(),HttpStatus.BAD_REQUEST);
        } catch (DuplicateKeyException dpke){
            return new ResponseEntity<String>(messageByLocaleService.getMessage("user.error.duplicated.key"), HttpStatus.BAD_REQUEST);
        }
        catch (DataException e) {
            return new ResponseEntity<String>(messageByLocaleService.getMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

}
