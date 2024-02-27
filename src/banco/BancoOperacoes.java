package banco;

import banco.conta.Conta;
import banco.conta.ContaTipo;
import banco.conta.Operacoes;
import banco.loterias.LoteriasTipos;
import banco.model.usuario.Usuario;

import java.util.List;
import java.util.Optional;

public interface BancoOperacoes {

    Boolean criarConta(Usuario usuario, ContaTipo contaTipo);

    Boolean acharConta(String email);

    Optional<Conta> logar(String email, String senha);

    String depositar(Double valor, Conta conta_, Operacoes operacoes, String nome);

    String cobrar(Double valor, Conta conta_, Operacoes operacoes, String nome);

    String sacar(Double valor, Conta conta_);

    String transferir(Double valor, Conta conta_, String email);

    Double gerarCredito();

    void extrato(Conta conta);

    Double loteriasValorDaAposta(int numerosDeAposta, LoteriasTipos tipos);

    void loterias(Conta conta, LoteriasTipos loterias, List<Integer> numerosJogados, String times);

    Boolean cobrarAposta(Conta conta, Double valor);

    List<Conta> listarContas();

}
