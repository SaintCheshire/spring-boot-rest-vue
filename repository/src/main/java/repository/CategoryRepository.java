package repository;

import entity.Categories;
import entity.Category;
import entity.Description;
import entity.Id;
import entity.Title;
import java.util.Optional;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

@Log4j2
@RequiredArgsConstructor
@Repository
public class CategoryRepository {

    @Getter
    @NonNull
    private final Categories categories = Categories.createEmpty();

    public void addCategory(@NonNull Title title, @NonNull Description description) {
        Category category = Category.builder()
                .id(categories.getNextId())
                .title(title)
                .description(description)
                .build();

        log.info("Adding category {}", category);

        categories.addCategory(category);

        log.info("Current category repository state {}", categories);
    }

    public Optional<Category> getCategoryByTitle(@NonNull Title title) {
        return categories.getMap().values().stream()
                .filter(category -> category.getTitle().equals(title))
                .findAny();
    }

    public void deleteCategoryById(@NonNull Id id) {
        log.info("Deleting category with id {}", id);

        categories.getMap().keySet().removeIf(categoryId -> categoryId.equals(id));

        log.info("Current category repository state {}", categories);
    }

}
