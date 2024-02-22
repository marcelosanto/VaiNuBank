package banco;

import banco.conta.Conta;

import java.util.Optional;

public interface BancoOperacoes {
    Boolean criarConta(Conta conta);

    Optional<Conta> logar(String email, String senha);

    String depositar(Long valor, int numeroConta);

    String sacar(Long valor, int numeroConta);

    String transferir(Long valor, int numeroConta, int numeroContaDestino);

    void extrato(int numeroConta);

    void sair();

}
