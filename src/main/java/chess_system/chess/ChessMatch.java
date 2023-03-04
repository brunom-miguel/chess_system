package chess_system.chess;

import chess_system.boardgame.Board;
import chess_system.boardgame.Piece;
import chess_system.boardgame.Position;
import chess_system.chess.pieces.King;
import chess_system.chess.pieces.Rook;

public class ChessMatch {
    // vai ter as regras do jogo

    private Board board;

    public ChessMatch() {
        board = new Board(8, 8);
        initialSetup();
    }

    public ChessPiece[][] getPieces() {
        ChessPiece[][] matriz = new ChessPiece[board.getRows()][board.getColumns()];
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                matriz[i][j] = (ChessPiece) board.piece(i, j);
            }
        }
        return matriz;
    }

    public ChessPiece performChessMove(ChessPosition positionOrigin, ChessPosition positionDestination) {
        Position origin = positionOrigin.toPosition();
        Position destination = positionDestination.toPosition();
        validateOriginPosition(origin);
        validateDestinationPosition(origin, destination);
        Piece capturedPiece = makeMove(origin, destination);
        return (ChessPiece) capturedPiece;
    }

    private Piece makeMove(Position origin, Position destination) {
        Piece p = board.removePiece(origin);
        Piece capturedPiece = board.removePiece(destination);
        board.placePiece(p, destination);
        return capturedPiece;
    }

    private void validateOriginPosition(Position position) {
        if (!board.isThereAPiece(position)) {
            throw new ChessException("There is no piece on origin position");
        }
        if (!board.piece(position).isThereAnyPossibleMove()) { // se não tiver nenhum movimento possível
            throw new ChessException("There is no possible moves for the chosen piece");
        }
    }

    private void validateDestinationPosition(Position origin, Position destination) {
        // valida se a posição de DESTINO é um movimento possível em relação à peça na posição de ORIGEM
        if (!this.board.piece(origin).possibleMove(destination)) {
            // valida se para a peça de ORIGEM, a posição de DESTINO não é um movimento possível, então não pode ir para lá
            throw new ChessException("The chosen pieces cannot move to destination position");
        }

    }

    private void placeNewPiece(char column, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
    }

    //inicial a partida, colocando as peças no tabuleiro
    private void initialSetup() {
        placeNewPiece('c',1, new Rook(board, Color.WHITE));
        placeNewPiece('c',2, new Rook(board, Color.WHITE));
        placeNewPiece('d',2, new Rook(board, Color.WHITE));
        placeNewPiece('e',2, new Rook(board, Color.WHITE));
        placeNewPiece('e',1, new Rook(board, Color.WHITE));
        placeNewPiece('d',1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new Rook(board, Color.BLACK));
    }
}
