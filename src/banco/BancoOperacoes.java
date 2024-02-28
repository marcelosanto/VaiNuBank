package banco;

import banco.conta.Conta;
import banco.conta.ContaTipo;
import banco.conta.Operacoes;
import banco.loterias.LoteriasTipos;
import banco.model.usuario.Usuario;

import java.util.List;
import java.util.Optional;

public interface BancoOperacoes {

    boolean criarConta(Usuario usuario, ContaTipo contaTipo);

    boolean acharConta(String email, int cpf);

    Optional<Conta> logar(String email, String senha);

    String depositar(Double valor, Conta conta_, Operacoes operacoes, String nome);

    String cobrar(Double valor, Conta conta_, Operacoes operacoes, String nome);

    String sacar(Double valor, Conta conta_);

    String transferir(Double valor, Conta conta_, String email, int cpf);

    Double gerarCredito();

    void extrato(Conta conta);

    Double loteriasValorDaAposta(int numerosDeAposta, LoteriasTipos tipos);

    void loterias(Conta conta, LoteriasTipos loterias, List<Integer> numerosJogados, String times);

    Boolean cobrarAposta(Conta conta, Double valor);

    List<Conta> listarContas();

    String enviarPix(String login, String senha, String contaPix, Double valorPix);
}
