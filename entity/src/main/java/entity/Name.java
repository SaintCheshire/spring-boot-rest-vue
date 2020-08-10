package entity;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
public class Name {

    @NonNull
    private final String value;

}
