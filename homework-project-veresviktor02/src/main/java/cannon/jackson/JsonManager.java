package cannon.jackson;

import cannon.util.JacksonHelper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.tinylog.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * A top10.json állományt kezelő osztály.
 */
public class JsonManager implements PlayerDataInterface {

    private PlayerData playerData;
    private ObjectMapper objectMapper = new ObjectMapper();
    private static File file = new File("top10.json");

    /**
     * Konstruktor.
     *
     * @param playerData {@code PlayerData} típusú objektum, ami a játékos adatait tárolja
     */
    public JsonManager(PlayerData playerData) {
        this.playerData = playerData;
    }

    /**
     * Adatok mentését végző metódus.
     */
    public void save() {
        try{
            List<PlayerData> playerDataList;
            playerDataList = load();
            playerDataList.add(playerData);
            ObjectMapper mapper = new ObjectMapper();
            ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
            writer.writeValue(new File("top10.json"), playerDataList);
            Logger.debug("save()");
            Logger.debug(playerData);
        }
        catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * A JSON állományhoz hozzáadja a paraméterként kapott eredményt.
     *
     * @param result eredménye a játékosnak
     * @return Egy listát, amiben a játékos adatai vannak
     * @throws IOException írásolvasási hiba
     */
    @Override
    public List<PlayerData> add(PlayerData result) throws IOException {
        var results = file.exists() ? getAll() : new ArrayList<PlayerData>();
        results.add(result);
        try(var out = new FileOutputStream(file)){
            JacksonHelper.writeList(out, results);
        }
        return results;
    }

    /**
     * Betölti a JSON állományból az adatokat.
     *
     * @return a ranglista adatait
     */
    public ArrayList<PlayerData> load() {
        ArrayList<PlayerData> playerData = new ArrayList<>();
        if(file.length() != 0){
            try{
                playerData = objectMapper.readValue(file, new TypeReference<>(){});
                Logger.debug("load()");
                Logger.info(playerData);
            }
            catch (IOException e){
                throw new RuntimeException(e);
            }
        }
        return playerData;
    }

    /**
     * Visszaadja a teljes ranglistát.
     *
     * @return az összes játékosadatot
     * @throws IOException írásolvasási hiba
     */
    @Override
    public List<PlayerData> getAll() throws IOException {
        try(var in = new FileInputStream(file)){
            return JacksonHelper.readList(in, PlayerData.class);
        }
    }

    @Override
    public List<PlayerData> getBest(int max) throws IOException {
        return PlayerDataInterface.super.getBest(max);
    }
}
