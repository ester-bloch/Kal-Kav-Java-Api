package com.example.Convertors;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.DTOs.StationDto;
import com.example.Models.Station;

public class StationConvertor {
       public static StationDto toDTO(Station Station) {
        StationDto StationDTO = new StationDto();
        StationDTO.setId(Station.getId());
        StationDTO.setName(Station.getName());
        StationDTO.setLineStations(Station_LineConvertor.toDTOList(Station.getLineStations()));
        return StationDTO;
    }

    public static Station toModel(StationDto StationDTO) {
        Station newStation = new Station();
        newStation.setId(StationDTO.getId());
        newStation.setName(StationDTO.getName());
        newStation.setLineStations(Station_LineConvertor.toModelList(StationDTO.getLineStations()));
        return newStation;
    }

    public static List<StationDto> toDTOList(List<Station> list) {
        return list.stream().map((Station) -> toDTO(Station)).collect(Collectors.toList());
    }

    public static List<Station> toModelList(List<StationDto> list) {
        return list.stream().map((Station) -> toModel(Station)).collect(Collectors.toList());
    }
}
