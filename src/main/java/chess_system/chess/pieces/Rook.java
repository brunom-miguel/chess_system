package chess_system.chess.pieces;

import chess_system.boardgame.Board;
import chess_system.boardgame.Position;
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

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] matriz = new boolean[getBoard().getRows()][getBoard().getColumns()]; // matriz do tamanho do tabuleiro

        Position auxPosition = new Position(0,0); // auxiliar para ter uma posição inicial

        // acima
        // position está definida em "PIECE" (avó de ROOK)
        auxPosition.setValues(this.position.getRow() -1, this.position.getColumn()); //atribuindo a mesma coluna da peça, porém 1 linha a menos (acima)
        while (this.getBoard().positionExist(auxPosition) && !getBoard().isThereAPiece(auxPosition)) {
            // enquanto a posição existir e NAO tiver uma peça no lugar, marca como VERDADEIRA
            matriz[auxPosition.getRow()][auxPosition.getColumn()] = true;
            auxPosition.setRow(auxPosition.getRow() - 1);
        }
        if (this.getBoard().positionExist(auxPosition) && this.isThereOpponentPiece(auxPosition)) {
            matriz[auxPosition.getRow()][auxPosition.getColumn()] = true;
        }

        // à esquerda
        // position está definida em "PIECE" (avó de ROOK)
        auxPosition.setValues(this.position.getRow(), this.position.getColumn() -1); //atribuindo a mesma linha da peça, porém 1 coluna a menos (esquerda)
        while (this.getBoard().positionExist(auxPosition) && !getBoard().isThereAPiece(auxPosition)) {
            // enquanto a posição existir e NAO tiver uma peça no lugar, marca como VERDADEIRA
            matriz[auxPosition.getRow()][auxPosition.getColumn()] = true;
            auxPosition.setColumn(auxPosition.getColumn() - 1);
        }
        if (this.getBoard().positionExist(auxPosition) && this.isThereOpponentPiece(auxPosition)) {
            matriz[auxPosition.getRow()][auxPosition.getColumn()] = true;
        }

        // à direita
        // position está definida em "PIECE" (avó de ROOK)
        auxPosition.setValues(this.position.getRow(), this.position.getColumn() +1); //atribuindo a mesma linha da peça, porém 1 coluna a mais (esquerda)
        while (this.getBoard().positionExist(auxPosition) && !getBoard().isThereAPiece(auxPosition)) {
            // enquanto a posição existir e NAO tiver uma peça no lugar, marca como VERDADEIRA
            matriz[auxPosition.getRow()][auxPosition.getColumn()] = true;
            auxPosition.setColumn(auxPosition.getColumn() + 1);
        }
        if (this.getBoard().positionExist(auxPosition) && this.isThereOpponentPiece(auxPosition)) {
            matriz[auxPosition.getRow()][auxPosition.getColumn()] = true;
        }

        // abaixo
        // position está definida em "PIECE" (avó de ROOK)
        auxPosition.setValues(this.position.getRow() +1, this.position.getColumn()); //atribuindo a mesma coluna da peça, porém 1 linha a menos (acima)
        while (this.getBoard().positionExist(auxPosition) && !getBoard().isThereAPiece(auxPosition)) {
            // enquanto a posição existir e NAO tiver uma peça no lugar, marca como VERDADEIRA
            matriz[auxPosition.getRow()][auxPosition.getColumn()] = true;
            auxPosition.setRow(auxPosition.getRow() + 1);
        }
        if (this.getBoard().positionExist(auxPosition) && this.isThereOpponentPiece(auxPosition)) {
            matriz[auxPosition.getRow()][auxPosition.getColumn()] = true;
        }

        return matriz;
    }
}
