package service;

import entity.Card;
import entity.Game;
import entity.Round;

public interface PokerGameService {
	void autoFillFromRound(Game game, int playerIndex, int roundIndex);
	void sortBySuit(Game game, int playerIndex);
	void sortByPoint(Game game, int playerIndex);
	int roundIndexOf(Game game, int playerIndex, int cardNumber);
	Card[] cardsOf(Game game, int playerIndex, int roundIndex);
	Round evaluate(Card cards[]);
	int roundWinner(Game game, int roundIndex);
}

