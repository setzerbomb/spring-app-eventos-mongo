package com.eventos.app.model.repositories;

import com.eventos.app.model.domain.User;
import com.eventos.app.model.repositories.common.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends GenericRepository<User, String> {
    User findByEmail(String email);
}
