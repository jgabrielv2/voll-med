package med.voll.api.endereco;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@ToString
public class Endereco {

    private String logradouro;

    private String bairro;

    private String cep;

    private String cidade;

    private String uf;

    private String numero;

    private String complemento;

    public Endereco(DadosEndereco dadosEndereco){
       this.logradouro = dadosEndereco.logradouro();
       this.bairro = dadosEndereco.bairro();
       this.cep = dadosEndereco.cep();
       this.cidade = dadosEndereco.cidade();
       this.uf = dadosEndereco.uf();
       this.numero = dadosEndereco.numero();
       this.complemento = dadosEndereco.complemento();
    }
}
