package dev.cloudeko.zenei.application.web.model.request;

import jakarta.ws.rs.FormParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @FormParam("identifier")
    private String identifier;
    @FormParam("password")
    private String password;
}
