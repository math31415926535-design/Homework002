package entity;

public class RoundResult {
	private int id;
	private String handtype;
	private String card1;
	private String card2;
	private String card3;
	private String card4;
	private String card5;

	public RoundResult() {
		super();
	}

	public RoundResult(String handtype, Card cards[]) {
		super();
		this.handtype = handtype;
		setCards(cards);
	}

	// 紀錄牌型 top
	private void setCards(Card cards[]) {
		card1 = "";
		card2 = "";
		card3 = "";
		card4 = "";
		card5 = "";
		if (cards.length > 0)
			card1 = cardToString(cards[0]);
		if (cards.length > 1)
			card2 = cardToString(cards[1]);
		if (cards.length > 2)
			card3 = cardToString(cards[2]);
		if (cards.length > 3)
			card4 = cardToString(cards[3]);
		if (cards.length > 4)
			card5 = cardToString(cards[4]);
	}
	// 紀錄牌型 end

	// 還原牌型 top
	public Card[] getCards() {
		int count = 0;
		if (card1 != null && card1.length() > 0)
			count++;
		if (card2 != null && card2.length() > 0)
			count++;
		if (card3 != null && card3.length() > 0)
			count++;
		if (card4 != null && card4.length() > 0)
			count++;
		if (card5 != null && card5.length() > 0)
			count++;

		Card cards[] = new Card[count];
		if (count > 0)
			cards[0] = stringToCard(card1);
		if (count > 1)
			cards[1] = stringToCard(card2);
		if (count > 2)
			cards[2] = stringToCard(card3);
		if (count > 3)
			cards[3] = stringToCard(card4);
		if (count > 4)
			cards[4] = stringToCard(card5);
		return cards;
	}
	// 還原牌型 end

	// Card轉字串
	public String cardToString(Card card) {
		if (card == null)
			return "";
		if (card.getNumber() < 0)
			return "";
		return card.getSuits() + card.getPoint();
	}
	// 字串轉Card
	public Card stringToCard(String text) {
		if (text == null || text.length() < 2)
			return new Card();

		String suits = ""+text.charAt(0);
		String point_text = "";
		for(int i=1;i<text.length();i++)
		{
			point_text+=text.charAt(i);
		}
		int point = Integer.parseInt(point_text);
		int suitIndex = 0;
		switch(suits)
		{
			case "♡":
				suitIndex = 1;
				break;
			case "♢":
				suitIndex = 2;
				break;
			case "♣":
				suitIndex = 3;
				break;
		}

		return new Card(suits, point, suitIndex * 13 + point - 1);
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHandtype() {
		return handtype;
	}

	public void setHandtype(String handtype) {
		this.handtype = handtype;
	}

	public String getCard1() {
		return card1;
	}

	public void setCard1(String card1) {
		this.card1 = card1;
	}

	public String getCard2() {
		return card2;
	}

	public void setCard2(String card2) {
		this.card2 = card2;
	}

	public String getCard3() {
		return card3;
	}

	public void setCard3(String card3) {
		this.card3 = card3;
	}

	public String getCard4() {
		return card4;
	}

	public void setCard4(String card4) {
		this.card4 = card4;
	}

	public String getCard5() {
		return card5;
	}

	public void setCard5(String card5) {
		this.card5 = card5;
	}

}
