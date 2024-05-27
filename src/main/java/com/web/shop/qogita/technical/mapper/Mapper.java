package com.web.shop.qogita.technical.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public interface Mapper<D, E> {

    D toDto(E entity);

    E toEntity(D dto);

    default List<D> toDtoList(List<E> entityList) {
        return entityList.stream().map(this::toDto).toList();
    }

    default Set<D> toDtoSet(Set<E> entitySet) {
        return entitySet.stream().map(this::toDto).collect(Collectors.toSet());
    }

    default Set<E> toEntitySet(Set<D> dtoSet) {
        return dtoSet.stream().map(this::toEntity).collect(Collectors.toSet());
    }
    default List<E> toEntityList(List<D> dtoList) {
        return dtoList.stream().map(this::toEntity).toList();
    }
}
