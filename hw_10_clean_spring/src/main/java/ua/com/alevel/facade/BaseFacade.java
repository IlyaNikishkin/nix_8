package ua.com.alevel.facade;

import ua.com.alevel.dto.request.RequestDto;
import ua.com.alevel.entity.BaseEntity;

import java.util.List;

public interface BaseFacade<REQ extends RequestDto, E extends BaseEntity> {

    void create(REQ req);

    void update(REQ req, Long id);

    void delete(Long id);

    E findById(Long id);

    List<E> findAll();
}
