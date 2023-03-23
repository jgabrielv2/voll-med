package med.voll.api.consultas.validacao.cancelamento;

import med.voll.api.consultas.Consulta;
import med.voll.api.consultas.ConsultaRepository;
import med.voll.api.consultas.DadosCancelamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class AntecedenciaCancelamento implements  ValidadorCancelamentoConsulta{

    private final ConsultaRepository consultaRepository;

    public AntecedenciaCancelamento(ConsultaRepository consultaRepository){
        this.consultaRepository = consultaRepository;
    }

    @Override
    public void validar(DadosCancelamentoConsulta dados) {

        Consulta c = consultaRepository.getReferenceById(dados.idConsulta());

        var tempoAteConsultaMenorQue24Horas = Duration.between(LocalDateTime.now(), c.getData()).toHours() < 24;
        //      if (c.getData().minusHours(24).isBefore(LocalDateTime.now())){
        if(tempoAteConsultaMenorQue24Horas){
            throw new IllegalArgumentException("A consulta somente poderá ser desmarcada com antecedência mínima de 24 horas");
        }


    }
}
