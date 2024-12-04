package springsix.spring6;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springsix.spring6.api.ApiTemplate;
import springsix.spring6.exrate.CachedExRateProvider;
import springsix.spring6.exrate.WebApiExRateProvider;
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
    public ApiTemplate apiTemplate() {
        return new ApiTemplate(); // 매번 새로운 인스턴스를 생성할 필요가 없기에 빈으로 설정.
    }

    @Bean
    public ExRateProvider exRateProvider() {
        return new WebApiExRateProvider(apiTemplate());
    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
