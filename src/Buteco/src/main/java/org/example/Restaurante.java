
package org.example;


import java.util.*;



/**
 * Classe para gerenciamento de um restaurante, incluindo mesas, clientes e requisições.
 */
public class Restaurante {
    private static final int MAX_CLIENTES = 100;
    private static final int MAX_FILA = 50;
    private static final int QNT_MESAS = 10;

    private Map<Integer, Mesa> mesas;
    private Map<Integer, Cliente> clientes;
    private List<Requisicao> atendidas;
    private List<Requisicao> emEspera;

    private int quantClientes;
    private int requisicoesAtendidas;
    private int requisicoesEmEspera;

    /**
     * Construtor que inicializa o restaurante com mesas e estruturas para clientes e requisições.
     */
    public Restaurante() {
        this.mesas = new HashMap<>();
        this.clientes = new HashMap<>();
        this.atendidas = new ArrayList<>();
        this.emEspera = new ArrayList<>();
        this.quantClientes = 0;
        this.requisicoesAtendidas = 0;
        this.requisicoesEmEspera = 0;
        criarMesas();
    }

    /**
     * Cria mesas com diferentes capacidades.
     */
    private void criarMesas() {
        int index = 1; // Index começa de 1 para ID de mesa
        int[] capacidades = {4, 6, 8};
        int[] quantidadePorCapacidade = {4, 4, 2};

        for (int i = 0; i < capacidades.length; i++) {
            for (int j = 0; j < quantidadePorCapacidade[i]; j++) {
                mesas.put(index++, new Mesa(capacidades[i]));
            }
        }
    }

    /**
     * Adiciona um novo cliente ao restaurante.
     *
     * @param novo Cliente a ser adicionado.
     * @throws IllegalStateException se o número máximo de clientes for excedido.
     */
    public void addCliente(Cliente novo) {
        if (quantClientes >= MAX_CLIENTES) {
            throw new IllegalStateException("Capacidade máxima de clientes atingida.");
        }
        clientes.put(novo.hashCode(), novo);
        quantClientes++;
    }

    /**
     * Localiza um cliente pelo ID.
     *
     * @param id ID do cliente.
     * @return Cliente correspondente ao ID.
     * @throws NoSuchElementException se o cliente não for encontrado.
     */
    public Cliente localizarCliente(int id) {
        Cliente cliente = clientes.get(id);
        if (cliente == null) {
            throw new NoSuchElementException("Cliente não encontrado com ID: " + id);
        }
        return cliente;
    }

