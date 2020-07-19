package com.eventos.app.services;

import com.eventos.app.controller.DTO.UserDTO;
import com.eventos.app.model.domain.User;
import com.eventos.app.model.service.UsersService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DuplicateKeyException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@ComponentScan(value = {"com.eventos.app.model"})
public class UserServiceTest implements ServiceTest{

    private UsersService usersService;

    @Autowired
    public UserServiceTest(UsersService usersService, MongoTemplate mongoTemplate) {
        this.usersService = usersService;
    }

    @Override
    @Test
    @DisplayName("Can insert users into the database and user email doesn't exists in the database")
    public void insert() throws Exception{
        Assertions.assertNotNull(usersService.insert(new UserDTO("set", "set@localhost","123","123")));
        Assertions.assertThrows(
                DuplicateKeyException.class,
                () -> {usersService.insert(new UserDTO("set", "set@localhost","123","123"));}
        );
    }

    @Override
    @Test
    @DisplayName("Can update an user")
    public void update() throws Exception{
        UserDTO userDTO = new UserDTO(usersService.findByEmail("set@localhost"));
        userDTO.setNome("setzerbomb");
        User user = usersService.update(userDTO);
        Assertions.assertTrue(user.getNome().equals("setzerbomb"));
    }

    @Test
    @Override
    @DisplayName("Can find user by email")
    public void find() throws Exception{
        Assertions.assertNotNull(usersService.findByEmail("set@localhost"));
    }

    @Test
    @DisplayName("Can delete users from database")
    @Override
    public void delete() throws Exception{
        Collection<User> users = usersService.list();
        Assertions.assertTrue(!users.isEmpty());
        for (User user: users) {
            usersService.delete(user);
        }
    }

    @Test
    @DisplayName("Can list users from the database")
    @Override
    public void list() throws Exception{
        Assertions.assertTrue(!usersService.list().isEmpty());
    }

}
