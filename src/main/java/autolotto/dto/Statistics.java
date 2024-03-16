package autolotto.dto;

import autolotto.machine.winning.Winning;

import java.util.Map;

public class Statistics {

    private final String profitRate;

    private final Map<Winning, Integer> countOfEachMatchingNumber;

    public Statistics(String profitRate, Map<Winning, Integer> countOfEachMatchingNumber) {
        this.profitRate = profitRate;
        this.countOfEachMatchingNumber = countOfEachMatchingNumber;
    }

    public String profitRate() {
        return this.profitRate;
    }

    public Map<Winning, Integer> countOfEachMatchingNumber() {
        return this.countOfEachMatchingNumber;
    }
}
