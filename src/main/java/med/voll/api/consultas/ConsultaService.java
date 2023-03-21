package med.voll.api.consultas;

import med.voll.api.consultas.validacao.ValidadorDeConsulta;
import med.voll.api.domain.medicos.Medico;
import med.voll.api.domain.medicos.MedicoRepository;
import med.voll.api.domain.pacientes.Paciente;
import med.voll.api.domain.pacientes.PacienteRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@SuppressWarnings("ClassHasNoToStringMethod")
@Service
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;
    private final List<ValidadorDeConsulta> validadores;

    public ConsultaService(ConsultaRepository consultaRepository, MedicoRepository medicoRepository, PacienteRepository pacienteRepository, List<ValidadorDeConsulta> validadores) {
        this.consultaRepository = consultaRepository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
        this.validadores = validadores;

    }

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) {


        if (!pacienteRepository.existsById(dados.idPaciente())) {
            throw new IllegalArgumentException("Paciente não encontrado com o Id fornecido.");
        }
        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
            throw new IllegalArgumentException("Médico não encontrado com o Id fornecido");
        }

        validadores.forEach(v -> v.validar(dados));


        Paciente p = pacienteRepository.getReferenceById(dados.idPaciente());
        Medico m = escolherMedico(dados);
        if (m == null){
            throw new IllegalArgumentException("Não há médico disponível nesta data!");
        }

        Consulta c = new Consulta();
        c.setData(dados.data());
        c.setMedico(m);
        c.setPaciente(p);
        consultaRepository.save(c);
        return new DadosDetalhamentoConsulta(c);
    }


    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if (dados.idMedico() != null) {
            return medicoRepository.getReferenceById(dados.idMedico());
        }
        if (dados.especialidade() == null) {
            throw new IllegalArgumentException("Pelo menos um dos seguintes ítens é obrigatório: medico, especialidade.");
        }
        return medicoRepository.findMedicoLivreNaEspecialidadeHorarioFornecido(dados.especialidade(), dados.data());
    }

    public void cancelar(DadosCancelamentoConsulta dados) {
        if (consultaRepository.existsById(dados.idConsulta())) {
            throw new IllegalArgumentException("Não existe consulta com a Id informada");
        }
        var consulta = consultaRepository.getReferenceById(dados.idConsulta());

//      if (consulta.getData().minusHours(24).isBefore(LocalDateTime.now())){
        if (Duration.between(LocalDateTime.now(), consulta.getData()).toHours() > 24) {
            throw new IllegalArgumentException("A consulta somente poderá ser desmarcada com antecedência mínima de 24 horas");
        }
        consulta.cancelar(dados.motivo());
    }
}


