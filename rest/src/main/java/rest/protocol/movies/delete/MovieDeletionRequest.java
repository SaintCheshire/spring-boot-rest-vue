package rest.protocol.movies.delete;

import entity.Id;
import lombok.Data;
import lombok.NonNull;

@Data
public class MovieDeletionRequest {

    @NonNull
    private final Id id;

}
