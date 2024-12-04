package springsix.spring6;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springsix.spring6.exreate.CachedExRateProvider;
import springsix.spring6.exreate.WebApiExRateProvider;
import springsix.spring6.payment.ExRateProvider;
import springsix.spring6.payment.PaymentService;

import java.time.Clock;

@Configuration
public class PaymentConfig {
    @Bean
    public PaymentService paymentService() {
        return new PaymentService(exRateProvider(), clock());
    }

    @Bean
    public ExRateProvider cachedExRateProvider() {
        return new CachedExRateProvider(exRateProvider());
    }

    @Bean
    public ExRateProvider exRateProvider() {
        return new WebApiExRateProvider();
    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
