package entity;

import java.time.Year;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class Movie {

    @NonNull
    private final Id id;

    @NonNull
    private final Title title;

    @NonNull
    private final Year year;

    @NonNull
    private final Name producer;

    @NonNull
    private final Description description;

    @NonNull
    private final Title category;

}
