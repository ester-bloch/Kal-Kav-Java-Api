package com.example.Convertors;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.DTOs.TravelDto;
import com.example.Models.Travel;
import com.example.Models.Driver;
import com.example.Models.Bus;
import com.example.Models.Line;
import com.example.Repositories.BusRepository;
import com.example.Repositories.DriverRepository;
import com.example.Repositories.LineRepository;

@Component
public class TravelConvertor {

    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private LineRepository lineRepository;
    @Autowired
    private BusRepository busRepository;

    public TravelDto toDTO(Travel travel) {
        TravelDto TravelDTO = new TravelDto();
        TravelDTO.setId(travel.getId());
        TravelDTO.setDepartureTime(travel.getDepartureTime());
        TravelDTO.setBusId(travel.getBus().getId());
        TravelDTO.setDriverName(travel.getDriver().getName());
        TravelDTO.setLineNumber(travel.getLine().getNumber());
        return TravelDTO;
    }

    public Travel toModel(TravelDto TravelDTO) {
        Travel newTravel = new Travel();
        newTravel.setId(TravelDTO.getId());
        newTravel.setDepartureTime(TravelDTO.getDepartureTime());

        Optional<Driver> driverOpt = driverRepository.findByName(TravelDTO.getDriverName());
        if (driverOpt.isEmpty()) {
            throw new RuntimeException("Driver not found: " + TravelDTO.getDriverName());
        }
        newTravel.setDriver(driverOpt.get());

        Optional<Bus> busOpt = busRepository.findById(TravelDTO.getBusId());
        if (busOpt.isEmpty()) {
            throw new RuntimeException("Bus not found: " + TravelDTO.getBusId());
        }
        newTravel.setBus(busOpt.get());

        Optional<Line> lineOpt = lineRepository.findByNumber(TravelDTO.getLineNumber());
        if (lineOpt.isEmpty()) {
            throw new RuntimeException("Line not found: " + TravelDTO.getLineNumber());
        }
        newTravel.setLine(lineOpt.get());

        return newTravel;
    }

    public List<TravelDto> toDTOList(List<Travel> list) {
        return list.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<Travel> toModelList(List<TravelDto> list) {
        return list.stream().map(this::toModel).collect(Collectors.toList());
    }
}
