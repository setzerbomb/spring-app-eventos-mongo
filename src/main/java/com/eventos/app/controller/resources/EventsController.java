package com.eventos.app.controller.resources;

import com.eventos.app.common.components.MessageByLocaleService;
import com.eventos.app.common.exceptions.DataException;
import com.eventos.app.common.interfaces.IAuthenticationFacade;
import com.eventos.app.controller.DTO.EventDTO;
import com.eventos.app.controller.DTO.UserDTO;
import com.eventos.app.model.domain.Event;
import com.eventos.app.model.service.EventsService;
import com.eventos.app.model.service.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController()
@RequestMapping(value = "/events")
@AllArgsConstructor
public class EventsController {

    private final UsersService usersService;
    private final EventsService eventsService;
    private final IAuthenticationFacade authenticationFacade;
    private final MessageByLocaleService messageByLocaleService;

    @GetMapping
    public ResponseEntity<Collection<Event>> list(){
        Authentication authentication = authenticationFacade.getAuthentication();
        try {
            return new ResponseEntity<>(eventsService.findByUser(authentication.getName()), HttpStatus.OK);
        } catch (DataException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<String> create(@Valid @RequestBody EventDTO eventDTO, BindingResult error){
        Authentication authentication = authenticationFacade.getAuthentication();
        try{
            if (authentication != null) {
                if (!error.hasErrors()) {
                    eventDTO.setUser(usersService.findByEmail(authentication.getName()).getId());
                    eventsService.insert(eventDTO);
                    return new ResponseEntity<String>(messageByLocaleService.getMessage("event.create.success"), HttpStatus.OK);
                }
                return new ResponseEntity<String>(error.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        catch (DataException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<String> update(@PathVariable String id, @Valid @RequestBody EventDTO eventDTO, BindingResult error){
        Authentication authentication = authenticationFacade.getAuthentication();
        try{
            if (authentication != null) {
                String user = usersService.findByEmail(authentication.getName()).getId();
                if (!error.hasErrors()) {
                    eventDTO.setId(id);
                    eventDTO.setUser(user);
                    eventsService.insert(eventDTO);
                    return new ResponseEntity<String>(messageByLocaleService.getMessage("event.updated.success"), HttpStatus.OK);
                }
                return new ResponseEntity<String>(error.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        catch (DataException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Event> find(@PathVariable String id){
        Authentication authentication = authenticationFacade.getAuthentication();
        if (authentication != null) {
            String user = usersService.findByEmail(authentication.getName()).getId();
            return new ResponseEntity<>(
                    eventsService.findByUserAndId(id,user),HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable String id){
        Authentication authentication = authenticationFacade.getAuthentication();
        if (authentication != null) {
            String user = usersService.findByEmail(authentication.getName()).getId();
            try {
                Event event = eventsService.findByUserAndId(id,user);
                return new ResponseEntity<Boolean>(eventsService.delete(event), HttpStatus.OK);
            } catch (DataException e) {
                return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
    }
}
