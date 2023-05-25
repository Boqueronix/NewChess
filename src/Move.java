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
    @Deprecated
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
        value = (piece.color == COLOR.WHITE)? 1 : -1;
        evaluate();
    }
    //Highlight the move (destination square) on the board
    public void highlight(){
        StdDraw.setPenColor((Board.board[row][col] == null)? StdDraw.GREEN: StdDraw.RED);
        StdDraw.square(col, row,  0.49);
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
                    moves.addAll(simBoard[row][col].getMoves());
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
            for (Move move : moves) {
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
