package chess_system.chess.pieces;

import chess_system.boardgame.Board;
import chess_system.chess.ChessPiece;
import chess_system.chess.Color;

public class Rook extends ChessPiece {


    public Rook(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "R";
    }
}
