import java.util.ArrayList;
import java.util.List;

/**
 * MIT License
 * <p>
 * Classe principal para o restaurante, gerencia mesas, clientes e requisições.
 */
public class Restaurante {
    private static final int MAX_CLIENTES = 100;
    private static final int MAX_FILA = 50;

    private Mesa[] mesas;
    private Cliente[] clientes;
    private List<Requisicao> atendidas;
    private List<Requisicao> emEspera;

    private int quantClientes;
    private int quantMesas;
    private int requisicoesAtendidas;
    private int requisicoesEmEspera;

    /**
     * Construtor do restaurante, inicializa mesas e listas de clientes e requisições.
     *
     * @param numMesas Número de mesas do restaurante.
     */
    public Restaurante(int numMesas) {
        this.mesas = new Mesa[numMesas];
        this.clientes = new Cliente[MAX_CLIENTES];
        this.atendidas = new ArrayList<>();
        this.emEspera = new ArrayList<>();
        this.quantClientes = 0;
        this.quantMesas = numMesas;
        this.requisicoesAtendidas = 0;
        this.requisicoesEmEspera = 0;
        criarMesas();
    }

    /**
     * Cria as mesas com capacidade padrão.
     */
    private void criarMesas() {
        for (int i = 0; i < quantMesas; i++) {
            mesas[i] = new Mesa(4); // Capacidade padrão, ajuste conforme necessário
        }
    }

    /**
     * Adiciona um novo cliente ao restaurante.
     *
     * @param novo Novo cliente a ser adicionado.
     * @return true se o cliente foi adicionado, false caso contrário.
     */
    public void addCliente(Cliente novo) {
        if (quantClientes < MAX_CLIENTES) {
            clientes[quantClientes++] = novo;

        }

    }

    /**
     * Localiza um cliente pelo seu ID.
     *
     * @param id ID do cliente.
     * @return Cliente localizado ou null se não encontrado.
     */
    public Cliente localizarCliente(int id) {
        for (Cliente cliente : clientes) {
            if (cliente != null && cliente.hashCode() == id) {
                return cliente;
            }
        }
        return null;
    }

    /**
     * Localiza uma mesa disponível para um determinado número de pessoas.
     *
     * @param quantPessoas Quantidade de pessoas.
     * @return Mesa disponível ou null se não encontrada.
     */
    public Mesa localizarMesaDisponivel(int quantPessoas) {
        for (Mesa mesa : mesas) {
            if (mesa != null && mesa.estahLiberada(quantPessoas)) {
                return mesa;
            }
        }
        return null;
    }

    /**
     * Encerra o atendimento de uma mesa.
     *
     * @param numeroMesa Número da mesa.
     * @return A mesa desocupada ou null se não encontrada.
     */
    public Mesa encerrarAtendimento(int numeroMesa) {
        for (Requisicao requisicao : atendidas) {
            if (!requisicao.estahEncerrada() && requisicao.ehDaMesa(numeroMesa)) {
                return requisicao.encerrar();
            }
        }
        return null;
    }

    /**
     * Processa a fila de requisições, atendendo a próxima requisição.
     *
     * @return Requisição processada ou null se a fila estiver vazia.
     */
    public Requisicao processarFila() {
        if (!emEspera.isEmpty()) {
            Requisicao requisicao = emEspera.remove(0);
            requisicoesEmEspera--;
            return requisicao;
        }
        return null;
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
     * Registra uma nova requisição na fila de espera.
     *
     * @param novaRequisicao Nova requisição a ser registrada.
     * @return true se a requisição foi registrada, false caso contrário.
     */
    public void registrarRequisicao(Requisicao novaRequisicao) {
        if (emEspera.size() < MAX_FILA) {
            emEspera.add(novaRequisicao);
            requisicoesEmEspera++;

        }

    }

    /**
     * Atende uma requisição em uma mesa específica.
     *
     * @param requisicao Requisição a ser atendida.
     * @param mesa       Mesa onde a requisição será atendida.
     * @return true se a requisição foi atendida, false caso contrário.
     */
    private void atenderRequisicao(Requisicao requisicao, Mesa mesa) {
        if (mesa != null && requisicao != null && mesa.estahLiberada(requisicao.getQuantPessoas())) {
            requisicao.alocarMesa(mesa);
            atendidas.add(requisicao);
            requisicoesAtendidas++;

        }

    }

    /**
     * Retorna o status de todas as mesas do restaurante.
     *
     * @return String com o status de todas as mesas.
     */
    public String statusMesas() {
        StringBuilder status = new StringBuilder();
        for (Mesa mesa : mesas) {
            status.append(mesa.toString()).append("\n");
        }
        return status.toString();
    }

    /**
     * Retorna a fila de espera de requisições.
     *
     * @return String com a fila de espera de requisições.
     */
    public String filaDeEspera() {
        StringBuilder fila = new StringBuilder();
        for (Requisicao requisicao : emEspera) {
            fila.append(requisicao.toString()).append("\n");
        }
        return fila.toString();
    }
}
