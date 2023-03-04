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

        Position p = new Position(0,0); // auxiliar para ter uma posição inicial

        // acima
        // position está definida em "PIECE" (avó de ROOK)
        p.setValues(this.position.getRow() -1, this.position.getColumn()); //atribuindo a mesma coluna da peça, porém 1 linha a menos (acima)
        while (this.getBoard().positionExist(p) && !getBoard().isThereAPiece(p)) {
            // enquanto a posição existir e NAO tiver uma peça no lugar, marca como VERDADEIRA
            matriz[p.getRow()][p.getColumn()] = true;
            p.setRow(p.getRow() - 1);
        }
        if (this.getBoard().positionExist(p) && this.isThereOpponentPiece(p)) {
            matriz[p.getRow()][p.getColumn()] = true;
        }

        // à esquerda
        // position está definida em "PIECE" (avó de ROOK)
        p.setValues(this.position.getRow(), this.position.getColumn() -1); //atribuindo a mesma linha da peça, porém 1 coluna a menos (esquerda)
        while (this.getBoard().positionExist(p) && !getBoard().isThereAPiece(p)) {
            // enquanto a posição existir e NAO tiver uma peça no lugar, marca como VERDADEIRA
            matriz[p.getRow()][p.getColumn()] = true;
            p.setColumn(p.getColumn() - 1);
        }
        if (this.getBoard().positionExist(p) && this.isThereOpponentPiece(p)) {
            matriz[p.getRow()][p.getColumn()] = true;
        }

        // à direita
        // position está definida em "PIECE" (avó de ROOK)
        p.setValues(this.position.getRow(), this.position.getColumn() +1); //atribuindo a mesma linha da peça, porém 1 coluna a mais (esquerda)
        while (this.getBoard().positionExist(p) && !getBoard().isThereAPiece(p)) {
            // enquanto a posição existir e NAO tiver uma peça no lugar, marca como VERDADEIRA
            matriz[p.getRow()][p.getColumn()] = true;
            p.setColumn(p.getColumn() + 1);
        }
        if (this.getBoard().positionExist(p) && this.isThereOpponentPiece(p)) {
            matriz[p.getRow()][p.getColumn()] = true;
        }

        // abaixo
        // position está definida em "PIECE" (avó de ROOK)
        p.setValues(this.position.getRow() +1, this.position.getColumn()); //atribuindo a mesma coluna da peça, porém 1 linha a menos (acima)
        while (this.getBoard().positionExist(p) && !getBoard().isThereAPiece(p)) {
            // enquanto a posição existir e NAO tiver uma peça no lugar, marca como VERDADEIRA
            matriz[p.getRow()][p.getColumn()] = true;
            p.setRow(p.getRow() + 1);
        }
        if (this.getBoard().positionExist(p) && this.isThereOpponentPiece(p)) {
            matriz[p.getRow()][p.getColumn()] = true;
        }

        return matriz;
    }
}
