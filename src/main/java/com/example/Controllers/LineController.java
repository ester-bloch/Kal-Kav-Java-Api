package com.example.Controllers;

import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

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
    public ResponseEntity<Object> getLineStations(@RequestParam String number, HttpServletResponse response) {
        if (number == null || number.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid line number.");
        }
        try {
            List<String> stationNames = LineService.GetAllLineStations(number);
            SavedSearch.LastSearch = new SavedSearch();
            SavedSearch.LastSearch.setLine(number);
            addLastSearchCookie(response);
            return ResponseEntity.ok().body(stationNames);
        } catch (LineService.LineNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    public void addLastSearchCookie(HttpServletResponse response) {
        if (SavedSearch.LastSearch != null) {
            Cookie lineCookie = new Cookie("lastLineSearch", SavedSearch.LastSearch.getLine());
            lineCookie.setPath("/");
            lineCookie.setMaxAge(86400);
            response.addCookie(lineCookie);
        } else {
            Cookie lineCookie = new Cookie("lastLineSearch", "");
            lineCookie.setPath("/");
            lineCookie.setMaxAge(0);
            response.addCookie(lineCookie);
        }
    }

    @GetMapping("/last")
    public ResponseEntity<Object> getLastSearch(HttpServletResponse response) {
        if (SavedSearch.LastSearch != null) {
            return getLineStations(SavedSearch.LastSearch.getLine(), response);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/addSearch")
    public ResponseEntity<Object> saveSearch(@RequestParam String number, @RequestParam int numSearch) {
        if (number == null || number.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid line number.");
        }
        SavedSearch savedSearch = new SavedSearch(number, numSearch);
        SavedSearch.savedSearches.add(savedSearch);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/lastFromCookie")
    public ResponseEntity<Object> getLastSearchFromCookie(jakarta.servlet.http.HttpServletRequest request,
            HttpServletResponse response) {
        String lastLineNumber = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("lastLineSearch".equals(cookie.getName())) {
                    lastLineNumber = cookie.getValue();
                    break;
                }
            }
        }
        if (lastLineNumber != null && !lastLineNumber.isEmpty()) {
            return getLineStations(lastLineNumber, response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No last line search found in cookies.");
        }
    }
}