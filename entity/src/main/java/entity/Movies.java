package entity;

import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import static java.util.Collections.max;
import static java.util.Comparator.comparingInt;

@Data
@RequiredArgsConstructor
public class Movies {

    @NonNull
    private final Map<Id, Movie> map;

    public static Movies createEmpty() {
        return new Movies(new LinkedHashMap<>());
    }

    public Id getNextId() {
        if (map.isEmpty()) {
            return new Id(1);
        } else {
            int currentMaxId = max(map.keySet(), comparingInt(Id::getValue)).getValue();
            return new Id(currentMaxId + 1);
        }
    }

    public void addMovie(@NonNull Movie movie) {
        map.put(movie.getId(), movie);
    }

    public void deleteMovie(@NonNull Id id) {
        map.keySet().removeIf(movieId -> movieId.equals(id));
    }

}
