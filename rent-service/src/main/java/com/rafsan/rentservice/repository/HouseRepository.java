package com.rafsan.rentservice.repository;

import com.rafsan.rentservice.model.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface HouseRepository extends JpaRepository<House, Long> {

    List<House> findAllByStatusTrueAndRejectionFalseOrderByIdDesc();

    List<House> findAllByStatusFalseAndRejectionFalseOrderByIdDesc();

    List<House> findAllByHostId(long id);

    List<House> findAllByStatusTrueAndRejectionFalseAndHostId(long id);

    List<House> findAllByStatusFalseAndRejectionFalseAndHostId(long id);

    @Modifying
    @Query("UPDATE House H SET H.status = true WHERE H.id = :id")
    void approveHousePost(long id);

    @Modifying
    @Query("UPDATE House H SET H.rejection = true WHERE H.id = :id")
    void rejectHousePost(long id);
}
