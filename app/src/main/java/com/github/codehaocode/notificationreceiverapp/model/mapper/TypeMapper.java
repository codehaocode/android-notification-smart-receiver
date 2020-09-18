package com.github.codehaocode.notificationreceiverapp.model.mapper;

import java.util.List;

public interface TypeMapper<M, E> {

    E mapToEntity(M model);
    M mapToModel(E entity);
    List<E> mapToEntity(List<M> model);
    List<M> mapToModel(List<E> entity);
}
