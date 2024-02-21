package banco;

import usuario.Usuario;

import java.util.UUID;

public class BancoConta {
    Usuario usuario;
    Long saldo = 0L;
    int numeroConta;

    public BancoConta() {

    }

    public BancoConta(Usuario usuario) {
        this.usuario = usuario;
        this.saldo = 2500L;
        this.numeroConta = UUID.randomUUID().hashCode() <0 ? -1 * UUID.randomUUID().hashCode() : UUID.randomUUID().hashCode();
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
}
