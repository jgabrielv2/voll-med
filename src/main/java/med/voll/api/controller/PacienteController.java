package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.pacientes.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@SuppressWarnings("ClassHasNoToStringMethod")
@SecurityRequirement(name = "bearer-key")
@RestController
@RequestMapping("pacientes")
public class PacienteController {

    private final PacienteRepository pacienteRepository;

    public PacienteController(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoPaciente> cadastrar(@RequestBody @Valid DadosCadastroPaciente dados, UriComponentsBuilder uriComponentsBuilder) {
        Paciente p = new Paciente(dados);
        pacienteRepository.save(p);

        var uri = uriComponentsBuilder.path("/pacientes/{id}").buildAndExpand(p.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(p));
    }

    @GetMapping("{id}")
    public ResponseEntity<DadosDetalhamentoPaciente> detalhar(@PathVariable Long id) {
        Paciente p = pacienteRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoPaciente(p));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemPaciente>> listar(@PageableDefault(sort = {"nome"}) Pageable paginacao) {
        Page<DadosListagemPaciente> listagemPacientes = pacienteRepository.findAllByAtivoTrue(paginacao).map(DadosListagemPaciente::new);
        return ResponseEntity.ok(listagemPacientes);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoPaciente> atualizar(@RequestBody DadosAtualizacaoPaciente dados) {
        Paciente p = pacienteRepository.getReferenceById(dados.id());
        p.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhamentoPaciente(p));
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        Paciente p = pacienteRepository.getReferenceById(id);
        p.excluir();
        return ResponseEntity.noContent().build();
    }


}
