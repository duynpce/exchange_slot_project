package main.mapper;

import main.dto.MajorClass.CreateMajorClassDTO;
import main.dto.MajorClass.GetMajorClassDTO;
import main.dto.MajorClass.UpdateMajorClassDTO;
import main.entity.MajorClass;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MajorClassMapper {
    MajorClass toEntity(CreateMajorClassDTO createMajorClassDTO);
    MajorClass toEntity(UpdateMajorClassDTO updateMajorClassDTO);

    List<GetMajorClassDTO> toDtoList(List<MajorClass> majorClassList);
}
