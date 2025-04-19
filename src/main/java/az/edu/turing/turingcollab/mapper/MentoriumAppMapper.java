package az.edu.turing.turingcollab.mapper;

import az.edu.turing.turingcollab.domain.entity.MentoriumApplicationEntity;
import az.edu.turing.turingcollab.domain.entity.UserEntity;
import az.edu.turing.turingcollab.model.dto.response.IncomingAppResponse;
import az.edu.turing.turingcollab.model.dto.response.SentAppResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring",
        uses = UserMapper.class,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface MentoriumAppMapper {

    @Mapping(source = "entity.id", target = "id")
    @Mapping(target = "createdAt", expression = "java(formatInstant(entity.getCreatedAt()))")
    IncomingAppResponse toIncomingAppResponse(MentoriumApplicationEntity entity, UserEntity creator);

    @Mapping(target = "createdAt", expression = "java(formatInstant(entity.getCreatedAt()))")
    SentAppResponse toSentAppResponse(MentoriumApplicationEntity entity);

    default String formatInstant(Instant instant) {
        if (instant == null) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")
                .withZone(ZoneId.of("Europe/Baku"));
        return formatter.format(instant);
    }
}
