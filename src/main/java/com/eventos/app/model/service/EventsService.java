package com.eventos.app.model.service;

import com.eventos.app.common.exceptions.DataException;
import com.eventos.app.controller.DTO.EventDTO;
import com.eventos.app.model.domain.Event;
import com.eventos.app.model.repositories.EventsRepository;
import com.eventos.app.model.service.common.GenericDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class EventsService extends GenericDataService<Event, EventDTO> {
    private EventsRepository eventsRepository;
    private UsersService usersService;

    @Autowired
    public EventsService(EventsRepository eventsRepository, UsersService usersService) {
        super(eventsRepository);
        this.eventsRepository = eventsRepository;
        this.usersService = usersService;
    }

    @Override
    public Event insert(EventDTO eventDTO) throws DuplicateKeyException, DataException {
        if (usersService.find(eventDTO.getUser()) == null){
            throw new DataException("user.not.found");
        }
        return eventsRepository.insert(new Event(eventDTO));
    }

    @Override
    public Event update(EventDTO eventDTO) throws DataException {
        if(eventDTO.getId() != null) {
            Event event = this.find(eventDTO.getId());
            if(event != null) {
                event.setNome(eventDTO.getNome());
                event.setData(eventDTO.getData());
                eventsRepository.save(event);
                return event;
            }
        }
        throw new DataException("event.not.fount");
    }

    public Collection<Event> findByUser(String user) throws DataException{
        return eventsRepository.findByUser(user);
    }

    public Event findByUserAndId(String user, String id) {
        return eventsRepository.findByUserAndId(user,id);
    }

}
