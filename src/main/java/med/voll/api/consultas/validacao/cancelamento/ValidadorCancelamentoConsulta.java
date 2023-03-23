package med.voll.api.consultas.validacao.cancelamento;

import med.voll.api.consultas.DadosCancelamentoConsulta;

public interface ValidadorCancelamentoConsulta {
    void validar(DadosCancelamentoConsulta dados);
}
