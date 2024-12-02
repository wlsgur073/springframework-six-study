package springsix.spring6.payment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import springsix.spring6.TestPaymentConfig;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDateTime;

import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestPaymentConfig.class)
class PaymentServiceSpringTest {

    @Autowired PaymentService paymentService;
    @Autowired Clock clock;
    @Autowired ExRateProviderStub exRateProviderStub;

    @Test
    @DisplayName("[Spring] convertedAmount")
    void convertedAmount() throws IOException {
        // exRate: 1000
        Payment payment = paymentService.prepare(1L, "USD", TEN);

        assertThat(payment.getExRete()).isEqualByComparingTo(valueOf(1_000));
        assertThat(payment.getConvertedAmount()).isEqualByComparingTo(valueOf(10_000));

        // exRate: 500
        exRateProviderStub.setExRate(valueOf(500));
        Payment payment2 = paymentService.prepare(1L, "USD", TEN);

        assertThat(payment2.getExRete()).isEqualByComparingTo(valueOf(500));
        assertThat(payment2.getConvertedAmount()).isEqualByComparingTo(valueOf(5_000));
    }

    @Test
    @DisplayName("[Spring] 원화 환산금액의 유효시간 계산")
    void validUntil() throws IOException {
        Payment payment = paymentService.prepare(1L, "USD", TEN);

        // valid until 이 prepare() 30분 뒤로 설정됐는가?
        LocalDateTime now = LocalDateTime.now(this.clock);
        LocalDateTime expectedDate = now.plusMinutes(30);

        assertThat(payment.getValidUntil()).isEqualTo(expectedDate);
    }
}