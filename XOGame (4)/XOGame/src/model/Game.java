package model;

import com.google.gson.Gson;
import database.DBM;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Game
{
	private int gameId;
	private int player1Id;
	private int player2Id;
	private int winnerId;
	private GameState state = new GameState();

	public Game()
	{
	}

	public int getGameId()
	{
		return gameId;
	}

	public void setGameId(int gameId)
	{
		this.gameId = gameId;
	}

	public int getPlayer1Id()
	{
		return player1Id;
	}

	public void setPlayer1Id(int player1Id)
	{
		this.player1Id = player1Id;
	}

	public int getPlayer2Id()
	{
		return player2Id;
	}

	public void setPlayer2Id(int player2Id)
	{
		this.player2Id = player2Id;
	}

	public int getWinnerId()
	{
		return winnerId;
	}

	public void setWinnerId(int winnerId)
	{
		this.winnerId = winnerId;
	}

	public GameState getState()
	{
		return state;
	}

	public void setState(GameState state)
	{
		this.state = state;
	}

	public class GameState
	{
		public String square1 = "";
		public String square2 = "";
		public String square3 = "";
		public String square4 = "";
		public String square5 = "";
		public String square6 = "";
		public String square7 = "";
		public String square8 = "";
		public String square9 = "";
	}

	public static Game addGame(int player1, int player2)
	{
		int gameId = (int) (Math.random() * 100000);
		DBM.exec("INSERT INTO game (id, f_P_Pid, s_P_Pid) VALUES (?,?,?);", String.valueOf(gameId), String.valueOf(player1), String.valueOf(player2));
		return getGame(gameId);
	}
        
        public static int createNewGagme(int player1, int player2){
            return DBM.execAndGetId("INSERT INTO game (f_P_Pid, s_P_Pid) VALUES (?,?)", ""+player1, ""+player2);
        }

	public static void saveGameState(int gameId, Game.GameState gameState)
	{
		Gson gson = new Gson();
		DBM.exec("UPDATE game SET gameStatus = ? WHERE id= ?", gson.toJson(gameState), String.valueOf(gameId));
	}

	public static boolean isUnfinished(int player1, int player2)
	{
		boolean isFinished = false;
		try
		{
			ResultSet result = DBM.get("SELECT * FROM game WHERE f_P_Pid = ? AND s_P_Pid = ? AND winnerId = NULL;", String.valueOf(player1), String.valueOf(player2));
			if (result.next())
			{
				isFinished = true;
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return isFinished;
	}

	public static Game getUnfinishedGame(int player1, int player2)
	{
		Gson gson = new Gson();
		Game game = null;
		try
		{
			ResultSet result = DBM.get("SELECT * FROM game WHERE f_P_Pid = ? AND s_P_Pid = ? AND winnerId = NULL;", String.valueOf(player1), String.valueOf(player2));
			if (result.next())
			{
				game = new Game();
				game.setGameId(result.getInt("id"));
				game.setPlayer1Id(result.getInt("f_P_Pid"));
				game.setPlayer2Id(result.getInt("s_P_Pid"));
				game.setWinnerId(result.getInt("winnerId"));
				game.setState(gson.fromJson(result.getString("gameStatus"), Game.GameState.class));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return game;
	}

        public static void setWinner(int gameId, int winnerId)
	{
		DBM.exec("UPDATE game SET winnerId = ? AND gameStatus = ? WHERE id = ?;", String.valueOf(winnerId), "{}", String.valueOf(gameId));
	}
	public static ArrayList<Game> getUnfinished()
	{
		Gson gson = new Gson();
		ArrayList<Game> unFinishedGames = new ArrayList<>();
		try
		{
			ResultSet result = DBM.get("SELECT * FROM game WHERE winnerId = NULL;");
			while (result.next())
			{
				Game game = new Game();
				game.setGameId(result.getInt("id"));
				game.setPlayer1Id(result.getInt("f_P_Pid"));
				game.setPlayer2Id(result.getInt("s_P_Pid"));
				game.setWinnerId(result.getInt("winnerId"));
				game.setState(gson.fromJson(result.getString("gameStatus"), Game.GameState.class));
				unFinishedGames.add(game);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return unFinishedGames;
	}

	public static Game getGame(int gameId)
	{
		Gson gson = new Gson();
		Game game = null;
		try
		{
			ResultSet result = DBM.get("SELECT * FROM game WHERE id = ?;", String.valueOf(gameId));
			if (result.next())
			{
				game = new Game();
				game.setGameId(gameId);
				game.setPlayer1Id(result.getInt("f_P_Pid"));
				game.setPlayer2Id(result.getInt("s_P_Pid"));
				game.setWinnerId(result.getInt("winnerId"));
				game.setState(gson.fromJson(result.getString("gameStatus"), Game.GameState.class));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return game;
	}
        
}
