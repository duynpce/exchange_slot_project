package Main.Mapper;

import Main.DTO.MajorClass.CreateMajorClassDTO;
import Main.DTO.MajorClass.GetMajorClassDTO;
import Main.DTO.MajorClass.UpdateMajorClassDTO;
import Main.Entity.MajorClass;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MajorClassMapper {
    MajorClass toEntity(CreateMajorClassDTO createMajorClassDTO);
    MajorClass toEntity(UpdateMajorClassDTO updateMajorClassDTO);

    List<GetMajorClassDTO> toDtoList(List<MajorClass> majorClassList);
}
