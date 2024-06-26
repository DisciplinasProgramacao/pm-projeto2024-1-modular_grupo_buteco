package org.example;

public class Item {
    private int codigo;
    private String nome;
    private String descricao;
    private double preco;
    private TipoItem tipo;

    public enum TipoItem {
        COMIDA, BEBIDA
    }

    /**
     * Construtor para criar um item com código, nome, descrição, preço e tipo.
     * @param codigo Código do produto.
     * @param nome Nome do produto.
     * @param descricao Descrição do produto.
     * @param preco Preço do produto.
     * @param tipo Tipo do produto (comida ou bebida).
     */
    public Item(int codigo, String nome, String descricao, double preco, TipoItem tipo) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.tipo = tipo;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getPreco() {
        return preco;
    }

    public TipoItem getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return "Item{" +
                "codigo=" + codigo +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", preco=" + preco +
                ", tipo=" + tipo +
                '}';
    }
}
