package autolotto.view;

import autolotto.dto.LottoDTO;
import autolotto.dto.Statistics;
import autolotto.dto.WinningAmount;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

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
        printEachCountOfMatchingNumber(statistics);
        System.out.println("총 수익률은 " + statistics.profitRate() + "입니다.");
    }

    private void printEachCountOfMatchingNumber(Statistics statistics) {
        for (Map.Entry<WinningAmount, Integer> entry : statistics.countOfEachMatchingNumber().entrySet()) {
            System.out.println(entry.getKey().matchCount() + "개 일치 (" + entry.getKey().winningAmount() + "원) - " + entry.getValue() + "개");
        }
    }
}
