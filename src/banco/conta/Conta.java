package banco.conta;

import banco.agencia.Agencia;
import banco.model.usuario.Usuario;

import java.util.ArrayList;
import java.util.List;

public class Conta {
    private Usuario usuario;
    private Agencia agencia;
    private Double saldo = 0.0;
    private int numeroConta;
    private ContaTipo contaTipo;

    private List<ContaOperacoes> historico = new ArrayList<>();

    public Conta() {
    }

    public Conta(Usuario usuario, Agencia agencia, Double saldo, int numeroConta, ContaTipo contaTipo, List<ContaOperacoes> historico) {
        this.usuario = usuario;
        this.agencia = agencia;
        this.saldo = saldo;
        this.numeroConta = numeroConta;
        this.contaTipo = contaTipo;
        this.historico = historico;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Agencia getAgencia() {
        return agencia;
    }

    public void setAgencia(Agencia agencia) {
        this.agencia = agencia;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public int getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(int numeroConta) {
        this.numeroConta = numeroConta;
    }

    public ContaTipo getContaTipo() {
        return contaTipo;
    }

    public void setContaTipo(ContaTipo contaTipo) {
        this.contaTipo = contaTipo;
    }

    public List<ContaOperacoes> getHistorico() {
        return historico;
    }

    public void setHistorico(List<ContaOperacoes> historico) {
        this.historico = historico;
    }

    @Override
    public String toString() {
        return "Conta{" +
                " " + usuario +
                ", " + agencia +
                ", " + saldo +
                ", " + numeroConta +
                ", " + contaTipo +
                ", " + historico +
                '}';
    }
}
