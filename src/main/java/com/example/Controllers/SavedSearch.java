package com.example.Controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
// @AllArgsConstructor
// @NoArgsConstructor
@Data
public class SavedSearch {
    public static String LAST_SEARCH;
    public static HashMap<Integer, String> SAVED_SEARCHES ;
    static {
        SAVED_SEARCHES = new HashMap<>();
        LAST_SEARCH = ""; 
    }
   
}
