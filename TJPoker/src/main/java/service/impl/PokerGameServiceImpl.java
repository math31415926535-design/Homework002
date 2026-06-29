package service.impl;

import entity.Card;
import entity.Game;
import entity.Round;
import service.PokerGameService;
import util.GameTool;

public class PokerGameServiceImpl implements PokerGameService {
	@Override
	public void autoFillFromRound(Game game, int playerIndex, int roundIndex) {
		GameTool.auto_choose(game.getPlayer()[playerIndex], roundIndex);
	}

	@Override
	public void sortBySuit(Game game, int playerIndex) {
		game.getPlayer()[playerIndex].sort_hand_suits();
	}

	@Override
	public void sortByPoint(Game game, int playerIndex) {
		game.getPlayer()[playerIndex].sort_hand_point();
	}

	@Override
	public int roundIndexOf(Game game, int playerIndex, int cardNumber) {
		return GameTool.round_index_of(game.getPlayer()[playerIndex], cardNumber);
	}

	@Override
	public Card[] cardsOf(Game game, int playerIndex, int roundIndex) {
		return GameTool.cards_of(game.getPlayer()[playerIndex], roundIndex);
	}

	@Override
	public Round evaluate(Card cards[]) {
		return GameTool.evaluate(cards);
	}

	@Override
	public int roundWinner(Game game, int roundIndex) {
		return GameTool.round_winner(game, roundIndex);
	}
}
