package com.example.Services;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.example.Convertors.TravelConvertor;
import com.example.DTOs.TravelDto;
import com.example.Models.Travel;
import com.example.Repositories.TravelRepository;

@Service
public class TravelService {
    @Autowired
    private TravelRepository travelRepository;
    @Autowired
    private TravelConvertor travelConvertor;

    public Optional<List<TravelDto>> getAllTravels() {
        List<TravelDto> ans = travelConvertor.toDTOList(travelRepository.findAll());
        if (ans != null && !ans.isEmpty())
            return Optional.of(ans);
        return Optional.empty();
    }

    public Optional<TravelDto> getById(Long id) {
        Optional<Travel> ans = travelRepository.findById(id);
        if (ans.isPresent())
            return Optional.of(travelConvertor.toDTO(ans.get()));
        return Optional.empty();
    }

    public boolean createTravel(TravelDto TravelDto) {
        Travel Travel = travelConvertor.toModel(TravelDto);
        System.out.println();
        if (!travelRepository.exists(Example.of(Travel))) {
            travelRepository.save(Travel);
            return true;
        }
        return false;
    }

    public boolean deleteTravel(Long id) {
        if (travelRepository.existsById(id)) {
            travelRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<List<TravelDto>> getAllTravelsByHour(Time hour) {
        var ans = travelRepository.findByDepartureTime(hour);
        if (ans == null || ans.isEmpty())
            return Optional.empty();
        List<TravelDto> travelDtos = travelConvertor.toDTOList(ans.get());
        if (travelDtos != null && !travelDtos.isEmpty())
            return Optional.of(travelDtos);
        return Optional.empty();
    }

    // פונקציה שמחזירה את כל הנסיעות ממוינונת לפי שעות בmap
    public Optional<Map<Time, List<TravelDto>>> getAllTravelsGroupedByHour() {
        List<Travel> ans = travelRepository.findAll();
        if (ans == null || ans.isEmpty())
            return Optional.empty();
        List<TravelDto> travelDtos = travelConvertor.toDTOList(ans);
        if (travelDtos != null && !travelDtos.isEmpty()) {
            Map<Time, List<TravelDto>> groupedByHour = travelDtos.stream()
                    .collect(Collectors.groupingBy(TravelDto::getDepartureTime));
            return Optional.of(groupedByHour);
        }
        return Optional.empty();
    }
}
