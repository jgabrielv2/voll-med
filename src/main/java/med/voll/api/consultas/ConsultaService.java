package med.voll.api.consultas;

import med.voll.api.consultas.validacao.agendamento.ValidadorAgendamentoDeConsulta;
import med.voll.api.consultas.validacao.cancelamento.ValidadorCancelamentoConsulta;
import med.voll.api.domain.medicos.Medico;
import med.voll.api.domain.medicos.MedicoRepository;
import med.voll.api.domain.pacientes.Paciente;
import med.voll.api.domain.pacientes.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@SuppressWarnings("ClassHasNoToStringMethod")
@Service
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;
    private final List<ValidadorAgendamentoDeConsulta> validadoresAgendamento;

    private final List<ValidadorCancelamentoConsulta> validadoresCancelamentoConsulta;

    public ConsultaService(ConsultaRepository consultaRepository, MedicoRepository medicoRepository,
                           PacienteRepository pacienteRepository, List<ValidadorAgendamentoDeConsulta> validadoresAgendamento,
                           List<ValidadorCancelamentoConsulta> validadoresCancelamentoConsulta) {
        this.consultaRepository = consultaRepository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
        this.validadoresAgendamento = validadoresAgendamento;
        this.validadoresCancelamentoConsulta = validadoresCancelamentoConsulta;

    }

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) {


        if (!pacienteRepository.existsById(dados.idPaciente())) {
            throw new IllegalArgumentException("Paciente não encontrado com o Id fornecido.");
        }
        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
            throw new IllegalArgumentException("Médico não encontrado com o Id fornecido");
        }

        validadoresAgendamento.forEach(v -> v.validar(dados));


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

    public List<DadosDetalhamentoConsulta> listar(){
        return consultaRepository.findAll().stream().map(DadosDetalhamentoConsulta::new).toList();
    }

    public DadosDetalhamentoConsulta detalhar(Long id){
       return new DadosDetalhamentoConsulta(consultaRepository.getReferenceById(id));
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
        if (!consultaRepository.existsById(dados.idConsulta())) {
            throw new IllegalArgumentException("Não existe consulta com a Id informada");
        }

        validadoresCancelamentoConsulta.forEach(v -> v.validar(dados));
        var consulta = consultaRepository.getReferenceById(dados.idConsulta());

        consulta.cancelar(dados.motivo());
    }
}


