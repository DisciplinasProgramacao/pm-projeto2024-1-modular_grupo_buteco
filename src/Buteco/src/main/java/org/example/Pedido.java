package org.example;

import java.util.ArrayList;
import java.util.List;

public abstract class Pedido {
    protected List<Item> itens;

    public Pedido() {
        this.itens = new ArrayList<>();
    }

    public void adicionarItem(Item item) {
        itens.add(item);
    }

    public abstract double calcularPreco();

    public List<Item> getItens() {
        return itens;
    }
}