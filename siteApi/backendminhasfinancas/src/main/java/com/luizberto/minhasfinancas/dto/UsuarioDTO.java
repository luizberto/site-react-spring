package com.luizberto.minhasfinancas.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private String email;
    private String senha;
    private String nome;
}
