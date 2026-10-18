
package com.app.Usuario.dto;

import com.app.Usuario.enums.Rol;

public class UsuarioDto {

    private Integer id;
    private String nombreCompleto;
    private String email;
    private Rol rol;

    public UsuarioDto() {
    }

    public UsuarioDto(Integer id, String nombreCompleto,
                      String email, Rol rol) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.email = email;
        this.rol = rol;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
