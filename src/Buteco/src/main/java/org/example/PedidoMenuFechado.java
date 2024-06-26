package org.example;

public class PedidoMenuFechado extends Pedido {
    private static final double PRECO_MENU_FECHADO = 32.0;
    private static final double TAXA_SERVICO = 0.10;
    private int numeroClientes;

    public PedidoMenuFechado(int numeroClientes) {
        super();
        this.numeroClientes = numeroClientes;
    }

    @Override
    public double calcularPreco() {
        double total = PRECO_MENU_FECHADO * numeroClientes;
        return total + (total * TAXA_SERVICO);
    }

    public int getNumeroClientes() {
        return numeroClientes;
    }
}
