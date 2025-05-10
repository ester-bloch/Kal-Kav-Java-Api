package com.example.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;

import com.example.Convertors.TravelConvertor;
import com.example.DTOs.TravelDto;
import com.example.Models.Travel;
import com.example.Repositories.TravelRepository;

public class TravelService {
    private TravelRepository TravelRepository;

    public Optional<List<TravelDto>> getAllTraveles() {
        List<TravelDto> ans = TravelConvertor.toDTOList(TravelRepository.findAll());
        if (ans != null && !ans.isEmpty())
            return Optional.of(ans);
        return Optional.empty();
    }

    public Optional<TravelDto> getById(Long id) {
        Optional<Travel> ans = TravelRepository.findById(id);
        if (ans.isPresent())
            return Optional.of(TravelConvertor.toDTO(ans.get()));
        return Optional.empty();
    }

    public boolean createTravel(TravelDto TravelDto) {
        Travel Travel=TravelConvertor.toModel(TravelDto);
        if(!TravelRepository.exists(Example.of(Travel))){
        TravelRepository.save(Travel);
        return true;
    }
    return false; 
    }
    public boolean deleteTravel(Long id) {
        if(TravelRepository.existsById(id)){
        TravelRepository.deleteById(id);
        return true;
    }
    return false; 
    }
}
