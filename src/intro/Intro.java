package intro;

import usuario.Usuario;

import java.util.Locale;
import java.util.Scanner;

public class Intro {
    public static void inicio() {
        Scanner sc = new Scanner(System.in);
        String escolha;
        Usuario usuario = new Usuario();

        System.out.println("Bem vindo ao NuVaiNoWEB");

        System.out.println("Gostaria de abrir a sua conta? S/N");
        escolha = sc.next();

        if (escolha.toLowerCase(Locale.ROOT).equals("s")) {
            usuario = abrirConta();
            System.out.println("Conta criada com sucesso! " + usuario.getNome());
        } else {
            System.out.println("Erro ao criar conta");
        }

        sc.close();
    }

    public static Usuario abrirConta() {
        Scanner scanner = new Scanner(System.in);

        String nome = fazerPergunta("Qual é o seu nome?", scanner);
        String sobrenome = fazerPergunta("Qual é o seu sobrenome?", scanner);
        String email = fazerPergunta("Qual é o seu email?", scanner);
        String senha = fazerPergunta("Qual é a sua senha?", scanner);

        scanner.close();

        return new Usuario(nome, sobrenome, email, senha);
    }

    private static String fazerPergunta(String pergunta, Scanner scanner) {
        String resposta;
        do {
            System.out.println(pergunta);
            resposta = scanner.nextLine().trim();
            if (resposta.isEmpty()) {
                System.out.println("Resposta inválida! "+pergunta);
            }
        } while (resposta.isEmpty());

        return resposta;
    }
}
