package autolotto.machine;

import autolotto.machine.lotto.Lotto;
import autolotto.machine.lotto.LottoGenerator;
import autolotto.machine.lotto.fixture.FixedNumberShuffler;
import autolotto.machine.winning.Winning;
import autolotto.machine.winning.WinningNumbers;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class LottoMachineTest {

    private final Integer bonusNumberAnyLottoNotContaining = 11;

    private final static int LOTTO_PRICE = 1000;

    private final int manualCount = 0;

    @Test
    void 총_로또의_개수를_알려준다() {
        // TODO : Money 를 캡슐화
        int inputMoney = LOTTO_PRICE * 2;

        LottoMachine lottoMachine =
                new LottoMachine(manualCount, new LottoGenerator(new FixedNumberShuffler()), LottoMoney.of(inputMoney));

        Assertions.assertThat(lottoMachine.totalLottoCount()).isEqualTo(inputMoney / LOTTO_PRICE);
    }

    @Test
    void 수동_로또_를_입력하지_않은_상황에서_lotteries_호출_시_자동로또_개수가_리턴된다() {
        // given
        int totalCount = 5;
        int manualCount = 2;
        LottoMachine lottoMachine =
                new LottoMachine(manualCount, new LottoGenerator(new FixedNumberShuffler()), LottoMoney.of(LOTTO_PRICE * totalCount));

        // when
        final List<Lotto> lotteries = lottoMachine.lotteries();

        // then
        Assertions.assertThat(lotteries.size()).isEqualTo(totalCount - manualCount);
        Assertions.assertThat(lotteries.size()).isNotEqualTo(totalCount);
    }

    @Test
    void 금액에_따라_로또를_생성해_갖고_있다() {
        // given
        int inputMoney = 2000;

        LottoMachine lottoMachine =
                new LottoMachine(manualCount, new LottoGenerator(new FixedNumberShuffler()), LottoMoney.of(inputMoney));

        // when
        List<Lotto> createdLotteries = lottoMachine.lotteries();

        // then
        Assertions.assertThat(createdLotteries).hasSize(inputMoney / LOTTO_PRICE);
    }


    @Nested
    class GivenManualLottoCountContext {

        private final int moneyAmount = 10000;

        @Test
        void F_수동로또의_개수가_음수로_주어진_경우_로또머신_생성에_실패한다() {
            int anyInputMoney = 10000;
            int invalidManualCount = -1;

            Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                    .isThrownBy(() -> new LottoMachine(
                            invalidManualCount,
                            new LottoGenerator(new FixedNumberShuffler()),
                            LottoMoney.of(anyInputMoney)
                    ));
        }

        @Test
        void F_수동로또의_개수가_총_로또_개수보다_큰_경우_로또머신_생성에_실패한다() {
            final int expectedTotalCount = moneyAmount / LOTTO_PRICE;

            Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                    .isThrownBy(() -> new LottoMachine(
                            expectedTotalCount + 1,
                            new LottoGenerator(new FixedNumberShuffler()),
                            LottoMoney.of(moneyAmount)));
        }
    }

    @Test
    void S_로또머신은_자동_로또_개수를_알려준다() {
        final int moneyAmount = 10000;
        final int manualCount = 6;
        LottoMachine lottoMachine = new LottoMachine(manualCount, new LottoGenerator(new FixedNumberShuffler()), LottoMoney.of(moneyAmount));

        int autoCount = lottoMachine.autoLottoCount();

        Assertions.assertThat(autoCount)
                .isEqualTo(moneyAmount / LOTTO_PRICE - manualCount);
    }

    @Nested
    class ManuallyGeneratedLottoTest {
        private LottoMachine lottoMachine;

        @BeforeEach
        void setUp() {
            int inputMoney = 2000;
            int anyManualCount = 1;
            lottoMachine = new LottoMachine(anyManualCount, new LottoGenerator(new FixedNumberShuffler()), LottoMoney.of(inputMoney));
        }

        @Test
        void S_수동_로또번호_리스트가_주어지면_해당_수동로또들을_생성한다() {
            int beforeAddTotalCount = lottoMachine.lotteries().size();

            lottoMachine.addManualLotteries(List.of(Arrays.asList(1, 2, 3, 4, 5, 6)));

            int afterAddTotalCount = lottoMachine.lotteries().size();

            Assertions.assertThat(afterAddTotalCount).isEqualTo(beforeAddTotalCount + 1);
        }
    }

    @Test
    void 당첨번호가_주어지면_수익률을_소수점_둘째_자리까지_반올림한_값으로_알려준다() {
        LottoMachine lottoMachine = new LottoMachine(manualCount, new LottoGenerator(new FixedNumberShuffler()), LottoMoney.of(3000));
        BigDecimal expectedProfitRate = BigDecimal.valueOf((double) Winning.THREE.winningMoney() * 3 / 3000).setScale(2);
        WinningNumbers winningNumbers = new WinningNumbers(Arrays.asList(1, 2, 3, 21, 22, 23), bonusNumberAnyLottoNotContaining);

        BigDecimal profitRate = lottoMachine.profitRateWhen(winningNumbers);

        Assertions.assertThat(profitRate).isEqualTo(expectedProfitRate);
    }

    @Test
    void 당첨번호가_주어지면_일치_개수별_로또_개수를_알려준다() {
        LottoMachine lottoMachineWithSameThreeLotto = new LottoMachine(manualCount, new LottoGenerator(new FixedNumberShuffler()), LottoMoney.of(3000));
        WinningNumbers winningNumbers = new WinningNumbers(Arrays.asList(1, 2, 3, 21, 22, 23), bonusNumberAnyLottoNotContaining);

        Map<Winning, Integer> numberOfEachMatchingCount = lottoMachineWithSameThreeLotto.winningStateWhen(winningNumbers);

        Assertions.assertThat(numberOfEachMatchingCount.get(Winning.THREE)).isEqualTo(3);
    }
}
