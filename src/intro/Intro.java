package intro;

import banco.Banco;
import banco.conta.Conta;
import banco.conta.ContaTipo;
import usuario.Usuario;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public class Intro {
    public static void inicio() {
        Scanner sc = new Scanner(System.in);
        Banco banco = new Banco();
        Conta conta;

        while (true) {
            System.out.println("Bem vindo ao VaiNuBank");
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Criar uma conta");
            System.out.println("2 - Acessar a conta");
            System.out.println("3 - Fazer transferencia");
            System.out.println("4 - Ver Contas");
            System.out.println("5 - Sair");
            System.out.print(": ");

            String escolha = sc.nextLine();

            switch (escolha) {
                case "1":
                    CustomRetorno retorno = criarUser(sc);
                    conta = new Conta(retorno.usuario(), retorno.contaTipo());
                    Boolean resultado = banco.criarConta(conta);
                    System.out.print("Conta criada com sucesso! " + resultado);
                    break;
                case "2":
                    Optional<Conta> contaLogada = fazerLogin(banco, sc);

                    if (contaLogada.isPresent()) {
                        conta = contaLogada.get();
                        while (true) {
                            System.out.println("Escolha uma opção:");
                            System.out.println("1 - Depositar");
                            System.out.println("2 - Sacar");
                            System.out.println("3 - Transferir");
                            System.out.println("4 - Extrato");
                            System.out.println("5 - Sair");
                            System.out.print(": ");

                            escolha = sc.nextLine();

                            switch (escolha) {
                                case "1":
                                    System.out.println("Depositar");
                                    break;
                                case "2":
                                    System.out.println("Sacar");
                                    break;
                                case "3":
                                    System.out.println("Transferir");
                                    break;
                                case "4":
                                    System.out.println("Extrato");
                                    System.out.println(conta.getSaldo());
                                    break;
                                case "5":
                                    System.out.println("Saindo...");
                                    sc.close();
                                    return;
                                default:
                                    System.out.println("Opção inválida");
                            }
                        }
                    }

                    break;
                case "3":
                    System.out.println("Fazer transferencia");
                    break;
                case "4":
                    System.out.println("Ver Contas");

                    List<Conta> contas = banco.listarContas();
                    System.out.println(contas.getFirst().getUsuario().nome());

                    break;
                case "5":
                    System.out.println("Saindo...");
                    sc.close();
                    return;
                default:
                    System.out.println("Opção inválida");
            }

        }

    }

    public static CustomRetorno criarUser(Scanner scanner) {
        //Scanner scanner = new Scanner(System.in);
        String nome = fazerPergunta("Qual é o seu nome?", scanner);
        String sobrenome = fazerPergunta("Qual é o seu sobrenome?", scanner);
        String cpf = fazerPergunta("Qual é o seu cpf?", scanner);
        String email = fazerPergunta("Qual é o seu email?", scanner);
        String senha = fazerPergunta("Qual é a sua senha?", scanner);

        Usuario usuario = new Usuario(UUID.randomUUID().hashCode() < 0 ? -1 * UUID.randomUUID().hashCode() : (long) UUID.randomUUID().hashCode(), nome, sobrenome, cpf, email, senha);

        String tipoConta = fazerPergunta("Qual é o tipo de conta que deseja criar 1 - Corrente, 2 - Poupança?", scanner);

        if (tipoConta.equals("1")) {
            return new CustomRetorno(usuario, ContaTipo.CORRENTE);
        } else if (tipoConta.equals("2")) {
            return new CustomRetorno(usuario, ContaTipo.POUPANCA);
        }

        return new CustomRetorno(usuario, ContaTipo.CORRENTE);
    }

    private static String fazerPergunta(String pergunta, Scanner scanner) {
        String resposta;
        do {
            System.out.println(pergunta);
            resposta = scanner.nextLine().trim();
            if (resposta.isEmpty()) {
                System.out.println("Resposta inválida! " + pergunta);
            }
        } while (resposta.isEmpty());

        return resposta;
    }

    private static Optional<Conta> fazerLogin(Banco banco, Scanner scanner) {
        Optional<Conta> conta = Optional.empty();
        boolean loginSucesso = false;
        int tentativas = 0;

        while (!loginSucesso && tentativas < 3) {
            System.out.println("Digite o seu email:");
            String email = scanner.nextLine();

            System.out.println("Digite a sua senha:");
            String senha = scanner.nextLine();

            conta = banco.logar(email, senha);

            // Verifica se o nome de usuário e senha estão corretos
            if (conta.isPresent()) {
                System.out.println("Login bem-sucedido! Bem-vindo, " + conta.get().getUsuario().nome() + ".");
                loginSucesso = true;
            } else {
                System.out.println("Nome de usuário ou senha incorretos. Tente novamente.");
                tentativas++;
            }
        }

        if (!loginSucesso) {
            System.out.println("Número máximo de tentativas atingido. Tente novamente mais tarde.");
        }


        return conta;
    }


}
