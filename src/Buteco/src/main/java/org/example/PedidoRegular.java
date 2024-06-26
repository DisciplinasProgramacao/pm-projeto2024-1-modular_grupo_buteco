package org.example;

public class PedidoRegular extends Pedido {
    private static final double TAXA_SERVICO = 0.10;

    @Override
    public double calcularPreco() {
        double total = 0;
        for (Item item : itens) {
            total += item.getPreco();
        }
        return total + (total * TAXA_SERVICO);
    }
}
