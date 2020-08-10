package rest.protocol.movies.list;

import entity.Movies;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.lang.Nullable;

@Data
@Builder
public class MoviesListResponse {

    @NonNull
    private final MoviesListResult result;

    @Nullable
    private final Movies movies;

}
