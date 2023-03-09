package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medicos.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

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
    public String cadastrar(@RequestBody @Valid DadosCadastroMedico dados) {

        System.out.println(dados);

        medicoRepository.save(new Medico(dados));

        return "Deu certo o cadastro meu pasero";
    }

    @GetMapping
    public Page<DadosListagemMedico> listar(@PageableDefault(size = 5, sort = {"nome"}) Pageable paginacao) {

        return medicoRepository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
    }

    @PutMapping
    @Transactional
    public String atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados){
        Medico m = medicoRepository.getReferenceById(dados.id());
        m.atualizarInformacoes(dados);
        return "Deu certo a atualização meu pasero";
    }

    @DeleteMapping("{id}")
    @Transactional
    public String excluir(@PathVariable Long id){
        Medico m = medicoRepository.getReferenceById(id);
        m.excluir();
        return "Inativou o médico meu pasero";
    }


}
