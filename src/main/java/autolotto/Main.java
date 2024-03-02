package autolotto;

import autolotto.dto.LottoDTO;
import autolotto.dto.Statistics;
import autolotto.dto.WinningAmount;
import autolotto.machine.LottoMachine;
import autolotto.machine.LottoMoney;
import autolotto.machine.ValidationUtil;
import autolotto.machine.lotto.LottoGenerator;
import autolotto.machine.lotto.RandomShuffler;
import autolotto.machine.winning.Winning;
import autolotto.machine.winning.WinningNumbers;
import autolotto.view.ConsoleView;
import calculator.parser.converter.IntegerStringConverter;

import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        ConsoleView consoleView = new ConsoleView();
        IntegerStringParser userInputParser = new IntegerStringParser(", ", new IntegerStringConverter());

        // todo : 예외처리
        int inputMoney = consoleView.inputPurchaseAmount();
        final LottoMoney lottoPurchaseMoney = LottoMoney.of(inputMoney);

        // todo : 예외처리
        int manualCount = consoleView.inputManualCount();
        ValidationUtil.checkValidManualCount(lottoPurchaseMoney, manualCount);


        LottoMachine lottoMachine =
                new LottoMachine(manualCount, new LottoGenerator(new RandomShuffler()), lottoPurchaseMoney);

        // TODO : 수동 로또 개수를 받야아함
        int autoLottoCount = lottoMachine.autoLottoCount();
        int manualLottoCount = lottoMachine.totalLottoCount() - autoLottoCount;

        consoleView.printLottoCount(autoLottoCount, manualLottoCount);
        consoleView.printLottoNumbers(
                lottoMachine.lotteries().stream()
                        .map(LottoDTO::from)
                        .collect(Collectors.toList()));

        WinningNumbers winningNumbers =
                new WinningNumbers(
                        userInputParser.parse(consoleView.inputWinningNumbers()),
                        consoleView.inputBonusNumber());

        consoleView.printStatistic(new Statistics(
                lottoMachine.profitRate(winningNumbers).toPlainString(),
                convertToWinningAmount(lottoMachine.winningState(winningNumbers))));
    }

    private static Map<WinningAmount, Integer> convertToWinningAmount(Map<Winning, Integer> lottoCountPerMatchingNumber) {
        return lottoCountPerMatchingNumber.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> new WinningAmount(entry.getKey().matchNumber(), entry.getKey().winningMoney()),
                        Map.Entry::getValue));
    }
}
