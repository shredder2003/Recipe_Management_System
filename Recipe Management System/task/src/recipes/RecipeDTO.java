package recipes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeDTO {
    private String name;
    private String description;
    private String category;
    private String[] ingredients;
    private String[] directions;
    private LocalDateTime date;
}
