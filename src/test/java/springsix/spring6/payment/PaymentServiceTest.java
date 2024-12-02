package springsix.spring6.payment;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class PaymentServiceTest {
    Clock clock;

    @BeforeEach
    void setUp() {
        this.clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
    }

    @Test
    @DisplayName("prepare 메소드가 요구사항 3가지를 잘 충족했는지 검증")
    void convertedAmount() throws IOException {
        testAmount(valueOf(500), valueOf(5_000), this.clock);
        testAmount(valueOf(1000), valueOf(10_000), this.clock);
        testAmount(valueOf(3000), valueOf(30_000), this.clock);
    }

    @Test
    @DisplayName("원화 환산금액의 유효시간 계산")
    void validUntil() throws IOException {
        PaymentService paymentService = new PaymentService(new ExRateProviderStub(valueOf(1_000)), clock);

        Payment payment = paymentService.prepare(1L, "USD", TEN);

        // valid until 이 prepare() 30분 뒤로 설정됐는가?
        LocalDateTime now = LocalDateTime.now(this.clock);
        LocalDateTime expectedDate = now.plusMinutes(30);

        assertThat(payment.getValidUntil()).isEqualTo(expectedDate);
    }

    // 여러 경우를 테스트하기 위해 인자를 받아서 검증해본다.
    private static void testAmount(BigDecimal exRate, BigDecimal convertedAmount, Clock clock) throws IOException {
        PaymentService paymentService = new PaymentService(new ExRateProviderStub(exRate), clock);

        Payment payment = paymentService.prepare(1L, "USD", TEN);

        assertThat(payment.getExRete()).isEqualByComparingTo(exRate); // `BigDecimal`의 동등성 검사는 다르다.
        assertThat(payment.getConvertedAmount()).isEqualTo(convertedAmount);
    }
}