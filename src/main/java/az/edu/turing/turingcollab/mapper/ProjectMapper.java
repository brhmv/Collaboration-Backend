package az.edu.turing.turingcollab.mapper;

import az.edu.turing.turingcollab.domain.entity.ProjectEntity;
import az.edu.turing.turingcollab.domain.entity.UserEntity;
import az.edu.turing.turingcollab.model.dto.request.ProjectCreateRequest;
import az.edu.turing.turingcollab.model.dto.response.ProjectCardResponse;
import az.edu.turing.turingcollab.model.dto.response.ProjectDetailedResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import static az.edu.turing.turingcollab.model.constant.AppConstant.DATE_FORMAT;
import static az.edu.turing.turingcollab.model.constant.AppConstant.DATE_FORMAT_2;

@Mapper(
        componentModel = "spring", uses = UserMapper.class,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProjectMapper {

    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    @Mapping(source = "project.id", target = "id")
    @Mapping(source = "project.applicationDeadline", target = "applicationDeadline", dateFormat = DATE_FORMAT)
    @Mapping(source = "project.name", target = "name")
    @Mapping(source = "project.imageName", target = "imageName")
    @Mapping(source = "project.shortDescription", target = "shortDescription")
    @Mapping(source = "creator", target = "creator")
    ProjectCardResponse toCardResponse(ProjectEntity project, UserEntity creator);

    @Mapping(source = "project.applicationDeadline", target = "applicationDeadline", dateFormat = DATE_FORMAT)
    @Mapping(source = "project.startDate", target = "startDate", dateFormat = DATE_FORMAT)
    @Mapping(source = "project.endDate", target = "endDate", dateFormat = DATE_FORMAT)
    @Mapping(source = "project.participants", target = "participants")
    ProjectDetailedResponse toResponse(ProjectEntity project);

    @Mapping(source = "createdBy", target = "createdBy")
    @Mapping(source = "request.startDate", target = "startDate", dateFormat = DATE_FORMAT_2)
    @Mapping(source = "request.endDate", target = "endDate", dateFormat = DATE_FORMAT_2)
    @Mapping(source = "request.applicationDeadline", target = "applicationDeadline", dateFormat = DATE_FORMAT_2)
    @Mapping(target = "file", ignore = true)
    @Mapping(target = "imageName", ignore = true)
    ProjectEntity toEntity(Long createdBy, ProjectCreateRequest request);

    @Mapping(source = "updatedBy", target = "updatedBy")
    @Mapping(source = "request.startDate", target = "startDate", dateFormat = DATE_FORMAT_2)
    @Mapping(source = "request.endDate", target = "endDate", dateFormat = DATE_FORMAT_2)
    @Mapping(source = "request.applicationDeadline", target = "applicationDeadline", dateFormat = DATE_FORMAT_2)
    @Mapping(target = "file", ignore = true)
    @Mapping(target = "imageName", ignore = true)
    void updateEntity(@MappingTarget ProjectEntity project, Long updatedBy, ProjectCreateRequest request);
}
