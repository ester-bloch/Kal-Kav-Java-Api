package com.example.DTOs;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class LineDto {
    private Long id;
    private String number;
    private String source;
    private String destination;
    private List<Long> lineStationsIds;
}
