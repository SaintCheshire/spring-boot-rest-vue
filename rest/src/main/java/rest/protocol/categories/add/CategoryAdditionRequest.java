package rest.protocol.categories.add;

import entity.Description;
import entity.Title;
import lombok.Data;
import lombok.NonNull;

@Data
public class CategoryAdditionRequest {

    @NonNull
    private final Title title;

    @NonNull
    private final Description description;

}