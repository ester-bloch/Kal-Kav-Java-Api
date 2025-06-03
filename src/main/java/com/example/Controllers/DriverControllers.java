package com.example.Controllers;

import org.springframework.web.bind.annotation.RestController;

import com.example.DTOs.DriverDto;
import com.example.Services.DriverService;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/drivers")
public class DriverControllers {
    
    @Autowired
    public DriverService driverService;

    @GetMapping("/all")
    public ResponseEntity<List<DriverDto>> getAll() {
        var all=driverService.getAllDriveres();
        if (all.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok().body(driverService.getAllDriveres().get());
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<DriverDto> getById(@PathVariable Long id) {
        Optional<DriverDto> driver = driverService.getById(id);
        if (driver.isPresent())
            return ResponseEntity.ok().body(driver.get());
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/add")
    public ResponseEntity<Void> add(@RequestBody DriverDto entity) {
        if (driverService.createDriver(entity))
            return ResponseEntity.status(HttpStatus.CREATED).build();
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (driverService.deleteDriver(id))
            return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }

}
