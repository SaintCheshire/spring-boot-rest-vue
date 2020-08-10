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
import rest.protocol.categories.add.CategoryAdditionRequest;
import rest.protocol.categories.add.CategoryAdditionResponse;
import rest.protocol.categories.add.CategoryAdditionResult;
import rest.protocol.categories.delete.CategoryDeletionRequest;
import rest.protocol.categories.delete.CategoryDeletionResponse;
import rest.protocol.categories.delete.CategoryDeletionResult;
import rest.protocol.categories.list.CategoriesListRequest;
import rest.protocol.categories.list.CategoriesListResponse;
import service.CategoryService;

@Log4j2
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
public class CategoryController {

    @NonNull
    private final CategoryService categoryService;

    @NonNull
    @CrossOrigin(origins = "https://192.168.43.103:45000")
    @PostMapping("/categories/list")
    public CategoriesListResponse categoriesList(@RequestBody @NonNull CategoriesListRequest request) {
        return CategoriesListResponse.builder()
                .categories(categoryService.getCategories())
                .build();
    }

    @NonNull
    @CrossOrigin(origins = "https://192.168.43.103:45000")
    @PostMapping("/categories/add")
    public CategoryAdditionResponse categoryAddition(@RequestBody @NonNull CategoryAdditionRequest request) {
        CategoryAdditionResponse.CategoryAdditionResponseBuilder response = CategoryAdditionResponse.builder();

        try {
            if (categoryService.isCategoryPresent(request.getTitle())) {
                return response
                        .result(CategoryAdditionResult.WARNING)
                        .description(new Description("Такая категория уже добавлена"))
                        .build();
            } else {
                categoryService.addCategory(
                        request.getTitle(),
                        request.getDescription()
                );

                return response
                        .result(CategoryAdditionResult.SUCCESS)
                        .description(new Description("Категория успешно добавлена"))
                        .build();
            }
        } catch (Exception e) {
            log.debug("Category addition failed", e);

            return response
                    .result(CategoryAdditionResult.ERROR)
                    .description(new Description("Ошибка при добавлении категории"))
                    .build();
        }
    }

    @NonNull
    @CrossOrigin(origins = "https://192.168.43.103:45000")
    @PostMapping("/categories/delete")
    public CategoryDeletionResponse categoriesList(@RequestBody @NonNull CategoryDeletionRequest request) {
        CategoryDeletionResponse.CategoryDeletionResponseBuilder response = CategoryDeletionResponse.builder();

        try {
            categoryService.deleteCategory(request.getId());

            return response
                    .result(CategoryDeletionResult.SUCCESS)
                    .description(new Description("Информация о категории удалена"))
                    .build();
        } catch (Exception e) {
            log.debug("Category addition failed", e);

            return response
                    .result(CategoryDeletionResult.ERROR)
                    .description(new Description("Невозможно удалить категорию"))
                    .build();
        }
    }

}
