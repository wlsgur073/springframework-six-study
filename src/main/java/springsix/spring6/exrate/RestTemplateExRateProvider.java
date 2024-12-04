package springsix.spring6.exrate;

import org.springframework.web.client.RestTemplate;
import springsix.spring6.payment.ExRateProvider;

import java.math.BigDecimal;
import java.util.Objects;

public class RestTemplateExRateProvider implements ExRateProvider {
    private final RestTemplate restTemplate;

    public RestTemplateExRateProvider(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public BigDecimal getExRate(String currency) {
        String url = "https://open.er-api.com/v6/latest/" + currency;

        return Objects.requireNonNull(restTemplate.getForObject(url, ExRateData.class)).rates().get("KRW");
    }
}
