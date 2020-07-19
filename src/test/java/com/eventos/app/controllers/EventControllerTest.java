package com.eventos.app.controllers;

import com.eventos.app.common.components.Formatter;
import com.eventos.app.controller.DTO.EventDTO;
import com.eventos.app.controller.DTO.UserDTO;
import com.eventos.app.interfaces.CrudTestInterface;
import com.eventos.app.model.domain.Event;
import com.eventos.app.model.domain.User;
import com.eventos.app.model.service.EventsService;
import com.eventos.app.model.service.UsersService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collection;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJson
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
public class EventControllerTest extends SecuredController implements CrudTestInterface {

    private MockMvc mvc;
    private User user;
    private EventsService eventsService;

    @Autowired
    public EventControllerTest(MockMvc mvc, UsersService usersService, EventsService eventsService) throws Exception {
        super(mvc);
        this.mvc = mvc;
        this.eventsService = eventsService;
        user = usersService.findByEmail("set@localhost");
        user = user == null ? usersService.insert(new UserDTO("set", "set@localhost","123","123")) : user;
    }


    @DisplayName("Can create events through POST request")
    @Test
    @Override
    public void insert() throws Exception {
        String token = "Bearer " + obtainAccessToken("set@localhost","123");
        Assertions.assertNotNull(token);

        String evento = "{\"data\":\"2020-12-12\",\"nome\":\"GOTY 2029\"}";
        mvc.perform(MockMvcRequestBuilders
                .post("/events").content(evento)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Override
    @DisplayName("Can create events with PUT requests on API")
    public void update() throws Exception {
        Collection events = this.eventsService.findByUser(user.getId());
        Assertions.assertTrue(!events.isEmpty());

        String token = "Bearer " + obtainAccessToken("set@localhost","123");
        Assertions.assertNotNull(token);

        String evento = "{\"data\":\"2020-12-12\",\"nome\":\"GOTY 2027\"}";
        mvc.perform(MockMvcRequestBuilders
                .put("/events/{id}",((List<Event>) events).get(0).getId())
                .content(evento).contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Override
    @Test
    @DisplayName("Find event by id")
    public void find() throws Exception {
        Collection events = this.eventsService.findByUser(user.getId());
        Assertions.assertTrue(!events.isEmpty());

        String token = "Bearer " + obtainAccessToken("set@localhost","123");
        Assertions.assertNotNull(token);

        mvc.perform(MockMvcRequestBuilders
                .get("/events/{id}",((List<Event>) events).get(0).getId())
                .header("Authorization", token).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Override
    @Test
    @DisplayName("Delete event by id")
    public void delete() throws Exception {
        Collection events = this.eventsService.list();

        if (!events.isEmpty()) {

            String token = "Bearer " + obtainAccessToken("set@localhost", "123");
            Assertions.assertNotNull(token);

            mvc.perform(MockMvcRequestBuilders
                    .delete("/events/{id}", ((List<Event>) events).get(0).getId())
                    .header("Authorization", token)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }
    }

    @Test
    @Override
    @DisplayName("Can request events list through the API")
    public void list() throws Exception {
        String token = "Bearer " + obtainAccessToken("set@localhost","123");
        Assertions.assertNotNull(token);

        mvc.perform(MockMvcRequestBuilders
                .get("/events")
                .header("Authorization", token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


}
