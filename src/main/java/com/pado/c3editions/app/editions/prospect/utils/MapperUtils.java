package com.pado.c3editions.app.editions.prospect.utils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MapperUtils {

    @Autowired
    private ModelMapper modelMapper;

    public <T,D> T asEntity(D dto,Class<T> clazz) {
        return (T)modelMapper.map(dto,clazz);
    }


    public <T,D> D asDTO(T entity,Class<D> dto) {
        return (D) modelMapper.map(entity,dto);
    }

    public <T,D> List<T> asEntityList(List<D> dtoList,Class<T> entityclass) {
        return dtoList.stream().map(d -> modelMapper.map(d,entityclass)).collect(Collectors.toList());
    }

    public <T,D> List<D> asDTOList(List<T> entityList, Class<D> dtoclass) {
        return entityList.stream().map(e -> modelMapper.map(e,dtoclass)).collect(Collectors.toList());
    }

    public <T,D> Set<D> asDTOSet(Set<T> entityList, Class<D> dtoclass) {
        return entityList.stream().map(e -> modelMapper.map(e,dtoclass)).collect(Collectors.toSet());
    }

}
