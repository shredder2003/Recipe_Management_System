package recipes;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recipe")
@AllArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @GetMapping(value = {"/{id}", ""})
    public Object getRecipe(@PathVariable(required = false) String id/*, @RequestParam(required = false) Integer page*/) {
        //return recipeService.getRecipe(page, id);
        return recipeService.getRecipe(id);
    }

    @GetMapping("/search/")
    public Object getRecipes(@RequestParam(required = false) String category, @RequestParam(required = false) String name) {
        //return recipeService.getRecipe(page, id);
        return recipeService.getRecipes(name, category);
    }

    @PostMapping("/new")
    public Object postRecipe(@Valid @RequestBody Recipe recipe) {
        return recipeService.postRecipe(recipe);
    }

    @DeleteMapping(value = {"/{id}", ""})
    public Object deleteRecipe(@PathVariable int id) {
        return recipeService.deleteRecipe(id);
    }

    @PutMapping(value = {"/{id}", ""})
    public Object updateRecipe(@PathVariable int id, @Valid @RequestBody Recipe recipe) {
        return recipeService.updateRecipe(id, recipe);
    }

}
