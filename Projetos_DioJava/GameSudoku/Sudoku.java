import java.util.Scanner;

public class Sudoku {
    public static void main(String[] args) {
        SudokuGame game = new SudokuGame();
      
        for (int i = 0; i + 2 < args.length; i += 3) {
            int row = Integer.parseInt(args[i]);
            int col = Integer.parseInt(args[i + 1]);
            int val = Integer.parseInt(args[i + 2]);
            game.setFixed(row, col, val);
        }

        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n==== MENU SUDOKU ====");
            System.out.println("1. Iniciar novo jogo");
            System.out.println("2. Colocar número");
            System.out.println("3. Remover número");
            System.out.println("4. Visualizar jogo");
            System.out.println("5. Verificar status");
            System.out.println("6. Limpar (apagar números do jogador)");
            System.out.println("7. Finalizar jogo");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();

            switch (opcao) {
                case 1 -> game.printBoard();
                case 2 -> {
                    System.out.print("Linha (0-8): ");
                    int r = sc.nextInt();
                    System.out.print("Coluna (0-8): ");
                    int c = sc.nextInt();
                    System.out.print("Valor (1-9): ");
                    int v = sc.nextInt();
                    game.placeNumber(r, c, v);
                }
                case 3 -> {
                    System.out.print("Linha (0-8): ");
                    int r = sc.nextInt();
                    System.out.print("Coluna (0-8): ");
                    int c = sc.nextInt();
                    game.removeNumber(r, c);
                }
                case 4 -> game.printBoard();
                case 5 -> System.out.println(game.checkStatus());
                case 6 -> game.clearBoard();
                case 7 -> {
                    if (game.isCompleteAndValid()) {
                        System.out.println("Parabéns! Você completou o Sudoku!");
                        opcao = 0; // encerra
                    } else {
                        System.out.println("O jogo ainda não está completo ou contém erros.");
                    }
                }
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }
}
