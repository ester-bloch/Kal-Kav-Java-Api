package com.example.Convertors;

import java.util.List;
import java.util.stream.Collectors;

import com.example.DTOs.BusDto;
import com.example.Models.Bus;

public class BusConvertor {
    public static BusDto toDTO(Bus bus) {
        BusDto busDTO = new BusDto();
        busDTO.setLicense_plate(bus.getLicense_plate());
        busDTO.setSeats(bus.getSeats());
        return busDTO;
    }

    public static Bus toModel(BusDto busDTO) {
        Bus newBus = new Bus();
        newBus.setId(busDTO.getId());
        newBus.setLicense_plate(busDTO.getLicense_plate());
        newBus.setSeats(busDTO.getSeats());
        return newBus;
    }

    public static List<BusDto> toDTOList(List<Bus> list) {
        return list.stream().map((bus) -> toDTO(bus)).collect(Collectors.toList());
    }
    public static List<Bus> toModelList(List<BusDto> list) {
        return list.stream().map((bus) -> toModel(bus)).collect(Collectors.toList());
    }
}
