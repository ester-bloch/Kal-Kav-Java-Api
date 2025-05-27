package com.example.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.example.Convertors.TravelConvertor;
import com.example.DTOs.TravelDto;
import com.example.Models.Travel;
import com.example.Repositories.TravelRepository;
@Service
public class TravelService {
    private TravelRepository travelRepository;

    public Optional<List<TravelDto>> getAllTraveles() {
        List<TravelDto> ans = TravelConvertor.toDTOList(travelRepository.findAll());
        if (ans != null && !ans.isEmpty())
            return Optional.of(ans);
        return Optional.empty();
    }

    public Optional<TravelDto> getById(Long id) {
        Optional<Travel> ans = travelRepository.findById(id);
        if (ans.isPresent())
            return Optional.of(TravelConvertor.toDTO(ans.get()));
        return Optional.empty();
    }

    public boolean createTravel(TravelDto TravelDto) {
        Travel Travel=TravelConvertor.toModel(TravelDto);
        if(!travelRepository.exists(Example.of(Travel))){
        travelRepository.save(Travel);
        return true;
    }
    return false; 
    }
    public boolean deleteTravel(Long id) {
        if(travelRepository.existsById(id)){
        travelRepository.deleteById(id);
        return true;
    }
    return false; 
    }
}
