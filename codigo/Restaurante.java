import java.util.ArrayList;
import java.util.List;

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

    private void criarMesas() {
        for (int i = 0; i < quantMesas; i++) {
            mesas[i] = new Mesa(i + 1);
        }
    }

    public void addCliente(Cliente novo) {
        if (quantClientes < MAX_CLIENTES) {
            clientes[quantClientes++] = novo;
        } else {
            System.out.println("Número máximo de clientes atingido.");
        }
    }

    public Cliente localizarCliente(int id) {
        for (Cliente cliente : clientes) {
            if (cliente != null && cliente.hashCode() == id) { 
                return cliente;
            }
        }
        return null;
    }

    public Mesa localizarMesaDisponivel(int quantPessoas) {
        for (Mesa mesa : mesas) {
            if (mesa != null && mesa.isDisponivel() && mesa.getCapacidade() >= quantPessoas) {
                return mesa;
            }
        }
        return null;
    }

    public void encerrarAtendimento(int numeroMesa) {
        if (numeroMesa >= 1 && numeroMesa <= quantMesas) {
            Mesa mesa = mesas[numeroMesa - 1];
            mesa.encerrarAtendimento();
        } else {
            System.out.println("Número da mesa inválido.");
        }
    }

    public Requisicao processarFila() {
        if (!emEspera.isEmpty()) {
            Requisicao requisicao = emEspera.remove(0);
            requisicoesEmEspera--;
            return requisicao;
        }
        return null;
    }

    public void retirarDaFila(int pos) {
        if (pos >= 0 && pos < emEspera.size()) {
            emEspera.remove(pos);
            requisicoesEmEspera--;
        } else {
            System.out.println("Posição inválida na fila.");
        }
    }

    public void registrarRequisicao(Requisicao novaRequisicao) {
        if (emEspera.size() < MAX_FILA) {
            emEspera.add(novaRequisicao);
            requisicoesEmEspera++;
        } else {
            System.out.println("Fila de espera cheia.");
        }
    }

    public void atenderRequisicao(Requisicao requisicao, Mesa mesa) {
        if (mesa != null && requisicao != null) {
            mesa.atenderRequisicao(requisicao);
            atendidas.add(requisicao);
            requisicoesAtendidas++;
        }
    }

    public String statusMesas() {
        StringBuilder status = new StringBuilder();
        for (Mesa mesa : mesas) {
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
}
