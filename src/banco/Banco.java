package banco;

import banco.agencia.Agencia;
import banco.conta.Conta;
import banco.conta.ContaOperacoes;
import banco.conta.ContaTipo;
import banco.conta.Operacoes;
import banco.db.Db_;
import banco.loterias.Loterias;
import banco.loterias.LoteriasTipos;
import banco.model.usuario.Usuario;
import banco.print.Prints;
import banco.seguranca.Seguranca;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Banco implements BancoOperacoes {
    private final Db_ db = new Db_();
    private final Loterias lotecas = new Loterias();

    @Override
    public Boolean criarConta(Usuario usuario, ContaTipo contaTipo) {
        Conta conta;

        while (true) {
            int numeroConta = new Random().nextInt(99999);

            if (!acharConta(usuario.email())) {
                conta = new Conta(new Usuario(Seguranca.gerarID(), usuario.nome(), usuario.sobrenome(), usuario.cpf(), usuario.email(), Seguranca.gerarHash(usuario.senha())), new Agencia(4293, "Vai no banco"), 2500.0, numeroConta, contaTipo, List.of());
                System.out.print("Conta criada com sucesso! ");
                break;
            } else {
                System.out.println("Email/CPF ja cadastrado...");
                return false;
            }
        }

        return db.inserir(conta);
    }

    @Override
    public Boolean acharConta(String email) {
        return db.achar(email) != null;
    }

    @Override
    public Optional<Conta> logar(String email, String senha) {
        return db.logar(email, Seguranca.gerarHash(senha));
    }


    @Override
    public String depositar(Double valor, Conta conta_, Operacoes operacoes, String nome) {
        Conta conta = db.achar(conta_.getUsuario().email());

        if (conta != null) {
            conta.setSaldo(conta.getSaldo() + valor);

            List<ContaOperacoes> historico = new ArrayList<>();

            if (!conta.getHistorico().isEmpty()) {
                historico.addAll(conta.getHistorico());
            }

            historico.add(new ContaOperacoes(operacoes, valor, nome, null));

            conta.setHistorico(historico);
            db.atualizar(conta);

            return "Deposito realizado";
        }

        return "Erro ao realizar o deposito";
    }

    @Override
    public String cobrar(Double valor, Conta conta_, Operacoes operacoes, String nome) {

        Conta conta = db.achar(conta_.getUsuario().email());

        if (conta != null && conta.getSaldo() >= valor) {

            conta.setSaldo(conta.getSaldo() - valor);

            List<ContaOperacoes> historico = new ArrayList<>();

            if (!conta.getHistorico().isEmpty()) {
                historico.addAll(conta.getHistorico());
            }

            historico.add(new ContaOperacoes(operacoes, valor, nome, null));
            conta.setHistorico(historico);
            db.atualizar(conta);


            return "Valor cobrado";
        }

        return "Erro ao realizar a cobrança";
    }


    @Override
    public String sacar(Double valor, Conta conta) {
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

                List<ContaOperacoes> historico = new ArrayList<>();

                if (!conta.getHistorico().isEmpty()) {
                    historico.addAll(conta.getHistorico());
                }

                conta.setSaldo(conta.getSaldo() - valorMenosPorcentagem - juros);
                historico.add(new ContaOperacoes(Operacoes.SACAR, valorMenosPorcentagem, conta.getUsuario().nome(), juros));
                conta.setHistorico(historico);
                db.atualizar(conta);

                return "Transferencia realizada";
            }

            List<ContaOperacoes> historico = new ArrayList<>();

            if (!conta.getHistorico().isEmpty()) {
                historico.addAll(conta.getHistorico());
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
    public String transferir(Double valor, Conta conta, String email) {
        Conta contaDestino = db.achar(email);

        if (conta != null && contaDestino != null) {
            if (conta.getSaldo() < 0 && valor > conta.getSaldo()) {
                return "Saldo insuficiente";
            }

            cobrar(valor, conta, Operacoes.TRANSFERENCIA, contaDestino.getUsuario().nome());

            depositar(valor, contaDestino, Operacoes.RECEBIDO, conta.getUsuario().nome());

            return "Transferencia realizada";
        }

        return "Erro ao realizar a Transferencia";
    }

    @Override
    public void extrato(Conta conta) {
        if (conta != null) {
            Prints.printExtratoConta(conta);
        }

    }

    @Override
    public Double loteriasValorDaAposta(int numerosDeAposta, LoteriasTipos tipos) {
        return lotecas.valorDaAposta(numerosDeAposta, tipos);
    }

    @Override
    public void loterias(Conta conta, LoteriasTipos loterias, List<Integer> numerosJogados, String times) {
        Double valorGanho = lotecas.realizarSorteio(loterias, numerosJogados, times);

        if (valorGanho > 0.0) {
            depositar(valorGanho, conta, Operacoes.LOTERIAS, loterias.name());
        }
    }

    @Override
    public Boolean cobrarAposta(Conta conta, Double valor) {
        if (conta.getSaldo() < valor) {
            return false;
        }

        cobrar(valor, conta, Operacoes.LOTERIAS, conta.getUsuario().nome());

        return true;
    }


    @Override
    public List<Conta> listarContas() {
        return db.getDbContas();
    }

}
