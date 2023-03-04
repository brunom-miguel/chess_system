package chess_system.application;

import chess_system.boardgame.Board;
import chess_system.chess.ChessException;
import chess_system.chess.ChessMatch;
import chess_system.chess.ChessPiece;
import chess_system.chess.ChessPosition;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Program {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();

        while (true) {
            try {
                UI.clearScreen();
                UI.printMatch(chessMatch);
                System.out.println();
                System.out.print("Origin: ");
                ChessPosition origin = UI.readChessPosition(sc);

                boolean[][] possibleMoves = chessMatch.possibleMoves(origin);
                UI.clearScreen();
                UI.printBoard(chessMatch.getPieces(), possibleMoves);

                System.out.println();
                System.out.print("Destination: ");
                ChessPosition destination = UI.readChessPosition(sc);

                ChessPiece capturedPiece = chessMatch.performChessMove(origin, destination);
            } catch (ChessException | InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }
    }
}