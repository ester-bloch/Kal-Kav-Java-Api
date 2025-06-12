package com.example.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.example.Convertors.LineConvertor;
import com.example.Convertors.StationConvertor;
import com.example.DTOs.LineDto;
import com.example.DTOs.StationDto;
import com.example.DTOs.Station_LineDto;
import com.example.Models.Line;
import com.example.Models.Station_Line;
import com.example.Repositories.LineRepository;

@Service
public class LineService {
    @Autowired
    private LineRepository LineRepository;

    public Optional<List<LineDto>> getAllLines() {
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
        Line Line = LineConvertor.toModel(LineDto);
        if (!LineRepository.exists(Example.of(Line))) {
            LineRepository.save(Line);
            return true;
        }
        return false;
    }

    public boolean deleteLine(Long id) {
        if (LineRepository.existsById(id)) {
            LineRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<String> GetAllLineStations(String number) throws LineNotFoundException {
        Optional<Line> line = LineRepository.findByNumber(number);
        if (!line.isPresent())
            throw new LineNotFoundException("Line with number " + number + " not found.");
        List<Station_Line> Station_Lines = line.get().getLineStations();
        if (Station_Lines == null || Station_Lines.isEmpty())
            throw new LineNotFoundException("No stations found for line with number " + number + ".");
        List<String> stationNames = Station_Lines.stream()
                .map(stationLine -> StationConvertor.toDTO(stationLine.getStation())).map(StationDto::getName)
                .toList();
        if (stationNames == null || stationNames.isEmpty())
            throw new LineNotFoundException("No stations found for line with number " + number + ".");
        return stationNames;
    }

    public class LineNotFoundException extends RuntimeException {
        public LineNotFoundException(String message) {
            super(message);
        }
    }
}
