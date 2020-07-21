package com.eventos.app.controller.resources;

import com.eventos.app.common.components.MessageByLocaleService;
import com.eventos.app.common.exceptions.DataException;
import com.eventos.app.common.interfaces.IAuthenticationFacade;
import com.eventos.app.controller.DTO.EventDTO;
import com.eventos.app.model.domain.Event;
import com.eventos.app.model.service.EventsService;
import com.eventos.app.model.service.UsersService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.Collection;

@RestController()
@RequestMapping(value = "/events")
@AllArgsConstructor
@Log4j2
public class EventsController {

    private final UsersService usersService;
    private final EventsService eventsService;
    private final IAuthenticationFacade authenticationFacade;
    private final MessageByLocaleService messageByLocaleService;

    @GetMapping
    public ResponseEntity<Collection<Event>> list(){
        Authentication authentication = authenticationFacade.getAuthentication();
        if (authentication != null) {
            String user = usersService.findByEmail(authentication.getName()).getId();
            return new ResponseEntity<>(eventsService.findByUser(user), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Event> create(@Valid @RequestBody EventDTO eventDTO, BindingResult error) throws ResponseStatusException {
        Authentication authentication = authenticationFacade.getAuthentication();
        if (authentication != null) {
            if (!error.hasErrors()) {
                eventDTO.setUser(usersService.findByEmail(authentication.getName()).getId());
                try {
                    return new ResponseEntity<>(eventsService.insert(eventDTO),  HttpStatus.OK);
                } catch (DataException e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,messageByLocaleService.getMessage(e.getMessage()));
                }
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,error.getFieldError().getDefaultMessage());
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Event> update(@PathVariable String id, @Valid @RequestBody EventDTO eventDTO, BindingResult error) throws ResponseStatusException {
        Authentication authentication = authenticationFacade.getAuthentication();
        if (authentication != null) {
            String user = usersService.findByEmail(authentication.getName()).getId();
            if (!error.hasErrors()) {
                eventDTO.setId(id);
                eventDTO.setUser(user);
                try {
                    return new ResponseEntity<>(eventsService.update(eventDTO), HttpStatus.OK);
                } catch (DataException e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,messageByLocaleService.getMessage(e.getMessage()));
                }
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,error.getFieldError().getDefaultMessage());
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Event> find(@PathVariable String id) {
        Authentication authentication = authenticationFacade.getAuthentication();
        if (authentication != null) {
            String user = usersService.findByEmail(authentication.getName()).getId();
            return new ResponseEntity<>(
                    eventsService.findByUserAndId(user,id),HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) throws DataException {
        Authentication authentication = authenticationFacade.getAuthentication();
        if (authentication != null) {
            String user = usersService.findByEmail(authentication.getName()).getId();
            Event event = eventsService.findByUserAndId(user,id);
            if (eventsService.delete(event)) {
                return new ResponseEntity<>(null, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
