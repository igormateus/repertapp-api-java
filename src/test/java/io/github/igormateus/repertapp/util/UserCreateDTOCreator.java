package io.github.igormateus.repertapp.util;

import org.modelmapper.ModelMapper;

import io.github.igormateus.repertapp.dto.user.UserCreateDTO;

public class UserCreateDTOCreator {

    public static UserCreateDTO createValid() {

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(UserCreator.createValid(), UserCreateDTO.class);
    }
}
