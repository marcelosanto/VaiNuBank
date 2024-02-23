package intro;

import banco.Banco;
import banco.agencia.Agencia;
import banco.conta.Conta;
import banco.conta.ContaTipo;
import usuario.Usuario;
import utils.Parses;

import java.util.*;

public class Intro {
    public static void inicio() {
        Scanner sc = new Scanner(System.in);
        Banco banco = new Banco();
        Conta conta;
        boolean logado = true;

        conta = new Conta(new Usuario(UUID.randomUUID().hashCode() < 0 ? -1 * UUID.randomUUID().hashCode() : (long) UUID.randomUUID().hashCode(), "Marcelo", "Santos", "1111111", "s", "s"), new Agencia(4293, "Vai no banco"), 12345, ContaTipo.POUPANCA);
        banco.criarConta(conta);

        conta = new Conta(new Usuario(UUID.randomUUID().hashCode() < 0 ? -1 * UUID.randomUUID().hashCode() : (long) UUID.randomUUID().hashCode(), "Alice", "Santos", "1111111", "ss", "ss"), new Agencia(4293, "Vai no banco"), 54321, ContaTipo.CORRENTE);
        banco.criarConta(conta);

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
                    while (true) {
                        int numeroConta = new Random().nextInt(99999);

                        if (!banco.acharConta(numeroConta)) {
                            conta = new Conta(retorno.usuario(), new Agencia(4293, "Vai no banco"), numeroConta, retorno.contaTipo());
                            Boolean resultado = banco.criarConta(conta);

                            System.out.print("Conta criada com sucesso! " + resultado);
                            break;
                        }
                    }
                    break;

                case "2":
                    Optional<Conta> contaLogada = fazerLogin(banco, sc);
                    Double valor = 0.0;

                    if (contaLogada.isPresent()) {
                        conta = contaLogada.get();
                        logado = true;

                        while (logado) {
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
                                    valor = Parses.parseDouble(fazerPergunta("Qual é o valor?", sc));
                                    System.out.println(banco.depositar(valor, conta));
                                    break;
                                case "2":
                                    System.out.println("Sacar");
                                    valor = Parses.parseDouble(fazerPergunta("Qual é o valor?", sc));
                                    System.out.println(banco.sacar(valor, conta));
                                    break;
                                case "3":
                                    System.out.println("Transferir");
                                    int destino = Parses.parseInt(fazerPergunta("Qual é o numero da conta destino?", sc));
                                    valor = Parses.parseDouble(fazerPergunta("Qual é o valor?", sc));
                                    System.out.println(banco.transferir(valor, conta, destino));

                                    break;
                                case "4":
                                    System.out.println("Extrato");
                                    banco.extrato(conta.getNumeroConta());
                                    break;
                                case "5":
                                    System.out.println("Saindo...");
                                    logado = false;
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

                    for (Conta c : contas) {
                        System.out.println(c.getUsuario().nome() + " " + c.getNumeroConta());
                    }

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
        String nome = fazerPergunta("Qual é o seu nome?", scanner);
        String sobrenome = fazerPergunta("Qual é o seu sobrenome?", scanner);
        String cpf = fazerPergunta("Qual é o seu cpf?", scanner);
        String email = fazerPergunta("Qual é o seu email?", scanner);
        String senha = fazerPergunta("Qual é a sua senha?", scanner);
        int gerarID = UUID.randomUUID().hashCode();

        Usuario usuario = new Usuario(gerarID < 0 ? -1 * gerarID : (long) gerarID, nome, sobrenome, cpf, email, senha);

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
