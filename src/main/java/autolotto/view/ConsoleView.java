package autolotto.view;

import autolotto.dto.LottoDTO;
import autolotto.dto.Statistics;
import autolotto.machine.winning.Winning;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ConsoleView {

    public int inputPurchaseAmount() {
        System.out.println("구입금액을 입력해 주세요.");

        return Integer.parseInt(getInputString());
    }

    private String getInputString() {
        Scanner scanner = new Scanner(System.in);

        return scanner.nextLine();
    }

    public int inputManualCount() {
        System.out.println("수동으로 구매할 로또 수를 입력해 주세요.");

        return Integer.parseInt(getInputString());
    }

    public List<String> inputManualLotto(int manualLottoCount) {
        final List<String> manualLotteries = new ArrayList<>(manualLottoCount);

        System.out.println("수동으로 구매할 번호를 입력해 주세요.");

        Scanner scanner = new Scanner(System.in);

        for (int cnt = 0; cnt < manualLottoCount; cnt++) {
            final String inputString = scanner.nextLine();
            manualLotteries.add(removeBrackets(inputString));
        }

        return manualLotteries;
    }

    private String removeBrackets(String input) {
        if (input.startsWith("[") && input.endsWith("]")) {
            return new StringBuilder(input).deleteCharAt(0).deleteCharAt(input.length() - 2).toString();
        }
        return input;
    }

    public void printLottoCount(int autoLottoCount, int manualLottoCount) {
        System.out.println("수동으로 " + manualLottoCount + ", 자동으로" + autoLottoCount + "개를 구매했습니다.");
    }

    public void printLottoNumbers(List<LottoDTO> lotteries) {
        for (LottoDTO lotto : lotteries) {
            printLotto(lotto);
        }
    }

    private void printLotto(LottoDTO lotto) {
        System.out.println(lotto.numbers());
    }

    public String inputWinningNumbers() {
        System.out.println("지난 주 당첨 번호를 입력해 주세요.");

        return getInputString();
    }

    public int inputBonusNumber() {
        System.out.println("보너스 볼을 입력해 주세요.");

        return Integer.parseInt(getInputString());
    }

    public void printStatistic(Statistics statistics) {
        System.out.println("당첨통계\n---------");
        printEachCountOfMatchingNumber(statistics, this::addBonusMessage);
        System.out.println("총 수익률은 " + statistics.profitRate() + "% 입니다.");
    }

    private String addBonusMessage(Winning winning) {
        if (winning.equals(Winning.FIVE_BONUS)) {
            return ", 보너스 볼 일치";
        }
        return "";
    }

    private void printEachCountOfMatchingNumber(Statistics statistics, Function<Winning, String> concatMessage) {
        List<Winning> sortedWinning = Arrays.stream(Winning.values())
                .sorted(Comparator.comparingInt(Winning::winningMoney))
                .collect(Collectors.toList());

        final Map<Winning, Integer> winningStatus = statistics.countOfEachMatchingNumber();
        for (Winning winning : sortedWinning) {
            System.out.println(
                    winning.matchNumber() + "개 일치 "
                            + concatMessage.apply(winning)
                            + "(" + winning.winningMoney() + "원) - "
                            + Optional.ofNullable(winningStatus.get(winning)).orElse(0) + "개");
        }
    }
}
