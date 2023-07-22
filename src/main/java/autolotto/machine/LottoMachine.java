package autolotto.machine;

import autolotto.machine.lotto.Lotto;
import autolotto.machine.lotto.LottoGenerator;
import autolotto.machine.lotto.LottoWallet;
import autolotto.machine.winning.Winning;
import autolotto.machine.winning.WinningNumbers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LottoMachine {
    private final static int LOTTO_PRICE = 1000;
    private final static int PROFIT_RATE_SCALE = 2;

    private final int inputMoney;
    private final int manualCount;
    private final LottoWallet wallet;

    public LottoMachine(int inputManualCount, LottoGenerator lottoGenerator, int inputMoney) {
        final int totalLottoCount = calculateLottoCount(inputMoney);

        checkInvalidManualLottoCount(totalLottoCount, inputManualCount);

        this.manualCount = inputManualCount;
        this.inputMoney = inputMoney;
        this.wallet = initWallet(lottoGenerator, totalLottoCount);
    }

    private static LottoWallet initWallet(LottoGenerator lottoGenerator, int totalLottoCount) {
        LottoWallet wallet = new LottoWallet();
        for (int i = 0; i < totalLottoCount; i++) {
            wallet.addLotto(lottoGenerator.generateLotto());
        }
        return wallet;
    }

    private static int calculateLottoCount(int inputMoney) {
        return inputMoney / LOTTO_PRICE;
    }

    private static void checkInvalidManualLottoCount(int totalCount, int manualCount) {
        if (isNegativeManualCount(manualCount) || isInvalidManualCount(totalCount, manualCount)) {
            throw new IllegalArgumentException("수동 로또의 개수가 적절하지 않은 값입니다");
        }
    }

    private static boolean isNegativeManualCount(int manualCount) {
        return manualCount < 0;
    }

    private static boolean isInvalidManualCount(int totalCount, int manualCount) {
        return manualCount > totalCount;
    }

    public List<Lotto> lotteries() {
        return this.wallet.allLotteries();
    }

    public BigDecimal profitRate(WinningNumbers winningNumbers) {
        int totalWinnings = Winning.totalAmountOf(wallet.allLotteries(), winningNumbers);
        return BigDecimal.valueOf(totalWinnings)
                .divide(BigDecimal.valueOf(this.inputMoney), PROFIT_RATE_SCALE, RoundingMode.HALF_UP);
    }

    public Map<Winning, Integer> winningState(WinningNumbers winningNumbers) {
        Map<Winning, Integer> lottoCountPerEachMatchingCount = setZeroToAllMatchingCount();

        for (Winning winning : lottoCountPerEachMatchingCount.keySet()) {
            int lottoCount = wallet.countOfLottoMatchingWith(winningNumbers, winning.matchNumber());
            lottoCountPerEachMatchingCount.put(winning, lottoCount);
        }

        return lottoCountPerEachMatchingCount;
    }

    private Map<Winning, Integer> setZeroToAllMatchingCount() {
        Map<Winning, Integer> lottoCountPerEachMatchingCount = new HashMap<>();

        Arrays.stream(Winning.values())
                .forEach(winning -> lottoCountPerEachMatchingCount.put(winning, 0));

        return lottoCountPerEachMatchingCount;
    }

    public int autoLottoCount() {
        return this.totalLottoCount() - this.manualCount;
    }

    public int totalLottoCount() {
        return this.wallet.lottoSize();
    }
}
