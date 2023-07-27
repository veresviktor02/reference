package cannon.model;

/**
 * A tábla négyzeteinek állapotait tároló osztály.
 */
public enum Square {
    /**
     * Nincs lézer a négyzetben.
     */
    CLEAR,

    /**
     * A lézer az 1. sorban helyezkedik el és be van kapcsolva.
     */
    COL_LASER_ON,

    /**
     * A lézer az 1. sorban helyezkedik el és ki van kapcsolva.
     */
    COL_LASER_OFF,

    /**
     * A lézer az 1. oszlopban helyezkedik el és be van kapcsolva.
     */
    ROW_LASER_ON,

    /**
     * A lézer az 1. oszlopban helyezkedik el és ki van kapcsolva.
     */
    ROW_LASER_OFF,

    /**
     * A négyzeten egy lézernyaláb halad át.
     */
    LASER_ON,

    /**
     * Mező, amire nem léphet a játékos.
     */
    BLACK,

    /**
     * A cél.
     */
    GOAL,

    /**
     * A játékos.
     */
    PLAYER
}
