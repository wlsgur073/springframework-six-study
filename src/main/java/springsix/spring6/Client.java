package springsix.spring6;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springsix.spring6.payment.Payment;
import springsix.spring6.payment.PaymentService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

public class Client {
    public static void main(String[] args) throws IOException, InterruptedException {
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(PaymentConfig.class);
        PaymentService service = beanFactory.getBean(PaymentService.class);

        Payment payment1 = service.prepare(100L, "USD", BigDecimal.valueOf(50.7));
        System.out.println("payment1 = " + payment1);

        System.out.println("================================");

        Payment payment2 = service.prepare(100L, "USD", BigDecimal.valueOf(50.7));
        System.out.println("payment2 = " + payment2);

        TimeUnit.SECONDS.sleep(3);

        System.out.println("================================");

        Payment payment3 = service.prepare(100L, "USD", BigDecimal.valueOf(50.7));
        System.out.println("payment3 = " + payment3);
    }
}
