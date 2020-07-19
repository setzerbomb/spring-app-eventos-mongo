package com.eventos.app.services;

import com.eventos.app.interfaces.CrudTestInterface;
import com.eventos.app.common.components.Formatter;
import com.eventos.app.common.exceptions.DataException;
import com.eventos.app.controller.DTO.EventDTO;
import com.eventos.app.controller.DTO.UserDTO;
import com.eventos.app.model.domain.Event;
import com.eventos.app.model.domain.User;
import com.eventos.app.model.service.EventsService;
import com.eventos.app.model.service.UsersService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;
import java.util.List;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@ComponentScan(value = {"com.eventos.app.model"})
public class EventServiceTest implements CrudTestInterface {

    private EventsService eventsService;
    private User user;

    @Autowired
    public EventServiceTest(EventsService eventsService,UsersService usersService, MongoTemplate mongoTemplate) throws DataException {
        this.eventsService = eventsService;
        user = usersService.findByEmail("set@localhost");
        user = user == null ? usersService.insert(new UserDTO("set", "set@localhost","123","123")) : user;
    }

    @Override
    @Test
    @DisplayName("Can insert events into the database")
    public void insert() throws Exception{
        Assertions.assertNotNull(eventsService.insert(new EventDTO("GOTY 2020", Formatter.StrToDate("2020-12-12"),user.getId())));
    }

    @Test
    @DisplayName("Tries to insert with empty user id")
    public void triesToInsertWithEmptyUser() {
        Assertions.assertThrows(
                DataException.class,
                () -> {eventsService.insert(new EventDTO("GOTY 2020",Formatter.StrToDate("2020-12-12"),""));}
        );
    }

    @Override
    @Test
    @DisplayName("Can update an event")
    public void update() throws Exception{
        List<Event> events = ((List) eventsService.list());
        Assertions.assertTrue(!events.isEmpty());
        EventDTO eventDTO = new EventDTO(eventsService.find(events.get(0).getId()));
        eventDTO.setNome("GOTY 2021");
        Event event = eventsService.update(eventDTO);
        Assertions.assertTrue(event.getNome().equals("GOTY 2021"));
    }

    @Test
    @Override
    @DisplayName("Can find events by user")
    public void find() throws Exception{
        Assertions.assertNotNull(eventsService.findByUser(user.getId()));
    }

    @Test
    @DisplayName("Can find event by user and event")
    public void findByUserAndEvent() throws Exception{
        List<Event> events = ((List) eventsService.list());
        Assertions.assertTrue(!events.isEmpty());
        Assertions.assertNotNull(eventsService.findByUserAndId(user.getId(), events.get(0).getId()));
    }

    @Test
    @DisplayName("Can delete events from database")
    @Override
    public void delete() throws Exception{
        Collection<Event> events = eventsService.list();
        Assertions.assertTrue(!events.isEmpty());
        for (Event event: events) {
            eventsService.delete(event);
        }
    }

    @Test
    @DisplayName("Can list events from the database")
    @Override
    public void list() throws Exception{
        Assertions.assertTrue(!eventsService.list().isEmpty());
    }

}