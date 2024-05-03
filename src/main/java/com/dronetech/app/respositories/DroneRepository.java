package com.dronetech.app.respositories;

import com.dronetech.app.entities.Drone;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DroneRepository extends CrudRepository<Drone, Long> {

    boolean existsBySerialNo(String sn);

    Drone findBySerialNo(String sn);

    List<Drone> findByStateIn(List<Drone.DroneState> states);

}
