package com.example.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BusDto {
    private Long id;
    private String license_plate;
    private int seats;
}
