package org.example;

import java.util.*;

/**
 * Exceção personalizada para indicar que a capacidade máxima do restaurante foi atingida.
 */
class CapacidadeMaximaAtingidaException extends Exception {
    public CapacidadeMaximaAtingidaException(String message) {
        super(message);
    }
}

/**
 * Exceção personalizada para indicar que uma operação inválida foi tentada no restaurante.
 */
class OperacaoInvalidaException extends Exception {
    public OperacaoInvalidaException(String message) {
        super(message);
    }
}

/**
 * Classe principal para o gerenciamento de um restaurante, incluindo mesas, clientes e requisições.
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

    private void criarMesas() {
        int index = 0;
        int[] capacidades = {4, 6, 8};
        int[] quantidadePorCapacidade = {4, 4, 2};

        for (int i = 0; i < capacidades.length; i++) {
            for (int j = 0; j < quantidadePorCapacidade[i]; j++) {
                mesas.put(index, new Mesa(capacidades[i]));
                index++;
            }
        }
    }

    public void addCliente(Cliente novo) throws CapacidadeMaximaAtingidaException {
        if (quantClientes >= MAX_CLIENTES) {
            throw new CapacidadeMaximaAtingidaException("Capacidade máxima de clientes atingida.");
        }
        clientes.put(novo.hashCode(), novo);
        quantClientes++;
    }

    public Cliente localizarCliente(int id) throws OperacaoInvalidaException {
        Cliente cliente = clientes.get(id);
        if (cliente == null) {
            throw new OperacaoInvalidaException("Cliente não encontrado.");
        }
        return cliente;
    }

    public Cliente localizarClienteNome(String nome) throws OperacaoInvalidaException {
        return clientes.values().stream()
            .filter(cliente -> cliente.hashNome().equals(nome))
            .findFirst()
            .orElseThrow(() -> new OperacaoInvalidaException("Cliente não encontrado pelo nome."));
    }

    public Mesa localizarMesaDisponivel(int quantPessoas) throws OperacaoInvalidaException {
        return mesas.values().stream()
            .filter(mesa -> mesa.estahLiberada(quantPessoas))
            .findFirst()
            .orElseThrow(() -> new OperacaoInvalidaException("Nenhuma mesa disponível para a quantidade de pessoas."));
    }

    public Mesa encerrarAtendimento(int numeroMesa) throws OperacaoInvalidaException {
        for (Requisicao requisicao : atendidas) {
            if (requisicao.getMesa() != null && requisicao.getMesa().getIdMesa() == numeroMesa) {
                if (!requisicao.estahEncerrada()) {
                    return requisicao.encerrar();
                } else {
                    throw new OperacaoInvalidaException("Requisição já encerrada para esta mesa.");
                }
            }
        }
        throw new OperacaoInvalidaException("Mesa não encontrada.");
    }

    public Requisicao processarFila() throws OperacaoInvalidaException {
        if (emEspera.isEmpty()) {
            throw new OperacaoInvalidaException("Nenhuma requisição na fila.");
        }

        Requisicao requisicao = emEspera.get(0);
        Mesa mesaDisponivel = localizarMesaDisponivel(requisicao.getQuantPessoas());
        if (mesaDisponivel != null) {
            atenderRequisicao(requisicao, mesaDisponivel);
            emEspera.remove(requisicao);
            requisicoesEmEspera--;
            return requisicao;
        }

        throw new OperacaoInvalidaException("Nenhuma mesa disponível para atender a requisição.");
    }

    private void atenderRequisicao(Requisicao requisicao, Mesa mesa) {
        requisicao.alocarMesa(mesa);
        atendidas.add(requisicao);
        requisicoesAtendidas++;
    }

    public String statusMesas() {
        StringBuilder status = new StringBuilder();
        for (Mesa mesa : mesas.values()) {
            status.append(mesa.toString()).append("\n");
        }
        return status.toString();
    }

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
     * @param novaRequisicao A nova requisição a ser adicionada à fila de espera.
     * @throws Exception Se a fila de espera estiver cheia.
     */
    public void registrarRequisicao(Requisicao novaRequisicao) throws Exception {
        if (emEspera.size() >= MAX_FILA) {
            throw new Exception("A fila de espera alcançou sua capacidade máxima.");
        }
        emEspera.add(novaRequisicao);
        requisicoesEmEspera++;
        System.out.println("Requisição registrada com sucesso. Agora há " + requisicoesEmEspera + " requisições na fila de espera.");
    }
    /** 
    * Adiciona um item ao pedido de uma requisição específica.
    *
    * @param idCliente ID do cliente.
    * @param item Item a ser adicionado ao pedido.
    */
   public void adicionarItemAoPedido(int idCliente, Item item) {
       for (Requisicao requisicao : atendidas) {
           if (requisicao.getCliente().hashCode() == idCliente && !requisicao.estahEncerrada()) {
               requisicao.adicionarItemAoPedido(item);
           }
       }
   }
      /**
     * Remove uma requisição da fila de espera.
     *
     * @param pos Posição da requisição na fila.
     * @return true se a requisição foi removida, false caso contrário.
     */
    private  void retirarDaFila(int pos) {
        if (pos >= 0 && pos < emEspera.size()) {
            emEspera.remove(pos);
            requisicoesEmEspera--;

        }

    }
    /**
     * Calcula o preço total do pedido de uma requisição específica.
     *
     * @param idCliente ID do cliente.
     * @return Preço total do pedido ou -1 se a requisição não for encontrada.
     */
    public double calcularPrecoTotalPedido(int idCliente) {
        for (Requisicao requisicao : atendidas) {
            if (requisicao.getCliente().hashCode() == idCliente) {
                return requisicao.calcularPrecoTotal();
            }
        }
        return -1;
    }
}

