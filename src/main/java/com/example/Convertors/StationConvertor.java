package com.example.Convertors;

import java.util.List;
import java.util.stream.Collectors;

import com.example.DTOs.StationDto;
import com.example.Models.Station;

public class StationConvertor {
  public static StationDto toDTO(Station Station) {

        StationDto StationDTO = new StationDto(Station.getId(),Station.getName(),Station.getLineStations());
        return StationDTO;
    }

    public static Station toModel(StationDto StationDTO) {
        Station newStation = new Station(StationDTO.getId(),StationDTO.getName(),
        );
        return newStation;
    }

    public static List<StationDto> toDTOList(List<Station> list) {
        return list.stream().map((Station) -> toDTO(Station)).collect(Collectors.toList());
    }
    public static List<Station> toModelList(List<StationDto> list) {
        return list.stream().map((Station) -> toModel(Station)).collect(Collectors.toList());
    }   
}
