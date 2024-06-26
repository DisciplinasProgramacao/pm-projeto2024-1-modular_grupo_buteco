package org.example;

public enum Item {
    MOQUECA_DE_PALMITO("Moqueca de Palmito", 32.00, "comida"),
    FALAFEL_ASSADO("Falafel Assado", 20.00, "comida"),
    SALADA_PRIMAVERA("Salada Primavera com Macarrão Konjac", 25.00, "comida"),
    ESCONDIDINHO_DE_INHAME("Escondidinho de Inhame", 18.00, "comida"),
    STROGONOFF_DE_COGUMELOS("Strogonoff de Cogumelos", 35.00, "comida"),
    CACAROLA_DE_LEGUMES("Caçarola de Legumes", 22.00, "comida"),
    AGUA("Água", 3.00, "bebida"),
    COPO_DE_SUCO("Copo de Suco", 7.00, "bebida"),
    REFRIGERANTE_ORGANICO("Refrigerante Orgânico", 7.00, "bebida"),
    CERVEJA_VEGANA("Cerveja Vegana", 9.00, "bebida"),
    TACA_DE_VINHO_VEGANO("Taça de Vinho Vegano", 18.00, "bebida");

    private final String nome;
    private final double preco;
    private final String tipo;

    Item(String nome, double preco, String tipo) {
        this.nome = nome;
        this.preco = preco;
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public String getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return String.format("%s - R$ %.2f", nome, preco);
    }
}