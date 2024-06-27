package org.example;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
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
                        adicionarItemAoPedido();
                        break;

                    case 8:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Erro durante a operação: " + e.getMessage());
            }
        } while (op != 8);
    }

    private static void cabecalho() {
        System.out.println("========================================");
        System.out.println("         Restaurante Buteco!");
        System.out.println("========================================");
    }

    private static void exibirMenu() {
        System.out.println("1- Cadastrar Cliente");
        System.out.println("2- Registrar Requisição por mesa");
        System.out.println("3- Alocar uma Mesa");
        System.out.println("4- Encerrar uma Mesa");
        System.out.println("5- Fila de Atendimento do Restaurante");
        System.out.println("6- Fila de Espera do Restaurante");
        System.out.println("7- Realizar Pedido");
        System.out.println("8- Sair");
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

            System.out.println("Informe o tipo de pedido (1 para Regular, 2 para Menu Fechado):");
            int tipoPedidoInt = scanner.nextInt();
            scanner.nextLine();

            TipoPedido tipoPedido = obterTipoPedido(tipoPedidoInt);
            restaurante.registrarRequisicao(quantPessoas, cliente, tipoPedido);
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
            scanner.nextLine(); 
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro ao registrar a requisição: " + e.getMessage());
        }
    }

    private static TipoPedido obterTipoPedido(int tipoPedidoInt) throws InputMismatchException {
        switch (tipoPedidoInt) {
            case 1:
                return TipoPedido.REGULAR;
            case 2:
                return TipoPedido.MENU_FECHADO;
            default:
                throw new InputMismatchException("Tipo de pedido inválido.");
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
        restaurante.encerrarAtendimento(numMesa);
        System.out.println("Requisição na mesa " + numMesa + " encerrada com sucesso.");
        calcularPrecoTotalPedido();
    }

    private static void filaEspera() {
        System.out.println("Fila de Espera:");
        System.out.println(restaurante.filaDeEspera());
    }

    private static void filaAtendimento() {
        System.out.println("Fila de Atendimentos:");
        System.out.println(restaurante.statusMesas());
    }
    private static void calcularPrecoTotalPedido() {
        System.out.println("Informe o nome do cliente:");
        String nome = scanner.nextLine();
        Cliente cliente = restaurante.localizarClienteNome(nome);
        if (cliente == null) {
            System.out.println("Cliente não encontrado!");
            return;
        }

        double precoTotal = restaurante.calcularPrecoTotalPedido(cliente.hashNome());
        if (precoTotal >= 0) {
            System.out.println("Preço total do pedido do cliente " + nome + ": R$ " + String.format("%.2f", precoTotal));
        } else {
            System.out.println("Requisição não encontrada para o cliente " + nome);
        }
    }

    private static void adicionarItemAoPedido() {
        System.out.println("Informe o nome do cliente:");
        String nome = scanner.nextLine();
        Cliente cliente = restaurante.localizarClienteNome(nome);
        if (cliente == null) {
            System.out.println("Cliente não encontrado!");
            return;
        }
    
        boolean continuar = true;
    
        while (continuar) {
            System.out.println("Selecione um item para adicionar ao pedido:");
            for (Item item : Item.values()) {
                System.out.println(item.ordinal() + " - " + item);
            }
            System.out.println(Item.values().length + " - Voltar ao menu principal");
    
            try {
                int itemIndex = scanner.nextInt();
                scanner.nextLine();
    
                if (itemIndex == Item.values().length) {
                    continuar = false;
                } else if (itemIndex < 0 || itemIndex >= Item.values().length) {
                    System.out.println("Índice de item inválido.");
                } else {
                    Item item = Item.values()[itemIndex];
                    restaurante.adicionarItemAoPedido(cliente.hashNome(), item);
                    System.out.println("Item " + item.getNome() + " adicionado ao pedido do cliente " + nome);
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, selecione um índice de item válido.");
                scanner.nextLine();
            }
        }
    }
}
