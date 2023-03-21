package med.voll.api.consultas.validacao;

import med.voll.api.consultas.ConsultaRepository;
import med.voll.api.consultas.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

@Component
public class DisponibilidadeDeMedico implements ValidadorDeConsulta {

    private final ConsultaRepository consultaRepository;

    public DisponibilidadeDeMedico (ConsultaRepository consultaRepository){
        this.consultaRepository = consultaRepository;
    }

    @Override
    public void validar(DadosAgendamentoConsulta dados) {

        if (dados.idMedico() == null) {
            return;
        }

        if (consultaRepository.existsByMedico_IdAndData(dados.idMedico(), dados.data())){
            throw new IllegalArgumentException("Não há consulta disponível para o médico e horário escolhido. Por favor, selecione outro médioc ou outra data.");
        }


    }
}
