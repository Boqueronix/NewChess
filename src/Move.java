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
                if (col != this.col && row != this.row) {
                    if (Board.board[row][col] != null) {
                        value += ((Board.board[row][col].color == COLOR.WHITE)? 1 : -1 ) * Board.board[row][col].value;
                    }
                }
            }
        }
    }
    //String representation of the move
    public String toString(){
        return piece + " " + col + " " + row + " Value: " + value;
    }
}
