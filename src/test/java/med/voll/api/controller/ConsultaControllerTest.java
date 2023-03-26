package med.voll.api.controller;

import med.voll.api.consultas.ConsultaService;
import med.voll.api.consultas.DadosAgendamentoConsulta;
import med.voll.api.consultas.DadosDetalhamentoConsulta;
import med.voll.api.domain.medicos.Especialidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SuppressWarnings("ClassHasNoToStringMethod")
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsultaJacksonTester;

    @Autowired
    private JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoConsultaJacksonTester;

    @MockBean
    private ConsultaService consultaService;

    @WithMockUser
    @Test
    @DisplayName("deveria retornar codigo http 400 quando informações são inválidas")
    void agendar_cenario1() throws Exception {



        var response = mockMvc.perform(MockMvcRequestBuilders.post("/consultas")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }

    @WithMockUser
    @Test
    @DisplayName("deveria retornar codigo http 200 quando informações são válidas")
    void agendar_cenario2() throws Exception {

        var data = LocalDateTime.now().plusHours(2);
        var especialidade = Especialidade.CARDIOLOGIA;
        var dadosDetalhamentoConsulta = new DadosDetalhamentoConsulta(null, 2L, 3L, data, null);
        when(consultaService.agendar(any())).thenReturn(dadosDetalhamentoConsulta);





        var response = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosAgendamentoConsultaJacksonTester
                                .write(new DadosAgendamentoConsulta(
                                2L, 5L, data, especialidade))
                                .getJson()))
                .andReturn()
                .getResponse();

        var jsonEsperado = dadosDetalhamentoConsultaJacksonTester
                .write(dadosDetalhamentoConsulta)
                .getJson();



        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);

    }
}