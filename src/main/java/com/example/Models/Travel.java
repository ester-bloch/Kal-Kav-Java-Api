package com.example.Models;

import java.sql.Time;

import org.hibernate.mapping.ManyToOne;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "travel")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Travel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @jakarta.persistence.ManyToOne
    @JoinColumn(name="bus" )
    private Bus bus;

    @jakarta.persistence.ManyToOne
    @JoinColumn(name = "driver")
    private Driver driver;

    @jakarta.persistence.ManyToOne
    @JoinColumn(name = "Line")
    private Line line;

    private Time departureTime;
}
