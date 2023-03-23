package med.voll.api.consultas.validacao.agendamento;

import med.voll.api.consultas.DadosAgendamentoConsulta;
import med.voll.api.domain.pacientes.PacienteRepository;
import org.springframework.stereotype.Component;

@Component
public class PacienteAtivo implements ValidadorAgendamentoDeConsulta {

    private final PacienteRepository pacienteRepository;

    public PacienteAtivo(PacienteRepository pacienteRepository){
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public void validar(DadosAgendamentoConsulta dados) {

        var isAtivo = pacienteRepository.findAtivoById(dados.idPaciente());

        if (!isAtivo) {
            throw new IllegalArgumentException("Desculpe, mas este paciente não está ativo no sistema. Por favor, verifique suas informações e tente novamente");
        }

    }
}
