package service;

import entity.Categories;
import entity.Description;
import entity.Id;
import entity.Title;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.CategoryRepository;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Service
public class CategoryService {

    @NonNull
    private final CategoryRepository categoryRepository;

    @NonNull
    public Categories getCategories() {
        return categoryRepository.getCategories();
    }

    public void addCategory(@NonNull Title title, @NonNull Description description) {
        categoryRepository.addCategory(title, description);
    }

    public boolean isCategoryPresent(@NonNull Title title) {
        return categoryRepository.getCategoryByTitle(title).isPresent();
    }

    public void deleteCategory(@NonNull Id id) {
        categoryRepository.deleteCategoryById(id);
    }

}
