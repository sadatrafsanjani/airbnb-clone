package com.search.controller;

import com.search.dto.request.SearchRequest;
import com.search.dto.response.HouseResponse;
import com.search.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search/searches")
@Slf4j
public class SearchController {

    private SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping("/searchByPriceRange")
    public ResponseEntity<List<HouseResponse>> searchByPriceRange(@RequestBody SearchRequest request){

        List<HouseResponse> responses = searchService.searchByPriceRange(request);

        if(!responses.isEmpty()){

            return ResponseEntity.ok(responses);
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/searchByLocation")
    public ResponseEntity<List<HouseResponse>> searchByLocation(@RequestBody SearchRequest request){

        List<HouseResponse> responses = searchService.searchByLocation(request);

        if(!responses.isEmpty()){

            return ResponseEntity.ok(responses);
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/searchByPriceRangeAndLocation")
    public ResponseEntity<List<HouseResponse>> searchByPriceRangeAndLocation(@RequestBody SearchRequest request){

        List<HouseResponse> responses = searchService.searchByPriceRangeAndLocation(request);

        if(!responses.isEmpty()){

            return ResponseEntity.ok(responses);
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/searchByGeolocation")
    public ResponseEntity<List<HouseResponse>> searchByGeolocation(@RequestBody SearchRequest request){

        List<HouseResponse> responses = searchService.searchByGeolocation(request);

        if(!responses.isEmpty()){

            return ResponseEntity.ok(responses);
        }

        return ResponseEntity.ok().build();
    }
}
