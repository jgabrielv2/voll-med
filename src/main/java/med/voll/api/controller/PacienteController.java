package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.pacientes.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

    private final PacienteRepository pacienteRepository;

    public PacienteController(PacienteRepository pacienteRepository){
        this.pacienteRepository = pacienteRepository;
    }

    @PostMapping
    @Transactional
    public String cadastrar(@RequestBody @Valid DadosCadastroPaciente dados){
        pacienteRepository.save(new Paciente(dados));
        return "Paciente cadastrado com sucesso meu pasero";
    }

    @GetMapping
    public Page<DadosListagemPaciente> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao){
        return pacienteRepository.findAllByAtivoTrue(paginacao).map(DadosListagemPaciente::new);
    }

    @PutMapping
    @Transactional
    public String atualizar(@RequestBody DadosAtualizacaoPaciente dados){
        Paciente p = pacienteRepository.getReferenceById(dados.id());
        p.atualizarInformacoes(dados);
        return "Paciente atualizado meu pasero";
    }

    @DeleteMapping("{id}")
    @Transactional
    public String excluir(@PathVariable Long id){
        Paciente p = pacienteRepository.getReferenceById(id);
        p.excluir();
        return "Inativaste o paciente, meu pasero";
    }



}
