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

import com.example.DTOs.LineDto;
import com.example.Services.LineService;

@RestController
@RequestMapping("/Lines")
public class LineController {
    
    @Autowired
    public LineService LineService;

    @GetMapping("/all")
    public ResponseEntity<List<LineDto>> getAll() {
        return ResponseEntity.ok().body(LineService.getAllLinees().get());
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<LineDto> getById(@PathVariable Long id) {
        Optional<LineDto> Line = LineService.getById(id);
        if (Line.isPresent())
            return ResponseEntity.ok().body(Line.get());
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/add")
    public ResponseEntity<Void> add(@RequestBody LineDto entity) {
        if (LineService.createLine(entity))
            return ResponseEntity.status(HttpStatus.CREATED).build();
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (LineService.deleteLine(id))
            return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}
