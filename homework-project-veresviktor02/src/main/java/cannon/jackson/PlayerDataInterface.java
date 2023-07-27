package cannon.jackson;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public interface PlayerDataInterface {
    List<PlayerData> add(PlayerData result) throws IOException;

    List<PlayerData> getAll() throws IOException;

    default List<PlayerData> getBest(int max) throws IOException {
        return getAll()
                .stream()
                .filter(PlayerData::isSolved)
                .sorted(Comparator.comparingInt(PlayerData::getMoves))
                .limit(max)
                .toList();
    }
}
