package med.voll.api.domain.usuario;

import java.io.Serializable;

/**
 * A DTO for the {@link Usuario} entity
 */
public record DadosAutenticacao(String login, String senha){
}