package autolotto.machine.lotto;

import autolotto.machine.winning.WinningNumbers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Lotto {
    private static final int LOTTO_NUMBER_COUNT = 6;

    private final Set<LottoNumber> numbers;

    public Lotto(List<Integer> numbers) {
        this(toSet(numbers));
    }

    private static Set<LottoNumber> toSet(List<Integer> numbers) {
        Set<LottoNumber> lotto = new HashSet<>();
        for (Integer numb : numbers) {
            lotto.add(LottoNumber.of(numb));
        }

        return lotto;
    }

    public Lotto(Set<LottoNumber> numbers) {
        this.numbers = numbers;
        checkCountOfNumbers();
    }

    private void checkCountOfNumbers() {
        if (this.numbers.size() != LOTTO_NUMBER_COUNT) {
            throw new IllegalArgumentException("로또는 중복되지 않은 6개의 수로 이루어져야 합니다");
        }
    }

    public int matchCount(WinningNumbers comparisonTarget) {
        ArrayList<LottoNumber> winningNumbers = new ArrayList<>(comparisonTarget.winningNumbers());

        return (int) winningNumbers.stream()
                .filter(this.numbers::contains)
                .count();
    }

    public int size() {
        return this.numbers.size();
    }

    public List<LottoNumber> lottoNumbers() {
        return new ArrayList<>(this.numbers);
    }

    public boolean contains(int number) {
        return numbers.contains(LottoNumber.of(number));
    }
}
