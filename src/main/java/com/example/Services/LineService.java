package com.example.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;

import com.example.Convertors.LineConvertor;
import com.example.DTOs.LineDto;
import com.example.Models.Line;
import com.example.Repositories.LineRepository;

public class LineService {
    @Autowired
    private LineRepository LineRepository;

    public Optional<List<LineDto>> getAllLinees() {
        List<LineDto> ans = LineConvertor.toDTOList(LineRepository.findAll());
        if (ans != null && !ans.isEmpty())
            return Optional.of(ans);
        return Optional.empty();
    }

    public Optional<LineDto> getById(Long id) {
        Optional<Line> ans = LineRepository.findById(id);
        if (ans.isPresent())
            return Optional.of(LineConvertor.toDTO(ans.get()));
        return Optional.empty();
    }

    public boolean createLine(LineDto LineDto) {
        Line Line=LineConvertor.toModel(LineDto);
        if(!LineRepository.exists(Example.of(Line))){
        LineRepository.save(Line);
        return true;
    }
    return false; 
    }
    public boolean deleteLine(Long id) {
        if(LineRepository.existsById(id)){
        LineRepository.deleteById(id);
        return true;
    }
    return false; 
    }
}
