package rest.protocol.categories.delete;

import entity.Description;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class CategoryDeletionResponse {

    @NonNull
    private final CategoryDeletionResult result;

    @NonNull
    private final Description description;

}
