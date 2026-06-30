package entity;

import util.Sort;

public class Player {
	private String name;
	private Card hand[]=new Card[15];
	private Round rounds[]=new Round[3];
	
	public Player()
	{
		super();
		for(int i=0;i<hand.length;i++)
		{
			hand[i]=new Card();
		}
		rounds[0]=new Round(1, 3);
		rounds[1]=new Round(2, 5);
		rounds[2]=new Round(3, 5);
		
	}
	// 整理手牌_花色優先 top
	public void sort_hand_suits()
	{
		Sort.sort_depends_on_number(hand);
	}
	// 整理手牌_花色優先 end
	// 整理手牌_點數優先 top
	public void sort_hand_point()
	{
		Sort.sort_depends_on_point_increasing(hand);
	}
	// 整理手牌_點數優先 end
	// 選擇出牌 top
	public void choose_hand()
	{
		int counter=0;
		for(int i=0;i<rounds.length;i++)
		{
			for(int j=0;j<rounds[i].getRound_hand().length;j++)
			{
				rounds[i].getRound_hand()[j].copy(hand[counter]);
				counter++;
			}
		}
	}
	// 選擇出牌 end
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Card[] getHand() {
		return hand;
	}
	public void setHand(Card[] hand) {
		this.hand = hand;
	}
	public Round[] getRounds() {
		return rounds;
	}
	public void setRounds(Round[] rounds) {
		this.rounds = rounds;
	}
	
	

}

