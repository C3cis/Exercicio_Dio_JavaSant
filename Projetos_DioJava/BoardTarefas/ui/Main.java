package ui;

import entity.BoardEntity;
import entity.CardEntity;
import service.BoardService;
import service.CardService;
import util.ConnectionFactory;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            conn.setAutoCommit(false);
            BoardService boardService = new BoardService(conn);
            CardService cardService = new CardService(conn);
            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.println("\n=== MENU PRINCIPAL ===");
                System.out.println("1 - Criar novo Board");
                System.out.println("2 - Listar Boards");
                System.out.println("3 - Selecionar Board");
                System.out.println("4 - Renomear Board");
                System.out.println("5 - Excluir Board");
                System.out.println("0 - Sair");
                System.out.print("Escolha: ");
                int op = sc.nextInt();
                sc.nextLine();

                switch (op) {
                    case 1 -> {
                        System.out.print("Nome do board: ");
                        String name = sc.nextLine();
                        BoardEntity b = boardService.createBoard(name);
                        System.out.println("Board criado: id=" + b.getId());
                    }
                    case 2 -> {
                        List<BoardEntity> boards = boardService.list();
                        System.out.println("Boards:");
                        for (BoardEntity b : boards) {
                            System.out.println(b.getId() + " - " + b.getName());
                        }
                    }
                    case 3 -> {
                        System.out.print("ID do Board: ");
                        Long id = sc.nextLong();
                        sc.nextLine();
                        // Submenu board
                        boardMenu(conn, id);
                    }
                    case 4 -> {
                        System.out.print("ID do board: ");
                        Long id = sc.nextLong();
                        sc.nextLine();
                        System.out.print("Novo nome: ");
                        String newName = sc.nextLine();
                        boolean ok = boardService.updateBoard(id, newName);
                        System.out.println(ok ? "Board atualizado" : "Board não encontrado");
                    }
                    case 5 -> {
                        System.out.print("ID do board: ");
                        Long id = sc.nextLong();
                        sc.nextLine();
                        boolean ok = boardService.delete(id);
                        System.out.println(ok ? "Board excluído" : "Board não encontrado");
                    }
                    case 0 -> {
                        System.out.println("Saindo...");
                        return;
                    }
                    default -> System.out.println("Opção inválida.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Sub-menu para gerenciamento de cards do board
    private static void boardMenu(Connection conn, Long boardId) {
        CardService cardService = new CardService(conn);
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== BOARD ID: " + boardId + " ===");
            System.out.println("1 - Criar Card (entra na coluna INITIAL)");
            System.out.println("2 - Listar Cards por Coluna (informe coluna id)");
            System.out.println("3 - Mover Card para próxima coluna");
            System.out.println("4 - Mover Card para Cancelamento");
            System.out.println("5 - Bloquear Card");
            System.out.println("6 - Desbloquear Card");
            System.out.println("7 - Voltar");
            System.out.print("Escolha: ");
            int op = sc.nextInt();
            sc.nextLine();

            try {
                switch (op) {
                    case 1 -> {
                        System.out.print("Título: ");
                        String title = sc.nextLine();
                        System.out.print("Descrição: ");
                        String desc = sc.nextLine();
                        var card = cardService.createCardInInitial(boardId, title, desc);
                        System.out.println("Card criado id=" + card.getId());
                    }
                    case 2 -> {
                        System.out.print("ID da coluna: ");
                        Long colId = sc.nextLong();
                        sc.nextLine();
                        List<CardEntity> cards = cardService.listByColumn(colId);
                        if (cards.isEmpty()) System.out.println("Nenhum card.");
                        for (CardEntity c : cards) {
                            System.out.printf("%d - %s %s\n", c.getId(), c.getTitle(), c.isBlocked() ? "[BLOQUEADO]" : "");
                        }
                    }
                    case 3 -> {
                        System.out.print("ID do card: ");
                        Long cardId = sc.nextLong();
                        sc.nextLine();
                        boolean ok = cardService.moveToNext(cardId);
                        System.out.println(ok ? "Card movido para próxima coluna." : "Não foi possível mover (inexistente / bloqueado / sem próxima).");
                    }
                    case 4 -> {
                        System.out.print("ID do card: ");
                        Long cardId = sc.nextLong();
                        sc.nextLine();
                        boolean ok = cardService.moveToCancel(cardId);
                        System.out.println(ok ? "Card movido para Cancelamento." : "Falha ao mover para Cancelamento.");
                    }
                    case 5 -> {
                        System.out.print("ID do card: ");
                        Long cardId = sc.nextLong();
                        sc.nextLine();
                        System.out.print("Motivo do bloqueio: ");
                        String reason = sc.nextLine();
                        boolean ok = cardService.blockCard(cardId, reason);
                        System.out.println(ok ? "Card bloqueado." : "Falha ao bloquear.");
                    }
                    case 6 -> {
                        System.out.print("ID do card: ");
                        Long cardId = sc.nextLong();
                        sc.nextLine();
                        System.out.print("Motivo do desbloqueio: ");
                        String reason = sc.nextLine();
                        boolean ok = cardService.unblockCard(cardId, reason);
                        System.out.println(ok ? "Card desbloqueado." : "Falha ao desbloquear.");
                    }
                    case 7 -> { return; }
                    default -> System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
