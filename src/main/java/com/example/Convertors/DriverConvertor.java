package com.example.Convertors;

import java.util.List;
import java.util.stream.Collectors;

import com.example.DTOs.DriverDto;
import com.example.Models.Driver;

public class DriverConvertor {
    public static DriverDto toDTO(Driver Driver) {
        DriverDto DriverDTO = new DriverDto();
        DriverDTO.setId(Driver.getId());
        DriverDTO.setName(Driver.getName());
        DriverDTO.setPhone(Driver.getPhone());
        return DriverDTO;
    }

    public static Driver toModel(DriverDto DriverDTO) {
        Driver newDriver = new Driver(DriverDTO.getId(),DriverDTO.getName(),DriverDTO.getPhone());
        return newDriver;
    }

    public static List<DriverDto> toDTOList(List<Driver> list) {
        return list.stream().map((Driver) -> toDTO(Driver)).collect(Collectors.toList());
    }
    public static List<Driver> toModelList(List<DriverDto> list) {
        return list.stream().map((Driver) -> toModel(Driver)).collect(Collectors.toList());
    }
}
