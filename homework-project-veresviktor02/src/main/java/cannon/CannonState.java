package cannon;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import org.tinylog.Logger;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.StringJoiner;

/**
 * A játék állapotát ábrázolja.
 */
public class CannonState implements Cloneable {

    /**
     * A tábla mérete.
     */
    public static final int BOARD_SIZE = 16;

    /**
     * A játékos indexe.
     */
    public static final int PLAYER = 0; // (1,1)

    /**
     * Az egyik (14,14) fekete négyzet indexe.
     */
    public static final int BLACK1 = 1; // (14,14)

    /**
     * A másik (7,15) fekete négyzet indexe.
     */
    public static final int BLACK2 = 2; // (7,15)

    /**
     * A célnégyzet indexe.
     */
    public static final int GOAL = 3; // (15,15)

    private Position[] positions;

    private ReadOnlyBooleanWrapper goal = new ReadOnlyBooleanWrapper();


    /**
     * Létrehoz egy {@code Cannon} típusú tömböt, amibe az ágyúkat hozzuk létre.
     */
    public static Cannon[] cannonPositions = {
            new Cannon(0, 2, false),
            new Cannon(0, 3, true),
            new Cannon(0, 5, false),
            new Cannon(0, 8, true),
            new Cannon(0, 9, false),
            new Cannon(0, 11, false),
            new Cannon(0, 13, false),
            new Cannon(2, 0, true),
            new Cannon(3, 0, false),
            new Cannon(5, 0, true),
            new Cannon(7, 0, false),
            new Cannon(8, 0, true),
            new Cannon(9, 0, false),
            new Cannon(11, 0, false),
            new Cannon(12, 0, true),
            new Cannon(13, 0, false)
    };

    /**
     * Létrehoz egy {@code CannonState} objektumot, a kezdőállapotnak
     * megfelelően inicializálva azt.
     *
     * Játékos: (1,1)
     * Fekete1: (14,14)
     * Fekete2: (15,7)
     * Cél:     (15,15)
     */
    public CannonState() {
        this(new Position(1, 1),
                new Position(14, 14),
                new Position(15, 7),
                new Position(15, 15)
        );
    }

    /**
     * Létrehoz egy {@code CannonState} objektumot a megadott pozícióknak megfelelően.
     * A konstruktor 4 pozíciót vár.
     *
     * @param positions a kezdőállapotai a pozícióknak (Játékos, Fekete1, Fekete2, Cél)
     */
    public CannonState(Position... positions) {
        checkPositions(positions);
        this.positions = deepClone(positions);
        checkCannonPositions(cannonPositions);
    }

    /**
     * {@return egy másolatát a pozíciónak}
     *
     * @param i a sorszáma a pozíciónak
     */
    public Position getPosition(int i) {
        return positions[i].clone();
    }

    /**
     * {@return beért-e a játékos a célba}
     */
    public boolean isGoal() {
        return haveSamePositions(PLAYER, GOAL);
    }

    /**
     * {@return a játékos tud-e mozogni a megadott irányba}
     *
     * @param direction az irány, amerre a játékos mozogni akar
     */
    public boolean canMove(Direction direction) {
        return switch (direction) {
            case UP -> canMoveUp();
            case RIGHT -> canMoveRight();
            case DOWN -> canMoveDown();
            case LEFT -> canMoveLeft();
        };
    }

    /**
     * @return hogy tud-e mozogni felfelé a játékos
     */
    private boolean canMoveUp() {
        if(positions[PLAYER].row() == 1){
            return false;
        }
        else if(positions[PLAYER].row() - 1 == positions[BLACK1].row() && positions[PLAYER].col() == positions[BLACK1].col()){
            return false;
        }
        //changeActiveWhen();
        for(int i = 0; i < cannonPositions.length; i++){ //végigmegyünk a cannonPositions-ön
            if(positions[PLAYER].row() - 1 == cannonPositions[i].row() && cannonPositions[i].isActive() == false){ //ha találunk olyan ágyút, ami felette sorban üzemel és inaktív (aktív lesz) nem léphet oda
                return false;
            }
            if(positions[PLAYER].row() - 1 == cannonPositions[i].row() && cannonPositions[i].isActive() == true){ //ha találunk olyan ágyút, ami felette sorban üzemel és aktív (inaktív lesz), léphetNÉNk oda, de...
                for(int j = 0; j < cannonPositions.length; j++){ // meg kell nézni egy újabb forral, hogy...
                    if(positions[PLAYER].col() == cannonPositions[j].col() && cannonPositions[j].isActive() == false){ //meg kel nézni, hogy az oszlopban, amiben marad (mert csak a row változik) van-e inaktív ágyú (ami aktív lesz)
                        return false;
                    }
                }
            }
        }
        return true; // ha ezeken végig tudunk menni, akkor true
    }

