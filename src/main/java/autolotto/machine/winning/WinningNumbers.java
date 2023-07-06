package autolotto.machine.winning;

import autolotto.machine.lotto.Lotto;
import autolotto.machine.lotto.LottoNumber;

import java.util.ArrayList;
import java.util.List;

public class WinningNumbers {
    private final Lotto winningNumbers;
    private final LottoNumber bonusNumber;

    public WinningNumbers(List<Integer> winningNumbers, Integer bonusNumber) {
        checkBonusIsDuplicatedNumber(winningNumbers, bonusNumber);
        this.winningNumbers = new Lotto(winningNumbers);
        this.bonusNumber = new LottoNumber(bonusNumber);
    }

    private void checkBonusIsDuplicatedNumber(List<Integer> winningNumbers, Integer bonusNumber) {
        if (winningNumbers.contains(bonusNumber)) {
            throw new IllegalArgumentException("보너스 번호는 당첨 번호와 중복될 수 없습니다.");
        }
    }

    public List<LottoNumber> winningNumbers() {
        return new ArrayList<>(this.winningNumbers.lottoNumbers());
    }

    public LottoNumber bonusNumber() {
        return this.bonusNumber;
    }
}
