package com.example.Controllers;

import java.net.URLEncoder;
import java.net.URLDecoder;
import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;

import org.json.JSONObject;
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

import com.example.DTOs.LineDto;
import com.example.Services.LineService;

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
    public ResponseEntity<Object> getLineStations(@RequestParam String LineNumber, HttpServletResponse response) {
        if (LineNumber == null || LineNumber.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid line number.");
        }
        try {
            List<String> stationNames = LineService.GetAllLineStations(LineNumber);
            SavedSearch.LAST_SEARCH = LineNumber;
            addLastSearchCookie(response);
            return ResponseEntity.ok().body(stationNames);
        } catch (LineService.LineNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    public void addLastSearchCookie(HttpServletResponse response) {
        if (SavedSearch.LAST_SEARCH != "") {
            Cookie lineCookie = new Cookie("lastLineSearch", SavedSearch.LAST_SEARCH);
            setCookie(response, "lastLineSearch", SavedSearch.LAST_SEARCH, 86400, "/");
        } else {
            setCookie(response, "lastLineSearch", "", 0, "/");
        }
    }

    public void setCookie(HttpServletResponse response, String cookieName, String cookieValue, int maxAge,
            String path) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }
    
    @GetMapping("/last")
    public ResponseEntity<Object> getLastSearch(HttpServletResponse response) {
        if (SavedSearch.LAST_SEARCH != "") {
            return getLineStations(SavedSearch.LAST_SEARCH, response);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/lastFromCookie")
    public ResponseEntity<Object> getLastSearchFromCookie(HttpServletRequest request,
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

    @PostMapping("/saveSearch")
    public ResponseEntity<Object> saveSearch(@RequestParam String Line, @RequestParam int numSearch,
            HttpServletRequest request, HttpServletResponse response) {
        if (Line == null || Line.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid line number.");
        }
        String jsonString = "{}";
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("savedSearches".equals(cookie.getName())) {
                    try {
                        jsonString = URLDecoder.decode(cookie.getValue(), "UTF-8");
                    } catch (Exception e) {
                        jsonString = "{}";
                    }
                    break;
                }
            }
        }
        JSONObject json = new JSONObject(jsonString);
        json.put(String.valueOf(numSearch), Line);
        String encodedJson = "{}";
        try {
            encodedJson = URLEncoder.encode(json.toString(), "UTF-8");
        } catch (Exception e) {
            encodedJson = json.toString();
        }
        setCookie(response,"savedSearches",encodedJson,86400,"/");
        SavedSearch.SAVED_SEARCHES.put(numSearch, Line);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getSavedSearch")
    public ResponseEntity<Object> getSavedSearch(@RequestParam int numSearch, HttpServletRequest request,
            HttpServletResponse response) {
        String jsonString = "{}";
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("savedSearches".equals(cookie.getName())) {
                    try {
                        jsonString = URLDecoder.decode(cookie.getValue(), "UTF-8");
                    } catch (Exception e) {
                        jsonString = "{}";
                    }
                    break;
                }
            }
        }
        JSONObject json = new JSONObject(jsonString);
        String line = json.optString(String.valueOf(numSearch), null);
        if (line != null) {
            return getLineStations(line, response);
        }
        line = SavedSearch.SAVED_SEARCHES.get(numSearch);
        if (line != null) {
            return getLineStations(line, response);
        }
        return ResponseEntity.notFound().build();
    }

}