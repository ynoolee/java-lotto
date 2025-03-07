package autolotto.machine.lotto;

import autolotto.machine.winning.WinningNumbers;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class LottoTest {

    @Test
    void 당첨번호와_비교해_일치하는_숫자의_개수를_알려준다() {
        int anyBonusNumber = 7;
        List<Integer> winningNumberIntegers = Arrays.asList(1, 2, 3, 4, 5, 6);
        List<Integer> lottoNumberIntegers = Arrays.asList(1, 2, 23, 24, 25, 21);

        WinningNumbers winningNumbers =
                new WinningNumbers(winningNumberIntegers, anyBonusNumber);
        Lotto lotto = new Lotto(lottoNumberIntegers);

        int matchCount = lotto.matchCount(winningNumbers);

        Assertions.assertThat(matchCount).isEqualTo(2);
    }

    @Test
    void 해당_넘버를_갖고_있는지_알려준다() {
        Lotto lotto = new Lotto(Arrays.asList(1, 2, 21, 23, 24, 25));
        int number = 21;

        Assertions.assertThat(lotto.contains(number)).isTrue();
    }

    @Test
    void 중복된_수_가_포함된_6개의_로또넘버로_로또를_생성하려_할_경우_예외가_발생한다() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Lotto(Arrays.asList(1, 1, 2, 3, 4, 5)));
    }

    @Test
    void 개수가_6개가_아닌_로또넘버들로_로또를_생성하려_할_경우_예외가_발생한다() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Lotto(randomListGenerator(5)));
    }

    private List<Integer> randomListGenerator(int count) {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 45; i++) {
            numbers.add(i);
        }

        Collections.shuffle(numbers);

        return numbers.subList(0, count);
    }
}
