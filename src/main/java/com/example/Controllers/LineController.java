package com.example.Controllers;

import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RestController;

import com.example.DTOs.LineDto;
import com.example.DTOs.StationDto;
import com.example.Services.LineService;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/Lines")
public class LineController {

    @Autowired
    public LineService LineService;

    @GetMapping("/all")
    public ResponseEntity<List<LineDto>> getAll() {
        if (LineService.getAllLines().isPresent())
            return ResponseEntity.ok().body(LineService.getAllLines().get());
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<LineDto> getById(@PathVariable Long id) {
        Optional<LineDto> Line = LineService.getById(id);
        if (Line.isPresent())
            return ResponseEntity.ok().body(Line.get());
        return ResponseEntity.notFound().build();
    }

       @PostMapping("/add")
    public ResponseEntity<Void> add(@RequestBody LineDto entity) {
        if (LineService.createLine(entity))
            return ResponseEntity.status(HttpStatus.CREATED).build();
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (LineService.deleteLine(id))
            return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/stations")
    public ResponseEntity<Object> getLineStations(@RequestParam String number) {
        if (number == null || number.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid line number.");
        }
        try {
            List<String> stationNames = LineService.GetAllLineStations(number);
            Map<Integer, List<String>> Response = new HashMap<>();
            Response.put(stationNames.size(), stationNames);
            System.out.println(stationNames.size() + " stations found for line " + number);
            System.out.println(stationNames);
            // return ResponseEntity.ok().body(Response);
            SavedSearch.LastSearch = new SavedSearch();
            SavedSearch.LastSearch.setLine( number) ;
            return ResponseEntity.ok().body(stationNames);
            // return ResponseEntity.ok().body("Stations for line " + number + ": " + stations.size() + " found.");
        } catch (LineService.LineNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping("/last")
    public ResponseEntity<Object> getLastSearch() {
        if (SavedSearch.LastSearch != null) {
            return getLineStations(SavedSearch.LastSearch.getLine());
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/saveSearch")
    public ResponseEntity<Object> saveSearch(@RequestParam String number,@RequestParam int numSearch) {
        if (number == null || number.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid line number.");
        }
        SavedSearch savedSearch =new SavedSearch(number,numSearch);
        SavedSearch.savedSearches.add(savedSearch);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("/search")
    public ResponseEntity<Object> search(@RequestParam int numSearch) {
        // חפש את האובייקט עם numSearch מתאים
        Optional<SavedSearch> searchOpt = SavedSearch.savedSearches.stream()
                .filter(search -> search.getNumSearch() == numSearch)
                .findFirst();
        if (searchOpt.isPresent()) {
            String lineNumber = searchOpt.get().getLine();
            return getLineStations(lineNumber);
        }
        return ResponseEntity.notFound().build();
    }
}
