package banco;

import banco.conta.Conta;
import banco.conta.ContaOperacoes;
import banco.conta.ContaTipo;
import banco.conta.Operacoes;
import banco.loterias.Loterias;
import banco.loterias.LoteriasTipos;
import banco.print.Prints;
import db.Db_;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Banco implements BancoOperacoes {
    private final Db_ db = new Db_();
    private final List<ContaOperacoes> historico = new ArrayList<>();

    private final Loterias lotecas = new Loterias();


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
            Prints.printExtratoConta(conta);
        }

    }

    @Override
    public Double loteriasValorDaAposta(int numerosDeAposta, LoteriasTipos tipos) {
        return lotecas.valorDaAposta(numerosDeAposta, tipos);
    }

    @Override
    public void loterias(LoteriasTipos loterias, List<Integer> numerosJogados, String times) {
        lotecas.realizarSorteio(loterias, numerosJogados, times); // vem como int 0 - Não ganhou, 1 - Ganhador, 2 - Ganhador em Time + Aposta

    }

    @Override
    public Boolean cobrarAposta(Conta conta_, Double valor) {
        Conta conta = db.achar(conta_.getNumeroConta());

        if (conta.getSaldo() < valor) {
            return false;
        }

        conta.setSaldo(conta.getSaldo() - valor);
        historico.add(new ContaOperacoes(Operacoes.LOTERIAS, valor, conta.getUsuario().nome(), null));
        conta.setHistorico(historico);
        db.atualizar(conta);

        return true;
    }


    @Override
    public List<Conta> listarContas() {
        return db.getDbContas();
    }

}
