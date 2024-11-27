package recipes;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    private final ModelMapper modelMapper;

    public Mapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    RecipeDTO convertRecipeToDTO(Recipe quiz) {
        return modelMapper.map(quiz, RecipeDTO.class);
    }

}
