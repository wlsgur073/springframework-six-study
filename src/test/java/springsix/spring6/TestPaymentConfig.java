package springsix.spring6;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springsix.spring6.payment.ExRateProvider;
import springsix.spring6.payment.ExRateProviderStub;
import springsix.spring6.payment.PaymentService;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

@Configuration
public class TestPaymentConfig {
    @Bean
    public PaymentService paymentService() {
        return new PaymentService(exRateProvider(), clock());
    }

    @Bean
    public ExRateProvider exRateProvider() {
        return new ExRateProviderStub(BigDecimal.valueOf(1_000));
    }

    @Bean
    public Clock clock() {
        return Clock.fixed(Instant.now(), ZoneId.systemDefault());
    }
}
