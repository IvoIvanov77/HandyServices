package org.softuni.handy.util;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Stream;

@Component
public class DtoMapper {

    private final ModelMapper modelMapper;

    public DtoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public  <S, D> D map(S source, Class<D> destinationClass) {
        return modelMapper.map(source, destinationClass);
    }

    public  <S, D> Stream<D> map(Collection<S> sourceCollection, Class<D> destinationClass) {
        return sourceCollection
                .stream()
                .map(s -> map(s, destinationClass));
    }


}
