package com.eventos.app.model.repositories;

import com.eventos.app.model.domain.Event;
import com.eventos.app.model.domain.User;
import com.eventos.app.model.repositories.common.GenericRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface EventsRepository extends GenericRepository<Event, String> {

    Collection<Event> findByUser(String user);

    Event findByUserAndId(String user, String id);
}
