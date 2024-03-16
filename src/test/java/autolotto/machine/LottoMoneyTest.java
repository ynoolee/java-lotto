package autolotto.machine;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class LottoMoneyTest {

    private static final int LOTTO_PRICE = 1000;

    @Test
    public void 돈이_1000_보다_작을_경우_예외를_발생시킨다() {
        Assertions.assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> LottoMoney.of(400));
    }

    @Test
    public void 돈이_1000_의_배수가_아닐_경우_예외를_발생시킨다() {

        Assertions.assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> LottoMoney.of(1400));
    }

    @Test
    public void 주어진_돈으로_생성_가능한_로또_개수를_알려준다() {
        final LottoMoney lottoMoney = LottoMoney.of(3000);

        int count = lottoMoney.lottoCount();

        Assertions.assertThat(count).isEqualTo(3);
    }

    @Test
    public void 입력된_금액을_알려준다() {
        final LottoMoney lottoMoney = LottoMoney.of(3000);

        Assertions.assertThat(lottoMoney.money()).isEqualTo(3000);
    }

    @Test
    public void 주어진_개수의_로또_를_구입_할_수_있는지_알려준다() {
        final LottoMoney lottoMoney = LottoMoney.of(5000);

        Assertions.assertThat(lottoMoney.isLottoPurchasePossibleOf(6)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 1000, -2, 40})
    public void 음수이거나_최대로만들수_있는_로또개수_보다_큰_개수가_주어지면_return_False(int inputCount) {
        final int totalCount = 10;
        final LottoMoney lottoMoney = LottoMoney.of(LOTTO_PRICE * totalCount);

        Assertions.assertThat(lottoMoney.isLottoPurchasePossibleOf(inputCount)).isFalse();
    }
}
