package autolotto.machine;

import autolotto.machine.lotto.Lotto;
import autolotto.machine.lotto.LottoGenerator;
import autolotto.machine.lotto.LottoWallet;
import autolotto.machine.winning.Winning;
import autolotto.machine.winning.WinningNumbers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LottoMachine {
    private final static int PROFIT_RATE_SCALE = 2;

    private final LottoGenerator lottoGenerator;
    private final LottoWallet wallet;
    private final LottoMoney lottoMoney;
    private final int manualCount;

    public LottoMachine(int inputManualCount, LottoGenerator lottoGenerator, LottoMoney lottoMoney) {
        this.lottoGenerator = lottoGenerator;
        this.lottoMoney = lottoMoney;
        this.manualCount = inputManualCount;
        this.wallet = initAutoLotto(inputManualCount, lottoGenerator);
    }

    private LottoWallet initAutoLotto(int manualCount, LottoGenerator lottoGenerator) {
        final LottoWallet wallet = new LottoWallet();
        final int autoLottoCount = this.lottoMoney.lottoCount() - manualCount;

        for (int i = 0; i < autoLottoCount; i++) {
            wallet.addLotto(lottoGenerator.generateLotto());
        }
        return wallet;
    }

    public void addManualLotteries(List<List<Integer>> lottoNumbers) {
        lottoNumbers.forEach(this::addManualLotto);
    }

    private void addManualLotto(List<Integer> lottoNumbers) {
        this.wallet.addLotto(lottoGenerator.generateManualLotto(lottoNumbers));
    }

    public List<Lotto> lotteries() {
        return this.wallet.allLotteries();
    }

    public BigDecimal profitRateWhen(WinningNumbers winningNumbers) {
        int totalWinnings = Winning.totalAmountOf(wallet.allLotteries(), winningNumbers);
        return BigDecimal.valueOf(totalWinnings)
                .divide(BigDecimal.valueOf(this.lottoMoney.money()), PROFIT_RATE_SCALE, RoundingMode.HALF_UP);
    }

    public Map<Winning, Integer> winningStateWhen(WinningNumbers winningNumbers) {
        Map<Winning, Integer> countPerEachPrize = new HashMap<>();

        for (Winning winning : Winning.values()) {
            int lottoCount = wallet.countOfLottoMatchingWith(winningNumbers, winning.matchNumber());
            countPerEachPrize.put(winning, lottoCount);
        }
        return countPerEachPrize;
    }

    public int autoLottoCount() {
        return this.lottoMoney.lottoCount() - this.manualCount;
    }

    public int manualLottoCount() {
        return this.manualCount;
    }

    public int totalLottoCount() {
        return this.wallet.lottoSize();
    }
}
