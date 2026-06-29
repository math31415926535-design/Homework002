package util;

import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;

import entity.Card;

public class CardIconFactory {
    private static ImageIcon cardIcons[] = new ImageIcon[52];
    private static ImageIcon smallCardIcons[] = new ImageIcon[52];
    private static ImageIcon exampleCardIcons[] = new ImageIcon[52];

    // 產生手牌圖示
    public static ImageIcon createCardIcon(Card card) {
        if(cardIcons[card.getNumber()]!=null) return cardIcons[card.getNumber()];

        ImageIcon icon = readCardIcon(card, 88, 128);
        cardIcons[card.getNumber()] = icon;
        return icon;
    }
    // 產生歷史紀錄小圖
    public static ImageIcon createSmallCardIcon(Card card) {
        if(smallCardIcons[card.getNumber()]!=null) return smallCardIcons[card.getNumber()];

        ImageIcon icon = readCardIcon(card, 39, 54);
        smallCardIcons[card.getNumber()] = icon;
        return icon;
    }
    // 產生範例牌型圖
    public static ImageIcon createExampleCardIcon(Card card) {
        if(exampleCardIcons[card.getNumber()]!=null) return exampleCardIcons[card.getNumber()];

        ImageIcon icon = readCardIcon(card, 36, 52);
        exampleCardIcons[card.getNumber()] = icon;
        return icon;
    }
    // 讀取牌圖
    private static ImageIcon readCardIcon(Card card, int width, int height) {
        String fileName = pointCode(card) + "_of_" + suitCode(card) + ".png";
        String resourceName = "/picture/cards_classic/" + fileName;
        java.net.URL imageUrl = CardIconFactory.class.getResource(resourceName);
        ImageIcon source = null;

        if(imageUrl!=null)
        {
            source = new ImageIcon(imageUrl);
        }

        File file = new File("picture/cards_classic/" + fileName);
        if(source==null && file.exists())
        {
            source = new ImageIcon(file.getPath());
        }

        if(source==null) return new ImageIcon();
        Image image = source.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }
    // 花色轉檔名
    private static String suitCode(Card card) {
        switch(card.getSuits())
        {
            case "♠":
                return "spades";
            case "♣":
                return "clubs";
            case "♡":
                return "hearts";
            case "♢":
                return "diamonds";
            default:
                return "spades";
        }
    }
    // 點數轉檔名
    private static String pointCode(Card card) {
        if(card.getPoint()==1) return "ace";
        if(card.getPoint()==11) return "jack";
        if(card.getPoint()==12) return "queen";
        if(card.getPoint()==13) return "king";
        return "" + card.getPoint();
    }
}


