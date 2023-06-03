import java.util.ArrayList;
import java.util.HashMap;

public class Move {
    //Moving piece
    public Piece piece;
    //Piece's destination column
    public int col;
    //Piece's destination row
    public int row;
    //Is the move possible?
    public boolean possible;
    //Is the move an en passant?
    public boolean enPassant;
    //Evaluate the move
    public int value;
    public int futureValue;
    //Constructor for Move
    public Move(Piece p, int col, int row){
        piece = p;
        this.col = col;
        this.row = row;
        evaluate();
    }
    public Move(Piece p, int col, int row, boolean eP){
        piece = p;
        this.col = col;
        this.row = row;
        enPassant = eP;
        evaluate();
    }

    //Highlight the move (destination square) on the board
     public void highlight(){
        StdDraw.setPenColor((Board.board[row][col] == null)? StdDraw.GREEN: StdDraw.RED);
        StdDraw.square(col, row,  0.49);
    }
    public boolean isCheck() {
        Piece[][] simBoard = new Piece[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                simBoard[row][col] = Board.board[row][col];
            }
        }
        simBoard[row][col] = piece;
        simBoard[piece.row][piece.col] = null;
        if (enPassant) {
            simBoard[row + ((piece.color == COLOR.WHITE)? -1: 1)][col] = null;
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print("[" + simBoard[i][j] + "]");
            }
            System.out.println();
        }



        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (simBoard[row][col] != null && simBoard[row][col].color != piece.color) {
                    ArrayList<Move> moves = simBoard[row][col].getMoves(simBoard);
                    for (Move move: moves) {
                        System.out.println(move);
                        if (simBoard[move.row][move.col] != null && simBoard[move.row][move.col].type == TYPE.KING && simBoard[move.row][move.col].color == piece.color) {
                            System.out.println("Check");
                            return false; // The move results in a check
                        }
                    }
                }
            }
        }
        System.out.println("No Check");
        return true; // The move does not result in a check
    }
    //Evaluate the move
    public void evaluate(){

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (!(col == this.col && row == this.row)) {
                    if (Board.board[row][col] != null) {
                        value += ((Board.board[row][col].color == COLOR.WHITE)? 1 : -1 ) * Board.board[row][col].value;
                    }
                }
            }
        }
    }
    public int genFutureValue(Piece[][] board, int depth){
        if (depth == 0) {
            return value;
        }
        ArrayList<Move> moves = new ArrayList<>();
        HashMap<Move, Integer> moveValues = new HashMap<>();
        Piece[][] simBoard = new Piece[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                simBoard[row][col] = board[row][col];
            }
        }
        simBoard[row][col] = piece;
        simBoard[piece.row][piece.col] = null;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (!(simBoard[row][col] == null || simBoard[row][col].color == piece.color)) {
                    moves.addAll(simBoard[row][col].getMoves(simBoard));
                }
            }
        }
        for (Move move : moves) {
            moveValues.put(move, move.genFutureValue(simBoard, depth - 1));
        }
        if (piece.color == COLOR.WHITE) {
            int max = Integer.MIN_VALUE;
            for (Move move : moves) {
                if (moveValues.get(move) > max) {
                    max = moveValues.get(move);
                }
            }
            return max;
        } else {
            int min = Integer.MAX_VALUE;
            for (Move move : moves){
                if (moveValues.get(move) < min) {
                    min = moveValues.get(move);
                }
            }
            return min;
        }
    }
    //String representation of the move
    public String toString(){
        return piece + " " + col + " " + row + " Value: " + value;
    }
}
