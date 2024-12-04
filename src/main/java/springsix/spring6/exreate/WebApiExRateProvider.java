package springsix.spring6.exreate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import springsix.spring6.api.ApiExecutor;
import springsix.spring6.api.ExRateExtractor;
import springsix.spring6.api.SimpleApiExecutor;
import springsix.spring6.payment.ExRateProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

public class WebApiExRateProvider implements ExRateProvider {
    @Override
    public BigDecimal getExRate(String currency) { // 1.클라이언트가
        String url = "https://open.er-api.com/v6/latest/" + currency;

        return runApiForExRate(url, new SimpleApiExecutor(), resp -> {
            ObjectMapper mapper = new ObjectMapper();
            ExRateData data = mapper.readValue(resp, ExRateData.class);
            return data.rates().get("KRW"); // callback 오브젝트를 람다 함수로 만들어봄, new ErApiExRateExtractor()로 대체 가능
        }); // 2.callback 만들고
    }

    private static BigDecimal runApiForExRate(String url, ApiExecutor apiExecutor, ExRateExtractor exRateExtractor) { // 3.템플릿의 메소드를 호출한다.
        URI uri = null;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        String resp;
        try {
            resp = apiExecutor.execute(uri);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            return exRateExtractor.extract(resp);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
