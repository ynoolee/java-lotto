package autolotto.machine;

public class ValidationUtil {

    private ValidationUtil() {
    }

    public static void checkValidManualCount(final LottoMoney lottoPurchaseMoney, final int manualCount) {
        if (!lottoPurchaseMoney.isLottoPurchasePossibleOf(manualCount)) {
            throw new RuntimeException();
        }
    }
}
