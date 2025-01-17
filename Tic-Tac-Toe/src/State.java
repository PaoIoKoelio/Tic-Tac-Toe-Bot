import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class State {
    private char[][] board;
    private int value;
    private int depth;
    private List<State> children;
    private State nextMove;
    private boolean max;
    private char playerSymbol;
    private char enemySymbol;


    public State(char[][] board, int x, int y, char symbol, int depth, boolean max, char playerSymbol, char enemySymbol) {
        this.depth = depth;
        this.board = board;
        this.board[x][y] = symbol;
        this.children = new ArrayList<>();
        this.max = max;
        this.value = max ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        this.playerSymbol = playerSymbol;
        this.enemySymbol = enemySymbol;
    }

    public State() {
        this.depth = 1;
        this.board = new char[3][3];
        this.max = true;
        this.value = Integer.MIN_VALUE;
        for (int i = 0; i < 3; i++) {
            Arrays.fill(this.board[i], '.');
        }
        this.children = new ArrayList<>();
        this.enemySymbol = 'O';
        this.playerSymbol = 'X';
    }

    public boolean isWin(char playerSymbol) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == playerSymbol && board[i][1] == playerSymbol && board[i][2] == playerSymbol) {
                return true;
            }
        }

        for (int j = 0; j < 3; j++) {
            if (board[0][j] == playerSymbol && board[1][j] == playerSymbol && board[2][j] == playerSymbol) {
                return true;
            }
        }

        if (board[0][0] == playerSymbol && board[1][1] == playerSymbol && board[2][2] == playerSymbol) {
            return true;
        }

        if (board[0][2] == playerSymbol && board[1][1] == playerSymbol && board[2][0] == playerSymbol) {
            return true;
        }

        return false;
    }

    public boolean isDraw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '.') {
                    return false;
                }
            }
        }
        return true;
    }


    private char getOppositeSymbol(char symbol) {
        if (symbol == 'X') {
            return 'O';
        }
        return 'X';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        State state = (State) obj;
        return Arrays.deepEquals(board, state.board);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }

    public boolean isOver() {
        return isDraw() || isWin(playerSymbol) || isWin(enemySymbol);
    }

    public void generateTree(char symbol, int alpha, int beta) {
        if (isWin(playerSymbol)) {
            value = 10 - depth;
            return;
        } else if (isWin(enemySymbol)) {
            value = depth - 10;
            return;
        } else if (isDraw()) {
            value = 0;
            return;
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '.') {
                    State child = new State(getCopyOfBoard(), i, j, getOppositeSymbol(symbol), depth + 1, !max, playerSymbol, enemySymbol);
                    child.generateTree(getOppositeSymbol(symbol), alpha, beta);
                    children.add(child);
                    int childValue=child.getValue();
                    if (max) {
                        if (childValue > value) {
                            value = childValue;
                            nextMove = child;
                        }
                        if(value>=beta){
                            return;
                        }
                        alpha=Math.max(alpha, value);

                    } else {
                        if (childValue < value) {
                            value = childValue;
                            nextMove = child;
                        }
                        if(value<=alpha){
                            return;
                        }
                        beta=Math.min(beta, value);
                    }
                }
            }
        }
    }

    public int getValue() {
        return value;
    }

    public int getDepth() {
        return depth;
    }

    public State getNext() {
        return nextMove;
    }

    public void printBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

   /* public int determineValue() {
        if (isWin(playerSymbol)) {
            value = 10 - depth;
            return 10 - depth;
        } else if (isWin(enemySymbol)) {
            value = depth - 10;
            return depth - 10;
        } else if (isDraw()) {
            value = 0;
            return 0;
        }
        if (max) {
            for (State curChild : children) {
                int curVal = curChild.determineValue();
                if (curVal > value) {
                    value = curVal;
                    nextMove = curChild;
                }
            }
        } else {
            for (State curChild : children) {
                int curVal = curChild.determineValue();
                if (curVal < value) {
                    value = curVal;
                    nextMove = curChild;
                }
            }
        }
        return value;
    }*/

    public char[][] getCopyOfBoard() {
        char[][] copy = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                copy[i][j] = board[i][j];
            }
        }
        return copy;
    }
}
