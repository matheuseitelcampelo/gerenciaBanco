/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.gerenciabanco;

/**
 * Este programa é uma demonstração simples de um sistema de gerenciamento de
 * contas bancárias. Ele inclui as seguintes funcionalidades: - Adicionar uma
 * nova conta bancária com nome, sobrenome, CPF e saldo inicial. - Acessar uma
 * conta bancária existente por meio do CPF ou por número na lista de contas. -
 * Consultar saldo de uma conta. - Realizar saques e depósitos em uma conta.
 *
 * @author Matheus Eitel Campelo
 */
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class GerenciaBanco {

    private ContaBancoFactory fabrica = new ContaBancoFactory();
    private LeitorDados leitor = new LeitorDados();

    /**
     * O método principal que inicia o programa de gerenciamento de contas
     * bancárias.
     *
     * @param args Os argumentos da linha de comando (não utilizados).
     */
    public static void main(String[] args) {
        GerenciaBanco gerencia = new GerenciaBanco();
        gerencia.iniciarPrograma();
    }

    /**
     * Inicia o programa, exibindo uma mensagem de boas-vindas e um menu
     * interativo para o usuário.
     */
    @SuppressWarnings("empty-statement")
    public void iniciarPrograma() {
        ContaBanco contaBanco;
        mensagemBoasVindas();
        float valor;
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

    /**
     * Exibe uma mensagem de boas-vindas ao usuário.
     */
    public void mensagemBoasVindas() {
        System.out.println("\nBem-vindo ao programa de gerenciamento de contas"
                + "bancárias!");
    }

    /**
     * Exibe o menu principal com opções para o usuário.
     */
    public void menu() {
        System.out.println("\nSelecione a opção:");
        System.out.println("1. Adicionar uma nova conta bancária.");
        System.out.println("2. Acessar uma conta bancaria.");
        System.out.println("0. sair.\n");
    }

    /**
     * Exibe o menu de seleção de forma de acesso.
     */
    public void menu2() {
        System.out.println("\nInforme a forma de acesso:");
        System.out.println("1. Acessar via cpf valido.");
        System.out.println("2. Acessar via numero na lista de contas.");
        System.out.println("0. Voltar ao menu anterior.\n");
    }

    /**
     * Exibe o menu de operações disponíveis para a conta selecionada.
     */
    public void menuContaSelecionada() {
        System.out.println("\nConta seleicionada com sucesso.");
        System.out.println("1. Consultar saldo.");
        System.out.println("2. Sacar dinheiro.");
        System.out.println("3. Depositar dinheiro.");
        System.out.println("0. Voltar ao menu anterior.\n");

    }

    /**
     * Exibe o menu para selecionar uma conta na lista de contas.
     */
    public void menu31() {
        System.out.println(fabrica);
        System.out.println("\nDigite numero da conta que deseja acessar:\n");
    }

    /**
     * Exibe o menu para realizar um depósito.
     */
    public void menuDeposito() {
        System.out.println("\nDigite o valor do deposito:\n");
    }

    /**
     * Exibe o menu para realizar um saque.
     */
    public void menuSaque() {
        System.out.println("\nDigite o valor do saque:\n");
    }

    /**
     * Adiciona uma nova conta bancária com base nas informações fornecidas pelo
     * usuário.
     */
    public void adicionarNovaConta() {
        String nome;
        String sobrenome;
        String cpf;
        float saldoInicial;
        System.out.println("\nDigite o primeiro nome:\n");
        nome = leitor.lerString();
        System.out.println("\nDigite o sobrenome:\n");
        sobrenome = leitor.lerString();
        boolean cpfValido;
        do {
            System.out.println("\nDigite apenas os numeros do cpf:\n");
            cpf = leitor.lerString();
            cpfValido = fabrica.isCPF(cpf);
            if (!cpfValido) {
                System.out.println("cpf Invalido");
            }
        } while (!cpfValido);
        System.out.println("\nDigite o saldo inicial:\n");
        saldoInicial = leitor.lerFloat();
        fabrica.adicionarConta(nome, sobrenome, cpf, saldoInicial);
    }

    /**
     * Verifica se uma conta bancária com o índice especificado na lista de
     * contas existe.
     *
     * @param indiceDaLista O índice da conta na lista.
     * @return true se a conta existe, false caso contrário.
     */
    public boolean verificaContaExistente(int indiceDaLista) {
        int indiceDaArray = indiceDaLista - 1;
        if (indiceDaArray < 0 || indiceDaArray > fabrica.getTamanhoDaListaContas()) {
            System.out.println("Conta bancaria não encontrada.");
            return false;
        } else if (fabrica.selecionarContaPorIndice(indiceDaArray) == null) {
            System.out.println("Conta bancaria não existe.");
            return false;
        } else {
            return true;
        }
    }

    /**
     * Retorna a conta bancária com base no índice especificado na lista de
     * contas.
     *
     * @param indiceDaLista O índice da conta na lista.
     * @return A conta bancária correspondente ou null se não existir.
     */
    public ContaBanco contaIndice(int indiceDaLista) {
        int indiceDoArray = indiceDaLista + 1;
        return fabrica.selecionarContaPorIndice(indiceDoArray);
    }

    /**
     * Seleciona uma conta bancária com base no CPF fornecido pelo usuário.
     *
     * @return A conta bancária correspondente ou null se não for encontrada.
     */
    public ContaBanco selecionarContaCpf() {
        String cpf;
        System.out.println("\nDigite o cpf da conta, apenas numeros.");
        cpf = leitor.lerString();
        ContaBanco contaSelecionada = fabrica.selecionarConta(cpf);
        if (contaSelecionada == null) {
            System.out.println("\nConta não encontrada.");
        }
        return contaSelecionada;
    }

    /**
     * Exibe uma mensagem de erro padrão para entradas inválidas do usuário.
     */
    public void mensagemDefault() {
        System.out.println("\nValor informado não aceito.");
    }

    /**
     * Esta classe representa uma conta bancária com nome, sobrenome, CPF e
     * saldo. Ela permite consultar saldo, fazer saques, depósitos e exibir
     * informações da conta.
     */
    class ContaBanco {

        private final String NOME;

        private final String SOBRENOME;
        private final String CPF;
        private float saldo;

        /**
         * Cria uma nova conta bancária com as informações fornecidas.
         *
         * @param nome O primeiro nome do titular da conta.
         * @param sobrenome O sobrenome do titular da conta.
         * @param cpf O CPF do titular da conta.
         * @param saldo O saldo inicial da conta.
         */
        ContaBanco(String nome, String sobrenome, String cpf, float saldo) {
            this.NOME = nome;
            this.SOBRENOME = sobrenome;
            this.CPF = cpf;
            this.saldo = saldo;

            System.out.printf("%nConta bancaria criada com sucesso!%n"
                    + "Nome: %S %S%n"
                    + "Cpf: %s%n",
                    this.NOME, this.SOBRENOME,
                    this.CPF);
        }

        /**
         * Consulta o saldo da conta e o exibe na saída padrão.
         */
        public void consultaSaldo() {
            System.out.printf("Saldo bancario: %.2f%n%n", this.saldo);
        }

        /**
         * Realiza um saque da conta, se o saldo for suficiente.
         *
         * @param valor O valor a ser sacado.
         * @return true se o saque for bem-sucedido, false caso contrário.
         */
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

        /**
         * Realiza um depósito na conta.
         *
         * @param valor O valor a ser depositado.
         */
        public void depositar(float valor) {
            this.saldo += valor;
            System.out.printf("Deposito realizado com sucesso, valor: %.2f"
                    + "%nSaldo bancario: %.2f%n%n", valor, this.saldo);
        }

        /**
         * Retorna o CPF da conta.
         *
         * @return O CPF da conta.
         */
        public String getCPF() {
            return CPF;
        }

        /**
         * Retorna o primeiro nome do titular da conta.
         *
         * @return O primeiro nome do titular da conta.
         */
        public String getNOME() {
            return NOME;
        }

        /**
         * Retorna o sobrenome do titular da conta.
         *
         * @return O sobrenome do titular da conta.
         */
        public String getSOBRENOME() {
            return SOBRENOME;
        }

        /**
         * Gera uma representação de string da conta bancária.
         *
         * @return Uma representação de string da conta bancária.
         */
        @Override
        public String toString() {
            return String.format("%nNome: %S %S%nCpf: %s%nSaldo: %.2f%n",
                    this.NOME, this.SOBRENOME,
                    this.CPF, this.saldo);
        }
    }

    /**
     * Esta classe é uma fábrica de contas bancárias que permite adicionar,
     * selecionar e remover contas.
     */
    class ContaBancoFactory {

        private final List<ContaBanco> listaDeContas;

        /**
         * Cria uma nova instância da fábrica de contas bancárias.
         */
        public ContaBancoFactory() {
            this.listaDeContas = new ArrayList<>();
        }

        /**
         * Adiciona uma nova conta bancária à lista de contas.
         *
         * @param nome O primeiro nome do titular da conta.
         * @param sobrenome O sobrenome do titular da conta.
         * @param cpf O CPF do titular da conta.
         * @param saldo O saldo inicial da conta.
         */
        public void adicionarConta(String nome, String sobrenome,
                String cpf, float saldo) {

            ContaBanco novaConta = new ContaBanco(nome, sobrenome, cpf, saldo);

            listaDeContas.add(novaConta);
        }

        public boolean isCPF(String CPF) {
            // considera-se erro CPF's formados por uma sequencia de numeros iguais
            if (CPF.equals("00000000000")
                    || CPF.equals("11111111111")
                    || CPF.equals("22222222222") || CPF.equals("33333333333")
                    || CPF.equals("44444444444") || CPF.equals("55555555555")
                    || CPF.equals("66666666666") || CPF.equals("77777777777")
                    || CPF.equals("88888888888") || CPF.equals("99999999999")
                    || (CPF.length() != 11)) {
                return (false);
            }

            char dig10, dig11;
            int sm, i, r, num, peso;

            // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
            try {
                // Calculo do 1o. Digito Verificador
                sm = 0;
                peso = 10;
                for (i = 0; i < 9; i++) {
                    // converte o i-esimo caractere do CPF em um numero:
                    // por exemplo, transforma o caractere '0' no inteiro 0
                    // (48 eh a posicao de '0' na tabela ASCII)
                    num = (int) (CPF.charAt(i) - 48);
                    sm = sm + (num * peso);
                    peso = peso - 1;
                }

                r = 11 - (sm % 11);
                if ((r == 10) || (r == 11)) {
                    dig10 = '0';
                } else {
                    dig10 = (char) (r + 48); // converte no respectivo caractere numerico
                }
                // Calculo do 2o. Digito Verificador
                sm = 0;
                peso = 11;
                for (i = 0; i < 10; i++) {
                    num = (int) (CPF.charAt(i) - 48);
                    sm = sm + (num * peso);
                    peso = peso - 1;
                }

                r = 11 - (sm % 11);
                if ((r == 10) || (r == 11)) {
                    dig11 = '0';
                } else {
                    dig11 = (char) (r + 48);
                }

                // Verifica se os digitos calculados conferem com os digitos informados.
                if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10))) {
                    return (true);
                } else {
                    return (false);
                }
            } catch (InputMismatchException erro) {
                return (false);
            }
        }

        /**
         * Seleciona uma conta bancária com base no CPF fornecido.
         *
         * @param cpf O CPF da conta a ser selecionada.
         * @return A conta bancária correspondente ou null se não for
         * encontrada.
         */
        public ContaBanco selecionarConta(String cpf) {
            for (ContaBanco conta : listaDeContas) {
                if (conta.getCPF().equals(cpf)) {
                    return conta;
                }
            }
            return null;
        }

        /**
         * Seleciona uma conta bancária com base no índice especificado na lista
         * de contas.
         *
         * @param indice O índice da conta na lista.
         * @return A conta bancária correspondente ou null se não existir.
         */
        public ContaBanco selecionarContaPorIndice(int indice) {
            if (indice >= 0 && indice < listaDeContas.size()) {
                return listaDeContas.get(indice);
            }
            return null;
        }

        /**
         * Remove uma conta bancária da lista de contas.
         *
         * @param conta A conta bancária a ser removida.
         */
        public void removerConta(ContaBanco conta) {
            listaDeContas.remove(conta);
            System.out.printf("Conta de %S %S removida com sucesso%n%n",
                    conta.getNOME(), conta.getSOBRENOME());
        }

        /**
         * Retorna o tamanho da lista de contas.
         *
         * @return O tamanho da lista de contas.
         */
        public int getTamanhoDaListaContas() {
            return this.listaDeContas.size();
        }

        /**
         * Gera uma representação de string da lista de contas bancárias.
         *
         * @return Uma representação de string da lista de contas bancárias.
         */
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("\n");
            int numeroConta = 1;
            for (ContaBanco conta : listaDeContas) {
                String nome = conta.getNOME();
                String sobrenome = conta.getSOBRENOME();
                String cpf = conta.getCPF();
                sb.append(numeroConta).append(". ").append(nome)
                        .append(" ").append(sobrenome).append(", cpf: ")
                        .append(cpf).append("\n");
                numeroConta++;
            }
            return sb.toString();
        }
    }

    /**
     * Esta classe é responsável por ler dados da entrada padrão.
     */
    class LeitorDados {

        private final Scanner scanner;

        /**
         * Cria uma nova instância do leitor de dados.
         */
        public LeitorDados() {
            this.scanner = new Scanner(System.in);
        }

        /**
         * Lê uma linha de texto da entrada padrão.
         *
         * @return A linha de texto lida.
         */
        public String lerString() {
            return scanner.nextLine();
        }

        /**
         * Lê um número inteiro da entrada padrão.
         *
         * @return O número inteiro lido.
         */
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

        /**
         * Lê um número de ponto flutuante da entrada padrão.
         *
         * @return O número de ponto flutuante lido.
         */
        public float lerFloat() {
            float valor = 0;
            boolean entradaValida = false;

            while (!entradaValida) {
                try {
                    valor = Float.parseFloat(scanner.nextLine());
                    entradaValida = true;
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida. Digite um numero"
                            + " de ponto flutuante (por exemplo, 123.45).");
                }
            }
            return valor;
        }
    }
}
