package springsix.spring6;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.math.BigDecimal;

public class Client {
    public static void main(String[] args) throws IOException {
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(ObjectFactory.class);
        PaymentService service = beanFactory.getBean(PaymentService.class);

        Payment payment = service.prepare(100L, "USD", BigDecimal.valueOf(50.7));
        System.out.println("payment = " + payment);
    }
}
