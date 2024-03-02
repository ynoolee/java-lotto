package autolotto.machine;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ValidationUtilTest {

    @Test
    public void 수동_로또_개수가_주어졌을_때_구매가능_개수가_아닌_경우_예외를_발생시킨다() {
        // given
        int manualCount = 3;
        final LottoMoney lottoPurchaseMoney = LottoMoney.of(1000 * (manualCount - 1));

        // when, then
        Assertions.assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> ValidationUtil.checkValidManualCount(lottoPurchaseMoney, manualCount));
    }
}
