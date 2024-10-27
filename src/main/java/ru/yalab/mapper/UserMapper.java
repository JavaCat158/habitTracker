package ru.yalab.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.yalab.dto.UserDto;
import ru.yalab.dto.UserRegistrationDTO;
import ru.yalab.entity.User;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto userToUserDto(User user); // user в userDTO

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "ACTIVE")
    User userRegistrationDtoToUser(UserRegistrationDTO userRegistrationDTO);

    User userDtoToUser(UserDto userDto);
}
