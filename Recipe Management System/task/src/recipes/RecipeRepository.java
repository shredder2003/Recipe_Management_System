package recipes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Integer>, PagingAndSortingRepository<Recipe, Integer> {
    public List<Recipe> findByNameContainingIgnoreCaseOrderByDateDesc(String name);
    public List<Recipe> findByCategoryIgnoreCaseOrderByDateDesc(String category);

}
