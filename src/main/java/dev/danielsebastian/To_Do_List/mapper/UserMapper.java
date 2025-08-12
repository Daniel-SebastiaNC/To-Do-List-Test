package dev.danielsebastian.To_Do_List.mapper;

import dev.danielsebastian.To_Do_List.dto.user.UserLoginRequest;
import dev.danielsebastian.To_Do_List.dto.user.UserRegisterRequest;
import dev.danielsebastian.To_Do_List.dto.user.UserResponse;
import dev.danielsebastian.To_Do_List.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    User toUser(UserRegisterRequest user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "username", ignore = true)
    User toUser(UserLoginRequest user);

    UserResponse toUserResponse(User user);
}
