package entity;

import java.util.Set;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Producer {

    @NonNull
    private final Id id;

    @NonNull
    private final Name name;

    @NonNull
    private final Set<Id> moviesId;

}
