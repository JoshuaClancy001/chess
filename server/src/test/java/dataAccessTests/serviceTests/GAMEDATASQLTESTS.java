package dataAccessTests.serviceTests;

import chess.*;
import dataAccess.DataAccessException;
import dataAccess.SQLDAO.SQLGAMEDAO;
import model.GameData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class GAMEDATASQLTESTS {

    SQLGAMEDAO dao = new SQLGAMEDAO();

    public GAMEDATASQLTESTS() throws DataAccessException {
    }

    @BeforeEach
    void setUp() throws DataAccessException {
        dao.clearGames();
    }

    @Test
    void positiveAddGame(){
        String actual;
        String expected = "No Error Thrown";
        try {
            dao.addGame("game1");
            actual = "No Error Thrown";
        } catch (DataAccessException e) {
            actual = "Error Thrown: " + e.getMessage();
        }
        Assertions.assertEquals(expected,actual);
    }

    //gameName is null
    @Test
    void negativeAddGame() throws DataAccessException {
        Assertions.assertThrows(DataAccessException.class,()->dao.addGame(null));
    }

    @Test
    void positiveReadGame() throws DataAccessException {
        ArrayList<GameData> expected = new ArrayList<>();
        expected.add(new GameData(1,null,null,"game1",new ChessGame()));
        dao.addGame("game1");
        ArrayList<GameData> actual = dao.readGame();

        Assertions.assertEquals(expected,actual);
    }

    @Test
    void negativeReadGame() throws DataAccessException {
        ArrayList<GameData> expected = new ArrayList<>();
        expected.add(new GameData(1,null,null,"game1",new ChessGame()));
        ArrayList<GameData> actual = dao.readGame();

        Assertions.assertNotEquals(expected,actual);
    }

    @Test
    void positiveReadOneGame() throws DataAccessException {
        dao.addGame("game1");
        GameData expected = new GameData(1,null,null,"game1",new ChessGame());
        GameData actual = dao.readOneGame(1);

        Assertions.assertEquals(expected,actual);
    }

    //gameID doesnt exist
    @Test
    void negativeReadOneGame() throws DataAccessException {
        dao.addGame("game1");
        Assertions.assertThrows(DataAccessException.class,()->dao.readOneGame(4));
    }

    @Test
    void positiveUpdateGame() throws DataAccessException {
        dao.addGame("game1");
        GameData expected = new GameData(1,"username",null,"game1",new ChessGame());
        dao.updateGame("WHITE","username",1);
        GameData actual = dao.readOneGame(1);

        Assertions.assertEquals(expected,actual);
    }

    // no game ID
    @Test
    void negativeUpdateGame(){
        Assertions.assertThrows(DataAccessException.class,()->dao.updateGame(null,"username",1));
    }

    @Test
    void positiveUpdateChessGame() throws InvalidMoveException, DataAccessException {
        ChessGame expected = new ChessGame();
        expected.setTeamTurn(ChessGame.TeamColor.WHITE);
        expected.makeMove(new ChessMove(new ChessPosition(2,1),new ChessPosition(3,1),null));
        dao.addGame("gameID");
        GameData gExpected = new GameData(1,null,null,"game1",expected);
        dao.updateChessGame(1,expected);
        GameData Gactual = dao.readOneGame(1);
        GameData actual = new GameData(1,null,null,"game1",Gactual.getGame());

        Assertions.assertEquals(gExpected,actual);



    }

    //null game
    @Test
    void negativeUpdateChessGame(){
        Assertions.assertThrows(DataAccessException.class,()->dao.updateChessGame(1,null));
    }

    @Test
    void positiveClearGames() throws DataAccessException {
        ArrayList<GameData> expected = new ArrayList<>();
        dao.addGame("game1");
        dao.clearGames();
        ArrayList<GameData> actual = dao.readGame();

        Assertions.assertEquals(expected,actual);
    }
}
