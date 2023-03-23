package med.voll.api.consultas.validacao.agendamento;

import med.voll.api.consultas.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class AntecedenciaMarcacao implements ValidadorAgendamentoDeConsulta {
    @Override
    public void validar(DadosAgendamentoConsulta dados) {

        var dataConsulta = dados.data();
        var dataAtual = LocalDateTime.now();

        var isLessThan30Min = Duration.between(dataAtual, dataConsulta).toMinutes() < 30;

        if (isLessThan30Min) {
            throw new IllegalArgumentException("Desculpe, mas para garantir que eu possa lhe atender da melhor forma possível, é necessário agendar a consulta com pelo menos 30 minutos de antecedência. Obrigado pela compreensão!");
        }
    }
}
