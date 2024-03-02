package autolotto.machine.lotto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class LottoNumberTest {

    @Nested
    class EdgeCaseContext {

        @Test
        void 숫자_45_보다_큰_값으로_생성자_호출시_실패한다() {
            int edgeNumber = 45;
            int invalidNumber = edgeNumber + 1;
            Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                    .isThrownBy(() -> LottoNumber.of(invalidNumber));
        }

        @Test
        void 숫자_1_보다_작은_값으로_생성자_호출시_실패한다() {
            int edgeNumber = 1;
            int invalidNumber = edgeNumber - 1;

            Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                    .isThrownBy(() -> LottoNumber.of(invalidNumber));

        }
    }

    @Test
    void 동일한_값을_가진_LottoNumber_는_동일한_인스턴스다(){
        int value = 1;

        LottoNumber lottoNumber1 = LottoNumber.of(value);
        LottoNumber lottoNumber2 = LottoNumber.of(value);

        Assertions.assertThat(lottoNumber1 == lottoNumber2)
                .isTrue();
    }
}
