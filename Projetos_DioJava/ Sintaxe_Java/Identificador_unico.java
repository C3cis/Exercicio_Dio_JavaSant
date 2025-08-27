import java.util.Scanner;
import java.util.UUID;

public class IdentificadorUnico {

    public static void main(String[] args) {
		 
		 Scanner scanner = new Scanner(System.in);

	        System.out.print("Digite seu nome: ");
	        String nome = scanner.nextLine();

	        int numero = 0;
	        boolean numeroValido = false;

	        while (!numeroValido) {
	            System.out.print("Digite o ID ");
	            String NumeroRemov = scanner.nextLine().replaceAll(" ", "");

	            try {
	                numero = Integer.parseInt(NumeroRemov);
	                numeroValido = true;
	            } catch (NumberFormatException e) {
	                System.out.println("ID inválido!");
	            }
	        }

	        String nomeFormatado = nome.toLowerCase().replaceAll(" ", "");
	        String identificador = nomeFormatado + "-" + numero;

	        System.out.println("\nIdentificador Unico Gerado: " + identificador);
        }
    }
