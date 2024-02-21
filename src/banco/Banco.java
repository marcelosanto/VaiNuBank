package banco;

import db.Db_;

public class Banco implements BancoOperacoes {
        Db_ db = new Db_();

    @Override
    public Boolean criarConta(BancoConta conta) {
        return db.inserir(conta);
    }

    @Override
    public String depositar(Long valor, int numeroConta) {
       BancoConta conta = db.achar(numeroConta);

        if (conta != null) {
            conta.setSaldo(conta.getSaldo() + valor);
            db.atualizar(conta);
            return "Deposito realizado";
        }

        return "Erro ao realizar o deposito";
    }

    @Override
    public String sacar(Long valor, int numeroConta) {
        BancoConta conta = db.achar(numeroConta);

        if (conta != null) {
            if (conta.getSaldo() < 0 && valor > conta.getSaldo()) {
                return "Saldo insuficiente";
            }
            conta.setSaldo(conta.getSaldo() - valor);
            db.atualizar(conta);
            return "Saque realizado";
        }

        return "Erro ao realizar o saque";
    }

    @Override
    public String transferir(Long valor, int numeroConta, int numeroContaDestino) {
        BancoConta conta = db.achar(numeroConta);
        BancoConta contaDestino = db.achar(numeroContaDestino);

        if (conta != null && contaDestino != null) {
            if (conta.getSaldo() < 0 && valor > conta.getSaldo()) {
                return "Saldo insuficiente";
            }

            conta.setSaldo(conta.getSaldo() - valor);
            db.atualizar(conta);

            contaDestino.setSaldo(contaDestino.getSaldo() + valor);
            db.atualizar(contaDestino);

            return "Transferencia realizada";
        }

        return "Erro ao realizar a Transferencia";
    }

    @Override
    public void extrato(int numeroConta) {
        BancoConta conta = db.achar(numeroConta);

        if (conta != null) {
            System.out.println(conta.getSaldo());
        }

    }

    @Override
    public void sair() {
        System.out.println("Saindo do sistema...");
    }
}
