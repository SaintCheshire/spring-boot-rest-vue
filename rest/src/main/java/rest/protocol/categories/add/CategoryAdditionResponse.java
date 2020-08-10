package rest.protocol.categories.add;

import entity.Description;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class CategoryAdditionResponse {

    @NonNull
    private final CategoryAdditionResult result;

    @NonNull
    private final Description description;

}
