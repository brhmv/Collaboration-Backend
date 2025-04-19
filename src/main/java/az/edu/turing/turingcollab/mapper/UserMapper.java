package az.edu.turing.turingcollab.mapper;

import az.edu.turing.turingcollab.domain.entity.UserEntity;
import az.edu.turing.turingcollab.model.dto.response.UserCardResponse;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserCardResponse toUserCardResponse(UserEntity creator);
}

