package med.voll.api.consultas;

import med.voll.api.domain.medicos.Especialidade;
import med.voll.api.domain.medicos.Medico;
import med.voll.api.domain.pacientes.Paciente;

public record DadosDetalhamentoConsulta(Long id, Medico medico, Paciente paciente, Especialidade especialidade) {

    public DadosDetalhamentoConsulta(Consulta c){
        this(c.getId(), c.getMedico(), c.getPaciente(), c.getMedico().getEspecialidade());
    }
}
