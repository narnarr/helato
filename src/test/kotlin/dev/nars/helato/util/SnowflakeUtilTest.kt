package dev.nars.helato.util

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.ZoneOffset

class SnowflakeUtilTest {

    @Nested
    @DisplayName("요청을 받으면")
    inner class ContextWithRequest {

        private val snowflakeUtil = SnowflakeUtil(
            1L
        )

        @Test
        @DisplayName("다음 ID를 생성한다")
        fun itReturnsNextId() {
            assertThat(snowflakeUtil.nextId()).isGreaterThan(0) // 18362456249864192L
        }
    }

    @Nested
    @DisplayName("sequence가 다 찼으면")
    inner class ContextWithFullSequence {

        private val snowflakeUtil = SnowflakeUtil(
            1L,
            0L,
            -1L xor (-1L shl SnowflakeUtil.SEQUENCE_BITS)
        )

        @Test
        @DisplayName("다음 millis까지 대기 후 ID를 생성한다")
        fun itReturnsNextId() {
            assertThat(snowflakeUtil.nextId()).isGreaterThan(0)
        }
    }

    @Nested
    @DisplayName("현재 timestamp가 마지막으로 사용한 timestamp보다 작으면")
    inner class ContextWithClockBackwards {

        private val snowflakeUtil = SnowflakeUtil(
            1L,
            LocalDate.now().plusYears(1).atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()
        )

        @Test
        @DisplayName("IllegalStateException을 던진다")
        fun itThrowsException() {
            assertThatThrownBy { snowflakeUtil.nextId() }
                .isInstanceOf(IllegalStateException::class.java)
        }
    }
}