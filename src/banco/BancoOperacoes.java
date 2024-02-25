package banco;

import banco.conta.Conta;
import banco.loterias.LoteriasTipos;

import java.util.List;
import java.util.Optional;

public interface BancoOperacoes {
    Boolean criarConta(Conta conta);

    Boolean acharConta(int contaNumero);

    Optional<Conta> logar(String email, String senha);

    String depositar(Double valor, Conta conta_);

    String sacar(Double valor, Conta conta_);

    String transferir(Double valor, Conta conta_, int numeroContaDestino);


    void extrato(int numeroConta);

    Double loteriasValorDaAposta(int numerosDeAposta, LoteriasTipos tipos);

    void loterias(LoteriasTipos loterias, List<Integer> numerosJogados, String times);

    Boolean cobrarAposta(Conta conta, Double valor);

    List<Conta> listarContas();

}
