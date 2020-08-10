package rest.controller;

import entity.Description;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import rest.protocol.movies.add.MovieAdditionRequest;
import rest.protocol.movies.add.MovieAdditionResponse;
import rest.protocol.movies.add.MovieAdditionResult;
import rest.protocol.movies.delete.MovieDeletionRequest;
import rest.protocol.movies.delete.MovieDeletionResponse;
import rest.protocol.movies.delete.MovieDeletionResult;
import rest.protocol.movies.list.MoviesListRequest;
import rest.protocol.movies.list.MoviesListResponse;
import rest.protocol.movies.list.MoviesListResult;
import service.MovieService;

@Log4j2
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
public class MovieController {

    @NonNull
    private final MovieService movieService;

    @NonNull
    @CrossOrigin(origins = "https://192.168.43.103:45000")
    @PostMapping("/movies/list")
    public MoviesListResponse moviesList(@RequestBody @NonNull MoviesListRequest request) {
        MoviesListResponse.MoviesListResponseBuilder response = MoviesListResponse.builder();

        try {
            return response
                    .result(MoviesListResult.SUCCESS)
                    .movies(movieService.getMovies())
                    .build();
        } catch (Exception e) {
            log.debug("Movies list request failed", e);

            return response
                    .result(MoviesListResult.ERROR)
                    .build();
        }
    }

    @NonNull
    @CrossOrigin(origins = "https://192.168.43.103:45000")
    @PostMapping("/movies/add")
    public MovieAdditionResponse movieAddition(@RequestBody @NonNull MovieAdditionRequest request) {
        MovieAdditionResponse.MovieAdditionResponseBuilder response = MovieAdditionResponse.builder();

        try {
            movieService.addMovie(
                    request.getTitle(),
                    request.getYear(),
                    request.getProducer(),
                    request.getDescription(),
                    request.getCategory()
            );

            return response
                    .result(MovieAdditionResult.SUCCESS)
                    .description(new Description("Фильм успешно добавлен"))
                    .build();
        } catch (Exception e) {
            log.debug("Movie addition failed", e);

            return response
                    .result(MovieAdditionResult.ERROR)
                    .description(new Description("Неизвестная ошибка при добавлении фильма"))
                    .build();
        }
    }

    @NonNull
    @CrossOrigin(origins = "https://192.168.43.103:45000")
    @PostMapping("/movies/delete")
    public MovieDeletionResponse movieDeletion(@RequestBody @NonNull MovieDeletionRequest request) {
        MovieDeletionResponse.MovieDeletionResponseBuilder response = MovieDeletionResponse.builder();

        try {
            movieService.deleteMovie(request.getId());

            return response
                    .result(MovieDeletionResult.SUCCESS)
                    .description(new Description("Информация о фильме удалена"))
                    .build();
        } catch (Exception e) {
            log.debug("Movies list request failed", e);

            return response
                    .result(MovieDeletionResult.ERROR)
                    .description(new Description("Не удалось удалить информацию о фильме"))
                    .build();
        }
    }

}
