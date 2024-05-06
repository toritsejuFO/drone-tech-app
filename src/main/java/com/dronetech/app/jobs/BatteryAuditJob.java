package com.dronetech.app.jobs;

import com.dronetech.app.entities.BatteryAuditLog;
import com.dronetech.app.respositories.BatteryAuditLogRepository;
import com.dronetech.app.respositories.DroneRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class BatteryAuditJob {

    @Autowired
    private final DroneRepository droneRepository;

    @Autowired
    private final BatteryAuditLogRepository auditLogRepository;

    @Scheduled(cron = "${battery-audit-log.cron}")
    public void auditBatteryLevels() {
        log.info("BATTERY_AUDIT_LOG: Performing battery audit log for all drones...");
        List<BatteryAuditLog> batteryAuditLogs = new ArrayList<>();
        droneRepository.findAll()
            .forEach(drone -> batteryAuditLogs.add(
                BatteryAuditLog.builder()
                    .droneSerialNo(drone.getSerialNo())
                    .batteryCapacity(drone.getBatteryCapacity())
                    .build())
            );
        auditLogRepository.saveAll(batteryAuditLogs);
        log.info("BATTERY_AUDIT_LOG: Battery audit log saved for all drones.");

    }
}
