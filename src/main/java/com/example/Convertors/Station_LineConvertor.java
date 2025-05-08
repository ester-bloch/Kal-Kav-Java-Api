package com.example.Convertors;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.DTOs.Station_LineDto;
import com.example.Models.Station_Line;
import com.example.Repositories.Station_LineRepository;

public class Station_LineConvertor {
    @Autowired
    private static Station_LineRepository  station_LineRepository;
     public static Station_LineDto toDTO(Station_Line Station_Line) {
        Station_LineDto Station_LineDTO = new Station_LineDto();
        Station_LineDTO.setId(Station_Line.getId());
        Station_LineDTO.setStationOrder(Station_Line.getStationOrder());
        Station_LineDTO.setLineNumber(Station_Line.getLine().getNumber());
        Station_LineDTO.setStationName(Station_Line.getStation().getName());
        return Station_LineDTO;
    }

    public static Station_Line toModel(Station_LineDto Station_LineDTO) {
        Station_Line newStation_Line = new Station_Line();
        newStation_Line.setId(Station_LineDTO.getId());
        newStation_Line.setStationOrder(Station_LineDTO.getStationOrder());
        newStation_Line.setLine(Station_LineDTO.getLineNumber());
        newStation_Line.setStationName(Station_LineDTO.getStation().getName());
        return newStation_Line;
    }

    public static List<Station_LineDto> toDTOList(List<Station_Line> list) {
        return list.stream().map((Station_Line) -> toDTO(Station_Line)).collect(Collectors.toList());
    }
    public static List<Station_Line> toModelList(List<Station_LineDto> list) {
        return list.stream().map((Station_Line) -> toModel(Station_Line)).collect(Collectors.toList());
    }
}
