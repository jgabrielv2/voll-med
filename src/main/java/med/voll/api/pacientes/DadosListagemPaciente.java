package med.voll.api.pacientes;

public record DadosListagemPaciente(Long id, String nome, String email, String cpf) {

    public DadosListagemPaciente(Paciente p){
        this(p.getId(), p.getNome(), p.getEmail(), p.getCpf());
    }

}
