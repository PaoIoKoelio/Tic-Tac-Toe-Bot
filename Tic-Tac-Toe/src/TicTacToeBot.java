import java.util.Scanner;

public class TicTacToeBot {

    private static boolean isValidMove(char[][] board, int x, int y) {
        return x >= 0 && x < 3 && y >= 0 && y < 3 && board[x][y] == '.';
    }

    public static void play(boolean playerStarts) {
        Scanner scanner = new Scanner(System.in);
        State state = new State();
        char ourSymbol = 'X';
        char theirSymbol = 'O';
        if (playerStarts) {
            state.printBoard();
            ourSymbol = 'O';
            theirSymbol = 'X';
            int y = scanner.nextInt();
            int x = scanner.nextInt();
            state = new State(state.getCopyOfBoard(), x, y, 'X', 1, true, ourSymbol, theirSymbol);
            state.generateTree(theirSymbol, Integer.MIN_VALUE, Integer.MAX_VALUE);
            state.printBoard();
            state = state.getNext();
            state.printBoard();
        } else {
            state.generateTree(theirSymbol, Integer.MIN_VALUE, Integer.MAX_VALUE);
            state = state.getNext();
            state.printBoard();
        }
        while (true) {
            int y = scanner.nextInt();
            int x = scanner.nextInt();
            if (!isValidMove(state.getCopyOfBoard(), x, y)) {
                System.out.println("Incorrect move");
                continue;
            }
            State playerState = new State(state.getCopyOfBoard(), x, y, theirSymbol, state.getDepth(), true, ourSymbol, theirSymbol);
            playerState.printBoard();
            if (playerState.isWin(theirSymbol)) {
                System.out.println("You won!");
                break;
            } else if (playerState.isDraw()) {
                System.out.println("Draw");
                break;
            }
            if (!playerState.equals(state.getNext())) {
                playerState.generateTree(theirSymbol, Integer.MIN_VALUE, Integer.MAX_VALUE);
                state = playerState;
            } else {
                state = state.getNext();
            }
            state = state.getNext();
            System.out.println();
            state.printBoard();
            if (state.isWin(ourSymbol)) {
                System.out.println("You lost!");
                break;
            } else if (state.isDraw()) {
                System.out.println("Draw");
                break;
            }
        }
    }

    public static void main(String[] args) {
        TicTacToeBot.play(true);
    }
}
