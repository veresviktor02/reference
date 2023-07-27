package cannon;

import java.util.Objects;

/**
 * Egy ágyút ábrázol.
 */
public class Cannon {
    private int row;
    private int col;
    private boolean active;

    /**
     * Létrehoz egy {@code Cannon} objektumot.
     *
     * @param row sor koordinátája az ágyú pozíciójának
     * @param col oszlop koordinátája az ágyú pozíciójának
     * @param active az ágyú aktív-e
     */
    public Cannon(int row, int col, boolean active) {
        this.row = row;
        this.col = col;
        this.active = active;
    }

    /**
     * {@return a sor koordinátáját az ágyúnak.}
     */
    public int row() {
        return row;
    }

    /**
     * {@return az oszlop koordinátáját az ágyúnak.}
     */
    public int col() {
        return col;
    }

    /**
     * {@return az ágyú aktív-e}
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Ágyú aktívságának a settere.
     *
     * @param active Beállítja az ágyú aktívsági státuszát
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null){
            return true;
        }
        return (o instanceof Cannon cannon) && cannon.row == row && cannon.col == col && cannon.isActive() == active;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col, active);
    }

    @Override
    public String toString() {
        if(isActive()){
            return String.format('(' + String.valueOf(row) + ',' + col + ") - Aktív");
        }
        return String.format('(' + String.valueOf(row) + ',' + col + ") - Inaktív");
    }


}
