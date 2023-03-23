package med.voll.api.consultas.validacao.agendamento;

import med.voll.api.consultas.ConsultaRepository;
import med.voll.api.consultas.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

@Component
public class MaisDeUmaConsultaNoDia implements ValidadorAgendamentoDeConsulta {

    private final ConsultaRepository consultaRepository;

    public MaisDeUmaConsultaNoDia(ConsultaRepository consultaRepository){
        this.consultaRepository = consultaRepository;
    }
    @Override
    public void validar(DadosAgendamentoConsulta dados) {
        var dataConsulta = dados.data();
        var primeiroHorario = dataConsulta.withHour(7);
        var ultimoHorario = dataConsulta.withHour(18);
        var jaPossuiConsultaNoMesmoDia = consultaRepository.existsByPaciente_IdAndDataBetween(dados.idPaciente(), primeiroHorario, ultimoHorario);

        if (jaPossuiConsultaNoMesmoDia) {
            throw new IllegalArgumentException("Infelizmente não é possível agendar uma nova consulta para este paciente nesta data, pois já há um agendamento existente. Por favor, selecione outra data ou horário.");
        }


    }
}
