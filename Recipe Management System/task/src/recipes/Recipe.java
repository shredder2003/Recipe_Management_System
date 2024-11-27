package recipes;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
    @SequenceGenerator(name = "user_generator", sequenceName = "user_SEQ", allocationSize = 1)
    private Integer id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @Size(min=1)
    private String[] ingredients;
    @Size(min=1)
    private String[] directions;
    @NotBlank
    private String category;
    private LocalDateTime date;
    private String email;
}
