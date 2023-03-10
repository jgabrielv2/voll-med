package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.pacientes.DadosCadastroPaciente;
import med.voll.api.pacientes.Paciente;
import med.voll.api.pacientes.PacienteRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

    private final PacienteRepository pacienteRepository;

    public PacienteController(PacienteRepository pacienteRepository){
        this.pacienteRepository = pacienteRepository;
    }

    @PostMapping
    public String cadastrar(@RequestBody @Valid DadosCadastroPaciente dados){
        pacienteRepository.save(new Paciente(dados));
        return "Paciente cadastrado com sucesso meu pasero";
    }


}
