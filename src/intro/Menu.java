package intro;

import banco.conta.Conta;

import java.util.List;

public class Menu {
    public static void verContas(List<Conta> contas) {
        System.out.println("| -- -------------------------------- -- |");
        for (Conta c : contas) {
            System.out.println("| -- Nome: " + c.getUsuario().nome() + " - Banco: " + c.getAgencia().nome() + " - Nº Conta: " + c.getNumeroConta() + " -- |");
        }
        System.out.println("| -- -------------------------------- -- |");
    }

    public static void menuBancoPrincipal() {
        System.out.println();
        System.out.println("Bem vindo ao VaiNuBank");
        System.out.println("Escolha uma opção:");
        System.out.println("1 - Criar uma conta");
        System.out.println("2 - Acessar a conta");
        System.out.println("3 - Ver Contas");
        System.out.println("4 - Sair");
        System.out.print(": ");
    }

    public static void menuBancoLogado() {
        System.out.println();
        System.out.println("Escolha uma opção:");
        System.out.println("1 - Depositar");
        System.out.println("2 - Sacar");
        System.out.println("3 - Transferir");
        System.out.println("4 - Extrato");
        System.out.println("5 - Loterias");
        System.out.println("6 - Sair");
        System.out.print(": ");
    }
}
