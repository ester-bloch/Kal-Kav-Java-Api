package com.example.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;

import com.example.Convertors.DriverConvertor;
import com.example.DTOs.DriverDto;
import com.example.Models.Driver;
import com.example.Repositories.DriverRepository;

public class DriverService {
    @Autowired
    private DriverRepository DriverRepository;

    public Optional<List<DriverDto>> getAllDriveres() {
        List<DriverDto> ans = DriverConvertor.toDTOList(DriverRepository.findAll());
        if (ans != null && !ans.isEmpty())
            return Optional.of(ans);
        return Optional.empty();
    }

    public Optional<DriverDto> getById(Long id) {
        Optional<Driver> ans = DriverRepository.findById(id);
        if (ans.isPresent())
            return Optional.of(DriverConvertor.toDTO(ans.get()));
        return Optional.empty();
    }

    public boolean createDriver(DriverDto DriverDto) {
        Driver Driver=DriverConvertor.toModel(DriverDto);
        if(!DriverRepository.exists(Example.of(Driver))){
        DriverRepository.save(Driver);
        return true;
    }
    return false; 
    }
    public boolean deleteDriver(Long id) {
        if(DriverRepository.existsById(id)){
        DriverRepository.deleteById(id);
        return true;
    }
    return false; 
    }
}
