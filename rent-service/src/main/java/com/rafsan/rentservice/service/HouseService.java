package com.rafsan.rentservice.service;

import com.rafsan.rentservice.dto.request.HouseRequest;
import com.rafsan.rentservice.dto.response.HouseResponse;
import java.util.List;

public interface HouseService {

    HouseResponse saveHouse(HouseRequest request);
    List<HouseResponse> findApprovedHouses();
    List<HouseResponse> findPendingHouses();
    List<HouseResponse> findPostedHousesByHostId(long id);
    HouseResponse findByHouseId(long id);
    List<HouseResponse> findApprovedHousesByHostId(long id);
    List<HouseResponse> findPendingHousesByHostId(long id);
    HouseResponse approveHousePost(long id);
    HouseResponse rejectHousePost(long id);
}
