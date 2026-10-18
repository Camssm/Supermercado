
package com.app.security.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.app.security.dto.AuthRequest;
import com.app.security.dto.UsuarioLoginResponse;

@Component
public class UsuarioClient {

    private final RestTemplate restTemplate;

    @Value("${usuario.url}")
    private String usuarioUrl;

    public UsuarioClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UsuarioLoginResponse validar(AuthRequest request) {
        try {
            ResponseEntity<UsuarioLoginResponse> respuesta =
                restTemplate.postForEntity(
                    usuarioUrl + "/api/usuario/validar-login",
                    new HttpEntity<>(request),
                    UsuarioLoginResponse.class
                );

            return respuesta.getBody();

        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.UNAUTHORIZED
                    || ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                return null;
            }

            throw ex;
        }
    }
}
