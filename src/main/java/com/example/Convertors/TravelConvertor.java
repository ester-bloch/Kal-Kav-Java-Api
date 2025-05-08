package com.example.Convertors;

import java.util.List;
import java.util.stream.Collectors;

import com.example.DTOs.TravelDto;
import com.example.Models.Travel;

public class TravelConvertor {
    public static TravelDto toDTO(Travel travel) {
        TravelDto TravelDTO = new TravelDto();
        TravelDTO.setId(travel.getId());
        TravelDTO.setDepartureTime(travel.getDepartureTime());
        TravelDTO.setBusId(travel.getBus().getId());
        TravelDTO.setDriverName(travel.getDriver().getName());
        TravelDTO.setLineNumber(travel.getLine().getNumber());
        return TravelDTO;
    }

    public static Travel toModel(TravelDto TravelDTO) {
        Travel newTravel = new Travel();
        newTravel.setId(TravelDTO.getId());
        newTravel.setDepartureTime(TravelDTO.getDepartureTime());
        newTravel.setBus(TravelDTO.getBus().getId());
        newTravel.setDriverName(TravelDTO.getDriver().getName());
        newTravel.setLineNumber(TravelDTO.getLine().getNumber());
        return newTravel;
    }

    public static List<TravelDto> toDTOList(List<Travel> list) {
        return list.stream().map((Travel) -> toDTO(Travel)).collect(Collectors.toList());
    }

    public static List<Travel> toModelList(List<TravelDto> list) {
        return list.stream().map((Travel) -> toModel(Travel)).collect(Collectors.toList());
    }
}
