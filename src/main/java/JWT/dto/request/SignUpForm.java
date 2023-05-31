package JWT.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter@Setter
@NoArgsConstructor@AllArgsConstructor
public class SignUpForm {
    private String name;
    @NotBlank
    private String username;
    private String email;
    @NotBlank
    private String password;
    private Set<String> roles;
}
