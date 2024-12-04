package springsix.spring6.api;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.math.BigDecimal;

public interface ExRateExtractor {
    BigDecimal extract(String resp) throws JsonProcessingException;
}
