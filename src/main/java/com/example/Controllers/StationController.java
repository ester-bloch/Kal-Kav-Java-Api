package com.example.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.DTOs.StationDto;
import com.example.Services.StationService;

@RestController
@RequestMapping("/Stations")
public class StationController {
    
    @Autowired
    public StationService StationService;

    @GetMapping("/all")
    public ResponseEntity<List<StationDto>> getAll() {
        return ResponseEntity.ok().body(StationService.getAllStationes().get());
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<StationDto> getById(@PathVariable Long id) {
        Optional<StationDto> Station = StationService.getById(id);
        if (Station.isPresent())
            return ResponseEntity.ok().body(Station.get());
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/add")
    public ResponseEntity<Void> add(@RequestBody StationDto entity) {
        if (StationService.createStation(entity))
            return ResponseEntity.status(HttpStatus.CREATED).build();
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (StationService.deleteStation(id))
            return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}
