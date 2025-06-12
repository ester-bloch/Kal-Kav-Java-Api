package com.example.Convertors;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.DTOs.TravelDto;
import com.example.Models.Travel;
import com.example.Repositories.BusRepository;
import com.example.Repositories.DriverRepository;
import com.example.Repositories.LineRepository;

public class TravelConvertor {
    @Autowired
    private static BusRepository busRepository;
    @Autowired
    private static DriverRepository driverRepository;
    @Autowired 
    private static LineRepository lineRepository;
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
        newTravel.setBus(busRepository.findById(TravelDTO.getBusId()).get());
        newTravel.setDriver(driverRepository.findByName(TravelDTO.getDriverName()).get());
        newTravel.setLine(lineRepository.findByNumber(TravelDTO.getLineNumber()).get());
        return newTravel;
    }

    public static List<TravelDto> toDTOList(List<Travel> list) {
        return list.stream().map((Travel) -> toDTO(Travel)).collect(Collectors.toList());
    }

    public static List<Travel> toModelList(List<TravelDto> list) {
        return list.stream().map((Travel) -> toModel(Travel)).collect(Collectors.toList());
    }
}
