package com.example.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.example.Convertors.StationConvertor;
import com.example.DTOs.StationDto;
import com.example.Models.Station;
import com.example.Repositories.StationRepository;
@Service
public class StationService {
    @Autowired
    private StationRepository StationRepository;

    public Optional<List<StationDto>> getAllStationes() {
        List<StationDto> ans = StationConvertor.toDTOList(StationRepository.findAll());
        if (ans != null && !ans.isEmpty())
            return Optional.of(ans);
        return Optional.empty();
    }

    public Optional<StationDto> getById(Long id) {
        Optional<Station> ans = StationRepository.findById(id);
        if (ans.isPresent())
            return Optional.of(StationConvertor.toDTO(ans.get()));
        return Optional.empty();
    }

    public boolean createStation(StationDto StationDto) {
        Station Station=StationConvertor.toModel(StationDto);
        if(!StationRepository.exists(Example.of(Station))){
        StationRepository.save(Station);
        return true;
    }
    return false; 
    }
    public boolean deleteStation(Long id) {
        if(StationRepository.existsById(id)){
        StationRepository.deleteById(id);
        return true;
    }
    return false; 
    }
}
