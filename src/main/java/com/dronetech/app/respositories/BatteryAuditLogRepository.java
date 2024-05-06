package com.dronetech.app.respositories;

import com.dronetech.app.entities.BatteryAuditLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatteryAuditLogRepository extends CrudRepository<BatteryAuditLog, Long> {
    List<BatteryAuditLog> findByDroneSerialNoOrderByTimestampDesc(String droneSn);
}
