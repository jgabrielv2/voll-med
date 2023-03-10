package med.voll.api.domain.medicos;

import med.voll.api.domain.endereco.Endereco;

/**
 * A DTO for the {@link Medico} entity
 */
public record DadosDetalhamentoMedico(Long id, String nome, String email, String telefone, String crm,
                                      Especialidade especialidade, Endereco endereco) {

    public DadosDetalhamentoMedico(Medico m){
        this(m.getId(), m.getNome(), m.getEmail(), m.getTelefone(), m.getCrm(), m.getEspecialidade(), m.getEndereco());
    }

}