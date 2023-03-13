package med.voll.api.consultas;

import med.voll.api.domain.medicos.Medico;
import med.voll.api.domain.medicos.MedicoRepository;
import med.voll.api.domain.pacientes.Paciente;
import med.voll.api.domain.pacientes.PacienteRepository;
import org.springframework.stereotype.Service;

@SuppressWarnings("ClassHasNoToStringMethod")
@Service
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;

    public ConsultaService(ConsultaRepository consultaRepository, MedicoRepository medicoRepository, PacienteRepository pacienteRepository) {
        this.consultaRepository = consultaRepository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;

    }

    public Consulta agendar(DadosAgendamentoConsulta dados) {
        if (!pacienteRepository.existsById(dados.idPaciente())) {
            throw new IllegalArgumentException("Paciente não encontrado com o Id fornecido.");
        }
        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
            throw new IllegalArgumentException("Médico não encontrado com o Id fornecido");
        }

        Medico m = escolherMedico(dados);
        Paciente p = pacienteRepository.getReferenceById(dados.idPaciente());
        Consulta c = new Consulta();
        c.setData(dados.data());
        c.setMedico(m);
        c.setPaciente(p);
        consultaRepository.save(c);
        return c;
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

    public void cancelar(Long id){
        consultaRepository.deleteById(id);
    }
}


