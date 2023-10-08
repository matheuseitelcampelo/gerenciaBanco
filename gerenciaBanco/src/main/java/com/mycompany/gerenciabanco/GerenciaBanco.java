/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.gerenciabanco;

/**
 *
 * @author mathe
 */
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class GerenciaBanco {

    private ContaBancoFactory fabrica = new ContaBancoFactory();
    private LeitorDados leitor = new LeitorDados();

    public static void main(String[] args) {
        GerenciaBanco gerencia = new GerenciaBanco();
        gerencia.iniciarPrograma();
    }

    @SuppressWarnings("empty-statement")
    public void iniciarPrograma() {
        ContaBanco contaBanco;
        mensagemBoasVindas();
        float valor = 0;
        boolean sair = false;
        do {
            menu();
            int opcaoPrincipal = leitor.lerInteiro();
            switch (opcaoPrincipal) {
                case 1:
                    adicionarNovaConta();
                    break;
                case 2:
                    boolean sairMenu2 = false;
                    do {
                        menu2();
                        int opcaoMenu2 = leitor.lerInteiro();
                        switch (opcaoMenu2) {
                            case 1:
                                contaBanco = selecionarContaCpf();
                                if (contaBanco == null) {
                                    sairMenu2 = true;
                                    break;
                                }
                                boolean sairMenu21 = false;
                                do {
                                    menuContaSelecionada();
                                    int opcaoMenu21 = leitor.lerInteiro();
                                    switch (opcaoMenu21) {
                                        case 1:
                                            contaBanco.consultaSaldo();
                                            break;
                                        case 2:
                                            menuSaque();
                                            valor = leitor.lerFloat();
                                            contaBanco.retirar(valor);
                                            break;
                                        case 3:
                                            menuDeposito();
                                            valor = leitor.lerFloat();
                                            contaBanco.depositar(valor);
                                            break;
                                        case 0:
                                            sairMenu21 = true;
                                            break;
                                        default:
                                            mensagemDefault();
                                            break;
                                    }
                                } while (!sairMenu21);
                            case 2:
                                menu31();
                                int indiceDaLista = leitor.lerInteiro();
                                boolean contaExiste = verificaContaExistente(indiceDaLista);
                                if (contaExiste) {
                                    boolean sairMenu31 = false;
                                    do {
                                        int indiceContaBanco = indiceDaLista - 1;
                                        contaBanco = fabrica.selecionarContaPorIndice(indiceContaBanco);
                                        System.out.println(contaBanco);
                                        menuContaSelecionada();
                                        int opcaoMenu31 = leitor.lerInteiro();
                                        switch (opcaoMenu31) {
                                            case 1:
                                                contaBanco.consultaSaldo();
                                                break;
                                            case 2:
                                                menuSaque();
                                                valor = leitor.lerFloat();
                                                contaBanco.retirar(valor);
                                                break;
                                            case 3:
                                                menuDeposito();
                                                valor = leitor.lerFloat();
                                                contaBanco.depositar(valor);
                                                break;
                                            case 0:
                                                sairMenu31 = true;
                                                break;
                                            default:
                                                mensagemDefault();
                                                break;
                                        }
                                    } while (!sairMenu31);
                                    break;
                                } else {
                                    sairMenu2 = true;
                                }
                                
                            case 0:
                                sairMenu2 = true;
                        }
                    } while (!sairMenu2);
                    break;
                case 0:
                    System.out.println("Fim do programa.");
                    sair = true;
                    break;
                default:
                    mensagemDefault();
                    break;
            }
        } while (!sair);
    }

    public void mensagemBoasVindas() {
        System.out.println("\nBem-vindo ao programa de gerenciamento de contas"
                + "bancárias!");
    }

    public void menu() {
        System.out.println("\nSelecione a opção:");
        System.out.println("1. Adicionar uma nova conta bancária.");
        System.out.println("2. Acessar uma conta bancaria.");
        System.out.println("0. sair.\n");
    }

    public void menu2() {
        System.out.println("\nInforme a forma de acesso:");
        System.out.println("1. Acessar via cpf valido.");
        System.out.println("2. Acessar via numero na lista de contas.");
        System.out.println("0. Voltar ao menu anterior.\n");
    }

    public void menuContaSelecionada() {
        System.out.println("\nConta seleicionada com sucesso.");
        System.out.println("1. Consultar saldo.");
        System.out.println("2. Sacar dinheiro.");
        System.out.println("3. Depositar dinheiro.");
        System.out.println("0. Voltar ao menu anterior.\n");

    }

    public void menu31() {
        System.out.println(fabrica);
        System.out.println("\nDigite numero da conta que deseja acessar:\n");
    }

    public void menuDeposito() {
        System.out.println("\nDigite o valor do deposito:\n");
    }

    public void menuSaque() {
        System.out.println("\nDigite o valor do saque:\n");
    }

    public void adicionarNovaConta() {
        String nome;
        String sobrenome;
        int cpf;
        float saldoInicial;
        System.out.println("\nDigite o primeiro nome:\n");
        nome = leitor.lerString();
        System.out.println("\nDigite o sobrenome:\n");
        sobrenome = leitor.lerString();
        System.out.println("\nDigite o cpf:\n");
        cpf = leitor.lerInteiro();
        System.out.println("\nDigite o saldo inicial:\n");
        saldoInicial = leitor.lerFloat();
        fabrica.adicionarConta(nome, sobrenome, cpf, saldoInicial);
    }

    public boolean verificaContaExistente(int indiceDaLista) {
        int indiceDaArray = indiceDaLista - 1;
        if (indiceDaArray < 0  || indiceDaArray > fabrica.getTamanhoDaListaContas()) {
            System.out.println("Conta bancaria não encontrada.");
            return false;
        } else if (fabrica.selecionarContaPorIndice(indiceDaArray) == null) {
            System.out.println("Conta bancaria não existe.");
            return false;
        } else {
            return true;
        }
    }

    public ContaBanco contaIndice(int indiceDaLista) {
        int indiceDoArray = indiceDaLista + 1;
        return fabrica.selecionarContaPorIndice(indiceDoArray);
    }

    public ContaBanco selecionarContaCpf() {
        int cpf;
        System.out.println("\nDigite o cpf da conta, apenas numeros.");
        cpf = leitor.lerInteiro();
        ContaBanco contaSelecionada = fabrica.selecionarConta(cpf);
        if (contaSelecionada == null) {
            System.out.println("\nConta não encontrada.");
        }
        return contaSelecionada;
    }

    public void mensagemDefault() {
        System.out.println("\nValor informado não aceito.");
    }

    class ContaBanco {

        private final String NOME;

        private final String SOBRENOME;
        private final int CPF;
        private float saldo;

        ContaBanco(String nome, String sobrenome, int cpf, float saldo) {
            this.NOME = nome;
            this.SOBRENOME = sobrenome;
            this.CPF = cpf;
            this.saldo = saldo;

            System.out.printf("%nConta bancaria criada com sucesso!%n"
                    + "Nome: %S %S%n"
                    + "Cpf: %d%n",
                    this.NOME, this.SOBRENOME,
                    this.CPF);
        }

        public void consultaSaldo() {
            System.out.printf("Saldo bancario: %.2f%n%n", this.saldo);
        }

        public boolean retirar(float valor) {
            if (valor <= this.saldo) {
                this.saldo -= valor;
                System.out.printf("Retirada realizada com sucesso, valor: %.2f%n"
                        + "Saldo bancario: %.2f%n%n", valor, this.saldo);
                return true;
            } else {
                System.out.println("Saldo insuficiente.");
                return false;
            }
        }

        public void depositar(float valor) {
            this.saldo += valor;
            System.out.printf("Deposito realizado com sucesso, valor: %.2f"
                    + "%nSaldo bancario: %.2f%n%n", valor, this.saldo);
        }

        public int getCPF() {
            return CPF;
        }

        public String getNOME() {
            return NOME;
        }

        public String getSOBRENOME() {
            return SOBRENOME;
        }

        @Override
        public String toString() {
            return String.format("%nNome: %S %S%nCpf: %d%nSaldo: %.2f%n",
                    this.NOME, this.SOBRENOME,
                    this.CPF, this.saldo);
        }
    }

    class ContaBancoFactory {

        private final List<ContaBanco> listaDeContas;

        public ContaBancoFactory() {
            this.listaDeContas = new ArrayList<>();
        }

        public void adicionarConta(String nome, String sobrenome,
                int cpf, float saldo) {

            ContaBanco novaConta = new ContaBanco(nome, sobrenome, cpf, saldo);

            listaDeContas.add(novaConta);
        }

        public ContaBanco selecionarConta(int cpf) {
            for (ContaBanco conta : listaDeContas) {
                if (conta.getCPF() == cpf) {
                    return conta;
                }
            }
            return null;
        }

        public ContaBanco selecionarContaPorIndice(int indice) {
            if (indice >= 0 && indice < listaDeContas.size()) {
                return listaDeContas.get(indice);
            }
            return null;
        }

        public void removerConta(ContaBanco conta) {
            listaDeContas.remove(conta);
            System.out.printf("Conta de %S %S removida com sucesso%n%n",
                    conta.getNOME(), conta.getSOBRENOME());
        }

        public int getTamanhoDaListaContas() {
            return this.listaDeContas.size();
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("\n");
            int numeroConta = 1;
            for (ContaBanco conta : listaDeContas) {
                String nome = conta.getNOME();
                String sobrenome = conta.getSOBRENOME();
                int cpf = conta.getCPF();
                sb.append(numeroConta).append(". ").append(nome)
                        .append(" ").append(sobrenome).append(", cpf: ")
                        .append(cpf).append("\n");
                numeroConta++;
            }
            return sb.toString();
        }
    }

    class LeitorDados {

        private final Scanner scanner;

        public LeitorDados() {
            this.scanner = new Scanner(System.in);
        }

        public String lerString() {
            return scanner.nextLine();
        }

        public int lerInteiro() {
            int valor = 0;
            boolean entradaValida = false;

            while (!entradaValida) {
                try {
                    valor = Integer.parseInt(scanner.nextLine());
                    entradaValida = true;
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida. Digite numeros inteiros.");
                }
            }
            return valor;
        }

        public float lerFloat() {
            float valor = 0;
            boolean entradaValida = false;

            while (!entradaValida) {
                try {
                    valor = Float.parseFloat(scanner.nextLine());
                    entradaValida = true;
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida. Digite um numero"
                            + "de ponto flutueante valido:");
                }
            }
            return valor;
        }
    }
}