    /**
     * @return hogy tud-e jobbra mozogni a játékos
     */
    private boolean canMoveRight() {
        if(positions[PLAYER].col() == BOARD_SIZE - 1){
            return false;
        }
        else if(positions[PLAYER].row() == positions[BLACK1].row() && positions[PLAYER].col() + 1 == positions[BLACK1].col()){
            return false;
        }
        else if(positions[PLAYER].row() == positions[BLACK2].row() && positions[PLAYER].col() + 1 == positions[BLACK2].col()){
            return false;
        }
        //changeActiveWhen();
        for(int i = 0; i < cannonPositions.length; i++){ //végigmegyünk a cannonPositions-ön
            if(positions[PLAYER].col() + 1 == cannonPositions[i].col() && cannonPositions[i].isActive() == false){ //ha találunk olyan ágyút, ami jobbra oszlopban üzemel és inaktív (aktív lesz) nem léphet oda
                return false;
            }
            if(positions[PLAYER].col() + 1 == cannonPositions[i].col() && cannonPositions[i].isActive() == true){ //ha találunk olyan ágyút, ami jobbra oszlopban üzemel és aktív (inaktív lesz), léphetNÉNk oda, de...
                for(int j = 0; j < cannonPositions.length; j++){ // meg kell nézni egy újabb forral, hogy...
                    if(positions[PLAYER].row() == cannonPositions[j].row() && cannonPositions[j].isActive() == false){ //meg kel nézni, hogy a sorban, amiben marad (mert csak a col változik) van-e inaktív ágyú (ami aktív lesz)
                        return false;
                    }
                }
            }
        }
        return true; // ha ezeken végig tudunk menni, akkor true
    }

    /**
     * @return hogy tud-e lefelé mozogni a játékos
     */
    private boolean canMoveDown() {
        if(positions[PLAYER].row() == BOARD_SIZE - 1){
            return false;
        }
        else if(positions[PLAYER].row() + 1 == positions[BLACK1].row() && positions[PLAYER].col() == positions[BLACK1].col()){
            return false;
        }
        else if(positions[PLAYER].row() + 1 == positions[BLACK2].row() && positions[PLAYER].col() == positions[BLACK2].col()){
            return false;
        }
        //changeActiveWhen();
        for(int i = 0; i < cannonPositions.length; i++){ //végigmegyünk a cannonPositions-ön
            if(positions[PLAYER].row() + 1 == cannonPositions[i].row() && cannonPositions[i].isActive() == false){ //ha találunk olyan ágyút, ami alatta sorban üzemel és inaktív (aktív lesz) nem léphet oda
                return false;
            }
            if(positions[PLAYER].row() + 1 == cannonPositions[i].row() && cannonPositions[i].isActive() == true){ //ha találunk olyan ágyút, ami alatta sorban üzemel és aktív (inaktív lesz), léphetNÉNk oda, de...
                for(int j = 0; j < cannonPositions.length; j++){ // meg kell nézni egy újabb forral, hogy...
                    if(positions[PLAYER].col() == cannonPositions[j].col() && cannonPositions[j].isActive() == false){ //meg kel nézni, hogy az oszlopban, amiben marad (mert csak a row változik) van-e inaktív ágyú (ami aktív lesz)
                        return false;
                    }
                }
            }
        }
        return true; // ha ezeken végig tudunk menni, akkor true
    }

    /**
     * @return hogy tud-e balra mozogni a játékos
     */
    private boolean canMoveLeft() {
        if(positions[PLAYER].col() == 1){
            return false;
        }
        else if(positions[PLAYER].row() == positions[BLACK1].row() && positions[PLAYER].col() - 1 == positions[BLACK1].col()){
            return false;
        }
        else if(positions[PLAYER].row() == positions[BLACK2].row() && positions[PLAYER].col() - 1 == positions[BLACK2].col()){
            return false;
        }
        //changeActiveWhen();
        for(int i = 0; i < cannonPositions.length; i++){ //végigmegyünk a cannonPositions-ön
            if(positions[PLAYER].col() - 1 == cannonPositions[i].col() && cannonPositions[i].isActive() == false){ //ha találunk olyan ágyút, ami balra oszlopban üzemel és inaktív (aktív lesz) nem léphet oda
                return false;
            }
            if(positions[PLAYER].col() - 1 == cannonPositions[i].col() && cannonPositions[i].isActive() == true){ //ha találunk olyan ágyút, ami balra oszlopban üzemel és aktív (inaktív lesz), léphetNÉNk oda, de...
                for(int j = 0; j < cannonPositions.length; j++){ // meg kell nézni egy újabb forral, hogy...
                    if(positions[PLAYER].row() == cannonPositions[j].row() && cannonPositions[j].isActive() == false){ //meg kel nézni, hogy a sorban, amiben marad (mert csak a col változik) van-e inaktív ágyú (ami aktív lesz)
                        return false;
                    }
                }
            }
        }
        return true; // ha ezeken végig tudunk menni, akkor true
    }

