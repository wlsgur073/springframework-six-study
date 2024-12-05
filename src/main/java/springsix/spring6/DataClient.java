package springsix.spring6;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import springsix.spring6.data.OrderRepository;
import springsix.spring6.order.Order;

import java.math.BigDecimal;

public class DataClient {
    public static void main(String[] args) {
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(DataConfig.class);
        OrderRepository repository = beanFactory.getBean(OrderRepository.class);
        JpaTransactionManager transactionManager = beanFactory.getBean(JpaTransactionManager.class);

        try { // `Spring`에서는 자기들만의 예외를 번역해서 `Spring`이 정의해둔 추상화 `Exception`으로 번역하여 던지는 장점이 있다.
            new TransactionTemplate(transactionManager).execute(status -> {
                Order order = new Order("100", BigDecimal.TEN);
                repository.save(order);

                System.out.println("order = " + order);

                Order order2 = new Order("100", BigDecimal.ONE);
                repository.save(order2);

                return null;
            });
        } catch (DataIntegrityViolationException e) {
            System.out.println("주문번호 중복 복구 작업");
        }

        /*
        * 만약에 같은 로직이지만 다른 엔진을 사용했을때, 이것의 예외처리를 추상화 하는 것이 좋다.
        * 가령 `Hibernate`나, `EclipseLink`, `JDBCTemplate` 등, 결국 오브젝트 데이터를 DB에 저장하는 프로세스에서
        * 예외가 발생하지만, 해당 엔진들마다의 Custom Exception 이 각각 다른 이름으로 설정돼 있다.
        * 이런 상황을 대처하기 위해 추상화된 Exception 을 만들어서 통일시켜 주면 확장과 재사용성이 높아질 것이다.
        * */
    }
}
