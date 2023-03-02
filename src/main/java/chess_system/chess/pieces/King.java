package chess_system.chess.pieces;

import chess_system.boardgame.Board;
import chess_system.chess.ChessPiece;
import chess_system.chess.Color;

public class King extends ChessPiece {
    public King(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "K";
    }
}
