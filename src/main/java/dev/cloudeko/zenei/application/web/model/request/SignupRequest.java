package dev.cloudeko.zenei.application.web.model.request;

import dev.cloudeko.zenei.domain.model.auth.Strategy;
import dev.cloudeko.zenei.domain.model.user.CreateUserInput;
import jakarta.ws.rs.FormParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {

    @FormParam("username")
    private String username;
    @FormParam("email")
    private String email;
    @FormParam("password")
    private String password;
    @FormParam("first_name")
    private String firstName;
    @FormParam("last_name")
    private String lastName;
    @FormParam("strategy")
    private Strategy strategy;
    @FormParam("redirect_to")
    private URI redirectTo;

    public CreateUserInput toCreateUserInput() {
        return new CreateUserInput(username, email, strategy.equals(Strategy.PASSWORD), password);
    }
}
