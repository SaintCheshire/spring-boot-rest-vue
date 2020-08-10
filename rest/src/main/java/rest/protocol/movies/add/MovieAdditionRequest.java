package rest.protocol.movies.add;

import entity.Description;
import entity.Name;
import entity.Title;
import lombok.Data;
import lombok.NonNull;

@Data
public class MovieAdditionRequest {

    @NonNull
    private final Title title;

    private final int year;

    @NonNull
    private final Name producer;

    @NonNull
    private final Description description;

    @NonNull
    private final Title category;

}
