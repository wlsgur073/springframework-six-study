package springsix.spring6.exreate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.Map;

// 레코드는 일종의 생성자이다.
@JsonIgnoreProperties(ignoreUnknown = true) // UnrecognizedPropertyException: Unrecognized field "provider" 해결
public record ExRateData(String result, Map<String, BigDecimal> rates) {
}
