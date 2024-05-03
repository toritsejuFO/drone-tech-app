package com.dronetech.app.respositories;

import com.dronetech.app.entities.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DroneRepository extends JpaRepository<Drone, Long> {

    boolean existsBySerialNo(String sn);

    Drone findBySerialNo(String sn);

}
