package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medicos.DadosCadastroMedico;
import med.voll.api.medicos.DadosListagemMedico;
import med.voll.api.medicos.Medico;
import med.voll.api.medicos.MedicoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ClassHasNoToStringMethod")
@RestController
@RequestMapping("medicos")
public class MedicoController {

    private final MedicoRepository medicoRepository;

    public MedicoController(MedicoRepository medicoRepository){
        this.medicoRepository = medicoRepository;
    }

    @PostMapping
    @Transactional
    public String cadastrar(@RequestBody @Valid DadosCadastroMedico dados){

        System.out.println(dados);

        medicoRepository.save(new Medico(dados));

        return "Deu certo pasero";
    }

    @GetMapping
    public List<DadosListagemMedico> listar(){
// List<DadosListagemMedico> list = new ArrayList<>();
// for (Medico medico : medicoRepository.findAll()) {
//     DadosListagemMedico dadosListagemMedico = new DadosListagemMedico(medico);
//     list.add(dadosListagemMedico);
// }
// return list;
        return medicoRepository.findAll().stream().map(DadosListagemMedico::new).toList();
    }


}
