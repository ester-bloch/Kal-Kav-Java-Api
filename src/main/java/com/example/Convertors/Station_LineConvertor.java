package com.example.Convertors;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.DTOs.Station_LineDto;
import com.example.Models.Station_Line;
import com.example.Repositories.LineRepository;
import com.example.Repositories.StationRepository;

@Component
public class Station_LineConvertor {
    @Autowired
    private static LineRepository lineRepository;
    @Autowired
    private static StationRepository stationRepository;

    public static Station_LineDto toDTO(Station_Line stationLine) {
        Station_LineDto stationLineDTO = new Station_LineDto();
        stationLineDTO.setId(stationLine.getId());
        stationLineDTO.setStationOrder(stationLine.getStationOrder());
        stationLineDTO.setLineNumber(stationLine.getLine().getNumber());
        stationLineDTO.setStationName(stationLine.getStation().getName());
        return stationLineDTO;
    }

    public static Station_Line toModel(Station_LineDto stationLineDTO) {
        Station_Line newStation_Line = new Station_Line();
        newStation_Line.setId(stationLineDTO.getId());
        newStation_Line.setStationOrder(stationLineDTO.getStationOrder());
        newStation_Line.setLine(lineRepository.findByNumber(stationLineDTO.getLineNumber()).get());
        newStation_Line.setStation(stationRepository.findByName(stationLineDTO.getStationName()).get());
        return newStation_Line;
    }

        public static List<Station_LineDto> toDTOList(List<Station_Line> list) {
            return list.stream().map(Station_LineConvertor::toDTO).collect(Collectors.toList());
        }
    
        public static List<Station_Line> toModelList(List<Station_LineDto> list) {
            return list.stream().map(Station_LineConvertor::toModel).collect(Collectors.toList());
        }
    }
