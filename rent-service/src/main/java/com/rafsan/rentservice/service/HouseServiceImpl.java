package com.rafsan.rentservice.service;

import com.rafsan.rentservice.dto.request.HouseRequest;
import com.rafsan.rentservice.dto.response.HouseResponse;
import com.rafsan.rentservice.model.House;
import com.rafsan.rentservice.repository.HouseRepository;
import com.rafsan.rentservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class HouseServiceImpl implements HouseService {

    private HouseRepository houseRepository;
    private UserRepository userRepository;
    private EmailService emailService;

    @Autowired
    public HouseServiceImpl(HouseRepository houseRepository,
                            UserRepository userRepository,
                            EmailService emailService) {
        this.houseRepository = houseRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    /* Save New House Post*/
    @Override
    public HouseResponse saveHouse(HouseRequest request){

        House newHouse = houseRepository.save(dtoToModel(request));

        return modelToDto(newHouse);

    }

    /* Find Approved Houses */
    @Override
    public List<HouseResponse> findApprovedHouses(){

        List<HouseResponse> responses = new ArrayList<>();

        for(House house : houseRepository.findAllByStatusTrueAndRejectionFalseOrderByIdDesc()){

            responses.add(modelToDto(house));
        }

        return responses;
    }

    /* Find Pending Houses */
    @Override
    public List<HouseResponse> findPendingHouses(){

        List<HouseResponse> responses = new ArrayList<>();

        for(House house : houseRepository.findAllByStatusFalseAndRejectionFalseOrderByIdDesc()){

            responses.add(modelToDto(house));
        }

        return responses;
    }

    /* Find All Houses Posted By Host Id */
    @Override
    public List<HouseResponse> findPostedHousesByHostId(long id){

        List<HouseResponse> responses = new ArrayList<>();

        userRepository.getById(id);

        for(House house : houseRepository.findAllByHostId(id)){

            responses.add(modelToDto(house));
        }

        return responses;
    }

    /* Find House Posted By Host */
    @Override
    public HouseResponse findByHouseId(long id){

        userRepository.getById(id);

        House house = houseRepository.findById(id).get();

        return modelToDto(house);

    }

    /* Find All Approved Houses Posted By Host */
    @Override
    public List<HouseResponse> findApprovedHousesByHostId(long id){

        List<HouseResponse> responses = new ArrayList<>();

        userRepository.getById(id);

        for (House house : houseRepository.findAllByStatusTrueAndRejectionFalseAndHostId(id)) {

            responses.add(modelToDto(house));
        }

        return responses;
    }

    /* Find All Pending Houses Posted By Host */
    @Override
    public List<HouseResponse> findPendingHousesByHostId(long id){

        List<HouseResponse> responses = new ArrayList<>();

        userRepository.getById(id);

        for (House house : houseRepository.findAllByStatusFalseAndRejectionFalseAndHostId(id)) {

            responses.add(modelToDto(house));
        }

        return responses;
    }

    /* Approve House Post */
    @Override
    public HouseResponse approveHousePost(long id){

        houseRepository.approveHousePost(id);
        House house = houseRepository.getById(id);

        if(house.isStatus()){
            emailService.notifyHouseApproval(house.getHost().getEmail());
        }

        return modelToDto(house);

    }

    /* Reject House Post */
    @Override
    public HouseResponse rejectHousePost(long id){

        houseRepository.rejectHousePost(id);
        House house = houseRepository.getById(id);

        if(house.isStatus()){
            emailService.notifyHouseRejection(house.getHost().getEmail());
        }

        return modelToDto(house);

    }

    private House dtoToModel(HouseRequest request){

        return House
                .builder()
                .host(userRepository.getById(request.getHostId()))
                .title(request.getTitle())
                .description(request.getDescription())
                .location(request.getLocation())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .price(request.getPrice())
                .build();
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
