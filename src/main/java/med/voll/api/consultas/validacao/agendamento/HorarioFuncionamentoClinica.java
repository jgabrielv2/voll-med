package med.voll.api.consultas.validacao.agendamento;

import med.voll.api.consultas.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class HorarioFuncionamentoClinica implements ValidadorAgendamentoDeConsulta {

    @Override
    public void validar(DadosAgendamentoConsulta dados) {

        var dataConsulta = dados.data();

        var isDomingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);

        var isAntesFuncionamento = dataConsulta.getHour() < 7;

        var isAposFuncionamento = dataConsulta.getHour() > 18;

        if (isDomingo || isAntesFuncionamento || isAposFuncionamento) {
            throw new IllegalArgumentException("Lembre-se de verificar o horário de funcionamento da clínica antes de agendar sua consulta");
        }


    }
}
