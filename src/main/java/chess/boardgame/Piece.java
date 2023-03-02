package chess.boardgame;

public class Piece {
    protected Position position;
    private Board board;

    public Piece(Board board) {
        this.board = board;
    }

    // apenas classes do mesmo pacote ou subclasses podem acessar o `getBoard`
    protected Board getBoard() {
        return board;
    }


}