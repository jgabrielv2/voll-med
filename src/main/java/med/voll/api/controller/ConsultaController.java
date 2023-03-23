package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.consultas.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        var consulta = consultaService.agendar(dados);
        return ResponseEntity.ok(consulta);
    }

    @GetMapping
    public ResponseEntity<List<DadosDetalhamentoConsulta>> listar(){
        var consultas = consultaService.listar();
        return ResponseEntity.ok(consultas);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> detalhar(@PathVariable Long id){
        var consulta = consultaService.detalhar(id);
        return ResponseEntity.ok(consulta);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<Void> cancelar(@RequestBody @Valid DadosCancelamentoConsulta dados) {
        consultaService.cancelar(dados);
        return ResponseEntity.noContent().build();
    }

}
