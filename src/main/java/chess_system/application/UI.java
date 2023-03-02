package chess_system.application;

import chess_system.chess.ChessPiece;

public class UI {

    public static void printBoard(ChessPiece[][] pieces) {
        for (int i = 0; i < pieces.length; i++) {
            //imprime o numero da linha à esquerda
            System.out.print( (8 - i) + " ");

            for (int j = 0; j < pieces.length; j++) {
                //imprime a peça
                printPiece(pieces[i][j]);
            }
            //quebra de linha da matriz
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }

    // metodo auxiliar que imprime uma unica peça
    private static void printPiece(ChessPiece piece) {
        if (piece == null) {
            System.out.print("-");
        }else {
            System.out.print(piece);
        }

        System.out.print(" ");
    }
}
