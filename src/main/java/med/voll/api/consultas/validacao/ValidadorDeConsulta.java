package med.voll.api.consultas.validacao;

import med.voll.api.consultas.DadosAgendamentoConsulta;

public interface ValidadorDeConsulta {
    void validar(DadosAgendamentoConsulta dados);
}
