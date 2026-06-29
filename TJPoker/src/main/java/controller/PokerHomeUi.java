package controller;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import entity.Member;
import util.Tool;

public class PokerHomeUi extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private Member loginMember;

    // 主頁 top
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MemberLoginUi().setVisible(true);
            }
        });
    }

    public PokerHomeUi() {
        loginMember=(Member) Tool.readObject("login_member_data.txt");
        Tool.deleteFile("login_member_data.txt");
        setTitle("田忌撲克");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 520, 230);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(227, 252, 255));
        panel.setBounds(0, 0, 504, 191);
        contentPane.add(panel);
        panel.setLayout(null);

        JLabel title = new JLabel("田忌撲克", SwingConstants.CENTER);
        title.setFont(new Font("Monospaced", Font.BOLD, 24));
        title.setBounds(100, 20, 304, 35);
        panel.add(title);

        String name = "訪客";
        if(loginMember!=null) name = loginMember.getName();
        JLabel hint = new JLabel(name + "，請選擇功能", SwingConstants.CENTER);
        hint.setFont(new Font("Monospaced", Font.BOLD, 15));
        hint.setBounds(80, 65, 344, 24);
        panel.add(hint);

        JButton startButton=createHomeButton("開始遊戲", new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Tool.writeObject(loginMember, "login_member_data.txt");
                Tool.writeObject(true, "game_mode_data.txt");
                new PokerGameUi().setVisible(true);
                dispose();
            }
        });
        startButton.setBounds(70, 110, 164, 50);
        panel.add(startButton);

        JButton historyButton=createHomeButton("歷史紀錄", new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Tool.writeObject(loginMember, "login_member_data.txt");
                new GameHistoryUi().setVisible(true);
                dispose();
            }
        });
        historyButton.setBounds(270, 110, 164, 50);
        panel.add(historyButton);
        setResizable(false);
    }

    private JButton createHomeButton(String text, MouseAdapter action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Monospaced", Font.BOLD, 16));
        button.addMouseListener(action);
        return button;
    }
    // 主頁 end
}

