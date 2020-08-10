package service;

import entity.Description;
import entity.Id;
import entity.Movies;
import entity.Name;
import entity.Title;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.MovieRepository;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Service
public class MovieService {

    @NonNull
    private final MovieRepository movieRepository;

    @NonNull
    public Movies getMovies() {
        return movieRepository.getMovies();
    }

    public void addMovie(
            @NonNull Title title,
            @NonNull Integer year,
            @NonNull Name producer,
            @NonNull Description description,
            @NonNull Title category
    ) {
        movieRepository.addMovie(
                title,
                year,
                producer,
                description,
                category
        );
    }

    public void deleteMovie(@NonNull Id id) {
        movieRepository.deleteMovie(id);
    }

}
