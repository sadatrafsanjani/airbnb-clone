package com.rafsan.rentservice.controller;

import com.rafsan.rentservice.dto.request.HouseRequest;
import com.rafsan.rentservice.dto.response.HouseResponse;
import com.rafsan.rentservice.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/rental/rents")
public class RentController {

    private HouseService houseService;

    @Autowired
    public RentController(HouseService houseService) {
        this.houseService = houseService;
    }

    @GetMapping("/test")
    public String test(){

        return "Rent Service Testing";
    }

    @PostMapping
    ResponseEntity<HouseResponse> saveHouse(@RequestBody HouseRequest request){

        HouseResponse response = houseService.saveHouse(request);

        if(response != null){

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/approvedHouses")
    ResponseEntity<List<HouseResponse>> findApprovedHouses(){

        List<HouseResponse> responses = houseService.findApprovedHouses();

        if(!responses.isEmpty()){

            return ResponseEntity.ok(responses);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/pendingHouses")
    ResponseEntity<List<HouseResponse>> findPendingHouses(){

        List<HouseResponse> responses = houseService.findPendingHouses();

        if(!responses.isEmpty()){

            return ResponseEntity.ok(responses);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/postedHousesByHostId/{id}")
    ResponseEntity<List<HouseResponse>> findPostedHousesByHostId(@PathVariable("id") long id){

        List<HouseResponse> responses = houseService.findPostedHousesByHostId(id);

        if(!responses.isEmpty()){

            return ResponseEntity.ok(responses);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/houseById/{id}")
    ResponseEntity<HouseResponse> findByHouseId(@PathVariable("id") long id){

        HouseResponse response = houseService.findByHouseId(id);

        if(response != null){

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/approvedHousesByHostId/{id}")
    ResponseEntity<List<HouseResponse>> findApprovedHousesByHostId(@PathVariable("id") long id){

        List<HouseResponse> responses = houseService.findApprovedHousesByHostId(id);

        if(!responses.isEmpty()){

            return ResponseEntity.ok(responses);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/pendingHousesByHostId/{id}")
    ResponseEntity<List<HouseResponse>> findPendingHousesByHostId(@PathVariable("id") long id){

        List<HouseResponse> responses = houseService.findPendingHousesByHostId(id);

        if(!responses.isEmpty()){

            return ResponseEntity.ok(responses);
        }

        return ResponseEntity.ok().build();
    }

    @PutMapping("/approve/{id}")
    ResponseEntity<?> approveHousePost(@PathVariable("id") long id){

        HouseResponse response = houseService.approveHousePost(id);

        if(response != null){

            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().build();
    }

    @PutMapping("/reject/{id}")
    ResponseEntity<?> rejectHousePost(@PathVariable("id") long id){

        HouseResponse response = houseService.rejectHousePost(id);

        if(response != null){

            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().build();
    }
}
