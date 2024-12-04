package springsix.spring6.api;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

public class ApiTemplate {
    private final ApiExecutor defaultApiExecutor;
    private final ExRateExtractor defaultExRateExtractor;

    public ApiTemplate() {
        this.defaultApiExecutor = new HttpClientApiExecutor();
        this.defaultExRateExtractor = new ErApiExRateExtractor();
    }

    public ApiTemplate(ApiExecutor defaultApiExecutor, ExRateExtractor defaultExRateExtractor) {
        this.defaultApiExecutor = defaultApiExecutor;
        this.defaultExRateExtractor = defaultExRateExtractor;
    }

    public BigDecimal getExRate(String url) {
        return this.getExRate(url, this.defaultApiExecutor, this.defaultExRateExtractor);
    }

    public BigDecimal getExRate(String url, ApiExecutor apiExecutor) {
        return this.getExRate(url, apiExecutor, this.defaultExRateExtractor);
    }

    public BigDecimal getExRate(String url, ExRateExtractor exRateExtractor) {
        return this.getExRate(url, this.defaultApiExecutor, exRateExtractor);
    }

    public BigDecimal getExRate(String url, ApiExecutor apiExecutor, ExRateExtractor exRateExtractor) {
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
