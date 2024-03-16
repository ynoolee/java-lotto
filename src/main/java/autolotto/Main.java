package autolotto;

import autolotto.dto.LottoDTO;
import autolotto.dto.Statistics;
import autolotto.machine.LottoMachine;
import autolotto.machine.LottoMoney;
import autolotto.machine.ValidationUtil;
import autolotto.machine.lotto.LottoGenerator;
import autolotto.machine.lotto.RandomShuffler;
import autolotto.machine.winning.Winning;
import autolotto.machine.winning.WinningNumbers;
import autolotto.view.ConsoleView;
import calculator.parser.converter.IntegerStringConverter;

import java.math.BigDecimal;
import java.util.List;
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

        int autoLottoCount = lottoMachine.autoLottoCount();
        int manualLottoCount = lottoMachine.manualLottoCount();
        consoleView.printLottoCount(autoLottoCount, manualLottoCount);

        // TODO : 예외처리
        final List<String> manualLotteries = consoleView.inputManualLotto(manualCount);
        final List<List<Integer>> manualLotteriesList = manualLotteries.stream()
                .map(userInputParser::parse)
                .collect(Collectors.toList());
        lottoMachine.addManualLotteries(manualLotteriesList);

        consoleView.printLottoNumbers(
                lottoMachine.lotteries().stream()
                        .map(LottoDTO::from)
                        .collect(Collectors.toList()));

        WinningNumbers winningNumbers =
                new WinningNumbers(
                        userInputParser.parse(consoleView.inputWinningNumbers()),
                        consoleView.inputBonusNumber());

        final BigDecimal profit = lottoMachine.profitRateWhen(winningNumbers);
        final Map<Winning, Integer> winningResult = lottoMachine.winningStateWhen(winningNumbers);

        consoleView.printStatistic(new Statistics(profit.toString(), winningResult));
    }
}
