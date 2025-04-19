package az.edu.turing.turingcollab.mapper;

import az.edu.turing.turingcollab.domain.entity.ProjectApplicationEntity;
import az.edu.turing.turingcollab.domain.entity.UserEntity;
import az.edu.turing.turingcollab.model.dto.response.IncomingAppResponse;
import az.edu.turing.turingcollab.model.dto.response.SentAppResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Mapper(
        componentModel = "spring",
        uses = UserMapper.class,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface ProjectAppMapper {

    @Mapping(source = "entity.id", target = "id")
    @Mapping(target = "createdAt", expression = "java(formatInstant(entity.getCreatedAt()))")
    @Mapping(source = "entity.project.name", target = "topic")
    IncomingAppResponse toIncomingAppResponse(ProjectApplicationEntity entity, UserEntity creator);

    @Mapping(target = "createdAt", expression = "java(formatInstant(entity.getCreatedAt()))")
    @Mapping(source = "entity.project.name", target = "topic")
    SentAppResponse toSentAppResponse(ProjectApplicationEntity entity);

    default String formatInstant(Instant instant) {
        if (instant == null) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")
                .withZone(ZoneId.of("Europe/Berlin"));
        return formatter.format(instant);
    }
}
