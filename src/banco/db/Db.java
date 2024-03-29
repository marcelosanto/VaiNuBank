package banco.db;

import banco.conta.Conta;

import java.util.List;
import java.util.Optional;

public interface Db {
    List<Conta> bancoDeDados();

    Boolean inserir(Conta conta);

    Conta atualizar(Conta conta);

    Boolean exluir(Conta conta);

    Conta achar(String email, int cpf);

    Optional<Conta> logar(String email, String senha);

}
