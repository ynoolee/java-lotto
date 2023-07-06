package autolotto.machine.lotto;

import java.util.HashMap;
import java.util.Map;

public class LottoNumber {
    private static final int LOTTO_MIN_NUMBER = 1;
    private static final int LOTTO_MAX_NUMBER = 45;
    private static final Map<Integer, LottoNumber> cachedNumbers = new HashMap<>();

    static {
        for (int i = LOTTO_MIN_NUMBER; i <= LOTTO_MAX_NUMBER; i++) {
            cachedNumbers.put(i, new LottoNumber(i));
        }
    }

    private final int number;

    private LottoNumber(int number) {
        this.number = number;
    }

    public static LottoNumber of(int number) {
        LottoNumber lottoNumber = cachedNumbers.get(number);
        if (lottoNumber == null) {
            throw new IllegalArgumentException("로또 번호는 1과 45 사이의 값이어야 합니다.");
        }

        return lottoNumber;
    }

    public int value() {
        return this.number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LottoNumber)) return false;

        LottoNumber that = (LottoNumber) o;

        return number == that.number;
    }

    @Override
    public int hashCode() {
        return number;
    }
}
