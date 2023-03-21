package med.voll.api.consultas.validacao;

import med.voll.api.consultas.DadosAgendamentoConsulta;
import med.voll.api.domain.medicos.MedicoRepository;
import org.springframework.stereotype.Component;

@Component
public class MedicoAtivo implements ValidadorDeConsulta {

    private final MedicoRepository medicoRepository;

    public MedicoAtivo(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    @Override
    public void validar(DadosAgendamentoConsulta dados) {

        if (dados.idMedico() == null) {
            return;
        }

        var isAtivo = medicoRepository.findAtivoById(dados.idMedico());

        if (!isAtivo) {
            throw new IllegalArgumentException("Médico indisponível");
        }

    }
}
