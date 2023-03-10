package med.voll.api.pacientes;

import med.voll.api.endereco.Endereco;

import java.io.Serializable;

/**
 * A DTO for the {@link Paciente} entity
 */
public record DadosDetalhamentoPaciente(Long id, String nome, String email, String telefone, String cpf,
                                        Endereco endereco) {

    public DadosDetalhamentoPaciente(Paciente p){
        this(p.getId(), p.getNome(), p.getEmail(), p.getTelefone(), p.getCpf(), p.getEndereco());
    }
}