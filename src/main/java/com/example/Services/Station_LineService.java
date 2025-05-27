package com.example.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.example.Convertors.Station_LineConvertor;
import com.example.Convertors.Station_LineConvertor;
import com.example.DTOs.Station_LineDto;
import com.example.Models.Station_Line;
import com.example.DTOs.Station_LineDto;
import com.example.Repositories.Station_LineRepository;
import com.example.Repositories.Station_LineRepository;
@Service
public class Station_LineService {
    @Autowired
    private Station_LineRepository Station_LineRepository;

    public Optional<List<Station_LineDto>> getAllStation_Linees() {
        List<Station_LineDto> ans = Station_LineConvertor.toDTOList(Station_LineRepository.findAll());
        if (ans != null && !ans.isEmpty())
            return Optional.of(ans);
        return Optional.empty();
    }

    public Optional<Station_LineDto> getById(Long id) {
        Optional<Station_Line> ans = Station_LineRepository.findById(id);
        if (ans.isPresent())
            return Optional.of(Station_LineConvertor.toDTO(ans.get()));
        return Optional.empty();
    }

    public boolean createStation_Line(Station_LineDto Station_LineDto) {
        Station_Line Station_Line=Station_LineConvertor.toModel(Station_LineDto);
        if(!Station_LineRepository.exists(Example.of(Station_Line))){
        Station_LineRepository.save(Station_Line);
        return true;
    }
    return false; 
    }
    public boolean deleteStation_Line(Long id) {
        if(Station_LineRepository.existsById(id)){
        Station_LineRepository.deleteById(id);
        return true;
    }
    return false; 
    }
}
