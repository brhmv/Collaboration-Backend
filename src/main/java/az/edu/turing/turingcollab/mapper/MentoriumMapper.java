package az.edu.turing.turingcollab.mapper;

import az.edu.turing.turingcollab.domain.entity.MentoriumEntity;
import az.edu.turing.turingcollab.model.dto.request.MentoriumCreateRequest;
import az.edu.turing.turingcollab.model.dto.request.MentoriumUpdateRequest;
import az.edu.turing.turingcollab.model.dto.response.MentoriumCardResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static az.edu.turing.turingcollab.model.constant.AppConstant.DATE_FORMAT;
import static az.edu.turing.turingcollab.model.constant.AppConstant.DATE_FORMAT_2;

@Mapper(componentModel = "spring", uses = UserMapper.class,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MentoriumMapper {

    MentoriumMapper INSTANCE = Mappers.getMapper(MentoriumMapper.class);

    @Mapping(source = "createdBy", target = "createdBy")
    @Mapping(source = "request.startTime", target = "startTime", dateFormat = DATE_FORMAT_2)
    @Mapping(source = "request.endTime", target = "endTime", dateFormat = DATE_FORMAT_2)
    @Mapping(target = "imageName", ignore = true)
    MentoriumEntity toEntity(Long createdBy, MentoriumCreateRequest request);

    @Mapping(source = "mentorium.applicationDeadline", target = "applicationDeadline", dateFormat = DATE_FORMAT)
    @Mapping(target = "imageName", ignore = true)
    MentoriumCardResponse toCardResponse(MentoriumEntity mentorium);

    @Mapping(source = "updatedBy", target = "updatedBy")
    @Mapping(source = "request.startTime", target = "startTime", dateFormat = DATE_FORMAT_2)
    @Mapping(source = "request.endTime", target = "endTime", dateFormat = DATE_FORMAT_2)
    @Mapping(target = "imageName", ignore = true)
    void updateEntity(@MappingTarget MentoriumEntity mentorium, Long updatedBy, MentoriumUpdateRequest request);

    @Mapping(source = "mentorium.startTime", target = "startTime", dateFormat = DATE_FORMAT)
    @Mapping(source = "mentorium.endTime", target = "endTime", dateFormat = DATE_FORMAT)
    @Mapping(source = "mentorium.applicationDeadline", target = "applicationDeadline", dateFormat = DATE_FORMAT)
    List<MentoriumCardResponse> toCardResponseList(List<MentoriumEntity> mentoriums);
}
