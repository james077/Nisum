package com.nisum.nisumapi.mapper;


import com.nisum.nisumapi.domain.User;
import com.nisum.nisumapi.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface IUserMapper extends IMapperGeneric<User, UserDto> {

}
