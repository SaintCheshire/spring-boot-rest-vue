package entity;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class Category {

    @NonNull
    private final Id id;

    @NonNull
    private final Title title;

    @NonNull
    private final Description description;

}
