import java.util.*;
import java.text.DecimalFormat;

public class Main {

        static abstract class Conta {
        private final int numeroConta;
        private final String titular;
        protected double saldo;

        public Conta(int numeroConta, String titular, double saldoInicial) {
            this.numeroConta = numeroConta;
            this.titular = titular;
            this.saldo = saldoInicial;
        }

        public int getNumeroConta() { return numeroConta; }
        public String getTitular()   { return titular; }
        public double getSaldo()     { return saldo; }

        public void depositar(double valor) {
            if (valor > 0) {
                saldo += valor;
                System.out.println("Deposito realizado com sucesso!");
            } else {
                System.out.println("Erro: valor de deposito deve ser positivo.");
            }
        }

        public abstract void sacar(double valor);

                public void solicitarEmprestimo(double valorParcela, int meses) {
            if (valorParcela <= 0 || meses <= 0) {
                System.out.println("Erro: valores invalidos para emprestimo.");
                return;
            }
            double total = valorParcela * meses;
            saldo += total;
            System.out.printf("Emprestimo aprovado! Valor total: R$%.2f%n", total);
        }

        public void exibirSaldo() {
            DecimalFormat df = new DecimalFormat("0.00");
            System.out.printf("Conta #%d - Titular: %s - Saldo: R$%s%n",
                    numeroConta, titular, df.format(saldo));
        }
    }

    static class ContaCorrente extends Conta {
        private final double taxaSaque = 2.50;

        public ContaCorrente(int numeroConta, String titular, double saldoInicial) {
            super(numeroConta, titular, saldoInicial);
        }

        @Override
        public void sacar(double valor) {
            if (valor <= 0) {
                System.out.println("Erro: valor invalido para saque.");
                return;
            }
            if (valor + taxaSaque <= saldo) {
                saldo -= (valor + taxaSaque);
                System.out.println("Saque realizado com taxa de R$2.50.");
            } else {
                System.out.println("Erro: saldo insuficiente.");
            }
        }
    }

    static class ContaPoupanca extends Conta {
        public ContaPoupanca(int numeroConta, String titular, double saldoInicial) {
            super(numeroConta, titular, saldoInicial);
        }

        @Override
        public void sacar(double valor) {
            if (valor <= 0) {
                System.out.println("Erro: valor invalido para saque.");
                return;
            }
            if (valor <= saldo) {
                saldo -= valor;
                System.out.println("Saque realizado com sucesso.");
            } else {
                System.out.println("Erro: saldo insuficiente.");
            }
        }
    }

    static class Banco {
        private final List<Conta> contas = new ArrayList<>();

        public void adicionarConta(Conta conta) {
            contas.add(conta);
        }

        public void listarContas() {
            for (Conta c : contas) {
                c.exibirSaldo();
            }
        }
    }

    // ======== Programa Principal ========
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        sc.useLocale(Locale.US);

        Banco banco = new Banco();

        // Criar conta
        System.out.println("--> Cadastro da Conta Digital Java <--");
        Conta minhaConta = criarConta(sc);
        banco.adicionarConta(minhaConta);

        int opcao;
        do {
            System.out.println("\n--- MENU BANCO DIGITAL ---");
            System.out.println("1 - Consultar saldo Total");
            System.out.println("2 - Depositar Dinheiro");
            System.out.println("3 - Sacar Dinheiro");
            System.out.println("4 - Solicitar emprestimo");
            System.out.println("5 - Listar contas do banco");
            System.out.println("0 - Finalizar serviço");
            System.out.print("Escolha uma opcao: ");
            opcao = lerInt(sc);

            switch (opcao) {
                case 1:
                    minhaConta.exibirSaldo();
                    break;
                case 2:
                    System.out.print("Digite valor para deposito: ");
                    minhaConta.depositar(lerDouble(sc));
                    break;
                case 3:
                    System.out.print("Digite valor para saque: ");
                    minhaConta.sacar(lerDouble(sc));
                    break;
                case 4:
                    System.out.print("Digite o valor da parcela: ");
                    double parcela = lerDouble(sc);
                    System.out.print("Digite a quantidade de meses: ");
                    int meses = lerInt(sc);
                    minhaConta.solicitarEmprestimo(parcela, meses);
                    break;
                case 5:
                    banco.listarContas();
                    break;
                case 0:
                    System.out.println("Saindo... :)");
                    break;
                default:
                    System.out.println("Opcao invalida!");
            }
        } while (opcao != 0);

        sc.close();
    }

    // ======== Helpers ========
    private static Conta criarConta(Scanner sc) {
        System.out.print("Numero da conta: ");
        int numero = lerInt(sc);
        System.out.print("Nome do titular: ");
        String titular = sc.nextLine().trim();
        System.out.print("Saldo inicial: ");
        double saldoInicial = lerDouble(sc);

        System.out.print("Criacao de Conta \n * 1 = Conta Corrente \n * 2 = Conta Poupança ");
        int tipo = lerInt(sc);
        if (tipo == 1) {
            return new ContaCorrente(numero, titular, saldoInicial);
        } else {
            return new ContaPoupanca(numero, titular, saldoInicial);
        }
    }

    private static int lerInt(Scanner sc) {
        while (true) {
            try {
                String s = sc.nextLine().trim();
                return Integer.parseInt(s);
            } catch (Exception e) {
                System.out.print("Entrada invalida.");
            }
        }
    }

    private static double lerDouble(Scanner sc) {
        while (true) {
            try {
                String s = sc.nextLine().trim().replace(",", ".");
                return Double.parseDouble(s);
            } catch (Exception e) {
                System.out.print("Entrada invalida.");
            }
        }
    }
}
