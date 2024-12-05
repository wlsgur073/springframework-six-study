package springsix.spring6;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springsix.spring6.data.OrderRepository;
import springsix.spring6.order.Order;

import java.math.BigDecimal;

public class DataClient {
    public static void main(String[] args) {
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(DataConfig.class);
        OrderRepository repository = beanFactory.getBean(OrderRepository.class);

        Order order = new Order("100", BigDecimal.TEN);
        repository.save(order);

        System.out.println("order = " + order);

        /*
        * 만약에 같은 로직이지만 다른 엔진을 사용했을때, 이것의 예외처리를 추상화 하는 것이 좋다.
        * 가령 `Hibernate`나, `EclipseLink`, `JDBCTemplate` 등, 결국 오브젝트 데이터를 DB에 저장하는 프로세스에서
        * 예외가 발생하지만, 해당 엔진들마다의 Custom Exception 이 각각 다른 이름으로 설정돼 있다.
        * 이런 상황을 대처하기 위해 추상화된 Exception 을 만들어서 통일시켜 주면 확장과 재사용성이 높아질 것이다.
        * */

        try {
            Order order2 = new Order("100", BigDecimal.ONE);
            repository.save(order2);
        } catch (ConstraintViolationException e) {
            System.out.println("주문 번호 충돌을 복구하는 작업");
        }
    }
}
