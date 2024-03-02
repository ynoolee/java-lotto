package autolotto.machine.winning;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class WinningNumbersTest {

    @Test
    void 당첨번호가_보너스_번호에_중복될_경우_예외가_발생한다() {
        int duplicatedBonusNumber = 1;

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new WinningNumbers(Arrays.asList(1, 2, 3, 4, 5, 6), duplicatedBonusNumber));
    }
}
