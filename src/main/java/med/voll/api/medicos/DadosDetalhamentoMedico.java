package med.voll.api.medicos;

import med.voll.api.endereco.Endereco;
import med.voll.api.medicos.Especialidade;
import med.voll.api.medicos.Medico;

import java.io.Serializable;

/**
 * A DTO for the {@link med.voll.api.medicos.Medico} entity
 */
public record DadosDetalhamentoMedico(Long id, String nome, String email, String telefone, String crm,
                                      Especialidade especialidade, Endereco endereco) {

    public DadosDetalhamentoMedico(Medico m){
        this(m.getId(), m.getNome(), m.getEmail(), m.getTelefone(), m.getCrm(), m.getEspecialidade(), m.getEndereco());
    }

}