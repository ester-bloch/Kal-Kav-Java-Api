package com.example.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.example.Convertors.BusConvertor;
import com.example.DTOs.BusDto;
import com.example.Models.Bus;
import com.example.Repositories.BusRepository;
@Service
public class BusService {
    @Autowired
    private BusRepository busRepository;

    public Optional<List<BusDto>> getAllBuses() {
        List<BusDto> ans = BusConvertor.toDTOList(busRepository.findAll());
        if (ans != null && !ans.isEmpty())
            return Optional.of(ans);
        return Optional.empty();
    }

    public Optional<BusDto> getById(Long id) {
        Optional<Bus> ans = busRepository.findById(id);
        if (ans.isPresent())
            return Optional.of(BusConvertor.toDTO(ans.get()));
        return Optional.empty();
    }

    public boolean createBus(BusDto busDto) {
        Bus bus=BusConvertor.toModel(busDto);
        if(!busRepository.exists(Example.of(bus))){
        busRepository.save(bus);
        return true;
    }
    return false; 
    }
    public boolean deleteBus(Long id) {
        if(busRepository.existsById(id)){
        busRepository.deleteById(id);
        return true;
    }
    return false; 
    }
}
