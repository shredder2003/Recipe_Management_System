package recipes.test;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import recipes.Recipe;
import recipes.RecipeDTO;
import recipes.RecipeRepository;
import recipes.RecipeService;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserTest {
    @MockBean
    private final RecipeRepository recipeRepository;

    @Autowired
    private RecipeService recipeService;

    public UserTest(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Test
    void getRecipe() {

        Recipe recipe = new Recipe(1, "name1", "descr1"
                , new String[]{"ing1","ing2"}, new String[]{"dir1","dir2"}
                , "cat1", LocalDateTime.now(), "email@email.com");
        Mockito.when(recipeRepository.getReferenceById(1))
                .thenReturn(recipe);

        RecipeDTO recipeDTO = recipeService.getRecipe("1");

        assertThat(recipeDTO.getName()).isEqualTo(recipe.getName());
    }
}
