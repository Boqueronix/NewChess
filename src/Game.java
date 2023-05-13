import java.util.ArrayList;

public class Game {
    //is the game running?
    public static boolean gameOn = true;
    //is the mouse pressed?
    public static boolean mousePressed = false;
    //Active piece
    public static Piece selected;
    //Active piece's moves
    public static ArrayList<Move> moves = new ArrayList<>();
    //Active player
    public static COLOR turn = COLOR.WHITE;
    public static void main(String[] args) {
        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-0.5,7.5);
        Board.readFEN();
        // active run
        while (gameOn) {
            if (StdDraw.isMousePressed() && !mousePressed) {
                mousePressed = true;
                if (selected == null) {
                    select(StdDraw.mouseX(), StdDraw.mouseY());
                } else {
                    attempt(StdDraw.mouseX(), StdDraw.mouseY());
                }
            } else if (!StdDraw.isMousePressed() && mousePressed) {
                mousePressed = false;
            }
        }
    }
    public static void select(double x, double y) {
        //If square is not empty select piece
        if (Board.board[(int) (y + 0.5)][(int) (x + 0.5)] != null) {
            selected = Board.board[(int) (y + 0.5)][(int) (x + 0.5)];
            //Show possible moves
            moves = selected.getMoves();
            for (Move move : moves) {
                move.highlight();
                StdDraw.show();
            }
        }
    }
    public static boolean attempt(double x, double y){
        //If it's that player's turn and the attempt is within the list of possible moves, move the piece
        if (selected.color == turn) {
            for (Move move : moves) {
                if (move.col == (int) (x + 0.5) && move.row == (int) (y + 0.5)) {
                    move(move);
                    return true;
                }
            }
            System.out.println("Deselected");
        } else {
            System.out.println("Not your turn");
        }
        //Deselect piece if move fails
        selected = null;
        moves = new ArrayList<>();
        Board.draw();
        return false;
    }
    public static void move(Move move){
        System.out.println(move.value);
        //Update move count
        Board.moveCount++;
        //Update half move count for 50 move rule
        if (Board.board[move.row][move.col] != null || selected.type == TYPE.PAWN) {
            Board.halfMoveCount = 0;
        } else {
            Board.halfMoveCount++;
            if (Board.halfMoveCount >= 50) {
                System.out.println("Draw by 50 move rule");
                gameOn = false;
            }
        }
        //Check for en passant
        if (selected.type == TYPE.PAWN && Math.abs(move.row - selected.row) == 2) {
            Board.enPassant = (char) (move.col + 'a') + String.valueOf(1 + (move.row + selected.row) /2);
        } else {
            Board.enPassant = "-";
        }
        //Move piece
        Board.board[move.row][move.col] = selected;
        Board.board[selected.row][selected.col] = null;
        selected.col = move.col;
        selected.row = move.row;
        if (move.enPassant) {
            Board.board[move.row + ((selected.color == COLOR.WHITE)? -1: 1)][move.col] = null;
        }
        //Deselect piece
        selected = null;
        moves = new ArrayList<>();
        //Switch turn
        turn = (turn == COLOR.WHITE)? COLOR.BLACK: COLOR.WHITE;
        //Update FEN and draw
        Board.updateFEN();
        Board.draw();
    }
}