package springsix.spring6.learningTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;


public class ClockTest {
    @Test
    @DisplayName("clock 기본 활용하기")
    void clock() throws InterruptedException {
        Clock clock = Clock.systemDefaultZone();

        LocalDateTime dt1 = LocalDateTime.now(clock);
        Thread.sleep(1);
        LocalDateTime dt2 = LocalDateTime.now(clock);

        // 시계는 계속 움직이고 있다는 것을 확인하기
        assertThat(dt2).isAfter(dt1);
    }

    @Test
    @DisplayName("원하는 시간을 지정해서 현재 시간을 가져오기")
    void fixedClock() {
        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

        LocalDateTime dt1 = LocalDateTime.now(clock);
        LocalDateTime dt2 = LocalDateTime.now(clock);

        LocalDateTime dt3 = LocalDateTime.now(clock).plusHours(1);

        // 시간 고정
        assertThat(dt2).isEqualTo(dt1);
        assertThat(dt3).isEqualTo(dt1.plusHours(1)); // 시간 테스트 확인하기 편하다.
    }
}
