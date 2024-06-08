package dev.nars.helato.util

import dev.nars.helato.util.SnowflakeUtil.Companion.DEFAULT_EPOCH
import dev.nars.helato.util.SnowflakeUtil.Companion.MACHINE_ID_BITS
import dev.nars.helato.util.SnowflakeUtil.Companion.SEQUENCE_BITS
import dev.nars.helato.util.SnowflakeUtil.Companion.SEQUENCE_MASK
import org.springframework.beans.factory.annotation.Value
import java.sql.Timestamp
import java.time.ZoneId
import java.time.ZonedDateTime

class SnowflakeSupport(
    @Value("\${config.machine-id}")
    private val machineId: Long
) {

    fun extractTime(id: Long): ZonedDateTime {
        return Timestamp(id.shr(MACHINE_ID_BITS + SEQUENCE_BITS).plus(DEFAULT_EPOCH))
            .toLocalDateTime().atZone(ZoneId.systemDefault())
    }

    fun generateMaxId(dateTime: ZonedDateTime): Long {
        return dateTime.toInstant().toEpochMilli()
            .minus(DEFAULT_EPOCH)
            .shl(MACHINE_ID_BITS + SEQUENCE_BITS)
            .xor(machineId.shl(SEQUENCE_BITS))
            .xor(SEQUENCE_MASK)
    }

    fun generateMinId(dateTime: ZonedDateTime): Long {
        return dateTime.toInstant().toEpochMilli()
            .minus(DEFAULT_EPOCH)
            .shl(MACHINE_ID_BITS + SEQUENCE_BITS)
            .xor(machineId.shl(SEQUENCE_BITS))
    }
}