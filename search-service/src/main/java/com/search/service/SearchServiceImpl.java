package com.search.service;

import com.search.dto.request.SearchRequest;
import com.search.dto.response.HouseResponse;
import com.search.model.House;
import com.search.repository.BookingRepository;
import com.search.repository.HouseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Slf4j
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

        for(House house : houseRepository.searchByLocation(request.getLocation())){

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

    /* Search By House within 1KM radius given Geolocation */
    @Override
    public List<HouseResponse> searchByGeolocation(SearchRequest request){

        List<HouseResponse> responses = new ArrayList<>();

        for(House house : houseRepository.findHousesByStatusTrueOrderByIdDesc()){

            if(calculateDistance(house.getLatitude(), house.getLatitude(), request.getLatitude(), request.getLongitude()) <= 1.00){

                responses.add(modelToDto(house));
            }
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

    private double calculateDistance(double x1, double y1, double x2, double y2){

        x1 = Math.toRadians(x1);
        y1 = Math.toRadians(y1);
        x2 = Math.toRadians(x2);
        y2 = Math.toRadians(y2);

        double dLat = x2 - x1;
        double dLon = y2 - y1;

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(x1) * Math.cos(x2) * Math.sin(dLon/2) * Math.sin(dLon/2);
        double c =  2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        log.info("--------------: " + c * 6371);
        return c * 6371;
    }
}
