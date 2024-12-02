package springsix.spring6;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SortTest {
    Sort sort;

    @BeforeEach
    void setup() {
        sort = new Sort();
    }


    @Test
    void sort() {
        // 준비

        // 실행
        List<String> list = sort.sortByLength(Arrays.asList("aa", "b"));

        // 검증
        assertThat(list).isEqualTo(List.of("b", "aa"));
    }

    @Test
    void sort3Items() {
        List<String> list = sort.sortByLength(Arrays.asList("aa", "ccc", "b"));

        assertThat(list).isEqualTo(List.of("b", "aa", "ccc"));
    }

    @Test
    void sortAlreadySorted() {
        List<String> list = sort.sortByLength(Arrays.asList("b", "aa", "ccc"));

        assertThat(list).isEqualTo(List.of("b", "aa", "ccc"));
    }
}
