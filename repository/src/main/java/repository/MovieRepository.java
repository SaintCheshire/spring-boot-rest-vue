package repository;

import entity.Description;
import entity.Id;
import entity.Movie;
import entity.Movies;
import entity.Name;
import entity.Title;
import java.time.Year;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

@Log4j2
@RequiredArgsConstructor
@Repository
public class MovieRepository {

    @Getter
    @NonNull
    private final Movies movies = Movies.createEmpty();

    public void addMovie(
            @NonNull Title title,
            @NonNull Integer year,
            @NonNull Name producer,
            @NonNull Description description,
            @NonNull Title category
    ) {
        Movie movie = Movie.builder()
                .id(movies.getNextId())
                .title(title)
                .year(Year.of(year))
                .producer(producer)
                .description(description)
                .category(category)
                .build();

        log.info("Adding movie {}", movie);

        movies.addMovie(movie);

        log.info("Current movies repository state {}", movies);
    }

    public void deleteMovie(@NonNull Id id) {
        log.info("Deleting movie with id {}", id);

        movies.deleteMovie(id);

        log.info("Current movie repository state {}", movies);
    }


}

