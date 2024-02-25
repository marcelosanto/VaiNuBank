package db;

import banco.conta.Conta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Db_ implements Db {
    List<Conta> dbContas = new ArrayList<>();

    @Override
    public List<Conta> bancoDeDados() {
        return dbContas;
    }

    @Override
    public Boolean inserir(Conta conta) {
        return dbContas.add(conta);
    }


    @Override
    public Conta atualizar(Conta conta) {
        return dbContas.set(dbContas.indexOf(conta), conta);
    }

    @Override
    public Boolean exluir(Conta conta) {
        return dbContas.remove(conta);
    }

    @Override
    public Conta achar(String email) {
        for (Conta conta : dbContas) {
            if (Objects.equals(conta.getUsuario().email(), email)) {
                return conta;
            }
        }
        return null;
    }

    @Override
    public Optional<Conta> logar(String email, String senha) {
        for (Conta conta : dbContas) {
            if (conta.getUsuario().email().equals(email) && conta.getUsuario().senha().equals(senha)) {
                return Optional.of(conta);
            }
        }

        return Optional.empty();
    }

    public List<Conta> getDbContas() {
        return dbContas;
    }


}
