package db;

import banco.Banco;
import banco.BancoConta;
import usuario.Usuario;

import java.util.List;

public interface Db {
    List<BancoConta> bancoDeDados();
    Boolean inserir(BancoConta conta);
    BancoConta atualizar(BancoConta conta);
    Boolean exluir(BancoConta conta);
    BancoConta achar(int numeroConta);

}
