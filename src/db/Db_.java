package db;

import banco.BancoConta;
import usuario.Usuario;

import java.util.ArrayList;
import java.util.List;

public class Db_ implements Db {
    List<BancoConta> dbContas = new ArrayList<>();

    @Override
    public List<BancoConta> bancoDeDados() {
        return dbContas;
    }

    @Override
    public Boolean inserir(BancoConta conta) {
        return dbContas.add(conta);
    }

    @Override
    public BancoConta atualizar(BancoConta conta) {
        return dbContas.set(dbContas.indexOf(conta), conta);
    }

    @Override
    public Boolean exluir(BancoConta conta) {
        return dbContas.remove(conta);
    }

    @Override
    public BancoConta achar(int numeroConta) {

        for (BancoConta conta : dbContas) {
            if (conta.getNumeroConta() == numeroConta) {
                return conta;
            }
        }

        return null;
    }
}
