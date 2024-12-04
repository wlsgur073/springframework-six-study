package springsix.spring6.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import springsix.spring6.exreate.ExRateData;

import java.math.BigDecimal;

public class ErApiExRateExtractor implements ExRateExtractor{
    @Override
    public BigDecimal extract(String resp) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ExRateData data = mapper.readValue(resp, ExRateData.class);
        return data.rates().get("KRW");
    }
}
