package med.voll.api.domain.medicos;

import med.voll.api.consultas.Consulta;
import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.pacientes.DadosCadastroPaciente;
import med.voll.api.domain.pacientes.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static java.time.DayOfWeek.MONDAY;
import static java.time.LocalDate.now;
import static java.time.temporal.TemporalAdjusters.next;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("ClassHasNoToStringMethod")
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Deve retornar null quando o único médico cadastrado não está disponível no horário solicitado")
    void escolherMedicoLivreNaEspecialidadeHorarioFornecidoCenario1() {

        var medico = cadastrarMedico("Francisco Vinicius Yuri Ramos", "chico@voll.med", "542226", Especialidade.CARDIOLOGIA);
        var paciente = cadastrarPaciente("Lorenzo Gabriel Manuel Alves", "lorenzo_alves@fanger.com.br", "34779391237");
        var proximaSegundaFeiraAs10 = now().with(next(MONDAY)).atTime(10, 0);

        cadastrarConsulta(medico, paciente, proximaSegundaFeiraAs10);
        Medico medicoLivre = medicoRepository.findMedicoLivreNaEspecialidadeHorarioFornecido(Especialidade.CARDIOLOGIA, proximaSegundaFeiraAs10);

        assertThat(medicoLivre).isNull();
    }

    @Test
    @DisplayName("Deve devolver médico quando ele estiver disponívelna data")
    void escolherMedicoLivreNaEspecialidadeHorarioFornecidoCenario2() {

        var proximaSegundaFeiraAs10 = now().with(next(MONDAY)).atTime(10, 0);
        Medico m = cadastrarMedico("Paulão", "paulo@voll.med", "522499", Especialidade.ORTOPEDIA);

        var medicoLivre = medicoRepository.findMedicoLivreNaEspecialidadeHorarioFornecido(Especialidade.ORTOPEDIA, proximaSegundaFeiraAs10);

        assertThat(medicoLivre).isEqualTo(m);

    }

    private void cadastrarConsulta(Medico m, Paciente p, LocalDateTime d) {
        em.persist(new Consulta(null, m, p, null, d));
    }

    private Medico cadastrarMedico(String nome, String email, String crm, Especialidade e) {
        Medico m = new Medico(dadosMedico(nome, email, crm, e));
        em.persist(m);
        return m;
    }

    @SuppressWarnings("SameParameterValue")
    private Paciente cadastrarPaciente(String nome, String email, String cpf) {
        Paciente p = new Paciente(dadosPaciente(nome, email, cpf));
        em.persist(p);
        return p;
    }

    private DadosCadastroMedico dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
        return new DadosCadastroMedico(nome, email,
                "6326026881", crm, especialidade, dadosEndereco());
    }

    private DadosCadastroPaciente dadosPaciente(String nome, String email, String cpf) {
        return new DadosCadastroPaciente(nome, email,
                "47992135156", cpf, dadosEndereco());
    }

    private DadosEndereco dadosEndereco() {
        return new DadosEndereco("Quadra 501 Norte Rua 4 A",
                "Plano Diretor Norte", "77001808", "Palmas", "TO", "773", null);
    }
}