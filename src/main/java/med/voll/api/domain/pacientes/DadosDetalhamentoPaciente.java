package med.voll.api.domain.pacientes;

import med.voll.api.domain.endereco.Endereco;

/**
 * A DTO for the {@link Paciente} entity
 */
public record DadosDetalhamentoPaciente(Long id, String nome, String email, String telefone, String cpf,
                                        Endereco endereco) {

    public DadosDetalhamentoPaciente(Paciente p){
        this(p.getId(), p.getNome(), p.getEmail(), p.getTelefone(), p.getCpf(), p.getEndereco());
    }
}