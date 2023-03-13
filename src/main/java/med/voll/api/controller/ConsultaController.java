package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.consultas.Consulta;
import med.voll.api.consultas.ConsultaService;
import med.voll.api.consultas.DadosAgendamentoConsulta;
import med.voll.api.consultas.DadosDetalhamentoConsulta;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("ClassHasNoToStringMethod")
@RestController
@RequestMapping("consultas")
public class ConsultaController {

    private final ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> agendar(@RequestBody @Valid DadosAgendamentoConsulta dados) {
        Consulta c = consultaService.agendar(dados);
        return ResponseEntity.ok(new DadosDetalhamentoConsulta(c));
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<?> cancelar(@PathVariable Long id) {
        consultaService.cancelar(id);
        return ResponseEntity.noContent().build();
    }

}