    /**
     * Elmozgatja a játékost a megadott irányba.
     *
     * @param direction az irány, amerre a játékos mozogni fog
     */
    public void move(Direction direction) {
        switch (direction) {
            case UP -> moveUp();
            case RIGHT -> moveRight();
            case DOWN -> moveDown();
            case LEFT -> moveLeft();
        };
    }

    /**
     * {@return Setben az irányokat, amiket szabályosan végrehajthat a játékos.}
     */
    public EnumSet<Direction> getLegalMoves() {
        var legalMoves = EnumSet.noneOf(Direction.class);
        for(var direction : Direction.values()){
            if(canMove(direction)){
                legalMoves.add(direction);
            }
        }
        return legalMoves;
    }

    /**
     * Megváltozatja az ágyúk aktívsági státuszát.
     */
    public void changeActive() {
        for(int i = 0; i < cannonPositions.length; i++){
            cannonPositions[i].setActive(!cannonPositions[i].isActive());
        }
        Logger.debug("changeActive()");
    }

    /**
     * Erre a függvényre azért van szükség, mert amíg pl. (1,1) pozíción áll a PLAYER,
     * addig az ágyúk aktivitása jó, de ha már (2,1)-en vagy (1,2)-n áll, akkor
     * a tesztek nem tudják, hogy történt mozgás, mert a "CannonState cannon" csak
     * önkényesen létre lett hozva és a program nem tud az adott lépésekről, amik
     * megváltoztatják körönként az ágyúk aktivitását.
     * Teszteken kívül az újraindítás is felhasználja.
     */
    public void changeActiveWhen() {
        if((positions[PLAYER].row() + positions[PLAYER].col()) % 2 != 0){
            changeActive();
        }
    }

    /**
     * Felfelé mozgatás, ágyú aktivitás cseréje.
     */
    public void moveUp() {
        changeActive();
        positions[0].setUp();
        Logger.trace("Felfelé mozgás");
    }


    /**
     * Jobbra mozgatás, ágyú aktivitás cseréje.
     */
    public void moveRight() {
        changeActive();
        positions[0].setRight();
        Logger.trace("Jobbra mozgás");
    }

    /**
     * Lefelé mozgatás, ágyú aktivitás cseréje.
     */
    public void moveDown() {
        changeActive();
        positions[0].setDown();
        Logger.trace("Lefelé mozgás");
    }

    /**
     * Balra mozgatás, ágyú aktivitás cseréje.
     */
    public void moveLeft() {
        changeActive();
        positions[0].setLeft();
        Logger.trace("Balra mozgás");
    }

    private void checkCannonPositions(Cannon[] cannonPositions) {
        for(int i = 0; i < cannonPositions.length; i++){
            if(cannonPositions[i].row() + cannonPositions[i].col() == 0){ // (0,0)
                throw new IllegalArgumentException();
            }
            else if(cannonPositions[i].row() * cannonPositions[i].col() != 0){ // (1,2)
                throw new IllegalArgumentException();
            }
        }
    }

    private void checkPositions(Position[] positions) {
        if(positions.length != 4) {
            throw new IllegalArgumentException();
        }
        for(int i = 0; i < positions.length; i++) {
            if(!isOnBoard(positions[i])) {
                throw new IllegalArgumentException();
            }
        }
        if(positions[PLAYER].equals(BLACK1) || positions[PLAYER].equals(BLACK2)){
            throw new IllegalArgumentException();
        }
    }

    private boolean isOnBoard(Position position) {
        return 0 < position.row() && // 0. sor az ágyúké!
                position.row() < BOARD_SIZE &&
                0 < position.col() && // 0. oszlop az ágyúké!
                position.col() < BOARD_SIZE;
    }

    private boolean haveSamePositions(int i, int j) {
        return positions[i].equals(positions[j]);
    }

    private Position[] deepClone(Position[] positions) {
        var copy = positions.clone();
        for(var i = 0; i < positions.length; i++){
            copy[i] = copy[i].clone();
        }
        return copy;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null) {
            return false;
        }
        return (o instanceof CannonState other) && Arrays.equals(positions, other.positions);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(positions);
    }

    @Override
    public CannonState clone() {
        CannonState copy = null;
        try {
            copy = (CannonState) super.clone();
        } catch(CloneNotSupportedException e) {
            // Sosem történik meg.
        }
        copy.positions = deepClone(positions);
        return copy;
    }

    @Override
    public String toString() {
        var sj = new StringJoiner(",", "[", "]");
        for(var p : positions) {
            sj.add(p.toString());
        }
        return sj.toString();
    }
}
