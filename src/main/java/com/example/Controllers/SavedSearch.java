package com.example.Controllers;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SavedSearch {
    public static SavedSearch LastSearch;
    public static List<SavedSearch> savedSearches ;
    static {
        savedSearches = new ArrayList<>();
    }
    private String line;
    private int numSearch;
}
