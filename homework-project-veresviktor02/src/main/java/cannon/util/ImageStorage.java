package cannon.util;

import javafx.scene.image.Image;

/**
 * Képtárolás interfésze.
 *
 * @param <T> Generikus paraméter
 */
public interface ImageStorage<T> {

    /**
     * {@code imageMap} getter metódusa.
     *
     * @param key kulcsérték
     * @return az {@code imageMap} kulcsértékét
     */
    Image get(T key);
}
