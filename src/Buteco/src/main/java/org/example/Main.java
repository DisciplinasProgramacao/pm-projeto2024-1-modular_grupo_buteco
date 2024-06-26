package org.example;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static Restaurante restaurante = new Restaurante(12);
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int op = 0;

        do {
            cabecalho();
            System.out.println("1- Cadastrar Cliente");
            System.out.println("2- Registrar Requisição por mesa");
            System.out.println("3- Alocar uma requisição");
            System.out.println("4- Encerrar uma Requisição");
            System.out.println("5- Fila de Atendimento do Restaurante");
            System.out.println("6- Fila de Espera do Restaurante");
            System.out.println("7- Adicionar Item ao Pedido");
            System.out.println("8- Calcular Preço Total do Pedido");
            System.out.println("9- Sair");

            try {
                op = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número de opção válido.");
                scanner.nextLine();
                continue;
            }

            switch (op) {
                case 1:
                    cadastrarCliente();
                    pausa();
                    break;
                case 2:
                    registrarRequisicao();
                    pausa();
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
                    adicionarItemAoPedido();
                    pausa();
                    break;
                case 8:
                    calcularPrecoTotalPedido();
                    pausa();
                    break;
                case 9:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        } while (op != 9);
    }

    static void pausa() {
        System.out.println("Tecle Enter para continuar.");
        scanner.nextLine();
    }

    static void cabecalho() {
        System.out.println("========================================");
        System.out.println("         Restaurante Buteco!");
        System.out.println("========================================");
    }

    static Cliente cadastrarCliente() {
        String nomeCliente;
        Cliente novoCliente;
        System.out.print("Digite o nome do cliente: ");
        nomeCliente = scanner.nextLine();
        try {
            novoCliente = new Cliente(nomeCliente);
            restaurante.addCliente(novoCliente);
        } catch (IllegalArgumentException ie) {
            System.out.println(ie.getMessage());
            return null;
        }

        System.out.println("Cliente cadastrado:\n" + novoCliente);
        return novoCliente;
    }

    private static void registrarRequisicao() {
        System.out.println("Informe o nome do cliente:");
        String nome = scanner.nextLine();
        Cliente cliente = restaurante.localizarCliente1(nome);
        if (cliente == null) {
            System.out.println("Cliente não encontrado!");
            return;
        }
        System.out.println("Informe a quantidade de pessoas para a requisição:");
        try {
            int quantPessoas = scanner.nextInt();
            Requisicao requisicao = new Requisicao(quantPessoas, cliente);
            restaurante.registrarRequisicao(requisicao);
            System.out.println("Requisição registrada com sucesso!");
        } catch (InputMismatchException e) {
            System.out.println("Informe um número inteiro válido para a quantidade de pessoas.");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void processarFilaRequisicoes() {
        Requisicao requisicao = restaurante.processarFila();
        if (requisicao != null) {
            System.out.println("Requisição processada e mesa alocada: " + requisicao);
        } else {
            System.out.println("Nenhuma requisição na fila ou nenhuma mesa disponível.");
        }
    }

    private static void encerrarRequisicao() {
        System.out.println("Informe o número da mesa para encerrar a requisição:");
        int numMesa = scanner.nextInt();
        scanner.nextLine();
        Mesa mesaEncerrada = restaurante.encerrarAtendimento(numMesa);
        if (mesaEncerrada != null) {
            System.out.println("Requisição na mesa " + numMesa + " encerrada com sucesso.");
        } else {
            System.out.println("Mesa não encontrada ou requisição já encerrada.");
        }
    }

    public static void filaEspera() {
        System.out.println("Fila de Espera:");
        System.out.println(restaurante.filaDeEspera());
    }

    public static void filaAtendimento() {
        System.out.println("Fila de Atendimentos:");
        System.out.println(restaurante.statusMesas());
    }

    private static void adicionarItemAoPedido() {
        System.out.println("Informe o nome do cliente:");
        String nome = scanner.nextLine();
        Cliente cliente = restaurante.localizarCliente1(nome);
        if (cliente == null) {
            System.out.println("Cliente não encontrado!");
            return;
        }

        System.out.println("Selecione um item para adicionar ao pedido:");
        for (Item item : Item.values()) {
            System.out.println(item.ordinal() + " - " + item);
        }

        try {
            int itemIndex = scanner.nextInt();
            scanner.nextLine();
            if (itemIndex < 0 || itemIndex >= Item.values().length) {
                System.out.println("Índice de item inválido.");
                return;
            }

            Item item = Item.values()[itemIndex];
            restaurante.adicionarItemAoPedido(cliente.hashCode(), item);
            System.out.println("Item " + item.getNome() + " adicionado ao pedido do cliente " + nome);
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, selecione um índice de item válido.");
            scanner.nextLine();
        }
    }

    private static void calcularPrecoTotalPedido() {
        System.out.println("Informe o nome do cliente:");
        String nome = scanner.nextLine();
        Cliente cliente = restaurante.localizarCliente1(nome);
        if (cliente == null) {
            System.out.println("Cliente não encontrado!");
            return;
        }

        double precoTotal = restaurante.calcularPrecoTotalPedido(cliente.hashCode());
        if (precoTotal >= 0) {
            System.out.println("Preço total do pedido do cliente " + nome + ": R$ " + String.format("%.2f", precoTotal));
        } else {
            System.out.println("Requisição não encontrada para o cliente " + nome);
        }
    }
}