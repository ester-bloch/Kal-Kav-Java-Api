package com.example.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "station_line")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Station_Line {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private int stationOrder;

    @ManyToOne 
    @JoinColumn(name = "station_id") // שיניתי מ-@JoinTable ל-@JoinColumn עם שם העמודה המתאים
    private Station station;

    @ManyToOne 
    @JoinColumn(name = "line_id") // שיניתי מ-@JoinTable ל-@JoinColumn עם שם העמודה המתאים
    private Line line;
}
