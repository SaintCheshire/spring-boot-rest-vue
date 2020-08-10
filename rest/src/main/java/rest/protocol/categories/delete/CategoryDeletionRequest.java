package rest.protocol.categories.delete;

import entity.Id;
import lombok.Data;
import lombok.NonNull;

@Data
public class CategoryDeletionRequest {

    @NonNull
    private final Id id;

}
