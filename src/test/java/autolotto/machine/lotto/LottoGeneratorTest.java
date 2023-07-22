package autolotto.machine.lotto;

import autolotto.InvalidManualLottoException;
import autolotto.machine.lotto.fixture.FixedNumberShuffler;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class LottoGeneratorTest {

    private final LottoGenerator lottoGenerator = new LottoGenerator(new FixedNumberShuffler());

    @Test
    void 로또생성기는_1에서_45_사이의_번호인_LottoNumber_6개를_생성한다() {
        List<LottoNumber> numbers = lottoGenerator.generateLotto().lottoNumbers();

        int createdNumbersSize = numbers.size();

        Assertions.assertThat(createdNumbersSize).isEqualTo(6);
    }

    @Test
    void 로또생성기는_중복되지_않은_수_6개를_생성한다() {
        List<LottoNumber> numbers = lottoGenerator.generateLotto().lottoNumbers();

        Assertions.assertThat(numbers)
                .extracting(LottoNumber::value)
                .doesNotHaveDuplicates();
    }

    @Test
    void 주어진_개수만큼의_로또를_생성한다() {
        int lottoCount = 3;

        List<Lotto> lotteries = lottoGenerator.generateMultipleLotto(lottoCount);

        Assertions.assertThat(lotteries).hasSize(lottoCount);
    }

    @Nested
    class ManuallyGenerateTest {

        @Test
        void F_수동로또_번호로_유효하지않은_로또넘버가_포함된_경우_생성에_실패한다() {
            int invalidLottoNumber = 55;
            List<Integer> lottoNumbers = Arrays.asList(invalidLottoNumber, 1, 2, 3, 4, 5);

            Assertions.assertThatExceptionOfType(InvalidManualLottoException.class)
                    .isThrownBy(() -> lottoGenerator.generateManualLotto(lottoNumbers));
        }


        @Test
        void S_수동로또_번호로_유효한_로또넘버들만이_포함된_경우_주어진_번호들로_이루어진_로또_생성에_성공한다() {
            List<Integer> lottoNumbers = Arrays.asList(1, 2, 3, 4, 5, 6);

            Lotto manuallyGeneratedLotto = lottoGenerator.generateManualLotto(lottoNumbers);

            lottoNumbers.forEach(number -> Assertions.assertThat(manuallyGeneratedLotto.contains(number)).isTrue());
        }

        @Test
        void F_수동로또_번호_6개가_주어지지_않는경우_생성에_실패한다() {
            List<Integer> lottoNumbers = Arrays.asList(1, 2, 3, 4, 5);

            Assertions.assertThatExceptionOfType(InvalidManualLottoException.class)
                    .isThrownBy(() -> lottoGenerator.generateManualLotto(lottoNumbers));
        }
    }

}
