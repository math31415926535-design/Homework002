package entity;

public class GameResult {
	private int id;
	private int gameIndex;
	private String playerName1;
	private String playerName2;
	private String winnerName;
	private int roundResult1;
	private int roundResult2;
	private int roundResult3;
	private int roundResult4;
	private int roundResult5;
	private int roundResult6;

	public GameResult() {
		super();
	}

	public GameResult(String playerName1, String playerName2, String winnerName) {
		super();
		this.playerName1 = playerName1;
		this.playerName2 = playerName2;
		this.winnerName = winnerName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGameIndex() {
		return gameIndex;
	}

	public void setGameIndex(int gameIndex) {
		this.gameIndex = gameIndex;
	}

	public String getPlayerName1() {
		return playerName1;
	}

	public void setPlayerName1(String playerName1) {
		this.playerName1 = playerName1;
	}

	public String getPlayerName2() {
		return playerName2;
	}

	public void setPlayerName2(String playerName2) {
		this.playerName2 = playerName2;
	}

	public String getWinnerName() {
		return winnerName;
	}

	public void setWinnerName(String winnerName) {
		this.winnerName = winnerName;
	}

	public int getRoundResult1() {
		return roundResult1;
	}

	public void setRoundResult1(int roundResult1) {
		this.roundResult1 = roundResult1;
	}

	public int getRoundResult2() {
		return roundResult2;
	}

	public void setRoundResult2(int roundResult2) {
		this.roundResult2 = roundResult2;
	}

	public int getRoundResult3() {
		return roundResult3;
	}

	public void setRoundResult3(int roundResult3) {
		this.roundResult3 = roundResult3;
	}

	public int getRoundResult4() {
		return roundResult4;
	}

	public void setRoundResult4(int roundResult4) {
		this.roundResult4 = roundResult4;
	}

	public int getRoundResult5() {
		return roundResult5;
	}

	public void setRoundResult5(int roundResult5) {
		this.roundResult5 = roundResult5;
	}

	public int getRoundResult6() {
		return roundResult6;
	}

	public void setRoundResult6(int roundResult6) {
		this.roundResult6 = roundResult6;
	}
}
