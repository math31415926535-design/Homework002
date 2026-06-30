package entity;

public class Game {
	private Card[] deck=new Card[52]; 		// 生成牌堆
	private Player[] player=new Player[2];	// 生成玩家陣列
	private int number=0;					// 這是第幾局
	private static int counter=0;			// 總共玩了幾局
	private int game_index;
	private int current_round=-1;
	private int current_player_index;
	private boolean computer_mode;
	private boolean round_revealed;
	private boolean game_finished;

	private final static String RULE=""
			+ "規則:\n"
			+ "\t從15張手牌中選3/5/5張作為第1/2/3輪的出牌(兩張棄牌)，然後依序比牌型大小\n"
			+ "\t五張牌牌型:\t同花順>鐵支>葫蘆>同花>順子>三條>兩對>一對>高牌\n"
			+ "\t三張牌牌型:\t三條>一對>高牌\n"
			+ "遊戲流程:\n"
			+ "\t遊戲開始階段>玩家一準備階段>玩家一選擇手牌階段>玩家一預覽出牌階段>\n"
			+ "\t玩家二準備階段>玩家二選擇手牌階段>玩家二預覽出牌階段>\n"
			+ "\t準備公布結果階段>結果公布階段";

	public Game()
	{
		player[0]=new Player();
		player[1]=new Player();
	}

	public void start()
	{
		player[0]=new Player();
		player[1]=new Player();
		player[0].setName("玩家一");
		player[1].setName("玩家二");

		createDeck();
		shuffle_02();
		deal();

		counter++;
		number=counter;
		game_index=0;
		current_round=0;
		current_player_index=0;
		round_revealed=false;
		game_finished=false;
	}

	private void createDeck()
	{
		String suits;
		for (int i=0;i<4;i++)
		{
			for(int j=0;j<13;j++)
			{
				switch(i)
				{
					case 0:
						suits ="♠";
						break;
					case 1:
						suits ="♡";
						break;
					case 2:
						suits ="♢";
						break;
					case 3:
						suits ="♣";
						break;
					default:
						suits="error";
				}
				deck[i*13+j]=new Card(suits, j+1, i*13+j);
			}
		}
	}

	// 洗牌(法2) top
	void shuffle_02()
	{
		int random_number;	// 隨機0~51-i的整數
		int step;			// 剩餘步數
		int current;		// 當前位置
		int random_array[]=new int[52];	// 隨機數陣列，0~51的整數順序隨機
		for(int i=0;i<random_array.length;i++) random_array[i]=-1; // 隨機數陣列內全給定初始值-1
		boolean is_placed;	// 第i個數字是否放置完成
		// 開始生成隨機數陣列
		for(int i=0;i<deck.length;i++)
		{
			current=0;			// 將當前位置重置到0
			is_placed=false;	// 數字i尚未放置完成
			random_number=(int)(Math.random()*(52-i));	// 生成隨機數0~51-i
			step=random_number;	// 把得到的隨機數當作步數
			while(!is_placed)	// 如果數字i還沒放好就繼續做
			{
				if(random_array[current]==-1)	// 如果隨機數陣列中當前位置裡是-1，說明這裡還是空的可以放置數字i
				{
					if(step==0)	// 步數為0(腳下的空間又是空的)說明抵達終點
					{
						random_array[current]=i;	// 放下手中的數字，丟進腳下的空間
						is_placed=true;				// 數字i放置完成
					}
					else if(step>0)					// 步數還沒用完
					{
						current+=1;					// 往前走一步
						step-=1;					// 剩餘步數-1
					}
					else
						System.out.println("Error: shuffle_02-step");	// 步數異常
				}
				else current+=1;			// 往前走一步(剩餘步數不減)
			}
		}
		// 隨機數陣列完成
		Card card_tmp=new Card();	// 暫存卡片
		int random_array_tmp;		// 隨機數列暫存數
		boolean is_sorted=false;
		while(!is_sorted)
		{
			is_sorted=true;
			for(int i=0;i<deck.length-1;i++)	// i=0~50
			{
				if(random_array[i]>random_array[i+1])	// 隨機數列中i位置內的數字比下一個大
				{
					// 就跟下一個做交換。同時，牌堆相應位置的卡片也同步作交換
					random_array_tmp=random_array[i];
					card_tmp.copy(deck[i]);
					random_array[i]=random_array[i+1];
					deck[i].copy(deck[i+1]);
					random_array[i+1]=random_array_tmp;
					deck[i+1].copy(card_tmp);
					is_sorted=false;
				}
			}
		}
		for(int i=0;i<deck.length;i++)
		{
			deck[i].setPosition(i);	// 設置卡牌當前位置
		}
	}
	// 洗牌(法2) end

