package autolotto.machine.lotto;

import autolotto.InvalidManualLottoException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LottoGenerator {
    private final Shuffler shuffler;

    private static final Integer LOTTO_MIN_NUMBER = 1;
    private static final Integer LOTTO_MAX_NUMBER = 45;
    private static final Integer LOTTO_NUMBERS_SIZE = 6;

    public LottoGenerator(Shuffler shuffler) {
        this.shuffler = shuffler;
    }

    public Lotto generateLotto() {
        return new Lotto(generateLottoRangeNumbers());
    }

    public Lotto generateManualLotto(List<Integer> lottoNumbers) {
        verifyValidLottoNumbers(lottoNumbers);

        return new Lotto(lottoNumbers);
    }

    private void verifyValidLottoNumbers(List<Integer> lottoNumbers) {
        if (lottoNumbers.size() != LOTTO_NUMBERS_SIZE) {
            throw new InvalidManualLottoException("6개의 수를 입력해주세요");
        }
        for (Integer lottoNumber : lottoNumbers) {
            verifyValidLottoNumber(lottoNumber);
        }
    }

    private void verifyValidLottoNumber(Integer target) {
        if (target > LOTTO_MAX_NUMBER || target < LOTTO_MIN_NUMBER) {
            throw new InvalidManualLottoException("적절하지 않은 로또 넘버가 포함되어있습니다");
        }
    }

    private List<Integer> generateLottoRangeNumbers() {
        List<Integer> shuffled = shuffler.shuffle(lottoRangeNumbers());

        return shuffled.stream()
                .limit(LOTTO_NUMBERS_SIZE)
                .sorted()
                .collect(Collectors.toList());
    }

    private List<Integer> lottoRangeNumbers() {
        return IntStream.range(LOTTO_MIN_NUMBER, LOTTO_MAX_NUMBER + 1)
                .boxed()
                .collect(Collectors.toList());
    }

    public List<Lotto> generateMultipleLotto(int lottoCount) {
        List<Lotto> lottoCollection = new ArrayList<>();

        for (int count = 0; count < lottoCount; count++) {
            lottoCollection.add(generateLotto());
        }

        return lottoCollection;
    }
}
