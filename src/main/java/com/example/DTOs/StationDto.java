package com.example.DTOs;

import java.util.List;

import com.example.Models.Station_Line;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StationDto {
    private Long id;
    private String name;
    private List<Station_LineDto> lineStations;
@Override
    public String toString() {
        return "StationDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lineStations=" + lineStations.toString() +
                '}';
    }
}
