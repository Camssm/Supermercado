
package com.app.Usuario.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.app.Usuario.entity.Usuario;
import com.app.Usuario.enums.Rol;
import com.app.Usuario.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminConfig {

    @Bean
    public CommandLineRunner crearAdministrador(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {
            if (usuarioRepository.findByNombreCompleto("admin").isEmpty()) {

                Usuario admin = new Usuario();
                admin.setNombreCompleto("admin");
                admin.setEmail("admin@supermercado.local");
                admin.setPassword(passwordEncoder.encode("1234"));
                admin.setRol(Rol.ADMIN);

                usuarioRepository.save(admin);

                System.out.println("Administrador inicial creado.");
            }
        };
    }
}
