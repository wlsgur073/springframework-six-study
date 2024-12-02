package springsix.spring6.payment;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;

public class Payment {
    private Long orderId;
    private String currency;
    private BigDecimal foreignCurrencyAmount; // 해당 데이터 타입을 사용하여 부동소수점을 항상 유의해야 한다.
    private BigDecimal exRete;
    private BigDecimal convertedAmount;
    private LocalDateTime validUntil;

    public Payment(Long orderId, String currency, BigDecimal foreignCurrencyAmount, BigDecimal exRete, BigDecimal convertedAmount, LocalDateTime validUntil) {
        this.orderId = orderId;
        this.currency = currency;
        this.foreignCurrencyAmount = foreignCurrencyAmount;
        this.exRete = exRete;
        this.convertedAmount = convertedAmount;
        this.validUntil = validUntil;
    } // BigDecimal 매개변수가 3개다. 잘못 입력하면 무엇이 A고 B인지 모를 수 있다. 따라서 builder 패턴 등의 기법을 이용하는 것이 좋다.

    // 도메인 오브젝트를 활용하여 Service 레이어의 코드를 조금 더 경량화 시킨다.
    public static Payment createPrepared(Long orderId, String currency, BigDecimal foreignCurrencyAmount, BigDecimal exRate, LocalDateTime now) {
        BigDecimal convertedAmount = foreignCurrencyAmount.multiply(exRate);
        LocalDateTime validUntil = now.plusMinutes(30);

        return new Payment(orderId, currency, foreignCurrencyAmount, exRate, convertedAmount, validUntil);
    }

    public boolean isValid(Clock clock) {
        return LocalDateTime.now(clock).isBefore(this.validUntil);
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getForeignCurrencyAmount() {
        return foreignCurrencyAmount;
    }

    public BigDecimal getExRete() {
        return exRete;
    }

    public BigDecimal getConvertedAmount() {
        return convertedAmount;
    }

    public LocalDateTime getValidUntil() {
        return validUntil;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "orderId=" + orderId +
                ", currency='" + currency + '\'' +
                ", foreignCurrencyAmount=" + foreignCurrencyAmount +
                ", exRete=" + exRete +
                ", convertedAmount=" + convertedAmount +
                ", validUntil=" + validUntil +
                '}';
    }
}
