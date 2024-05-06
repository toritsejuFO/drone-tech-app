package com.dronetech.app.respositories;

import com.dronetech.app.entities.BatteryAuditLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatteryAuditLogRepository extends CrudRepository<BatteryAuditLog, Long> {
}
