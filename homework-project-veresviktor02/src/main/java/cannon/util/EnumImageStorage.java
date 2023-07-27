package cannon.util;

import javafx.scene.image.Image;

import org.tinylog.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum alapú képtárolást végző osztály.
 *
 * @param <T> Generikus programozás miatt megadott paraméter, ami az {@code enum} osztályt terjeszti ki.
 */
public class EnumImageStorage<T extends Enum> implements ImageStorage<T> {

    private Map<T, Image> imageMap = new HashMap<>();

    /**
     * Osztály konstruktora.
     *
     * @param enumClass enum osztály
     */
    public EnumImageStorage(Class<T> enumClass) {
        for(var constant : enumClass.getEnumConstants()){
            var path = String.format("%s/%s.png", "CannonPictures", constant.name().toUpperCase());
            try {
                imageMap.put(constant, new Image(path));
                Logger.debug("Kép betöltése sikeres - " + path);
            } catch(Exception e) {
                Logger.debug("Kép betöltése sikertelen - " + path);
            }
        }
    }

    /**
     * {@code imageMap} getter metódusa.
     *
     * @param key kulcsérték
     * @return az {@code imageMap} kulcsértékét
     */
    @Override
    public Image get(T key) {
        return imageMap.get(key);
    }
}
