package com.example.DTOs;

import lombok.Data;

@Data
public class BusDto {
    private Long id;
    private String license_plate;
    private int seats;
}
