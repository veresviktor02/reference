package cannon.util;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * A JsonManager osztály segédosztálya.
 */
public class JacksonHelper {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .enable(SerializationFeature.INDENT_OUTPUT)
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS, false);

    /**
     * {@code MAPPER} írása.
     *
     * @param out kimenet
     * @param elements lista elemei
     * @param <T> generikus paraméter
     * @throws IOException írásolvasási hiba
     */
    public static <T> void writeList(OutputStream out, List<T> elements) throws IOException {
        MAPPER.writeValue(out, elements);
    }

    /**
     * {@code MAPPER} olvasása.
     *
     * @param in bemenet
     * @param elementClass lista elemének az osztálya
     * @param <T> generikus paraméter
     * @throws IOException írásolvasás kivétel
     */
    public static <T> List<T> readList(InputStream in, Class<T> elementClass) throws IOException {
        JavaType listType = MAPPER.getTypeFactory().constructCollectionType(List.class, elementClass);
        return MAPPER.readValue(in, listType);
    }
}
