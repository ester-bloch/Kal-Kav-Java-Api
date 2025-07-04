package com.example.Controllers;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.DTOs.TravelDto;
import com.example.Services.TravelService;

@RestController
@RequestMapping("/Travels")
public class TravelConroller {

    @Autowired
    public TravelService TravelService;

    @GetMapping("/all")
    public ResponseEntity<List<TravelDto>> getAll() {
        if (TravelService.getAllTravels().isPresent())
            return ResponseEntity.ok().body(TravelService.getAllTravels().get());
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<TravelDto> getById(@PathVariable Long id) {
        Optional<TravelDto> Travel = TravelService.getById(id);
        if (Travel.isPresent())
            return ResponseEntity.ok().body(Travel.get());
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/add")
    public ResponseEntity<Void> add(@RequestBody TravelDto entity) {
        if (TravelService.createTravel(entity))
            return ResponseEntity.status(HttpStatus.CREATED).build();
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (TravelService.deleteTravel(id))
            return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }

    /*
     * )לוחות זמנים:
     * (חיפוש כל הנסיעות / נסיעות בשעה מסוימת)
     */
    @GetMapping("/byHour")
    public ResponseEntity<List<TravelDto>> getTravelsByHour(@RequestParam String hour) {
        LocalTime time = LocalTime.parse(hour);
        Optional<List<TravelDto>> travels = TravelService.getAllTravelsByHour(Time.valueOf(time));
        if (travels.isPresent())
            return ResponseEntity.ok().body(travels.get());
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/groupedByHour")
    public ResponseEntity<Map<Time, List<TravelDto>>> allGroupedByHours() {
        Optional<Map<Time, List<TravelDto>>> groupedTravels = TravelService.getAllTravelsGroupedByHour();
        if (groupedTravels.isPresent())
            return ResponseEntity.ok().body(groupedTravels.get());
        return ResponseEntity.notFound().build();
    }
}
