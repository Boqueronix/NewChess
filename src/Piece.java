import java.awt.*;
import java.util.ArrayList;

public class Piece {
    //Piece type
    public TYPE type;
    //Piece color
    public COLOR color;
    //Piece's column on the board
    public int col;
    //Piece's row on the board
    public int row;
    //Piece's value
    public int value;
    //Constructor for Piece
    public Piece (char t, COLOR c, int col, int row){
        switch (t) {
            case 'r', 'R' -> {
                type = TYPE.ROOK;
                value = 5;
            }
            case 'n', 'N' -> {
                type = TYPE.KNIGHT;
                value = 3;
            }
            case 'b', 'B' -> {
                type = TYPE.BISHOP;
                value = 3;
            }
            case 'q', 'Q' -> {
                type = TYPE.QUEEN;
                value = 9;
            }
            case 'k', 'K' -> {
                type = TYPE.KING;
                value = 1000;
            }
            default -> {
                type = TYPE.PAWN;
                value = 1;
            }
        }
        color = c;
        this.col = col;
        this.row = row;
    }
    //Draws the piece on the board
    public void draw(){
        StdDraw.setPenColor((color == COLOR.WHITE)? Color.RED: Color.BLACK);
        switch (type) {
            case ROOK -> StdDraw.text(col, row, "R");
            case KNIGHT -> StdDraw.text(col, row, "N");
            case BISHOP -> StdDraw.text(col, row, "B");
            case QUEEN -> StdDraw.text(col, row, "Q");
            case KING -> StdDraw.text(col, row, "K");
            case PAWN -> StdDraw.text(col, row, "P");
        }
    }
    //Returns a list of all possible moves for the piece
    public ArrayList<Move> getMoves(){
        ArrayList<Move> moves = new ArrayList<>();
        switch (type) {
            case ROOK -> {
                //Checks for moves to the right
                for (int col = this.col + 1; col < 8; col++) {
                    if (Board.board[row][col] == null) {
                        moves.add(new Move(this, col, row));
                    } else if (Board.board[row][col].color != this.color) {
                        moves.add(new Move(this, col, row));
                        break;
                    } else {
                        break;
                    }
                }
                //Checks for moves to the left
                for (int col = this.col - 1; col > 0; col--) {
                    if (Board.board[row][col] == null) {
                        moves.add(new Move(this, col, row));
                    } else if (Board.board[row][col].color != this.color) {
                        moves.add(new Move(this, col, row));
                        break;
                    } else {
                        break;
                    }
                }
                //Checks for moves upwards
                for (int row = this.row + 1; row < 8; row++) {
                    if (Board.board[row][col] == null) {
                        moves.add(new Move(this, col, row));
                    } else if (Board.board[row][col].color != this.color) {
                        moves.add(new Move(this, col, row));
                        break;
                    } else {
                        break;
                    }
                }
                //Checks for moves downwards
                for (int row = this.row - 1; row > 0; row--) {
                    if (Board.board[row][col] == null) {
                        moves.add(new Move(this, col, row));
                    } else if (Board.board[row][col].color != this.color) {
                        moves.add(new Move(this, col, row));
                        break;
                    } else {
                        break;
                    }
                }
            }
            case KNIGHT -> {
                if ((col + 2 < 8 && row + 1 < 8) && (Board.board[row + 1][col + 2] == null || Board.board[row + 1][col + 2].color != this.color)) {
                    moves.add(new Move(this, col + 2, row + 1));
                }
                if ((col - 2 > -1 && row + 1 < 8) && (Board.board[row + 1][col - 2] == null || Board.board[row + 1][col - 2].color != this.color)) {
                    moves.add(new Move(this, col - 2, row + 1));
                }
                if ((col + 2 < 8 && row - 1 > -1) && (Board.board[row - 1][col + 2] == null || Board.board[row - 1][col + 2].color != this.color)) {
                    moves.add(new Move(this, col + 2, row - 1));
                }
                if ((col - 2 > -1 && row - 1 > 1) && (Board.board[row - 1][col - 2] == null || Board.board[row - 1][col - 2].color != this.color)) {
                    moves.add(new Move(this, col - 2, row - 1));
                }
                if ((col + 1 < 8 && row + 2 < 8) && (Board.board[row + 2][col + 1] == null || Board.board[row + 2][col + 1].color != this.color)) {
                    moves.add(new Move(this, col + 1, row + 2));
                }
                if ((col - 1 > -1 && row + 2 < 8) && (Board.board[row + 2][col - 1] == null || Board.board[row + 2][col - 1].color != this.color)) {
                    moves.add(new Move(this, col - 1, row + 2));
                }
                if ((col + 1 < 8 && row - 2 > -1) && (Board.board[row - 2][col + 1] == null || Board.board[row - 2][col + 1].color != this.color)) {
                    moves.add(new Move(this, col + 1, row - 2));
                }
                if ((col - 1 > -1 && row - 2 > -1) && (Board.board[row - 2][col - 1] == null || Board.board[row - 2][col - 1].color != this.color)) {
                    moves.add(new Move(this, col - 1, row - 2));
                }

            }
            case BISHOP -> {
                // Up Right
                int c = col + 1;
                int r = row + 1;
                while (c < 8 && r < 8) {
                    if (Board.board[r][c] == null) {
                        moves.add(new Move(this, c, r));
                    } else if (Board.board[r][c].color != this.color) {
                        moves.add(new Move(this, c, r));
                        break;
                    } else {
                        break;
                    }
                    c++;
                    r++;
                }
                // Up Left
                c = col - 1;
                r = row + 1;
                while (c >= 0 && r < 8) {
                    if (Board.board[r][c] == null) {
                        moves.add(new Move(this, c, r));
                    } else if (Board.board[r][c].color != this.color) {
                        moves.add(new Move(this, c, r));
                        break;
                    } else {
                        break;
                    }
                    c--;
                    r++;
                }
                // Down Right
                c = col + 1;
                r = row - 1;
                while (c < 8 && r >= 0) {
                    if (Board.board[r][c] == null) {
                        moves.add(new Move(this, c, r));
                    } else if (Board.board[r][c].color != this.color) {
                        moves.add(new Move(this, c, r));
                        break;
                    } else {
                        break;
                    }
                    c++;
                    r--;
                }
                // Down Left
                c = col - 1;
                r = row - 1;
                while (c >= 0 && r >= 0) {
                    if (Board.board[r][c] == null) {
                        moves.add(new Move(this, c, r));
                    } else if (Board.board[r][c].color != this.color) {
                        moves.add(new Move(this, c, r));
                        break;
                    } else {
                        break;
                    }
                    c--;
                    r--;
                }
            }
            case QUEEN -> {
                // Check right
                for (int col = this.col + 1; col < 8; col++) {
                    if (Board.board[row][col] == null) {
                        moves.add(new Move(this, col, row));
                    } else if (Board.board[row][col].color != this.color) {
                        moves.add(new Move(this, col, row));
                        break;
                    } else {
                        break;
                    }
                }
                // Check left
                for (int col = this.col - 1; col > 0; col--) {
                    if (Board.board[row][col] == null) {
                        moves.add(new Move(this, col, row));
                    } else if (Board.board[row][col].color != this.color) {
                        moves.add(new Move(this, col, row));
                        break;
                    } else {
                        break;
                    }
                }
                // Check up
                for (int row = this.row + 1; row < 8; row++) {
                    if (Board.board[row][col] == null) {
                        moves.add(new Move(this, col, row));
                    } else if (Board.board[row][col].color != this.color) {
                        moves.add(new Move(this, col, row));
                        break;
                    } else {
                        break;
                    }
                }
                // Check down
                for (int row = this.row - 1; row > 0; row--) {
                    if (Board.board[row][col] == null) {
                        moves.add(new Move(this, col, row));
                    } else if (Board.board[row][col].color != this.color) {
                        moves.add(new Move(this, col, row));
                        break;
                    } else {
                        break;
                    }
                }
                // Up Right
                int co = col + 1;
                int ro = row + 1;
                while (co < 8 && ro < 8) {
                    if (Board.board[ro][co] == null) {
                        moves.add(new Move(this, co, ro));
                    } else if (Board.board[ro][co].color != this.color) {
                        moves.add(new Move(this, co, ro));
                        break;
                    } else {
                        break;
                    }
                    co++;
                    ro++;
                }
                // Up Left
                co = col - 1;
                ro = row + 1;
                while (co >= 0 && ro < 8) {
                    if (Board.board[ro][co] == null) {
                        moves.add(new Move(this, co, ro));
                    } else if (Board.board[ro][co].color != this.color) {
                        moves.add(new Move(this, co, ro));
                        break;
                    } else {
                        break;
                    }
                    co--;
                    ro++;
                }
                // Down Right
                co = col + 1;
                ro= row - 1;
                while (co < 8 && ro >= 0) {
                    if (Board.board[ro][co] == null) {
                        moves.add(new Move(this, co, ro));
                    } else if (Board.board[ro][co].color != this.color) {
                        moves.add(new Move(this, co, ro));
                        break;
                    } else {
                        break;
                    }
                    co++;
                    ro--;
                }
                // Down Left
                co = col - 1;
                ro = row - 1;
                while (co >= 0 && ro >= 0) {
                    if (Board.board[ro][co] == null) {
                        moves.add(new Move(this, co, ro));
                    } else if (Board.board[ro][co].color != this.color) {
                        moves.add(new Move(this, co, ro));
                        break;
                    } else {
                        break;
                    }
                    co--;
                    ro--;
                }
            }
            case KING -> {
                if (row + 1 < 8 && (Board.board[row + 1][col] == null || Board.board[row + 1][col].color != this.color)) {
                    moves.add(new Move(this, col, row + 1));
                }
                if (row + 1 < 8 && col + 1 < 8 && (Board.board[row + 1][col + 1] == null || Board.board[row + 1][col + 1].color != this.color)) {
                    moves.add(new Move(this, col + 1, row + 1));
                }
                if (col + 1 < 8 && (Board.board[row][col + 1] == null || Board.board[row][col + 1].color != this.color)) {
                    moves.add(new Move(this, col + 1, row));
                }
                if (row - 1 >= 0 && col + 1 < 8 && (Board.board[row - 1][col + 1] == null || Board.board[row - 1][col + 1].color != this.color)) {
                    moves.add(new Move(this, col + 1, row - 1));
                }
                if (row - 1 >= 0 && (Board.board[row - 1][col] == null || Board.board[row - 1][col].color != this.color)) {
                    moves.add(new Move(this, col, row - 1));
                }
                if (row - 1 >= 0 && col - 1 >= 0 && (Board.board[row - 1][col - 1] == null || Board.board[row - 1][col - 1].color != this.color)) {
                    moves.add(new Move(this, col - 1, row - 1));
                }
                if (col - 1 >= 0 && (Board.board[row][col - 1] == null || Board.board[row][col - 1].color != this.color)) {
                    moves.add(new Move(this, col - 1, row));
                }
                if (row + 1 < 8 && col - 1 >= 0 && (Board.board[row + 1][col - 1] == null || Board.board[row + 1][col - 1].color != this.color)) {
                    moves.add(new Move(this, col - 1, row + 1));
                }
            }
            case PAWN -> {
                int enPassantCol = -7;
                int enPassantRow = -7;
                if (!Board.enPassant.equals("-")){
                    enPassantCol = Board.enPassant.charAt(0) - 97;
                    enPassantRow = Integer.parseInt(Board.enPassant.substring(1)) - 1;
//                    System.out.println(enPassantCol + " " + enPassantRow);
                }
                if (color == COLOR.WHITE) {
                    if ((col - 1 == enPassantCol || col + 1 == enPassantCol) && row + 1 == enPassantRow) {
                        moves.add(new Move(this, enPassantCol, enPassantRow, true));
                    }
                    if (row + 1 < 8 && Board.board[row + 1][col] == null) {
                        moves.add(new Move(this, col, row + 1));
                    }
                    if (col - 1 > 0 && Board.board[row + 1][col - 1] != null && Board.board[row + 1][col - 1].color == COLOR.BLACK) {
                        moves.add(new Move(this, col - 1, row + 1));
                    }
                    if (col + 1 < 8 && Board.board[row + 1][col + 1] != null && Board.board[row + 1][col + 1].color == COLOR.BLACK) {
                        moves.add(new Move(this, col + 1, row + 1));
                    }
                    if (row == 1 && Board.board[row + 2][col] == null) {
                        moves.add(new Move(this, col, row + 2));
                    }
                } else {
                    if ((col - 1 == enPassantCol || col + 1 == enPassantCol) && row - 1 == enPassantRow) {
                        moves.add(new Move(this, enPassantCol, enPassantRow, true));
                    }
                    if (row - 1 >= 0 && Board.board[row - 1][col] == null) {
                        moves.add(new Move(this, col, row - 1));
                    }
                    if (col - 1 > 0 && Board.board[row - 1][col - 1] != null && Board.board[row - 1][col - 1].color == COLOR.WHITE) {
                        moves.add(new Move(this, col - 1, row - 1));
                    }
                    if (col + 1 < 8 && Board.board[row - 1][col + 1] != null && Board.board[row - 1][col + 1].color == COLOR.WHITE) {
                        moves.add(new Move(this, col + 1, row - 1));
                    }
                    if (row == 6 && Board.board[row - 2][col] == null) {
                        moves.add(new Move(this, col, row - 2));
                    }
                }
            }
        }
        for (Move move : moves) {
            System.out.println(move);
        }
        return moves;
    }
    //Returns the piece's FEN representation
    public String toString(){
        switch (type) {
            case ROOK -> {
                return (color == COLOR.WHITE)? "R": "r";
            }
            case KNIGHT -> {
                return (color == COLOR.WHITE)? "N": "n";
            }
            case BISHOP -> {
                return (color == COLOR.WHITE)? "B": "b";
            }
            case QUEEN -> {
                return (color == COLOR.WHITE)? "Q": "q";
            }
            case KING -> {
                return (color == COLOR.WHITE)? "K": "k";
            }
            case PAWN -> {
                return (color == COLOR.WHITE)? "P": "p";
            }
        }
        return "";
    }
}
