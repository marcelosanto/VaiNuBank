package banco.conta;

import usuario.Usuario;

import java.util.UUID;

public class Conta {
    private Usuario usuario;
    private Long saldo = 0L;
    private int numeroConta;
    private ContaTipo contaTipo;

    public Conta() {
    }

    public Conta(Usuario usuario, ContaTipo contaTipo) {
        this.usuario = usuario;
        this.saldo = 2500L;
        this.numeroConta = UUID.randomUUID().hashCode() < 0 ? -1 * UUID.randomUUID().hashCode() : UUID.randomUUID().hashCode();
        this.contaTipo = contaTipo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Long getSaldo() {
        return saldo;
    }

    public void setSaldo(Long saldo) {
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
}
