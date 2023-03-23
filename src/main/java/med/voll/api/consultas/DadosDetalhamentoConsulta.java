package med.voll.api.consultas;

import java.time.LocalDateTime;

public record DadosDetalhamentoConsulta(Long id, Long idMedico, Long idPaciente, LocalDateTime data,
                                        MotivoCancelamento motivoCancelamento) {

    public DadosDetalhamentoConsulta(Consulta c) {
        this(c.getId(), c.getMedico().getId(), c.getPaciente().getId(), c.getData(), c.getMotivo());
    }
}
