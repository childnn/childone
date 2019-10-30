package org.anonymous.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/1/5 14:28
 */
@Mapper
public interface UserMapper {
    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    @Mappings({
            @Mapping(source = "id", target = "userId"),
            @Mapping(source = "name", target = "username"),
            @Mapping(source = "role.name", target = "roleName"),
    })
    UserRoleDTO toDTO(User user);
}
