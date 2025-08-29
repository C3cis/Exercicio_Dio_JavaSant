public class SudokuGame {
    private final int[][] board = new int[9][9];
    private final boolean[][] fixed = new boolean[9][9];

    public void setFixed(int r, int c, int val) {
        board[r][c] = val;
        fixed[r][c] = true;
    }

    public void placeNumber(int r, int c, int val) {
        if (fixed[r][c]) {
            System.out.println("⚠ Não é permitido alterar número fixo!");
            return;
        }
        if (board[r][c] != 0) {
            System.out.println("⚠ Já existe um número nessa posição!");
            return;
        }
        board[r][c] = val;
    }

    public void removeNumber(int r, int c) {
        if (fixed[r][c]) {
            System.out.println("⚠ Não é permitido remover número fixo!");
            return;
        }
        if (board[r][c] == 0) {
            System.out.println("⚠ Essa posição já está vazia!");
            return;
        }
        board[r][c] = 0;
    }

    public void clearBoard() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (!fixed[r][c]) {
                    board[r][c] = 0;
                }
            }
        }
        System.out.println("✔ Tabuleiro limpo (apenas números fixos mantidos).");
    }

    public void printBoard() {
        System.out.println("---- TABULEIRO ----");
        for (int r = 0; r < 9; r++) {
            if (r % 3 == 0 && r != 0) {
                System.out.println("------+-------+------");
            }
            for (int c = 0; c < 9; c++) {
                if (c % 3 == 0 && c != 0) {
                    System.out.print("| ");
                }
                if (board[r][c] == 0) {
                    System.out.print(". ");
                } else {
                    System.out.print(board[r][c] + " ");
                }
            }
            System.out.println();
        }
    }

    public String checkStatus() {
        boolean vazio = false;
        boolean erro = false;

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                int v = board[r][c];
                if (v == 0) {
                    vazio = true;
                    continue;
                }
                for (int i = 0; i < 9; i++) {
                    if (i != c && board[r][i] == v) erro = true;
                    if (i != r && board[i][c] == v) erro = true;
                }
                int br = (r / 3) * 3;
                int bc = (c / 3) * 3;
                for (int rr = br; rr < br + 3; rr++) {
                    for (int cc = bc; cc < bc + 3; cc++) {
                        if ((rr != r || cc != c) && board[rr][cc] == v) erro = true;
                    }
                }
            }
        }

        if (isEmpty()) return "Status: NÃO INICIADO (sem erros)";
        if (vazio) return "Status: INCOMPLETO " + (erro ? "(com erros)" : "(sem erros)");
        return "Status: COMPLETO " + (erro ? "(com erros)" : "(sem erros)");
    }

    public boolean isEmpty() {
        for (int[] row : board) {
            for (int v : row) {
                if (v != 0) return false;
            }
        }
        return true;
    }

    public boolean isCompleteAndValid() {
        return checkStatus().equals("Status: COMPLETO (sem erros)");
    }
}
