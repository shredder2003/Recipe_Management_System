package recipes;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import recipes.security.CurrentUser;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Slf4j
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final Mapper mapper;
    private final CurrentUser currentUser;


    public RecipeDTO getRecipe(String id){
        log.info("getRecipe(+) id={}",id);
        if( currentUser.userName().isBlank() ) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"UNAUTHORIZED!");
        }
        Recipe recipe;
        Integer idInt = Integer.parseInt(id);
        try{
            recipe = recipeRepository.getReferenceById(idInt);
            log.info("getRecipe id={} name={} description={} ingridients={} directions={}",recipe.getId(), recipe.getName(), recipe.getDescription(),recipe.getIngredients(),recipe.getDirections());
        }catch(Exception e){
            log.info("getRecipe(-) id={} e={}",id,e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"not found!");
        }
        log.info("getRecipe(-) recipe={}",recipe);
        return mapper.convertRecipeToDTO(recipe);
    }

    public Object getRecipes(String name, String category){
        log.info("getRecipes(+) name={} category={}",name,category);
        if( currentUser.userName().isBlank() ) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"UNAUTHORIZED!");
        }
        if(  (name==null && category==null)
           ||(name!=null && category!=null)
        ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if(name!=null){
            return recipeRepository.findByNameContainingIgnoreCaseOrderByDateDesc(name)
                    .stream().map(mapper::convertRecipeToDTO).toList()
                    ;
        }else{
            return recipeRepository.findByCategoryIgnoreCaseOrderByDateDesc(category)
                    .stream().map(mapper::convertRecipeToDTO).toList()
                    ;
        }
    }

    public String postRecipe(Recipe recipe){
        log.info("postRecipe(+) recipe={}",recipe);
        if( currentUser.userName().isBlank() ) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"UNAUTHORIZED!");
        }
        if(recipe.getDirections()==null
                || recipe.getIngredients()==null
                || recipe.getName()==null
                || recipe.getDescription()==null
        ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"bad request!");
        }
        recipe.setDate(LocalDateTime.now());
        recipe.setEmail(currentUser.userName());
        recipe = recipeRepository.save(recipe);
        log.info("postRecipe(-) recipe={}",recipe);
        return "{ \"id\": "+recipe.getId()+"}";
    }

    public Object deleteRecipe(int id) {
        log.info("deleteRecipe(+) id={}",id);
        if( currentUser.userName().isBlank() ) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"UNAUTHORIZED!");
        }
        Recipe recipe;
        try{
            recipe = recipeRepository.getReferenceById(id);
            log.info("deleteRecipe id={} name={} description={} ingridients={} directions={}",recipe.getId(), recipe.getName(), recipe.getDescription(),recipe.getIngredients(),recipe.getDirections());
        }catch(Exception e){
            log.info("deleteRecipe(-) id={} e={}",id,e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"not found!");
        }
        if(recipe.getEmail().equalsIgnoreCase( currentUser.userName() )) {
            recipeRepository.deleteById(id);
        }else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"forbidden!");
        }
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    public Object updateRecipe(int id, Recipe recipe) {
        log.info("updateRecipe(+) id={} recipe={}",id,recipe);
        if( currentUser.userName().isBlank() ) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"UNAUTHORIZED!");
        }
        Recipe dbRecipe;
        if( (recipe.getCategory()==null || recipe.getCategory().isBlank() )
                ||(recipe.getDescription()==null ||recipe.getDescription().isBlank() )
                ||(recipe.getName()==null ||recipe.getName().isBlank() )
                ||(recipe.getDirections()==null || recipe.getDirections().length==0)
                ||(recipe.getIngredients()==null || recipe.getIngredients().length==0)
        ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"bad request!");
        }
        try{
            dbRecipe = recipeRepository.getReferenceById(id);
            log.info("updateRecipe id={} name={} description={} ingridients={} directions={}",dbRecipe.getId(), dbRecipe.getName(), dbRecipe.getDescription(),dbRecipe.getIngredients(),dbRecipe.getDirections());
        }catch(Exception e){
            log.info("updateRecipe(-) id={} e={}",id,e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"not found!");
        }
        if(! dbRecipe.getEmail().equalsIgnoreCase( currentUser.userName() )) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"forbidden!");
        }
        recipe.setId(id);
        recipe.setDate(LocalDateTime.now());
        recipe.setEmail(currentUser.userName());
        recipeRepository.save(recipe);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

}
