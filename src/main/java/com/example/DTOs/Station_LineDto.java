package com.example.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Station_LineDto {
    private Long id;
    private int stationOrder;
    private String stationName;
    private String lineNumber;
}
