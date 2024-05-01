package com.dronetech.app.respositories;

import com.dronetech.app.entities.Drone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DroneRepository extends JpaRepository<Drone, Long> {

    boolean existsBySerialNo(String sn);

    Drone findBySerialNo(String sn);

}
