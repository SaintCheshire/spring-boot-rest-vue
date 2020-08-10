package entity;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Categories {

    @NonNull
    private final Map<Id, Category> map;

    public static Categories createEmpty() {
        return new Categories(new HashMap<>());
    }

    public Id getNextId() {
        return new Id(map.size() + 1);
    }

    public void addCategory(@NonNull Category category) {
        map.put(category.getId(), category);
    }

}