	// 發牌 top
	public void deal()
	{
		for(int i=0;i<30;i++)
		{
			if(i==i>>1<<1) // i是偶數
			{
				player[0].getHand()[i/2].copy(deck[i]);
				player[0].getHand()[i/2].setPlayer(1);
			}
			else if(i!=i>>1<<1)
			{
				player[1].getHand()[i/2].copy(deck[i]);
				player[1].getHand()[i/2].setPlayer(2);
			}
			else
			{
				System.out.println("Error!!!");
			}
		}
	}
	// 發牌 end

	public void nextRound()
	{
		if (current_round < 2)
			current_round++;
		current_player_index = 0;
		round_revealed = false;
	}

	// 計算勝負 top
	public int determine_winner()
	{
		int player_0_wins=0;
		int player_1_wins=0;
		for(int i=0;i<player[0].getRounds().length;i++)
		{
			if(player[0].getRounds()[i].getHand_strength()>player[1].getRounds()[i].getHand_strength())
				player_0_wins+=1;
			else if(player[0].getRounds()[i].getHand_strength()<player[1].getRounds()[i].getHand_strength())
				player_1_wins+=1;
			else
				System.out.println("Error! determine_winner wins");
		}
		if(player_0_wins>player_1_wins)
			return 0;
		else if(player_0_wins<player_1_wins)
			return 1;
		else
			return -1;
	}
	// 計算勝負 end

	// 計算單回合勝負 top
	public int determine_round_winner(int round)
	{
		if(player[0].getRounds()[round].getHand_strength()>player[1].getRounds()[round].getHand_strength())
			return 0;
		else if(player[0].getRounds()[round].getHand_strength()<player[1].getRounds()[round].getHand_strength())
			return 1;
		else
		{
			System.out.println("Error determine_round_winner");
			return -1;
		}
	}
	// 計算單回合勝負 end

	public Card[] getDeck() {
		return deck;
	}
	public void setDeck(Card[] deck) {
		this.deck = deck;
	}

	public Player[] getPlayer() {
		return player;
	}
	public void setPlayer(Player[] player) {
		this.player = player;
	}

	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}

	public static int getCounter() {
		return counter;
	}
	public static void setCounter(int counter) {
		Game.counter = counter;
	}

	public int getGame_index() {
		return game_index;
	}

	public void setGame_index(int game_index) {
		this.game_index = game_index;
	}

	public int getCurrent_round() {
		return current_round;
	}
	public void setCurrent_round(int current_round) {
		this.current_round = current_round;
	}

	public int getCurrent_player_index() {
		return current_player_index;
	}

	public void setCurrent_player_index(int current_player_index) {
		this.current_player_index = current_player_index;
	}

	public boolean isComputer_mode() {
		return computer_mode;
	}

	public void setComputer_mode(boolean computer_mode) {
		this.computer_mode = computer_mode;
	}

	public boolean isRound_revealed() {
		return round_revealed;
	}
	public void setRound_revealed(boolean round_revealed) {
		this.round_revealed = round_revealed;
	}

	public boolean isGame_finished() {
		return game_finished;
	}

	public void setGame_finished(boolean game_finished) {
		this.game_finished = game_finished;
	}

	public static String getRULE() {
		return RULE;
	}
}
