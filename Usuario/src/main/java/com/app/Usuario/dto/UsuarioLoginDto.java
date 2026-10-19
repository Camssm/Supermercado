
package com.app.Usuario.dto;

public class UsuarioLoginDto {

    private String nombreCompleto;
    private String password;

    public UsuarioLoginDto() {
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
