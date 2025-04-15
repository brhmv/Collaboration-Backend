package az.edu.turing.turingcollab.mapper;

import az.edu.turing.turingcollab.domain.entity.MentoriumEntity;
import az.edu.turing.turingcollab.model.dto.request.MentoriumCreateRequest;
import az.edu.turing.turingcollab.model.dto.request.MentoriumUpdateRequest;
import az.edu.turing.turingcollab.model.dto.response.MentoriumCardResponse;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface MentoriumMapper {

    MentoriumMapper INSTANCE = Mappers.getMapper(MentoriumMapper.class);

    MentoriumEntity toEntity(Long userId, MentoriumCreateRequest request);

    MentoriumCardResponse toCardResponse(MentoriumEntity mentorium);

    void updateEntity(MentoriumEntity mentorium, Long userId, MentoriumUpdateRequest request);

    List<MentoriumCardResponse> toCardResponseList(List<MentoriumEntity> mentoriums);
}
