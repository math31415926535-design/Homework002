package util;

import entity.Card;
import entity.Game;
import entity.Player;
import entity.Round;

public class GameTool {

	// 自動選牌 top
	public static void auto_choose(Player player, int round_index)
	{
		if(round_index<0 || round_index>=player.getRounds().length) return;

		Round round=player.getRounds()[round_index];
		return_round_cards(player, round);
		int count=round.getRound_hand().length;
		Card hand[]=cards_in_hand(player);
		if(hand.length<count) return;

		Card best[]=find_best_cards(hand, count);
		put_round(player, round_index, best);
	}

	// 自動選牌 end

	// 查詢牌所屬輪次 top
	public static int round_index_of(Player player, int card_number)
	{
		for(int i=0;i<player.getRounds().length;i++)
		{
			Card cards[]=player.getRounds()[i].getRound_hand();
			for(int j=0;j<cards.length;j++)
			{
				if(cards[j].getNumber()==card_number) return i;
			}
		}
		return -1;
	}
	// 查詢牌所屬輪次 end

	// 取得回合出牌 top
	public static Card[] cards_of(Player player, int round_index)
	{
		Card round_hand[]=player.getRounds()[round_index].getRound_hand();
		int count=0;

		for(int i=0;i<round_hand.length;i++)
		{
			if(round_hand[i].getNumber()>=0) count++;
		}

		Card cards[]=new Card[count];
		int index=0;
		for(int i=0;i<round_hand.length;i++)
		{
			if(round_hand[i].getNumber()>=0)
			{
				cards[index]=new Card();
				cards[index].copy(round_hand[i]);
				index++;
			}
		}
		Sort.sort_depends_on_suit_decreasing(cards);
		Sort.sort_depends_on_point_A14_decreasing(cards);
		return cards;
	}
	// 取得回合出牌 end

	// 判斷牌型 top
	public static Round evaluate(Card cards[])
	{
		Player temp_player=new Player();
		Round round;
		if(cards.length==3)
			round=temp_player.getRounds()[0];
		else
			round=temp_player.getRounds()[1];
		for(int i=0;i<cards.length;i++)
		{
			round.getRound_hand()[i].copy(cards[i]);
		}
		round.determine_hand_strength();
		return round;
	}
	// 判斷牌型 end

	// 判斷單輪勝負 top
	public static int round_winner(Game game, int round_index)
	{
		game.getPlayer()[0].getRounds()[round_index].determine_hand_strength();
		game.getPlayer()[1].getRounds()[round_index].determine_hand_strength();
		return game.determine_round_winner(round_index);
	}
	// 判斷單輪勝負 end

	// 退回目前回合的牌 top
	private static int owner_of(Player player)
	{
		for(int i=0;i<player.getHand().length;i++)
		{
			if(player.getHand()[i].getPlayer()>0)
			{
				return player.getHand()[i].getPlayer();
			}
		}
		return 1;
	}

	private static void return_round_cards(Player player, Round round)
	{
		int owner=owner_of(player);
		for(int i=0;i<round.getRound_hand().length;i++)
		{
			int cardNumber=round.getRound_hand()[i].getNumber();
			if(cardNumber>=0)
			{
				for(int j=0;j<player.getHand().length;j++)
				{
					if(player.getHand()[j].getNumber()==cardNumber)
					{
						player.getHand()[j].setPlayer(owner);
						break;
					}
				}
			}
			round.getRound_hand()[i].copy(new Card());
		}
	}
	// 退回目前回合的牌 end

	// 取得剩餘手牌 top
	private static Card[] cards_in_hand(Player player)
	{
		int count=0;
		for(int i=0;i<player.getHand().length;i++)
		{
			if(player.getHand()[i].getPlayer()>0)
			{
				count++;
			}
		}

		Card hand[]=new Card[count];
		int index=0;
		for(int i=0;i<player.getHand().length;i++)
		{
			if(player.getHand()[i].getPlayer()>0)
			{
				hand[index]=player.getHand()[i];
				index++;
			}
		}
		return hand;
	}
	// 取得剩餘手牌 end

	// 找最強牌組 top
	public static Card[] find_best_cards(Card hand[], int count)
	{
		Card current[]=new Card[count];
		Card best[]=new Card[count];
		long best_score[]=new long[] {-1};
		// 評分物件只生成一次
		Player temp_player=new Player();
		Round evaluator;
		if(count==3)
			evaluator=temp_player.getRounds()[0];
		else
			evaluator=temp_player.getRounds()[1];
		find_best_cards(hand, count, 0, 0, current, best, best_score, evaluator);
		return best;
	}

	private static void find_best_cards(Card hand[], int count, int start, int depth,
			Card current[], Card best[], long best_score[], Round evaluator)
	{
		if(depth==count)
		{
			for(int i=0;i<count;i++)
			{
				evaluator.getRound_hand()[i].copy(current[i]);
			}
			evaluator.determine_hand_strength();
			long score=evaluator.getHand_strength();
			if(score>best_score[0])
			{
				best_score[0]=score;
				for(int i=0;i<count;i++)
				{
					best[i]=current[i];
				}
			}
			return;
		}

		for(int i=start;i<=hand.length-(count-depth);i++)
		{
			current[depth]=hand[i];
			find_best_cards(hand, count, i+1, depth+1, current, best, best_score, evaluator);
		}
	}
	// 找最強牌組 end

	// 紀錄自動選牌結果 top
	private static void put_round(Player player, int round_index, Card cards[])
	{
		for(int i=0;i<cards.length;i++)
		{
			player.getRounds()[round_index].getRound_hand()[i].copy(cards[i]);
			player.getRounds()[round_index].getRound_hand()[i].setPlayer(0);
			cards[i].setPlayer(0);
		}
	}
	// 紀錄自動選牌結果 end
}
