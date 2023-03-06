package chess_system.chess;

import chess_system.boardgame.Board;
import chess_system.boardgame.Piece;
import chess_system.boardgame.Position;

public abstract class ChessPiece extends Piece {
    private Color color;
    private int moveCount;

    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void increaseMoveCount(){
        moveCount++;
    }

    public void decreaseMoveCount() {
        moveCount--;
    }

    public ChessPosition getChessPosition() {
        return ChessPosition.fromPosition(position);
    }

    // função declarada na classe genérica (e não nas peças em si) pq será reaproveitada em cada peça
    protected boolean isThereOpponentPiece(Position position) {
        ChessPiece piece = (ChessPiece) this.getBoard().piece(position);
        return piece != null && piece.getColor() != this.color; // se true, então a peça é ADVERSÁRIA
    }

}
