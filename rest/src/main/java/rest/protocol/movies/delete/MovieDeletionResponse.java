package rest.protocol.movies.delete;

import entity.Description;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class MovieDeletionResponse {

    @NonNull
    private final MovieDeletionResult result;

    @NonNull
    private final Description description;

}
