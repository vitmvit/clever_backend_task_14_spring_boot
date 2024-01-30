package ru.clevertec.house.converter;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.clevertec.house.model.dto.HouseHistoryDto;
import ru.clevertec.house.model.entity.HouseHistory;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HouseHistoryConverter {

    HouseHistoryDto convert(HouseHistory source);

    HouseHistory convert(HouseHistoryDto source);
}
