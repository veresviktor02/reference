package cannon;

/**
 * A négy fő irányt ábrázolja.
 */
public enum Direction {

    /**
     * Fel irány koordinátaváltozásai.
     */
    UP(-1, 0),

    /**
     * Jobbra irány koordinátaváltozásai.
     */
    RIGHT(0, 1),

    /**
     * Le irány koordinátaváltozásai.
     */
    DOWN(1, 0),

    /**
     * Balra irány koordinátaváltozásai.
     */
    LEFT(0, -1);
    private final int rowChange;
    private final int colChange;

    /**
     * Létrehoz egy {@code Direction} objektumot.
     *
     * @param rowChange sorkoordináta változása
     * @param colChange oszlopkoordináta változása
     */
    Direction(int rowChange, int colChange) {
        this.rowChange = rowChange;
        this.colChange = colChange;
    }

    /**
     * {@return a sorkoordonáta változását.}
     */
    public int getRowChange() {
        return rowChange;
    }

    /**
     * {@return az oszlopkoordináta változását.}
     */
    public int getColChange() {
        return colChange;
    }

    /**
     * {@return a megadott koordinátaváltozásoknak megfelelő irányt.}
     *
     * @param rowChange sorkoordináta változása
     * @param colChange oszlopkoordináta változása
     */
    public static Direction of(int rowChange, int colChange) {
        for(var direction : values()) {
            if(direction.rowChange == rowChange && direction.colChange == colChange){
                return direction;
            }
        }
        throw new IllegalArgumentException();
    }
}
