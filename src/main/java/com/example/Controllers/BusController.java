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

import com.example.DTOs.BusDto;


@RestController
@RequestMapping("/Buses")
public class BusController {
    
    @Autowired
    public com.example.Services.BusService BusService;

    @GetMapping("/all")
    public ResponseEntity<List<BusDto>> getAll() {
        return ResponseEntity.ok().body(BusService.getAllBuses().get());
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<BusDto> getById(@PathVariable Long id) {
        Optional<BusDto> Bus = BusService.getById(id);
        if (Bus.isPresent())
            return ResponseEntity.ok().body(Bus.get());
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/add")
    public ResponseEntity<Void> add(@RequestBody BusDto entity) {
        if (BusService.createBus(entity))
            return ResponseEntity.status(HttpStatus.CREATED).build();
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (BusService.deleteBus(id))
            return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}
