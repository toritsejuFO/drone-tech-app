package com.dronetech.app.dtos.mappers;

import com.dronetech.app.dtos.BatteryAuditLogDto;
import com.dronetech.app.entities.BatteryAuditLog;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BatteryAuditLogDtoMapper {

    public static BatteryAuditLogDto auditLogDto(BatteryAuditLog batteryAuditLog) {
        return BatteryAuditLogDto.builder()
            .droneSerialNo(batteryAuditLog.getDroneSerialNo())
            .batteryCapacity(batteryAuditLog.getBatteryCapacity())
            .timestamp(batteryAuditLog.getTimestamp())
            .build();
    }
}
