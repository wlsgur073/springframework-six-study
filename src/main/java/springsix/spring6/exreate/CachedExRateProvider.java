package springsix.spring6.exreate;

import springsix.spring6.payment.ExRateProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CachedExRateProvider implements ExRateProvider {
    private final ExRateProvider target;

    private BigDecimal cachedExRate;
    private LocalDateTime cachedLastUpdate;

    public CachedExRateProvider(ExRateProvider target) {
        this.target = target;
    }

    @Override
    public BigDecimal getExRate(String currency) throws IOException {
        if (cachedExRate == null || cachedLastUpdate.isBefore(LocalDateTime.now())) {
            cachedExRate = this.target.getExRate(currency);
            cachedLastUpdate = LocalDateTime.now().plusSeconds(3);

            System.out.println("Cache Updated");
        }
        return cachedExRate;
    }
}
