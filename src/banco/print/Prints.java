package banco.print;

import banco.conta.Conta;
import banco.conta.ContaOperacoes;


public class Prints {
    public static void printExtratoConta(Conta conta) {
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
