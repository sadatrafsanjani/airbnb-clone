package com.search.service;

import com.search.dto.request.SearchRequest;
import com.search.dto.response.HouseResponse;
import java.util.List;

public interface SearchService {

    List<HouseResponse> searchByPriceRange(SearchRequest request);
    List<HouseResponse> searchByLocation(SearchRequest request);
    List<HouseResponse> searchByPriceRangeAndLocation(SearchRequest request);
    List<HouseResponse> searchByGeolocation(SearchRequest request);
}
