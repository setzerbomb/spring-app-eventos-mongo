package com.eventos.app.model.repositories.common;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericRepository<E,ID> extends MongoRepository<E, ID> {
}
