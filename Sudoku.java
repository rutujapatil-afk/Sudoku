import java.util.Random;
import java.util.Scanner;

public class Sudoku {
  
    private static final int GRID_SIZE = 9;
    private static final int EMPTY_CELL = 0;
  
    public static void main(String[] args) {
        int[][] board = generateRandomBoard();
      
        printBoard(board);
        solveByUser(board);
    }
    
    private static int[][] generateRandomBoard() {
        int[][] board = new int[GRID_SIZE][GRID_SIZE];
        solveBoard(board);
        removeCells(board, 40); // Adjust the number of cells to remove for desired difficulty
        return board;
    }
    
    private static void removeCells(int[][] board, int numToRemove) {
        Random random = new Random();
        int count = 0;
        while (count < numToRemove) {
            int row = random.nextInt(GRID_SIZE);
            int col = random.nextInt(GRID_SIZE);
            if (board[row][col] != EMPTY_CELL) {
                board[row][col] = EMPTY_CELL;
                count++;
            }
        }
    }
    
    private static boolean solveBoard(int[][] board) {
        return solveBoardHelper(board, 0, 0);
    }
    
    private static boolean solveBoardHelper(int[][] board, int row, int col) {
        if (row == GRID_SIZE) {
            row = 0;
            if (++col == GRID_SIZE) {
                return true;
            }
        }
        
        if (board[row][col] != EMPTY_CELL) {
            return solveBoardHelper(board, row + 1, col);
        }
        
        for (int num = 1; num <= GRID_SIZE; num++) {
            if (isValidPlacement(board, num, row, col)) {
                board[row][col] = num;
                if (solveBoardHelper(board, row + 1, col)) {
                    return true;
                }
                board[row][col] = EMPTY_CELL;
            }
        }
        
        return false;
    }
    
    private static void printBoard(int[][] board) {
        for (int row = 0; row < GRID_SIZE; row++) {
            if (row % 3 == 0 && row != 0) {
                System.out.println("---------------------");
            }
            for (int column = 0; column < GRID_SIZE; column++) {
                if (column % 3 == 0 && column != 0) {
                    System.out.print("| ");
                }
                if (board[row][column] == EMPTY_CELL) {
                    System.out.print("  ");
                } else {
                    System.out.print(board[row][column] + " ");
                }
            }
            System.out.println();
        }
    }
    
    private static boolean isValidPlacement(int[][] board, int number, int row, int column) {
        for (int i = 0; i < GRID_SIZE; i++) {
            if (board[row][i] == number || board[i][column] == number) {
                return false;
            }
        }
        
        int localBoxRow = row - row % 3;
        int localBoxColumn = column - column % 3;
      
        for (int i = localBoxRow; i < localBoxRow + 3; i++) {
            for (int j = localBoxColumn; j < localBoxColumn + 3; j++) {
                if (board[i][j] == number) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    private static boolean solveByUser(int[][] board) {
        Scanner scanner = new Scanner(System.in);
        while (!isSolved(board)) {
            System.out.print("Enter row (1-9), column (1-9), and value (1-9) to place: ");
            int row = scanner.nextInt() - 1;
            int col = scanner.nextInt() - 1;
            int value = scanner.nextInt();

            if (row < 0 || row >= GRID_SIZE || col < 0 || col >= GRID_SIZE || value < 1 || value > 9) {
                System.out.println("Invalid input. Please try again.");
                continue;
            }

            if (board[row][col] != EMPTY_CELL) {
                System.out.println("Cell already filled. Please try again.");
                continue;
            }

            if (!isValidPlacement(board, value, row, col)) {
                System.out.println("Invalid move. Please try again.");
                continue;
            }

            board[row][col] = value;
            printBoard(board);
        }

        System.out.println("Congratulations! You solved the Sudoku puzzle!");
        scanner.close();
        return true;
    }
    
    private static boolean isSolved(int[][] board) {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (board[i][j] == EMPTY_CELL) {
                    return false;
                }
            }
        }
        return true;
    }
}
