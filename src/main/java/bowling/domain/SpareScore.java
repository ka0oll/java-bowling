package bowling.domain;

import java.util.List;
import java.util.Objects;

public class SpareScore implements Score {

    private List<Integer> pins;
    private List<Symbol> symbols;

    public SpareScore(List<Integer> pins, List<Symbol> symbols) {
        this.pins = pins;
        this.symbols = symbols;
    }

    @Override
    public Score ofNext(int pin) {
        throw new IllegalArgumentException("unable to get next Score at Spare");
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public List<Symbol> getSymbols() {
        return symbols;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpareScore that = (SpareScore) o;
        return Objects.equals(pins, that.pins) &&
                Objects.equals(symbols, that.symbols);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pins, symbols);
    }
}