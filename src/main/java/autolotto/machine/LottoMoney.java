package autolotto.machine;

final public class LottoMoney {

    private final static int LOTTO_PRICE = 1000;

    private final int inputMoney;

    private final int maxLottoCount;


    private LottoMoney(final int input, final int maxLottoCount) {
        this.inputMoney = input;
        this.maxLottoCount = maxLottoCount;
    }

    public static LottoMoney of(final int input) {
        if (input < LOTTO_PRICE) {
            throw new RuntimeException("0 보다 큰 1000원 단위의 금액을 입력하세요"); // todo : 커스텀 예외 추가
        }

        int mod = input % LOTTO_PRICE;
        if (mod != 0) {
            throw new RuntimeException("0 보다 큰 1000원 단위의 금액을 입력하세요");
        }

        return new LottoMoney(input, input / LOTTO_PRICE);
    }

    public int lottoCount() {
        return this.maxLottoCount;
    }

    public int money() {
        return this.inputMoney;
    }

    public boolean isLottoPurchasePossibleOf(int count) {
        return !isNegativeManualCount(count) && isWithinMaxPurchaseLimit(count);
    }

    private boolean isWithinMaxPurchaseLimit(int count) {
        return this.maxLottoCount >= count;
    }

    private boolean isNegativeManualCount(int count) {
        return count < 0;
    }
}
