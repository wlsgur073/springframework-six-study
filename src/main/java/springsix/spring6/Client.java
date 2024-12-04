package springsix.spring6;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springsix.spring6.payment.Payment;
import springsix.spring6.payment.PaymentService;

import java.math.BigDecimal;

public class Client {
    public static void main(String[] args) {
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(PaymentConfig.class);
        PaymentService service = beanFactory.getBean(PaymentService.class);

        Payment payment1 = service.prepare(100L, "USD", BigDecimal.valueOf(50.7));
        System.out.println("payment1 = " + payment1);
    }
}
