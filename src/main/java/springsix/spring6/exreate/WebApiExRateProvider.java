package springsix.spring6.exreate;

import springsix.spring6.api.ApiTemplate;
import springsix.spring6.api.ErApiExRateExtractor;
import springsix.spring6.api.HttpClientApiExecutor;
import springsix.spring6.payment.ExRateProvider;

import java.math.BigDecimal;

public class WebApiExRateProvider implements ExRateProvider {
    ApiTemplate apiTemplate = new ApiTemplate();

    @Override
    public BigDecimal getExRate(String currency) {
        String url = "https://open.er-api.com/v6/latest/" + currency;

        return apiTemplate.getExRate(url, new HttpClientApiExecutor(), new ErApiExRateExtractor());
    }
}
