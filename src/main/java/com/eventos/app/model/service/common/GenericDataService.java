package com.eventos.app.model.service.common;

import com.eventos.app.common.exceptions.DataException;
import com.eventos.app.controller.DTO.common.BaseDTO;
import com.eventos.app.model.repositories.common.GenericRepository;

import java.util.Collection;
import java.util.Optional;

public abstract class GenericDataService<E,DTO extends BaseDTO> implements DataServiceInterface<E,DTO> {
    private GenericRepository<E,String> genericRepository;

    public GenericDataService(GenericRepository<E, String> genericRepository) {
        this.genericRepository = genericRepository;
    }

    @Override
    public E insert(DTO dto) throws DataException {
        return null;
    }

    @Override
    public E update(DTO dto) throws DataException {
        return null;
    }

    @Override
    public boolean delete(E e) throws DataException {
        if (e != null){
            genericRepository.delete(e);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String id) throws DataException {
        E entity = this.find(id);
        if (entity != null){
            genericRepository.delete(entity);
            return true;
        }
        return false;
    }

    @Override
    public Collection<E> list() throws DataException {
        return genericRepository.findAll();
    }

    @Override
    public E find(String id) throws DataException {
        Optional<E> entity = genericRepository.findById(id);
        return entity.isPresent() ? entity.get() : null;
    }
}
