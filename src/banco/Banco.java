package banco;

import banco.conta.Conta;
import banco.conta.ContaOperacoes;
import banco.conta.ContaTipo;
import banco.conta.Operacoes;
import db.Db_;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Banco implements BancoOperacoes {
    private final Db_ db = new Db_();
    private final List<ContaOperacoes> historico = new ArrayList<>();


    @Override
    public Boolean criarConta(Conta conta) {
        return db.inserir(conta);
    }

    @Override
    public Boolean acharConta(int contaNumero) {
        return db.achar(contaNumero) != null;
    }

    @Override
    public Optional<Conta> logar(String email, String senha) {
        return db.logar(email, senha);
    }


    @Override
    public String depositar(Double valor, Conta conta_) {
        Conta conta = db.achar(conta_.getNumeroConta());

        if (conta != null) {
            conta.setSaldo(conta.getSaldo() + valor);
            historico.add(new ContaOperacoes(Operacoes.DEPOSITAR, valor, conta.getUsuario().nome(), null));
            conta.setHistorico(historico);
            db.atualizar(conta);
            return "Deposito realizado";
        }

        return "Erro ao realizar o deposito";
    }

    @Override
    public String sacar(Double valor, Conta conta_) {
        Conta conta = db.achar(conta_.getNumeroConta());

        if (conta != null) {
            if (conta.getSaldo() < 0 && valor > conta.getSaldo()) {
                return "Saldo insuficiente";
            }

            if (conta.getContaTipo().equals(ContaTipo.POUPANCA)) {
                Double juros = ((2.0 / 100.0) * valor);
                Double valorMenosPorcentagem = valor - juros;

                if (valorMenosPorcentagem + juros > conta.getSaldo()) {
                    return "Saldo insuficiente";
                }

                conta.setSaldo(conta.getSaldo() - valorMenosPorcentagem - juros);
                historico.add(new ContaOperacoes(Operacoes.SACAR, valorMenosPorcentagem, conta.getUsuario().nome(), juros));
                conta.setHistorico(historico);
                db.atualizar(conta);

                return "Transferencia realizada";
            }

            conta.setSaldo(conta.getSaldo() - valor);
            historico.add(new ContaOperacoes(Operacoes.SACAR, valor, conta.getUsuario().nome(), null));
            conta.setHistorico(historico);
            db.atualizar(conta);

            return "Saque realizado";
        }

        return "Erro ao realizar o saque";
    }

    @Override
    public String transferir(Double valor, Conta conta_, int numeroContaDestino) {
        Conta conta = db.achar(conta_.getNumeroConta());
        Conta contaDestino = db.achar(numeroContaDestino);

        if (conta != null && contaDestino != null) {
            if (conta.getSaldo() < 0 && valor > conta.getSaldo()) {
                return "Saldo insuficiente";
            }

            conta.setSaldo(conta.getSaldo() - valor);
            historico.add(new ContaOperacoes(Operacoes.TRANSFERENCIA, valor, contaDestino.getUsuario().nome(), null));
            conta.setHistorico(historico);
            db.atualizar(conta);

            contaDestino.setSaldo(contaDestino.getSaldo() + valor);
            contaDestino.setHistorico(List.of(new ContaOperacoes(Operacoes.RECEBIDO, valor, conta.getUsuario().nome(), null)));
            db.atualizar(contaDestino);

            return "Transferencia realizada";
        }

        return "Erro ao realizar a Transferencia";
    }

    @Override
    public void extrato(int numeroConta) {
        Conta conta = db.achar(numeroConta);

        if (conta != null) {
            System.out.println("| -- BANCO: " + conta.getAgencia().nome() + " -------------------- -- |");
            System.out.println("| -- AG: " + conta.getAgencia().numero() + " -- CONTA-" + conta.getContaTipo() + ": " + conta.getNumeroConta() + " -- |");
            System.out.println("| -- CLIENTE: " + conta.getUsuario().nome() + " " + conta.getUsuario().sobrenome() + "-- |");
            System.out.println("| -- -------------------------------- -- |");

            System.out.println("| -- TRANSAÇÕES:-- |");
            for (ContaOperacoes historico : conta.getHistorico()) {
                String juros = historico.juros() != null ? historico.juros().toString() : " ";
                System.out.println("| -- " + historico.operacoes() + ": " + historico.valor() + " - " + historico.nome() + " - " + juros + " -- |");
            }
            System.out.println("| -- -------------------------------- -- |");
            System.out.println("| -- SALDO: R$ " + conta.getSaldo() + " -- |");
            System.out.println("| -- -------------------------------- -- |");


            //System.out.println(String.format("%-32s", "--------------------------------"));
        }

    }

    @Override
    public void sair() {
        System.out.println("Saindo do sistema...");
    }

    @Override
    public List<Conta> listarContas() {
        return db.getDbContas();
    }

}
