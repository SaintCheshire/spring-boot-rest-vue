package rest.protocol.categories.list;

import entity.Categories;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class CategoriesListResponse {

    @NonNull
    private final Categories categories;

}
