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
    private boolean checkmate;

    private List<Piece> piecesOnTheBoard = new ArrayList<>();
    private List<Piece> capturedPieces = new ArrayList<>();

    public ChessMatch() {
        board = new Board(8, 8);
        turn = 1;
        currentPlayer = Color.WHITE;
        check = false;
        initialSetup();
    }

    public boolean isCheckmate() {
        return checkmate;
    }

    public int getTurn() {
        return turn;
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isCheck() {
        return check;
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

        if (testCheck(currentPlayer)) {
            unMakeMove(origin, destination, capturedPiece);
            throw new ChessException("You cannot put yourself in check");
        }

        check = (testCheck(opponent(currentPlayer))) ? true : false;

        if (testCheckmate(opponent(currentPlayer))) { // testa se o oponente ficou em checkmate
            checkmate = true;
        } else {
            nextTurn();
        }

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

    private boolean testCheck(Color color) { // color como parametro pois queremos saber se o rei de tal cor esta em check
        Position kingPosition = king(color).getChessPosition().toPosition(); // pego a posição do rei da cor X em posição de matriz
        List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(piece -> ((ChessPiece)piece).getColor() == opponent(color)).collect(Collectors.toList()); // lista das peças do oponente dessa cor

        for (Piece p : opponentPieces) {
            boolean[][] mat = p.possibleMoves(); // matriz de movimentos possiveis da peça
            if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
                return true;
            }
        }
        return false;
    }

    private boolean testCheckmate(Color color) {
        if (!testCheck(color)) {
            return false;
        }

        // a lista vai ter todas as peças dessa cor
        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
        for (Piece p : list) {
            boolean[][] mat = p.possibleMoves();
            for (int i=0; i<board.getRows(); i++) {
                for (int j=0; j<board.getColumns(); j++) {
                    if (mat[i][j]) {
                        Position origin = ((ChessPiece)p).getChessPosition().toPosition(); // posição da peça p
                        Position destination = new Position(i, j);
                        Piece capturedPiece = makeMove(origin, destination);
                        boolean testCheck = testCheck(color);
                        unMakeMove(origin, destination, capturedPiece);
                        if (!testCheck) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private void placeNewPiece(char column, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
        piecesOnTheBoard.add(piece);
    }

    //inicial a partida, colocando as peças no tabuleiro
    private void initialSetup() {
        placeNewPiece('h', 7, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE));

        placeNewPiece('b', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 8, new King(board, Color.BLACK));
    }
}