    /**
     * Localiza um cliente pelo nome.
     *
     * @param nome Nome do cliente.
     * @return Cliente encontrado.
     * @throws NoSuchElementException se nenhum cliente com esse nome for encontrado.
     */
    public Cliente localizarClienteNome(String nome) {
        return clientes.values().stream()
            .filter(cliente -> cliente.hashNome().equals(nome))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException("Cliente não encontrado pelo nome: " + nome));
    }

    /**
     * Localiza uma mesa disponível que possa acomodar um determinado número de pessoas.
     *
     * @param quantPessoas Número de pessoas.
     * @return Mesa disponível.
     * @throws NoSuchElementException se nenhuma mesa disponível for encontrada.
     */
    public Mesa localizarMesaDisponivel(int quantPessoas) {
        return mesas.values().stream()
            .filter(mesa -> mesa.estahLiberada(quantPessoas))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException("Nenhuma mesa disponível para a quantidade de pessoas: " + quantPessoas));
    }

    /**
     * Encerra o atendimento em uma mesa específica.
     *
     * @param numeroMesa Número da mesa cujo atendimento será encerrado.
     * @return Mesa que foi liberada.
     * @throws NoSuchElementException se a mesa não for encontrada ou já estiver encerrada.
     */
    public Mesa encerrarAtendimento(int numeroMesa) {
        Requisicao requisicao = atendidas.stream()
            .filter(r -> r.getMesa().getIdMesa() == numeroMesa && !r.estahEncerrada())
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException("Mesa não encontrada ou já encerrada com número: " + numeroMesa));

        return requisicao.encerrar();
    }

    /**
     * Processa a fila de requisições tentando alocar a primeira requisição na fila a uma mesa disponível.
     *
     * @return Requisicao que foi processada.
     * @throws NoSuchElementException se não houver requisições na fila ou mesas disponíveis.
     */
    public Requisicao processarFila() {
        if (emEspera.isEmpty()) {
            throw new NoSuchElementException("Fila de espera está vazia.");
        }

        Requisicao requisicao = emEspera.remove(0);
        Mesa mesaDisponivel = localizarMesaDisponivel(requisicao.getQuantPessoas());
        if (mesaDisponivel != null) {
            atenderRequisicao(requisicao, mesaDisponivel);
            return requisicao;
        } else {
            throw new NoSuchElementException("Nenhuma mesa disponível para atender a requisição.");
        }
    }

    /**
     * Atende uma requisição alocando uma mesa disponível para ela.
     *
     * @param requisicao Requisição a ser atendida.
     * @param mesa Mesa onde a requisição será atendida.
     */
    private void atenderRequisicao(Requisicao requisicao, Mesa mesa) {
        requisicao.alocarMesa(mesa);
        atendidas.add(requisicao);
        requisicoesAtendidas++;
    }

    /**
     * Retorna o status atual de todas as mesas no restaurante.
     *
     * @return String contendo o status de cada mesa.
     */
    public String statusMesas() {
        StringBuilder status = new StringBuilder();
        for (Mesa mesa : mesas.values()) {
            status.append(mesa.toString()).append("\n");
        }
        return status.toString();
    }

    /**
     * Retorna uma lista formatada da fila de espera de requisições.
     *
     * @return String contendo todas as requisições na fila de espera.
     */
    public String filaDeEspera() {
        StringBuilder fila = new StringBuilder();
        for (Requisicao requisicao : emEspera) {
            fila.append(requisicao.toString()).append("\n");
        }
        return fila.toString();
    }

        /**
     * Registra uma nova requisição na fila de espera do restaurante.
     *
     * @param quantPessoas Quantidade de pessoas a serem atendidas.
     * @param cliente Cliente que faz a requisição.
     * @param tipoPedido Tipo de pedido (REGULAR ou MENU_FECHADO).
     * @throws Exception Se a fila de espera estiver cheia.
     */
    public void registrarRequisicao(int quantPessoas, Cliente cliente, TipoPedido tipoPedido) throws Exception {
        if (emEspera.size() >= MAX_FILA) {
            throw new Exception("A fila de espera alcançou sua capacidade máxima.");
        }
        Requisicao novaRequisicao = new Requisicao(quantPessoas, cliente, tipoPedido);
        emEspera.add(novaRequisicao);
        requisicoesEmEspera++;
        System.out.println("Requisição registrada com sucesso. Agora há " + requisicoesEmEspera + " requisições na fila de espera.");
    }
      /**
     * Adds an item to the order of a specific request, identified by the client's name.
     *
     * @param nomeCliente Name of the client.
     * @param item Item to be added to the order.
     */
    public void adicionarItemAoPedido(String nomeCliente, Item item) {
        atendidas.stream()
                 .filter(requisicao -> requisicao.getCliente().hashNome().equals(nomeCliente) && !requisicao.estahEncerrada())
                 .findFirst()
                 .ifPresent(requisicao -> requisicao.adicionarItemAoPedido(item));
    }

    /**
     * Calculates the total price of an order for a specific request identified by the client's name.
     *
     * @param nomeCliente Name of the client.
     * @return Total price of the order or -1 if the request is not found.
     */
    public double calcularPrecoTotalPedido(String nomeCliente) {
        return atendidas.stream()
                        .filter(requisicao -> requisicao.getCliente().hashNome().equals(nomeCliente))
                        .findFirst()
                        .map(Requisicao::calcularPrecoTotal)
                        .orElse(-1.0);
    }

    /**
     * Calculates the total price of an order per person for a specific request identified by the client's name.
     *
     * @param nomeCliente Name of the client.
     * @return Total price per person of the order or -1 if the request is not found.
     */
    public double calcularPrecoTotalPedidoPorPessoa(String nomeCliente) {
        return atendidas.stream()
                        .filter(requisicao -> requisicao.getCliente().hashNome().equals(nomeCliente))
                        .findFirst()
                        .map(Requisicao::calcularPrecoTotalPorPessoa)
                        .orElse(-1.0);
    }
}
