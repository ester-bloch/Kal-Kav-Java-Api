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

import com.example.DTOs.Station_LineDto;
import com.example.Services.Station_LineService;

@RestController
@RequestMapping("/Station_Lines")
public class Station_LineController {
    
    @Autowired
    public Station_LineService Station_LineService;

    @GetMapping("/all")
    public ResponseEntity<List<Station_LineDto>> getAll() {
        return ResponseEntity.ok().body(Station_LineService.getAllStation_Linees().get());
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<Station_LineDto> getById(@PathVariable Long id) {
        Optional<Station_LineDto> Station_Line = Station_LineService.getById(id);
        if (Station_Line.isPresent())
            return ResponseEntity.ok().body(Station_Line.get());
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/add")
    public ResponseEntity<Void> add(@RequestBody Station_LineDto entity) {
        if (Station_LineService.createStation_Line(entity))
            return ResponseEntity.status(HttpStatus.CREATED).build();
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (Station_LineService.deleteStation_Line(id))
            return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}
