import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

class ParametrosInvalidosException extends Exception {
    public ParametrosInvalidosException(String mensagem) {
        super(mensagem);
    }
}
class Candidato {
    private final String nome;
    private final double salarioPretendido;
    private final boolean contatoRealizado;

    public Candidato(String nome, double salarioPretendido, boolean contatoRealizado) {
        this.nome = nome;
        this.salarioPretendido = salarioPretendido;
        this.contatoRealizado = contatoRealizado;
    }

    public String getNome() { return nome; }
    public double getSalarioPretendido() { return salarioPretendido; }
    public boolean isContatoRealizado() { return contatoRealizado; }

    @Override
    public String toString() {
        return String.format("%s | R$ %.2f | Contato: %s",
                nome,
                salarioPretendido,
                contatoRealizado ? "Sim" : "Não");
    }
}

public class ProcessoSeletivo {

    private static final double SALARIO_FIXO = 2000.0;
    private final Candidato[] aprovados = new Candidato[5]; 
    private final List<Candidato> todosTentados = new ArrayList<>(); 
    private int qtdAprovados = 0;

    private final Scanner scanner = new Scanner(System.in);
    private final Random random = new Random();

    public void iniciarProcesso() {
        System.out.println("--> Processo Seletivo Iniciando <--");

        while (qtdAprovados < aprovados.length) {
            System.out.printf("\nVagas Preenchidas: %d/%d\n", qtdAprovados, aprovados.length);

            String nome = lerNome();
            if (nome == null) continue; 

            Double salarioPretendido = lerSalario();
            if (salarioPretendido == null) continue; 

            try {
                validarSalario(salarioPretendido);

                boolean candidatoAprovadoPorPretensao = false;
                if (salarioPretendido < SALARIO_FIXO) {
                    System.out.println("Status: Candidato Aprovado - Fazer Contato");
                    candidatoAprovadoPorPretensao = true;
                } else if (Double.compare(salarioPretendido, SALARIO_FIXO) == 0) {
                    System.out.println("Status: Proposta Positiva - Fazer Contato");
                    candidatoAprovadoPorPretensao = true;
                } else {
                    System.out.println("Status: Salario Ultrapassado - Candidato na Lista de Espera");
                }

                if (candidatoAprovadoPorPretensao) {
                    boolean atendeu = tentarLigar(nome);
                    Candidato c = new Candidato(nome, salarioPretendido, atendeu);
                    todosTentados.add(c);

                    if (atendeu) {
                        aprovados[qtdAprovados] = c;
                        qtdAprovados++;
                        System.out.printf("Vaga confirmada para %s. (%d/%d)\n", nome, qtdAprovados, aprovados.length);
                    } else {
                        System.out.printf("%s não atendeu nas 2 stentativas. Candidato Perdido, Vaga liberada.\n", nome);
                        
                    }
                } else {
                    Candidato c = new Candidato(nome, salarioPretendido, false);
                    todosTentados.add(c);
                }

            } catch (ParametrosInvalidosException e) {
                System.out.println("Erro: " + e.getMessage());
            }

            if (qtdAprovados == aprovados.length) {
                System.out.println("\nTodas as vagas foram preenchidas :( ");
                break;
            }
            
            System.out.print("\nDeseja cadastrar outro candidato? (S/N): ");
            String resposta = scanner.nextLine().trim();
            if (resposta.equalsIgnoreCase("n")) {
                System.out.println("Encerrando cadastro e Fechando Processo Seletivo.");
                break;
            }
        }

        mostrarResumoFinal();
        scanner.close();
    }

	private String lerNome() {
        System.out.print("Nome do candidato: ");
        String nome = scanner.nextLine().trim();
        if (nome.isEmpty()) {
            System.out.println("Nome inválido. Tente novamente.");
            return null;
        }
        return nome;
    }

    private Double lerSalario() {
        System.out.print("Salário pretendido pelo Candidato: R$ ");
        String entrada = scanner.nextLine().trim();
        try {
            return Double.parseDouble(entrada);
        } catch (NumberFormatException e) {
            System.out.println("Entrada de salário inválida. Tente novamente");
            return null;
        }
    }

    private void validarSalario(double salario) throws ParametrosInvalidosException {
        if (salario <= 0) {
            throw new ParametrosInvalidosException("Salário inválido!");
        }
    }
    private boolean tentarLigar(String nome) {
        System.out.printf("\nIniciando contato com %s...\n", nome);

        for (int tentativa = 1; tentativa <= 2; tentativa++) {
            System.out.printf("  → Tentativa %d de 2... ", tentativa);

            boolean atendeu = random.nextBoolean();
            if (atendeu) {
                System.out.println("Atendeu!");
                return true;
            } else {
                System.out.println("Não atendeu.");
            }
        }
        return false;
    }

    private void mostrarResumoFinal() {
        System.out.println("\n--> REGISTRO FINAL DO PROCESSO <--\n");

        System.out.println("Vagas preenchidas:");
        if (qtdAprovados == 0) {
            System.out.println("  (nenhuma vaga preenchida)");
        } else {
            for (int i = 0; i < qtdAprovados; i++) {
                System.out.printf("  %d) %s\n", i + 1, aprovados[i].toString());
            }
        }

        System.out.println("\nCandidatos Total Cadastrados");
        if (todosTentados.isEmpty()) {
            System.out.println("  (nenhum candidato registrado)");
        } else {
            for (Candidato c : todosTentados) {
                System.out.println("  - " + c.toString());
            }
        }
        
        System.out.println("\nTotal de vagas disponíveis: " + aprovados.length);
        System.out.println("Vagas Prenchidas " + qtdAprovados);
        System.out.println("Vagas livres: " + (aprovados.length - qtdAprovados));
        System.out.println("\n--> Fim do Processo Seletivo <--");
    }

    public static void main(String[] args) {
        new ProcessoSeletivo().iniciarProcesso();
    }
}
