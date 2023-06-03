import java.awt.*;

public class Board {
    //Number of moves made (starts at 1 for some reason, ask FEN) and half moves (for 50 move rule)
    public static int moveCount = 1, halfMoveCount = 0;
    //En passant destination square
    public static String enPassant = "-";

    // FEN Board Notation : https://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation
    public static String FEN = "2k5/8/8/6R1/8/8/8/3K2r1 w - - 0 1";
    // 2D Array of Pieces representing the board
    public static Piece[][] board = new Piece[8][8];

    // Uses a large switch case to read through the FEN String
    public static void readFEN() {
        board = new Piece[8][8];
        int row = 7;
        int col = 0;
        // Builds board
        for (char c : FEN.toCharArray()) {
            switch (c) {
                case 'r', 'n', 'b', 'q', 'k', 'p' -> {
                    board[row][col] = new Piece(c, COLOR.BLACK, col, row);
                    col++;
                }
                case 'R', 'N', 'B', 'Q', 'K', 'P' -> {
                    board[row][col] = new Piece(c, COLOR.WHITE, col, row);
                    col++;
                }
                case '1', '2', '3', '4', '5', '6', '7', '8' -> col += Integer.parseInt(String.valueOf(c));
                case '/' -> {
                    col = 0;
                    row--;
                }
            }
            if (col > 7 && row <= 0) {
                break;
            }
        }
        draw();
    }
    // Draws the board and pieces
    public static void draw(){
        StdDraw.clear();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                StdDraw.setPenColor(((i + j) % 2 == 1)? new Color(255, 215, 160): new Color(166, 128, 60));
                StdDraw.filledSquare(i, j, 0.5);
                if (board[j][i] != null) { board[j][i].draw();}
            }
        }
        //Titles the STDDraw window with the current turn
        if (FEN.charAt(FEN.indexOf(" ") + 1) == 'w') {
            Game.turn = COLOR.WHITE;
            StdDraw.setTitle("Turn: White");
        } else {
            Game.turn = COLOR.BLACK;
            StdDraw.setTitle("Turn: Black");
        }
        StdDraw.show();
    }
    // Updates the FEN String with the current board state
    public static void updateFEN() {
        String fen = "";
        for (int row = 7; row > -1; row--) {
            int empty = 0;
            for (int col = 0; col < 8; col++) {
                if (board[row][col] == null){
                    empty++;
                } else {
                    if (empty > 0) {
                        fen += empty;
                        empty = 0;
                    }
                    fen += board[row][col].toString();
                }
            }
            if (empty > 0) {
                fen += empty;
            }
            if (row > 0) {
                fen += "/";
            }
        }
        fen += " " + ((Game.turn == COLOR.WHITE)? "w": "b") + " KQkq " + enPassant + " " + halfMoveCount + " " + moveCount;
        FEN = fen;
//        System.out.println(FEN);
    }
}
