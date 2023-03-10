package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medicos.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@SuppressWarnings("ClassHasNoToStringMethod")
@RestController
@RequestMapping("medicos")
public class MedicoController {

    private final MedicoRepository medicoRepository;

    public MedicoController(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriComponentsBuilder) {

        Medico m = new Medico(dados);
        medicoRepository.save(m);

        var uri = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(m.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(m));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(size = 5, sort = {"nome"}) Pageable paginacao) {

        var listagemMedicos = medicoRepository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);

        return ResponseEntity.ok(listagemMedicos);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
        Medico m = medicoRepository.getReferenceById(dados.id());
        m.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(m));
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        Medico m = medicoRepository.getReferenceById(id);
        m.excluir();
        return ResponseEntity.noContent().build();
    }


}
