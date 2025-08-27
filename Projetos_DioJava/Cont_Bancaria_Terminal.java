import java.util.Scanner;

public class SimuContBancaria {
    public static void main(String[] args) {
		        Scanner sc = new Scanner(System.in);

		        int numeroConta;
		        String nome;
		        double saldo;
		        double deposito;
		        double retDinheiro = 0.0;

		        System.out.print("Numero da Conta: ");
		        numeroConta = sc.nextInt();
		        sc.nextLine(); 

    		        System.out.print("Nome Titular: ");
    		        nome = sc.nextLine();

		        System.out.print("Saldo da conta: R$ ");
		        saldo = sc.nextDouble();

    		        System.out.print("Valor do Depósito: R$ ");
    		        deposito = sc.nextDouble();
    		        saldo += deposito;

		        sc.nextLine(); 
		        System.out.print("Irá Retirar um valor da Conta (S/N)?\n ");
		        String s_n = sc.nextLine().trim();
		        
		            System.out.print("\n___Cadastro de Conta___\n"); 
		        
		        if (s_n.equalsIgnoreCase("S")) {
		            System.out.print("Valor a retirar: R$ \n");
		            retDinheiro = sc.nextDouble();
		            saldo -= retDinheiro;
		            
		           
		            if (saldo < 0) {
		                System.out.println("Atenção: saldo negativo após a retirada.");
		            }
		          
		            System.out.printf( "\nNumero da Conta: %d\nNome Titular: %s\nSaldo da Conta Total: R$ %.2f\nValor Depositado: R$ %.2f\nValor Retirado: R$ %.2f\n",
		                numeroConta, nome, saldo, deposito, retDinheiro
		            );
		        } else {
		            System.out.printf("Conta: %d\nNome Titular: %s\nSaldo da Conta Total: R$ %.2f\nValor do Depositado: R$ %.2f\n",
		                numeroConta, nome, saldo, deposito
		            );
		        }
	}
	
}
