package com.rafsan.rentservice.repository;

import com.rafsan.rentservice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

@Repository
@Transactional
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("SELECT R FROM Role R WHERE R.role = :role")
    Role findByRoleName(String role);
}
