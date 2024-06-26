package org.example;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static Restaurante restaurante = new Restaurante();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int op = 0;

        do {
            cabecalho();
            exibirMenu();

            try {
                op = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número de opção válido.");
                scanner.next(); 
                continue;
            }

            try {
                switch (op) {
                    case 1:
                        cadastrarCliente(); 
                        break;
                    case 2:
                        registrarRequisicao();
                        break;
                    case 3:
                        processarFilaRequisicoes();
                        break;
                    case 4:
                        encerrarRequisicao();
                        break;
                    case 5:
                        filaAtendimento();
                        break;
                    case 6:
                        filaEspera();
                        break;
                    case 7:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Erro durante a operação: " + e.getMessage());
            }
        } while (op != 7);
    }

    private static void cabecalho() {
        System.out.println("========================================");
        System.out.println("         Restaurante Buteco!");
        System.out.println("========================================");
    }

    private static void exibirMenu() {
        System.out.println("1- Cadastrar Cliente");
        System.out.println("2- Registrar Requisição por mesa");
        System.out.println("3- Alocar uma requisição");
        System.out.println("4- Encerrar uma Requisição");
        System.out.println("5- Fila de Atendimento do Restaurante");
        System.out.println("6- Fila de Espera do Restaurante");
        System.out.println("7- Sair");
    }

   private static void cadastrarCliente() throws Exception {
        System.out.print("Digite o nome do cliente: ");
        String nomeCliente = scanner.nextLine();

        Cliente novoCliente = new Cliente(nomeCliente);
        restaurante.addCliente(novoCliente);
        System.out.println("Cliente cadastrado:\n" + novoCliente);
    }

    private static void registrarRequisicao() {
        System.out.println("Informe o nome do cliente:");
        String nome = scanner.nextLine();

        try {
            Cliente cliente = restaurante.localizarClienteNome(nome);
            System.out.println("Informe a quantidade de pessoas para a mesa:");
            int quantPessoas = scanner.nextInt();
            scanner.nextLine();
            Requisicao requisicao = new Requisicao(quantPessoas, cliente);
            restaurante.registrarRequisicao(requisicao);
        } catch (InputMismatchException e) {
            System.out.println("Por favor, informe um número válido para a quantidade de pessoas.");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Erro ao registrar a requisição: " + e.getMessage());
        }
    }

    private static void processarFilaRequisicoes() throws Exception {
        Requisicao requisicao = restaurante.processarFila();
        System.out.println("Requisição processada e mesa alocada: " + requisicao);
    }

    private static void encerrarRequisicao() throws Exception {
        System.out.println("Informe o número da mesa para encerrar a requisição:");
        int numMesa = scanner.nextInt();
        scanner.nextLine();
        Mesa mesaEncerrada = restaurante.encerrarAtendimento(numMesa);
        System.out.println("Requisição na mesa " + numMesa + " encerrada com sucesso.");
    }

    private static void filaEspera() {
        System.out.println("Fila de Espera:");
        System.out.println(restaurante.filaDeEspera());
    }

    private static void filaAtendimento() {
        System.out.println("Fila de Atendimentos:");
        System.out.println(restaurante.statusMesas());
    }
}
