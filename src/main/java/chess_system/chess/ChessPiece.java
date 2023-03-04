package chess_system.chess;

import chess_system.boardgame.Board;
import chess_system.boardgame.Piece;

public class ChessPiece extends Piece {
    private Color color;
    private int moveCount;

    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
