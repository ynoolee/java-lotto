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
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Main {
    private static final ConsoleView consoleView = new ConsoleView();
    private static final IntegerStringParser userInputParser = new IntegerStringParser(", ", new IntegerStringConverter());

    public static void main(String[] args) {
        // 로또 구입금액 입력받는 과정
        final LottoMoney lottoPurchaseMoney = handleException(Main::getLottoMoney, "유효한 금액이 아닙니다");

        // 수동로또 개수 입력받는 과정
        int manualCount = handleException(() -> getManualCount(lottoPurchaseMoney), "유효한 로또 개수가 아닙니다");

        LottoMachine lottoMachine =
                new LottoMachine(manualCount, new LottoGenerator(new RandomShuffler()), lottoPurchaseMoney);

        int autoLottoCount = lottoMachine.autoLottoCount();
        int manualLottoCount = lottoMachine.manualLottoCount();
        consoleView.printLottoCount(autoLottoCount, manualLottoCount);

        // 수동 로또들을 입력받는 과정
        handleException(() -> getManualLotteries(manualCount, lottoMachine), "유효한 로또번호가 아닙니다");

        consoleView.printLottoNumbers(
                lottoMachine.lotteries().stream()
                        .map(LottoDTO::from)
                        .collect(Collectors.toList()));

        // 당첨번호를 입력받는 과정
        WinningNumbers winningNumbers =
                handleException(Main::getWinningNumbers, "유효한 당첨번호와 보너스볼이 아닙니다");

        final BigDecimal profit = lottoMachine.profitRateWhen(winningNumbers);
        final Map<Winning, Integer> winningResult = lottoMachine.winningStateWhen(winningNumbers);

        consoleView.printStatistic(new Statistics(profit.toString(), winningResult));
    }

    private static WinningNumbers getWinningNumbers() {
        return new WinningNumbers(
                userInputParser.parse(consoleView.inputWinningNumbers()),
                consoleView.inputBonusNumber());
    }

    private static List<List<Integer>> getManualLotteries(final int manualCount, final LottoMachine lottoMachine) {
        final List<String> manualLotteries = consoleView.inputManualLotto(manualCount);
        final List<List<Integer>> manualLotteriesList = manualLotteries.stream()
                .map(userInputParser::parse)
                .collect(Collectors.toList());
        lottoMachine.addManualLotteries(manualLotteriesList);

        return manualLotteriesList;
    }

    private static int getManualCount(final LottoMoney lottoPurchaseMoney) {
        int manualCount = consoleView.inputManualCount();
        ValidationUtil.checkValidManualCount(lottoPurchaseMoney, manualCount);
        return manualCount;
    }

    private static LottoMoney getLottoMoney() {
        int inputMoney = consoleView.inputPurchaseAmount();
        return LottoMoney.of(inputMoney);
    }

    private static <T> T handleException(Supplier<T> function, String errorMessage) {
        while (true) {
            try {
                return function.get();
            } catch (Exception e) {
                consoleView.printMessage(errorMessage);
            }
        }
    }
}
