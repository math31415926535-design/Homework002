package test_ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import entity.Card;
import entity.Game;
import entity.GameResult;
import entity.Member;
import entity.Player;
import entity.Round;
import entity.RoundResult;
import util.CardIconFactory;
import util.Tool;
import service.impl.PokerGameServiceImpl;
import service.impl.GameResultServiceImpl;

public class PokerGameUi extends JFrame {
    private static final long serialVersionUID = 1L;

    private static final Color BACKGROUND_COLOR = UiTheme.GREEN_DARK;
    private static final Color TEXT_AREA_COLOR = UiTheme.PAPER;
    private static final String BACKGROUND_IMAGE = "poker_table_felt.png";

    private JPanel contentPane;
    private PokerGameServiceImpl pokerService = new PokerGameServiceImpl();
    private GameResultServiceImpl gameResultService = new GameResultServiceImpl();
    private Game game = new Game();
    private JButton cardButtons[] = new JButton[15];
    private Member loginMember;

    private JPanel cardPanel;
    private JPanel roundPanel;
    private JPanel playerPlayPanel;
    private JPanel opponentPlayPanel;
    private JPanel historyPanel;
    private JLabel playerCountLabel;
    private JLabel opponentCountLabel;
    private JLabel playerTypeLabel;
    private JLabel opponentTypeLabel;
    private JLabel titleLabel;
    private JLabel hintLabel;
    private JLabel headerRoundLabel;
    private JLabel headerScoreLabel;
    private JTextArea statusTextArea;
    private JButton confirmButton;
    private JButton gameHistoryPrintButton;
    private Color confirmButtonColor;
    private boolean sortBySuitNext[] = {true, true};
    private boolean recordSaved = false;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                UiTheme.install();
                new MemberLoginUi().setVisible(true);
            }
        });
    }

    public PokerGameUi() {
        UiTheme.install();
        loginMember=(Member) Tool.readObject("login_member_data.txt");
        Boolean mode=(Boolean) Tool.readObject("game_mode_data.txt");
        Tool.deleteFile("login_member_data.txt");
        Tool.deleteFile("game_mode_data.txt");
        game.setComputer_mode(true);
        if(mode!=null) game.setComputer_mode(mode);
        if(game.isComputer_mode()) setTitle("田忌撲克 - 電腦對戰");
        else setTitle("田忌撲克 - 玩家對戰");
        prepareNewGame();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(20, 20, contentWidth() + 44, contentHeight() + 67);

        contentPane = new BackgroundPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setBackground(BACKGROUND_COLOR);
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JPanel gamePanel = new JPanel(null);
        gamePanel.setOpaque(false);
        gamePanel.setBounds(14, 14, contentWidth(), contentHeight());

        JPanel tablePanel = createTablePanel();
        tablePanel.setBounds(0, 0, contentWidth(), tableAreaHeight());
        gamePanel.add(tablePanel);

        JPanel bottomPanel = createBottomPanel();
        bottomPanel.setBounds((contentWidth() - handAreaWidth()) / 2, tableAreaHeight() + 4, handAreaWidth(), bottomAreaHeight());
        gamePanel.add(bottomPanel);

        contentPane.add(gamePanel);

        showNewGame();
        setLocationRelativeTo(null);
        setResizable(false);
    }

    // 範例牌型 top
    private Card[] exampleStraightFlush() {
        Card cards[] = { card("♠", 10), card("♠", 11), card("♠", 12), card("♠", 13), card("♠", 1) };
        return cards;
    }

    private Card[] exampleFourKind() {
        Card cards[] = { card("♠", 9), card("♡", 9), card("♢", 9), card("♣", 9), card("♠", 2) };
        return cards;
    }

    private Card[] exampleFullHouse() {
        Card cards[] = { card("♠", 8), card("♡", 8), card("♢", 8), card("♠", 3), card("♡", 3) };
        return cards;
    }

    private Card[] exampleFlush() {
        Card cards[] = { card("♡", 2), card("♡", 5), card("♡", 8), card("♡", 11), card("♡", 13) };
        return cards;
    }

    private Card[] exampleStraight() {
        Card cards[] = { card("♠", 4), card("♡", 5), card("♢", 6), card("♣", 7), card("♠", 8) };
        return cards;
    }

    private Card[] exampleThreeKind() {
        Card cards[] = { card("♠", 6), card("♡", 6), card("♢", 6), card("♣", 2), card("♠", 10) };
        return cards;
    }

    private Card[] exampleTwoPair() {
        Card cards[] = { card("♠", 5), card("♡", 5), card("♢", 12), card("♣", 12), card("♠", 3) };
        return cards;
    }

    private Card[] exampleOnePair() {
        Card cards[] = { card("♠", 11), card("♡", 11), card("♢", 2), card("♣", 7), card("♠", 13) };
        return cards;
    }

    private Card[] exampleHighCard() {
        Card cards[] = { card("♠", 1), card("♡", 10), card("♢", 7), card("♣", 4), card("♠", 2) };
        return cards;
    }

    private Card[] exampleThreeCardThreeKind() {
        Card cards[] = { card("♠", 6), card("♡", 6), card("♢", 6) };
        return cards;
    }

    private Card[] exampleThreeCardOnePair() {
        Card cards[] = { card("♠", 11), card("♡", 11), card("♢", 2) };
        return cards;
    }

    private Card[] exampleThreeCardHighCard() {
        Card cards[] = { card("♠", 1), card("♡", 10), card("♢", 7) };
        return cards;
    }

    private Card card(String suits, int point) {
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
    // 範例牌型 end

    // 預覽區 top
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(null);
        panel.setOpaque(false);

        JPanel header = createGameHeader();
        header.setBounds(0, 0, contentWidth(), 62);
        panel.add(header);

        roundPanel = new JPanel(null);
        roundPanel.setOpaque(true);
        roundPanel.setBackground(UiTheme.GREEN_DARK);
        JPanel playerBox = createPlayBox(game.getPlayer()[0].getName(), 1);
        JPanel opponentBox = createPlayBox(game.getPlayer()[1].getName(), 2);
        playerBox.setBounds(0, 0, playBoxWidth(), playBoxHeight());
        opponentBox.setBounds(playBoxWidth() + 24, 0, playBoxWidth(), playBoxHeight());
        roundPanel.add(playerBox);
        roundPanel.add(opponentBox);
        
        JPanel messagePanel = createCenterBox();

        String leftName[] = { "同花順", "鐵支", "葫蘆", "同花", "順子", "三條" };
        Card leftCards[][] = { exampleStraightFlush(), exampleFourKind(), exampleFullHouse(),
                exampleFlush(), exampleStraight(), exampleThreeKind() };
        JPanel leftRank = createFixedRankPanel(leftName, leftCards);

        String rightName[] = { "兩對", "一對", "高牌", "三條", "一對", "高牌" };
        Card rightCards[][] = { exampleTwoPair(), exampleOnePair(), exampleHighCard(),
                exampleThreeCardThreeKind(), exampleThreeCardOnePair(), exampleThreeCardHighCard() };
        JPanel rightRank = createFixedRankPanel(rightName, rightCards);

        leftRank.setBounds(0, 76, rankPanelWidth(), rankPanelHeight());
        messagePanel.setBounds((contentWidth() - messageBoxWidth()) / 2, 76, messageBoxWidth(), messageBoxHeight());
        roundPanel.setBounds((contentWidth() - playAreaWidth()) / 2, playAreaY(), playAreaWidth(), playBoxHeight());
        rightRank.setBounds(contentWidth() - rankPanelWidth(), 76, rankPanelWidth(), rankPanelHeight());

        panel.add(leftRank);
        panel.add(messagePanel);
        panel.add(roundPanel);
        panel.add(rightRank);
        return panel;
    }

    private JPanel createGameHeader() {
        JPanel header = new JPanel(null);
        header.setBackground(UiTheme.GREEN_DARK);
        header.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                new LineBorder(new Color(216, 165, 72, 180), 1),
                new EmptyBorder(0, 0, 0, 0)));

        JLabel brand = new JLabel("田忌撲克  /  MATCH TABLE");
        brand.setFont(UiTheme.font(Font.BOLD, 18));
        brand.setForeground(Color.WHITE);
        brand.setBounds(24, 8, 360, 44);
        header.add(brand);

        headerRoundLabel = new JLabel("", SwingConstants.CENTER);
        headerRoundLabel.setFont(UiTheme.font(Font.BOLD, 18));
        headerRoundLabel.setForeground(UiTheme.GOLD);
        headerRoundLabel.setBounds((contentWidth() - 360) / 2, 8, 360, 44);
        header.add(headerRoundLabel);

        headerScoreLabel = new JLabel("", SwingConstants.RIGHT);
        headerScoreLabel.setFont(UiTheme.font(Font.BOLD, 15));
        headerScoreLabel.setForeground(new Color(226, 234, 229));
        headerScoreLabel.setBounds(contentWidth() - 430, 8, 400, 44);
        header.add(headerScoreLabel);
        return header;
    }

    private JPanel createFixedRankPanel(String name[], Card cards[][]) {
        JPanel rank = new JPanel(null);
        rank.setOpaque(false);

        for (int i = 0; i < name.length; i++) {
            JPanel box = new JPanel(null);
            box.setOpaque(true);
            box.setBackground(UiTheme.GREEN_DARK);
            box.setBorder(new LineBorder(UiTheme.GREEN_DARK));
            box.setBounds(4, i * 80, rankPanelWidth() - 8, 76);

            int width = cards[i].length * 36;
            int x = (rankPanelWidth() - 8 - width) / 2;

            JLabel label = new JLabel(name[i], SwingConstants.CENTER);
            label.setFont(new Font("Monospaced", Font.BOLD, 13));
            label.setOpaque(true);
            label.setBackground(UiTheme.GREEN_DARK);
            label.setForeground(Color.WHITE);
            label.setBounds(x, 0, width, 18);

            JPanel cardsPanel = new JPanel(null);
            cardsPanel.setOpaque(true);
            cardsPanel.setBackground(UiTheme.GREEN_DARK);
            cardsPanel.setBounds(x, 18, width, 56);

            for (int j = 0; j < cards[i].length; j++) {
                JLabel cardLabel = new JLabel(CardIconFactory.createExampleCardIcon(cards[i][j]));
                cardLabel.setBounds(j * 36, 2, 36, 52);
                cardsPanel.add(cardLabel);
            }

            box.add(label);
            box.add(cardsPanel);
            rank.add(box);
        }
        return rank;
    }

    private JPanel createCenterBox() {
        JPanel box = new JPanel(null);
        box.setOpaque(false);

        JPanel titleBox = new JPanel(null);
        titleBox.setBackground(new Color(249, 246, 237));
        titleBox.setBorder(new LineBorder(UiTheme.GOLD, 2));
        titleBox.setBounds(0, 0, messageBoxWidth(), 70);

        titleLabel = new JLabel("", SwingConstants.CENTER);
        titleLabel.setForeground(UiTheme.GREEN_DARK);
        titleLabel.setFont(UiTheme.font(Font.BOLD, 26));
        titleLabel.setBounds(150, 4, messageBoxWidth() - 300, 36);

        hintLabel = new JLabel("", SwingConstants.CENTER);
        hintLabel.setFont(UiTheme.font(Font.BOLD, 14));
        hintLabel.setForeground(UiTheme.MUTED);
        hintLabel.setBounds(150, 42, messageBoxWidth() - 300, 22);

        statusTextArea = new JTextArea();
        statusTextArea.setEditable(false);
        statusTextArea.setLineWrap(true);
        statusTextArea.setWrapStyleWord(true);
        statusTextArea.setOpaque(true);
        statusTextArea.setBackground(new Color(249, 246, 237));
        statusTextArea.setFont(UiTheme.font(Font.BOLD, 16));
        statusTextArea.setForeground(UiTheme.INK);
        statusTextArea.setBorder(new EmptyBorder(14, 16, 14, 16));

        JScrollPane statusScroll = new JScrollPane(statusTextArea);
        statusScroll.setBounds(0, 84, 450, 240);
        statusScroll.setBorder(new LineBorder(UiTheme.BORDER));

        historyPanel = new JPanel(null);
        historyPanel.setOpaque(true);
        historyPanel.setBackground(new Color(249, 246, 237));
        historyPanel.setBorder(new LineBorder(UiTheme.BORDER));
        historyPanel.setBounds(590, 84, 450, 240);

        JButton sortButton = createButton("切換排序", new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                switchSort();
            }
        });
        sortButton.setBounds((messageBoxWidth() - 112) / 2, 132, 112, 36);

        confirmButton = UiTheme.primaryButton("確認出牌");
        confirmButton.setFont(UiTheme.font(Font.BOLD, 14));
        confirmButton.setBounds((messageBoxWidth() - 112) / 2, 182, 112, 42);
        confirmButtonColor=confirmButton.getBackground();
        confirmButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                confirmCurrentPlayer();
            }
        });

        // 列印按鈕
        gameHistoryPrintButton = UiTheme.secondaryButton("列印本局");
        gameHistoryPrintButton.setFont(UiTheme.font(Font.BOLD, 13));
        gameHistoryPrintButton.setBounds(messageBoxWidth() - 112, 18, 96, 34);
        gameHistoryPrintButton.setEnabled(false);
        gameHistoryPrintButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                printGameHistory();
            }
        });

        titleBox.add(titleLabel);
        titleBox.add(hintLabel);
        box.add(titleBox);
        box.add(statusScroll);
        box.add(historyPanel);
        box.add(sortButton);
        box.add(confirmButton);
        box.add(gameHistoryPrintButton);
        return box;
    }

    private JPanel createPlayBox(String titleText, int type) {
        JPanel box = new JPanel(null);
        box.setOpaque(true);
        box.setBackground(new Color(249, 246, 237));
        box.setBorder(new LineBorder(type == 1 ? UiTheme.GOLD : new Color(177, 187, 180), 2));

        JPanel information = new JPanel(null);
        information.setBackground(type == 1 ? UiTheme.GREEN : UiTheme.GREEN_DARK);
        information.setBounds(1, 1, playBoxWidth() - 2, 34);

        JLabel title = new JLabel(titleText, SwingConstants.CENTER);
        title.setFont(UiTheme.font(Font.BOLD, 16));
        title.setForeground(Color.WHITE);
        title.setBounds(0, 4, 165, 26);

        JLabel count = new JLabel("0 / " + currentRoundNeed());
        count.setHorizontalAlignment(SwingConstants.CENTER);
        count.setFont(UiTheme.font(Font.BOLD, 15));
        count.setForeground(UiTheme.GOLD);
        count.setBounds(360, 4, 148, 26);
        if(type==1) playerCountLabel = count;
        if(type==2) opponentCountLabel = count;

        JLabel handType = new JLabel("張數不足", SwingConstants.CENTER);
        handType.setFont(UiTheme.font(Font.BOLD, 17));
        handType.setForeground(Color.WHITE);
        handType.setBounds(165, 4, 195, 26);
        if(type==1) playerTypeLabel = handType;
        if(type==2) opponentTypeLabel = handType;

        JPanel preview = new JPanel(null);
        preview.setOpaque(true);
        preview.setBackground(new Color(249, 246, 237));
        preview.setBounds(8, 46, previewAreaWidth(), 140);
        if(type==1) playerPlayPanel = preview;
        if(type==2) opponentPlayPanel = preview;

        information.add(title);
        information.add(handType);
        information.add(count);
        box.add(information);
        box.add(preview);
        return box;
    }
    // 預覽區 end

    // 手牌與按鈕區 top
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(null);
        panel.setOpaque(true);
        panel.setBackground(UiTheme.GREEN_DARK);
        panel.setBorder(new LineBorder(new Color(216, 165, 72, 170)));

        JLabel handTitle = new JLabel("你的手牌  ·  點擊卡牌加入本輪");
        handTitle.setFont(UiTheme.font(Font.BOLD, 15));
        handTitle.setForeground(Color.WHITE);
        handTitle.setBounds(18, 5, 480, 28);

        JPanel cards = createCardPanel();
        cards.setBounds(6, 32, handAreaWidth() - 12, 158);

        JPanel action = createActionPanel();
        action.setBounds(12, 198, handAreaWidth() - 24, 40);

        panel.add(handTitle);
        panel.add(cards);
        panel.add(action);
        return panel;
    }

    private JPanel createCardPanel() {
        cardPanel = new JPanel(null);
        cardPanel.setOpaque(true);
        cardPanel.setBackground(UiTheme.GREEN_DARK);
        return cardPanel;
    }

    private JPanel createActionPanel() {
        JPanel panel = new JPanel(null);
        panel.setOpaque(true);
        panel.setBackground(UiTheme.GREEN_DARK);

        JPanel commandPanel = new JPanel(null);
        commandPanel.setOpaque(true);
        commandPanel.setBackground(UiTheme.GREEN_DARK);
        commandPanel.setBounds(handAreaWidth() - 504, 0, 480, 34);

        JButton clearButton = createButton("清除本輪", new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clearCurrentRoundChoices();
            }
        });
        clearButton.setBounds(0, 0, 110, 32);

        JButton restartButton = createButton("重新開始", new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                startNewGame();
            }
        });
        restartButton.setBounds(120, 0, 110, 32);

        JButton autoButton = createButton("自動選牌", new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                autoChooseBest();
            }
        });
        autoButton.setBounds(240, 0, 110, 32);

        JButton homeButton = createButton("返回主頁", new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Tool.writeObject(loginMember, "login_member_data.txt");
                new PokerHomeUi().setVisible(true);
                dispose();
            }
        });
        homeButton.setBounds(360, 0, 110, 32);

        commandPanel.add(clearButton);
        commandPanel.add(restartButton);
        commandPanel.add(autoButton);
        commandPanel.add(homeButton);

        JLabel tip = new JLabel("先選滿本輪張數，再確認開牌");
        tip.setOpaque(true);
        tip.setBackground(UiTheme.GREEN_DARK);
        tip.setForeground(new Color(226, 234, 229));
        tip.setFont(UiTheme.font(Font.BOLD, 14));
        tip.setBounds(0, 0, 500, 30);

        JLabel timeLabel = new JLabel("");
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeLabel.setFont(UiTheme.font(Font.BOLD, 15));
        timeLabel.setForeground(UiTheme.GOLD);
        timeLabel.setOpaque(true);
        timeLabel.setBackground(UiTheme.GREEN_DARK);
        timeLabel.setBorder(new EmptyBorder(2, 8, 2, 8));
        timeLabel.setBounds((handAreaWidth() - 100) / 2, 0, 100, 30);

        DateTimeFormatter now = DateTimeFormatter.ofPattern("HH:mm:ss");
        timeLabel.setText(LocalDateTime.now().format(now));
        Timer timer = new Timer(1000, e ->timeLabel.setText(LocalDateTime.now().format(now)));
        timer.start();

        panel.add(tip);
        panel.add(timeLabel);
        panel.add(commandPanel);
        return panel;
    }

    private JButton createButton(String text, MouseAdapter action) {
        JButton button = UiTheme.secondaryButton(text);
        button.setFont(UiTheme.font(Font.BOLD, 13));
        button.addMouseListener(action);
        return button;
    }
    // 手牌與按鈕區 end

    // 遊戲流程控制 top
    private void startNewGame() {
        prepareNewGame();
        showNewGame();
    }

    private void prepareNewGame() {
        game.start();
        if(loginMember!=null) game.getPlayer()[0].setName(loginMember.getName());
        if(game.isComputer_mode()) game.getPlayer()[1].setName("電腦");
        else game.getPlayer()[1].setName("玩家二");
        sortBySuitNext[0] = true;
        sortBySuitNext[1] = true;
        recordSaved = false;
    }

    private void showNewGame() {
        statusTextArea.setText("");
        refreshHistoryPanel();
        showConfirmButton();
        confirmButton.setEnabled(true);
        loadCurrentPlayer();
    }

    private void confirmCurrentPlayer() {
        if(game.isGame_finished()) return;

        if(game.isRound_revealed()) {
            nextRoundStart();
            return;
        }

        String error = validateCurrentRound();
        if (error != null) {
            JOptionPane.showMessageDialog(this, error, "選牌尚未完成", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if(!game.isComputer_mode() && game.getCurrent_player_index()==0) {
            cardPanel.removeAll();
            playerPlayPanel.removeAll();
            opponentPlayPanel.removeAll();
            cardPanel.repaint();
            roundPanel.repaint();
            JOptionPane.showMessageDialog(this,
                    game.getPlayer()[0].getName() + "選牌完畢，請"
                    + game.getPlayer()[1].getName() + "準備。",
                    "交換玩家", JOptionPane.INFORMATION_MESSAGE);
            game.setCurrent_player_index(1);
            loadCurrentPlayer();
            return;
        }

        finishCurrentRound();
    }

    private void autoChooseBest() {
        if(game.isGame_finished()) return;
        if(game.isRound_revealed()) return;

        pokerService.autoFillFromRound(game, game.getCurrent_player_index(), game.getCurrent_round());
        refreshView();
    }

    private void finishCurrentRound() {
        if(game.isComputer_mode())
        {
            pokerService.autoFillFromRound(game, 1, game.getCurrent_round());
        }
        int winner = pokerService.roundWinner(game, game.getCurrent_round());
        game.setRound_revealed(true);
        titleLabel.setText(roundLabel(game.getCurrent_round()) + "  比牌結果");
        statusTextArea.setText(roundResultName(game.getCurrent_round()) + " " + winnerName(winner));
        refreshHistoryPanel();

        if(game.getCurrent_round()==2) {
            titleLabel.setText("遊戲結束");
            hintLabel.setText(scoreText());
            statusTextArea.setText(gameResultText());
            game.setGame_finished(true);
            gameHistoryPrintButton.setEnabled(true);
            saveGameRecord();
            confirmButton.setEnabled(false);
            refreshView();
            return;
        }

        hintLabel.setText(scoreText());
        showNextRoundButton();
        refreshView();
    }

    private void saveGameRecord() {
        if(recordSaved) return;

        String winnerName = "平手";
        int winner=game.determine_winner();
        if(winner==0) winnerName = game.getPlayer()[0].getName();
        if(winner==1) winnerName = game.getPlayer()[1].getName();

        GameResult gameResult = new GameResult(
                game.getPlayer()[0].getName(), game.getPlayer()[1].getName(), winnerName);

        RoundResult roundResults[] = new RoundResult[6];
        int index = 0;
        for(int i=0;i<game.getPlayer()[0].getRounds().length;i++)
        {
            Card p1Cards[] = pokerService.cardsOf(game, 0, i);
            Card p2Cards[] = pokerService.cardsOf(game, 1, i);

            roundResults[index] = new RoundResult(
                    game.getPlayer()[0].getRounds()[i].getHand_type(), p1Cards);
            index++;
            roundResults[index] = new RoundResult(
                    game.getPlayer()[1].getRounds()[i].getHand_type(), p2Cards);
            index++;
        }

        int gameResultId = gameResultService.createGameResult(gameResult, roundResults);
        if(gameResultId>0)
        {
            game.setGame_index(gameResult.getGameIndex());
            recordSaved = true;
        }
        else
        {
            statusTextArea.setText(statusTextArea.getText() + "\n本局紀錄儲存失敗");
        }
    }

    private String winnerName(int winner) {
        if(winner==0) return game.getPlayer()[0].getName() + "勝";
        if(winner==1) return game.getPlayer()[1].getName() + "勝";
        return "平手";
    }

    private String gameResultText() {
        String text="";
        for(int i=0;i<game.getPlayer()[0].getRounds().length;i++)
        {
            text+=roundResultName(i) + " " + winnerName(game.determine_round_winner(i)) + "\n";
        }
        text+=game.getPlayer()[0].getName() + winsOfPlayer(0) + "勝\n";
        text+=game.getPlayer()[1].getName() + winsOfPlayer(1) + "勝\n";

        int winner=game.determine_winner();
        if(winner==0) text+="本局勝者：" + game.getPlayer()[0].getName();
        else if(winner==1) text+="本局勝者：" + game.getPlayer()[1].getName();
        else text+="本局勝者：平手";
        return text;
    }

    private String roundResultName(int roundIndex) {
        if(roundIndex==0) return "第一回合";
        if(roundIndex==1) return "第二回合";
        return "第三回合";
    }

    private String scoreText() {
        return game.getPlayer()[0].getName() + " " + winsOfPlayer(0)
                + "勝 / " + game.getPlayer()[1].getName() + " " + winsOfPlayer(1) + "勝";
    }

    private int winsOfPlayer(int playerIndex) {
        int wins=0;
        for(int i=0;i<completedRoundCount();i++)
        {
            if(game.determine_round_winner(i)==playerIndex) wins++;
        }
        return wins;
    }

    private int completedRoundCount() {
        int count=game.getCurrent_round();
        if(game.isRound_revealed()) count++;
        return count;
    }

    private void nextRoundStart() {
        game.nextRound();
        showConfirmButton();
        loadCurrentPlayer();
    }

    private void showConfirmButton() {
        confirmButton.setText("確認出牌");
        confirmButton.setFont(UiTheme.font(Font.BOLD, 14));
        confirmButton.setBackground(UiTheme.GOLD);
        confirmButton.setForeground(UiTheme.GREEN_DARK);
    }

    private void showNextRoundButton() {
        confirmButton.setText("下一回合");
        confirmButton.setFont(UiTheme.font(Font.BOLD, 16));
        confirmButton.setBackground(UiTheme.GOLD);
        confirmButton.setForeground(UiTheme.GREEN_DARK);
    }

    private String validateCurrentRound() {
        int count = roundHandCount();
        if(count!=currentRoundNeed()) {
            return roundLabel(game.getCurrent_round()) + " 需要 " + currentRoundNeed()
                    + " 張，目前是 " + count + " 張。";
        }
        return null;
    }
    // 遊戲流程控制 end

    // 選牌控制 top
    private void switchCardChoice(Card card) {
        if(game.isGame_finished()) return;
        if(game.isRound_revealed()) return;

        int now = pokerService.roundIndexOf(
                game, game.getCurrent_player_index(), card.getNumber());
        if(now!=-1) {
            if(now==game.getCurrent_round()) {
                removeRoundHand(card.getNumber());
                refreshView();
            }
            return;
        }

        if(roundHandCount()>=currentRoundNeed()) {
            removeFirstRoundHand();
        }

        addRoundHand(card);
        refreshView();
    }

    private void clearCurrentRoundChoices() {
        if(game.isRound_revealed()) return;

        clearCurrentRoundHand();
        refreshView();
    }

    private void removePlayCard(Card card) {
        if(game.isGame_finished()) return;
        if(game.isRound_revealed()) return;

        int roundIndex=pokerService.roundIndexOf(
                game, game.getCurrent_player_index(), card.getNumber());
        if(roundIndex!=game.getCurrent_round()) return;
        removeRoundHand(card.getNumber());
        refreshView();
    }

    private void switchSort() {
        if(game.isRound_revealed()) return;

        int playerIndex=game.getCurrent_player_index();
        if (sortBySuitNext[playerIndex]) {
            pokerService.sortBySuit(game, playerIndex);
            sortBySuitNext[playerIndex] = false;
        } else {
            pokerService.sortByPoint(game, playerIndex);
            sortBySuitNext[playerIndex] = true;
        }
        loadCurrentPlayer();
    }

    private void addRoundHand(Card card) {
        Card roundHand[] = currentRoundHand();
        for (int i = 0; i < roundHand.length; i++) {
            if(roundHand[i].getNumber()<0) {
                roundHand[i].copy(card);
                roundHand[i].setPlayer(0);
                card.setPlayer(0);
                return;
            }
        }
    }

    private void removeRoundHand(int cardNumber) {
        Card roundHand[] = currentRoundHand();
        for (int i = 0; i < roundHand.length; i++) {
            if(roundHand[i].getNumber()==cardNumber) {
                returnCardToHand(cardNumber);
                moveRoundHand(i);
                return;
            }
        }
    }

    private void removeFirstRoundHand() {
        removeRoundHand(currentRoundHand()[0].getNumber());
    }

    private void moveRoundHand(int start) {
        Card roundHand[] = currentRoundHand();
        for (int i = start; i < roundHand.length - 1; i++) {
            roundHand[i].copy(roundHand[i + 1]);
        }
        roundHand[roundHand.length - 1].copy(new Card());
    }

    private void clearCurrentRoundHand() {
        Card roundHand[] = currentRoundHand();
        for (int i = 0; i < roundHand.length; i++) {
            if(roundHand[i].getNumber()>=0) returnCardToHand(roundHand[i].getNumber());
            roundHand[i].copy(new Card());
        }
    }

    private void returnCardToHand(int cardNumber) {
        Card hand[]=currentPlayer().getHand();
        for(int i=0;i<hand.length;i++)
        {
            if(hand[i].getNumber()==cardNumber)
            {
                hand[i].setPlayer(game.getCurrent_player_index()+1);
                return;
            }
        }
    }

    private int roundHandCount() {
        int count = 0;
        Card roundHand[] = currentRoundHand();
        for (int i = 0; i < roundHand.length; i++) {
            if(roundHand[i].getNumber()>=0) count++;
        }
        return count;
    }

    private Card[] currentRoundHand() {
        return currentPlayer().getRounds()[game.getCurrent_round()].getRound_hand();
    }

    // 選牌控制 end

    // 畫面更新 top
    private void loadCurrentPlayer() {
        Player p = currentPlayer();
        refreshRoundBox();
        titleLabel.setText(roundLabel(game.getCurrent_round()) + "  " + p.getName());
        hintLabel.setText("點牌出牌，選滿可替換");
        statusTextArea.setText("請選 " + currentRoundNeed() + " 張牌");
        cardPanel.removeAll();

        for (int i = 0; i < p.getHand().length; i++) {
            Card card = p.getHand()[i];
            JButton button = new JButton();
            button.setFont(UiTheme.font(Font.BOLD, 11));
            button.setBounds(2 + i * 96, 16, 92, 136);
            button.setMargin(new Insets(0, 0, 0, 0));
            button.setBorder(new LineBorder(new Color(255, 255, 255, 170), 2));
            button.setVerticalTextPosition(SwingConstants.BOTTOM);
            button.setHorizontalTextPosition(SwingConstants.CENTER);
            button.setFocusPainted(false);
            button.setEnabled(!game.isGame_finished());
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    switchCardChoice(card);
                }
            });
            cardButtons[i] = button;
            cardPanel.add(button);
        }

        refreshView();
        cardPanel.revalidate();
        cardPanel.repaint();
    }

    private void refreshView() {
        refreshGameHeader();
        for (int i = 0; i < cardButtons.length; i++) {
            Card card = currentPlayer().getHand()[i];
            int roundIndex = pokerService.roundIndexOf(
                    game, game.getCurrent_player_index(), card.getNumber());
            cardButtons[i].setBackground(colorOf(roundIndex));
            if(card.getSuits().equals("♡") || card.getSuits().equals("♢")) cardButtons[i].setForeground(Color.RED);
            else cardButtons[i].setForeground(Color.BLACK);
            ImageIcon cardIcon = CardIconFactory.createCardIcon(card);
            cardButtons[i].setIcon(cardIcon);
            cardButtons[i].setDisabledIcon(cardIcon);
            cardButtons[i].setText("");
            cardButtons[i].setEnabled(cardCanClick(roundIndex));
        }

        refreshOnePlayBox(0, playerPlayPanel, playerCountLabel, playerTypeLabel);
        refreshOnePlayBox(1, opponentPlayPanel, opponentCountLabel, opponentTypeLabel);
    }

    private void refreshGameHeader() {
        if(headerRoundLabel==null || headerScoreLabel==null) return;
        if(game.isGame_finished()) {
            headerRoundLabel.setText("對局完成  ·  FINAL");
        } else {
            headerRoundLabel.setText("ROUND " + (game.getCurrent_round() + 1) + " / 3  ·  需選 "
                    + currentRoundNeed() + " 張");
        }
        headerScoreLabel.setText(game.getPlayer()[0].getName() + "  " + winsOfPlayer(0)
                + "  —  " + winsOfPlayer(1) + "  " + game.getPlayer()[1].getName());
    }

    private void refreshOnePlayBox(int index, JPanel panel, JLabel countLabel, JLabel typeLabel) {
        Card cards[] = new Card[0];
        if(canShowPlayerCards(index)) cards = cardsForShow(index);

        refreshPlayCards(index, panel, cards);

        if(canShowPlayerCards(index)) {
            countLabel.setText(cards.length + " / " + currentRoundNeed());
            if(cards.length == currentRoundNeed()) countLabel.setForeground(new Color(32, 120, 70));
            else countLabel.setForeground(new Color(170, 65, 65));
            typeLabel.setText(previewText(game.getCurrent_round(), cards));
        } else {
            if(roundChoiceCount(index)==currentRoundNeed()) countLabel.setText("已選好");
            else countLabel.setText("待開牌");
            countLabel.setForeground(new Color(32, 120, 70));
            typeLabel.setText("--");
        }
    }

    private boolean canShowPlayerCards(int index) {
        if(game.isRound_revealed()) return true;
        if(game.isGame_finished()) return true;
        if(index==game.getCurrent_player_index()) return true;
        return false;
    }

    private Card[] cardsForShow(int index) {
        if(!game.isRound_revealed() && !game.isGame_finished()
                && index==game.getCurrent_player_index()) return playerCardsInOrder();
        return pokerService.cardsOf(game, index, game.getCurrent_round());
    }

    private int roundChoiceCount(int index) {
        return pokerService.cardsOf(game, index, game.getCurrent_round()).length;
    }

    private void refreshRoundBox() {
        roundPanel.removeAll();
        JPanel playerBox = createPlayBox(game.getPlayer()[0].getName(), 1);
        JPanel opponentBox = createPlayBox(game.getPlayer()[1].getName(), 2);
        playerBox.setBounds(0, 0, playBoxWidth(), playBoxHeight());
        opponentBox.setBounds(playBoxWidth() + 24, 0, playBoxWidth(), playBoxHeight());
        roundPanel.add(playerBox);
        roundPanel.add(opponentBox);
        roundPanel.revalidate();
        roundPanel.repaint();
    }

    private void refreshHistoryPanel() {
        if(historyPanel==null) return;
        historyPanel.removeAll();

        int y = 0;
        for(int i=0;i<completedRoundCount();i++)
        {
            JPanel row = createHistoryRow(i);
            row.setBounds(0, y, 450, 78);
            historyPanel.add(row);
            y += 80;
        }

        gameHistoryPrintButton.setEnabled(game.isGame_finished());
        historyPanel.revalidate();
        historyPanel.repaint();
    }

    // 列印歷史紀錄
    private void printGameHistory() {
        if(!game.isGame_finished()) return;

        JTextArea output=new JTextArea(gameHistoryPrintText());
        try {
            output.print();
        } catch (PrinterException e) {
            e.printStackTrace();
        }
    }

    private String gameHistoryPrintText() {
        String text=gameResultText() + "\n\n";
        for(int i=0;i<completedRoundCount();i++)
        {
            Round player1Round=game.getPlayer()[0].getRounds()[i];
            Round player2Round=game.getPlayer()[1].getRounds()[i];
            text+=roundResultName(i) + "\n";
            text+=game.getPlayer()[0].getName() + "：" + player1Round.getHand_type() + "\n";
            text+=player1Round.show_round_hand() + "\n";
            text+=game.getPlayer()[1].getName() + "：" + player2Round.getHand_type() + "\n";
            text+=player2Round.show_round_hand() + "\n\n";
        }
        return text;
    }

    private JPanel createHistoryRow(int roundIndex) {
        JPanel row = new JPanel(null);
        row.setBackground(TEXT_AREA_COLOR);
        row.setBorder(new LineBorder(Color.BLACK));

        Card p1Cards[] = pokerService.cardsOf(game, 0, roundIndex);
        Card p2Cards[] = pokerService.cardsOf(game, 1, roundIndex);

        JLabel label = new JLabel(roundLabel(roundIndex) + "  " + game.getPlayer()[0].getName()
                + ":" + game.getPlayer()[0].getRounds()[roundIndex].getHand_type()
                + " / " + game.getPlayer()[1].getName()
                + ":" + game.getPlayer()[1].getRounds()[roundIndex].getHand_type());
        label.setFont(new Font("Monospaced", Font.BOLD, 12));
        label.setBounds(6, 2, 438, 16);
        row.add(label);

        addSmallCards(row, p1Cards, 5, 210, 20);
        addSmallCards(row, p2Cards, 235, 210, 20);
        return row;
    }

    private void addSmallCards(JPanel panel, Card cards[], int areaX, int areaWidth, int y) {
        int cardWidth = 39;
        int gap = 3;
        int allWidth = cards.length * cardWidth + (cards.length - 1) * gap;
        int startX = areaX + (areaWidth - allWidth) / 2;

        for(int i=0;i<cards.length;i++)
        {
            JLabel label = new JLabel(CardIconFactory.createSmallCardIcon(cards[i]));
            label.setBounds(startX + i * (cardWidth + gap), y, cardWidth, 54);
            panel.add(label);
        }
    }

    private String previewText(int roundIndex, Card cards[]) {
        if (cards.length == roundNeed(roundIndex)) {
            Round result = pokerService.evaluate(cards);
            return result.getHand_type();
        }
        return "張數不足";
    }

    private void refreshPlayCards(int index, JPanel panel, Card cards[]) {
        panel.removeAll();

        for (int i = 0; i < currentRoundNeed(); i++) {
            JButton button = new JButton();
            int allWidth = currentRoundNeed() * 92 + (currentRoundNeed() - 1) * 6;
            int startX = (previewAreaWidth() - allWidth) / 2;
            button.setBounds(startX + i * 98, 0, 92, 136);
            button.setMargin(new Insets(0, 0, 0, 0));
            button.setFocusPainted(false);
            button.setBorder(new LineBorder(Color.BLACK));
            button.setBackground(colorOf(game.getCurrent_round()));
            if(i<cards.length) {
                Card card = cards[i];
                button.setIcon(CardIconFactory.createCardIcon(card));
                if(index==game.getCurrent_player_index()
                        && !game.isRound_revealed() && !game.isGame_finished()) {
                    button.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            removePlayCard(card);
                        }
                    });
                }
            }
            panel.add(button);
        }

        panel.revalidate();
        panel.repaint();
    }

    private Card[] playerCardsInOrder() {
        Card roundHand[] = currentRoundHand();
        Card cards[] = new Card[roundHandCount()];
        for (int i = 0; i < cards.length; i++) {
            cards[i] = roundHand[i];
        }
        return cards;
    }

    private int playBoxWidth() {
        return 5 * 98 + 18;
    }

    private int previewAreaWidth() {
        return playBoxWidth() - 16;
    }

    private int playAreaWidth() {
        return playBoxWidth() * 2 + 24;
    }

    private int handAreaWidth() {
        return 1452;
    }

    private int rankPanelWidth() {
        return 190;
    }

    private int rankPanelHeight() {
        return 476;
    }

    private int tableAreaHeight() {
        return 620;
    }

    private int playAreaY() {
        return 414;
    }

    private int playBoxHeight() {
        return 202;
    }

    private int messageBoxWidth() {
        return 1040;
    }

    private int messageBoxHeight() {
        return 330;
    }

    private int bottomAreaHeight() {
        return 244;
    }

    private int tableAreaWidth() {
        int widthByPlay = playAreaWidth() + rankPanelWidth() * 2 + 40;
        int widthByMessage = messageBoxWidth() + rankPanelWidth() * 2 + 40;
        if(widthByPlay>widthByMessage) return widthByPlay;
        return widthByMessage;
    }

    private int contentWidth() {
        if(tableAreaWidth()>handAreaWidth()) return tableAreaWidth();
        return handAreaWidth();
    }

    private int contentHeight() {
        return tableAreaHeight() + 8 + bottomAreaHeight();
    }

    private class BackgroundPanel extends JPanel {
        private static final long serialVersionUID = 1L;
        private Image backgroundImage;

        public BackgroundPanel() {
            java.net.URL backgroundUrl = getClass().getResource("/picture/" + BACKGROUND_IMAGE);
            ImageIcon backgroundIcon = null;
            if(backgroundUrl!=null)
            {
                backgroundIcon = new ImageIcon(backgroundUrl);
            }

            File file = new File("picture/" + BACKGROUND_IMAGE);
            if(backgroundIcon==null && file.exists())
            {
                backgroundIcon = new ImageIcon(file.getPath());
            }

            if(backgroundIcon!=null)
            {
                backgroundImage = backgroundIcon.getImage();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if(backgroundImage!=null)
            {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    private Player currentPlayer() {
        return game.getPlayer()[game.getCurrent_player_index()];
    }

    private int roundNeed(int roundIndex) {
        return game.getPlayer()[0].getRounds()[roundIndex].getRound_hand().length;
    }

    private int currentRoundNeed() {
        return roundNeed(game.getCurrent_round());
    }

    private String roundLabel(int roundIndex) {
        return "第" + (roundIndex + 1) + "輪";
    }

    private Color colorOf(int roundIndex) {
        if (roundIndex == 0) return new Color(228, 199, 132);
        if (roundIndex == 1) return new Color(175, 207, 183);
        if (roundIndex == 2) return new Color(224, 169, 142);
        return UiTheme.CREAM;
    }

    private boolean cardCanClick(int roundIndex) {
        if(game.isGame_finished()) return false;
        if(game.isRound_revealed()) return false;
        if(roundIndex==-1) return true;
        if(roundIndex==game.getCurrent_round()) return true;
        return false;
    }
    // 畫面更新 end
}

