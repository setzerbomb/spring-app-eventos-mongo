package com.eventos.app.model.service;

import com.eventos.app.common.exceptions.DataException;
import com.eventos.app.controller.DTO.UserDTO;
import com.eventos.app.model.domain.User;
import com.eventos.app.model.repositories.UsersRepository;
import com.eventos.app.model.service.common.GenericDataService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class UsersService extends GenericDataService<User, UserDTO> {
    private UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        super(usersRepository);
        this.usersRepository = usersRepository;
    }

    @Override
    public User insert(UserDTO userDTO) throws DuplicateKeyException, DataException {
        return usersRepository.insert(new User(userDTO));
    }

    @Override
    public User update(UserDTO userDTO) throws DataException {
        if(userDTO.getId() != null) {
            User user = this.find(userDTO.getId());
            if(user != null) {
                user.setNome(userDTO.getNome());
                usersRepository.save(user);
                return user;
            }
        }
        throw new DataException("user.not.found");
    }

    public User findByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

}
