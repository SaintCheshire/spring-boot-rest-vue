package rest.protocol.movies.add;

import entity.Description;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class MovieAdditionResponse {

    @NonNull
    private final MovieAdditionResult result;

    @NonNull
    private final Description description;

}
