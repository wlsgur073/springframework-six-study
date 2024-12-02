package springsix.spring6.payment;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.*;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class PaymentTest {
    // 도메인 오브젝트 테스트 아주 좋군요!

    @Test
    void createPrepared() {
        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

        Payment payment = Payment.createPrepared(
                1L, "USD", BigDecimal.TEN, BigDecimal.valueOf(1_000), LocalDateTime.now(clock)
        );

        assertThat(payment.getConvertedAmount()).isEqualByComparingTo(BigDecimal.valueOf(10_000));
        assertThat(payment.getValidUntil()).isEqualTo(LocalDateTime.now(clock).plusMinutes(30));
    }

    @Test
    void isValid() {
        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

        Payment payment = Payment.createPrepared(
                1L, "USD", BigDecimal.TEN, BigDecimal.valueOf(1_000), LocalDateTime.now(clock)
        );

        assertThat(payment.isValid(clock)).isTrue();
        // 시간을 강제로 30분 돌린다.
        assertThat(payment.isValid(Clock.offset(clock, Duration.of(30, ChronoUnit.MINUTES)))).isFalse();
    }
}
