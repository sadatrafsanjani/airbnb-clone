package com.search.service;

import com.search.dto.request.BookingRequest;
import com.search.dto.request.SearchRequest;
import com.search.dto.response.BookingResponse;
import com.search.dto.response.HouseResponse;
import com.search.model.House;
import com.search.repository.BookingRepository;
import com.search.repository.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    private HouseRepository houseRepository;
    private BookingRepository bookingRepository;

    @Autowired
    public SearchServiceImpl(HouseRepository houseRepository,
                             BookingRepository bookingRepository) {
        this.houseRepository = houseRepository;
        this.bookingRepository = bookingRepository;
    }

    /* Search By Price Range */
    @Override
    public List<HouseResponse> searchByPriceRange(SearchRequest request){

        List<HouseResponse> responses = new ArrayList<>();

        for(House house : houseRepository.findByPriceRange(request.getMinPrice(), request.getMaxPrice())){

            responses.add(modelToDto(house));
        }

        return responses;
    }

    /* Search By Location */
    @Override
    public List<HouseResponse> searchByLocation(SearchRequest request){

        List<HouseResponse> responses = new ArrayList<>();

        for(House house : houseRepository.findByLocationAndStatusTrue(request.getLocation())){

            responses.add(modelToDto(house));
        }

        return responses;
    }


    /* Search By Price Range and Location */
    @Override
    public List<HouseResponse> searchByPriceRangeAndLocation(SearchRequest request){

        List<HouseResponse> responses = new ArrayList<>();

        for(House house : houseRepository.findByPriceRangeAndLocation(request.getMinPrice(), request.getMaxPrice(), request.getLocation())){

            responses.add(modelToDto(house));
        }

        return responses;
    }

    /* Search By Geolocation */
    @Override
    public List<HouseResponse> searchByGeolocation(SearchRequest request){

        List<HouseResponse> responses = new ArrayList<>();

        for(House house : houseRepository.findByGeolocation(request.getLatitude(), request.getLongitude())){

            responses.add(modelToDto(house));
        }

        return responses;
    }

    private HouseResponse modelToDto(House house){

        return HouseResponse
                .builder()
                .id(house.getId())
                .hostId(house.getHost().getId())
                .hostName(house.getHost().getUsername())
                .title(house.getTitle())
                .description(house.getDescription())
                .price(house.getPrice())
                .location(house.getLocation())
                .latitude(house.getLatitude())
                .longitude(house.getLongitude())
                .status(house.isStatus())
                .build();
    }
}
