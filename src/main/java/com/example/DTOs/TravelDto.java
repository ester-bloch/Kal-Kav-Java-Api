package com.example.DTOs;

import java.sql.Time;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TravelDto {
     private Long id;

    private Long busId;

    private String driverName;

    private String LineNumber;

    private Time departureTime;

}
