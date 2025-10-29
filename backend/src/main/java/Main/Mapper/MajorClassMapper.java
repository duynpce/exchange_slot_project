package Main.Mapper;

import Main.DTO.MajorClass.CreateMajorClassDTO;
import Main.Entity.MajorClass;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MajorClassMapper {
    MajorClass toEntity(CreateMajorClassDTO createMajorClassDTO);
}
