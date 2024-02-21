package banco;

public interface BancoOperacoes {
    Boolean criarConta(BancoConta conta);

    String depositar(Long valor, int numeroConta);
    String sacar(Long valor, int numeroConta);
    String transferir(Long valor, int numeroConta, int numeroContaDestino);
    void extrato(int numeroConta);
    void sair();

}
