package io.github.igormateus.repertapp.util;

import org.modelmapper.ModelMapper;

import io.github.igormateus.repertapp.dto.user.UserAuthDTO;

public class UserAuthDTOCreator {

    public static UserAuthDTO createValid() {

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(UserCreator.createValid(), UserAuthDTO.class);
    }
}
