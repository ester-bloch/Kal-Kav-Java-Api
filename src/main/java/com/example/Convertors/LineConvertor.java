package com.example.Convertors;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.DTOs.LineDto;
import com.example.Models.Line;
import com.example.Repositories.Station_LineRepository;

public class LineConvertor {
    @Autowired
    private static  Station_LineRepository station_LineRepository;
    public static LineDto toDTO(Line Line) {
        List<Long> StationIds = Line.getLineStations().stream().map((y) -> y.getId()).collect(Collectors.toList());
        LineDto LineDTO = new LineDto();
        LineDTO.setId(Line.getId());
        LineDTO.setNumber(Line.getNumber());
        LineDTO.setSource(Line.getSource());
        LineDTO.setDestination(Line.getDestination());
        LineDTO.setLineStationsIds(StationIds);
        return LineDTO;
    }

    public static Line toModel(LineDto LineDTO) {
        Line newLine = new Line();
        newLine.setId(LineDTO.getId());
        newLine.setSource(LineDTO.getSource());
        newLine.setDestination(LineDTO.getDestination());
        newLine.setNumber(LineDTO.getNumber());
        if(LineDTO.getLineStationsIds()!=null)
            // Assuming that the station_LineRepository is already initialized and available
                // This will fetch the Station_Line entities based on the IDs provided in LineDTO
        newLine.setLineStations(LineDTO.getLineStationsIds().stream().map((p)->station_LineRepository.findById(p).get()).collect(Collectors.toList()));
                return newLine;
    }

    public static List<LineDto> toDTOList(List<Line> list) {
        return list.stream().map((Line) -> toDTO(Line)).collect(Collectors.toList());
    }

    public static List<Line> toModelList(List<LineDto> list) {
        return list.stream().map((Line) -> toModel(Line)).collect(Collectors.toList());
    }
}
