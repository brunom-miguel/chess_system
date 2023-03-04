package chess_system.chess.pieces;

import chess_system.boardgame.Board;
import chess_system.boardgame.Position;
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

    // informa se o REI pode mover para determinada posição
    private boolean canMove(Position position) {
        // pega a peça que está nessa posição
        ChessPiece p = (ChessPiece) this.getBoard().piece(position);
        return p == null || p.getColor() != this.getColor();
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] matriz = new boolean[getBoard().getRows()][getBoard().getColumns()]; // matriz do tamanho do tabuleiro

        Position p = new Position(0,0);

         //acima
        p.setValues(this.position.getRow() -1, this.position.getColumn()); //define a posição ACIMA do REI
        if (getBoard().positionExist(p) && canMove(p)) { // se true, o rei pode mover para a posição P
            matriz[p.getRow()][p.getColumn()] = true;
        }

        //diagonal superior esquerda
        p.setValues(this.position.getRow() -1, this.position.getColumn() -1);
        if (getBoard().positionExist(p) && canMove(p)) {
            matriz[p.getRow()][p.getColumn()] = true;
        }

        //diagonal superior direita
        p.setValues(this.position.getRow() -1, this.position.getColumn() +1);
        if (getBoard().positionExist(p) && canMove(p)) {
            matriz[p.getRow()][p.getColumn()] = true;
        }

        //abaixo
        p.setValues(this.position.getRow() +1, this.position.getColumn());
        if (getBoard().positionExist(p) && canMove(p)) {
            matriz[p.getRow()][p.getColumn()] = true;
        }

        //diagonal inferior esquerda
        p.setValues(this.position.getRow() +1, this.position.getColumn() -1);
        if (getBoard().positionExist(p) && canMove(p)) {
            matriz[p.getRow()][p.getColumn()] = true;
        }

        //diagonal inferior direita
        p.setValues(this.position.getRow() +1, this.position.getColumn() +1);
        if (getBoard().positionExist(p) && canMove(p)) {
            matriz[p.getRow()][p.getColumn()] = true;
        }


        //esquerda
        p.setValues(this.position.getRow(), this.position.getColumn() -1);
        if (getBoard().positionExist(p) && canMove(p)) {
            matriz[p.getRow()][p.getColumn()] = true;
        }

        //direita
        p.setValues(this.position.getRow(), this.position.getColumn() +1);
        if (getBoard().positionExist(p) && canMove(p)) {
            matriz[p.getRow()][p.getColumn()] = true;
        }

        return matriz;
    }
}
