package com.eventos.app.model.service.common;

import com.eventos.app.common.exceptions.DataException;

import java.util.Collection;
import java.util.UUID;

public interface DataServiceInterface<E,DTO> {

    E insert(DTO dto) throws DataException;

    E update(DTO dto) throws DataException;

    boolean delete (String id) throws DataException;

    boolean delete (E e)  throws DataException;

    Collection<E> list () throws DataException;

    E find(String id) throws DataException;
}
