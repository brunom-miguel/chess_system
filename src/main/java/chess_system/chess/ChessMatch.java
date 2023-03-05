package chess_system.chess;

import chess_system.boardgame.Board;
import chess_system.boardgame.Piece;
import chess_system.boardgame.Position;
import chess_system.chess.pieces.King;
import chess_system.chess.pieces.Rook;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChessMatch {
    // vai ter as regras do jogo

    private Board board;
    private int turn;
    private Color currentPlayer;
    private boolean check;

    private List<Piece> piecesOnTheBoard = new ArrayList<>();
    private List<Piece> capturedPieces = new ArrayList<>();

    public ChessMatch() {
        board = new Board(8, 8);
        turn = 1;
        currentPlayer = Color.WHITE;
        check = false;
        initialSetup();
    }

    public int getTurn() {
        return turn;
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
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

    // método para que seja possível imprimir as posições possíveis a partir de uma posição de origem
    public boolean[][] possibleMoves(ChessPosition originPosition) {
        Position position = originPosition.toPosition(); // converte a posição de XADREZ, para uma posição de MATRIZ
        this.validateOriginPosition(position);
        return this.board.piece(position).possibleMoves();
    }

    public ChessPiece performChessMove(ChessPosition positionOrigin, ChessPosition positionDestination) {
        Position origin = positionOrigin.toPosition();
        Position destination = positionDestination.toPosition();
        validateOriginPosition(origin);
        validateDestinationPosition(origin, destination);
        Piece capturedPiece = makeMove(origin, destination);
        nextTurn();
        return (ChessPiece) capturedPiece;
    }

    private Piece makeMove(Position origin, Position destination) {
        Piece p = board.removePiece(origin);
        Piece capturedPiece = board.removePiece(destination);
        board.placePiece(p, destination);

        if (capturedPiece != null) {
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }

        return capturedPiece;
    }

    // desfaz a lógica do "makeMove"
    public void unMakeMove(Position origin, Position destination, Piece capturedPiece) {
        Piece p = board.removePiece(destination);
        board.placePiece(p, origin);

        if (capturedPiece != null) {
            board.placePiece(capturedPiece, destination);
            capturedPieces.remove(capturedPiece);
            piecesOnTheBoard.add(capturedPiece);
        }
    }

    private void validateOriginPosition(Position position) {
        if (!board.isThereAPiece(position)) {
            throw new ChessException("There is no piece on origin position");
        }
        if (currentPlayer != ((ChessPiece)board.piece(position)).getColor()) { // método getColor é da classe mais específica, por isso o downcasting
            throw new ChessException("The chosen piece is not yours");
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

    private void nextTurn() {
        turn++;
        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private Color opponent(Color color) {
        return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private ChessPiece king(Color color) {
        List<Piece> list = piecesOnTheBoard.stream().filter(piece -> ((ChessPiece)piece).getColor() == color).collect(Collectors.toList());

        for (Piece p : list) {
            if (p instanceof King) {
                return (ChessPiece) p;
            }
        }

        throw new IllegalStateException("There is no " + color + " king on the board");
    }

    private void placeNewPiece(char column, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
        piecesOnTheBoard.add(piece);
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
