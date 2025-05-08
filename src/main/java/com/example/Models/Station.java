package com.example.Models;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;
@Data
@AllArgsConstructor
@Table(name = "stations")
@Entity
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "station", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Station_Line> lineStations;
}
