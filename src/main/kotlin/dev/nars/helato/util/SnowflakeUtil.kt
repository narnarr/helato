package dev.nars.helato.util

import org.springframework.beans.factory.annotation.Value
import java.time.Instant

class SnowflakeUtil(
    @Value("\${config.machine-id}")
    private val machineId: Long,
    private var lastTimestamp: Long = 0,
    private var sequence: Long = 0
) {

    init {
        check(machineId <= MAX_MACHINE_ID) {
            "Machine ID cannot be greater than $MAX_MACHINE_ID"
        }
    }

    @Synchronized
    fun nextId(): Long {
        var timestamp = generateTimestamp()

        check(timestamp >= lastTimestamp) {
            "Clock is moving backwards! Rejecting requests until ${Instant.ofEpochMilli(lastTimestamp)}."
        }

        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) and SEQUENCE_MASK

            if (sequence == 0L) {
                timestamp = tillNextMillis()
            }
        } else {
            sequence = 0
        }

        lastTimestamp = timestamp

        return ((timestamp - DEFAULT_EPOCH) shl (MACHINE_ID_BITS + SEQUENCE_BITS)) or
                (machineId shl SEQUENCE_BITS) or
                sequence
    }

    private fun tillNextMillis(): Long {
        var timestamp: Long

        do {
            timestamp = generateTimestamp()
        } while (timestamp <= lastTimestamp)

        return timestamp
    }

    private fun generateTimestamp(): Long {
        return System.currentTimeMillis()
    }

    companion object {
        const val DEFAULT_EPOCH = 1_577_836_800_000L // Default Epoch (January 1, 2020, Midnight UTC = 2020-01-01T00:00:00Z)
        const val MACHINE_ID_BITS = 5
        const val MAX_MACHINE_ID = -1L xor (-1L shl MACHINE_ID_BITS)
        const val SEQUENCE_BITS = 12
        const val SEQUENCE_MASK = -1L xor (-1L shl SEQUENCE_BITS)
    }
}