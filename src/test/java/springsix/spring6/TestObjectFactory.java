package springsix.spring6;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springsix.spring6.exreate.CachedExRateProvider;
import springsix.spring6.exreate.WebApiExRateProvider;
import springsix.spring6.payment.ExRateProvider;
import springsix.spring6.payment.ExRateProviderStub;
import springsix.spring6.payment.PaymentService;

import java.math.BigDecimal;

@Configuration
public class TestObjectFactory {
    @Bean
    public PaymentService paymentService() {
        return new PaymentService(exRateProvider());
    }

    @Bean
    public ExRateProvider exRateProvider() {
        return new ExRateProviderStub(BigDecimal.valueOf(1_000));
    }
}
