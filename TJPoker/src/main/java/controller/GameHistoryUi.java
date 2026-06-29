package controller;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import entity.Member;
import service.impl.GameResultServiceImpl;
import util.Tool;

public class GameHistoryUi extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Member loginMember;
	private JTextField playerName;
	private JTextArea resultArea;
	private JButton printButton;
	private GameResultServiceImpl grsi=new GameResultServiceImpl();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new MemberLoginUi().setVisible(true);
			}
		});
	}

	public GameHistoryUi() {
		loginMember=(Member) Tool.readObject("login_member_data.txt");
		Tool.deleteFile("login_member_data.txt");

		setTitle("田忌撲克 - 歷史紀錄");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 60, 760, 600);

		contentPane=new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel=new JPanel();
		panel.setBackground(new Color(163, 216, 220));
		panel.setBounds(0, 0, 744, 561);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel title=new JLabel("歷史紀錄", SwingConstants.CENTER);
		title.setFont(new Font("Monospaced", Font.BOLD, 20));
		title.setBounds(272, 15, 200, 30);
		panel.add(title);

		JLabel nameLabel=new JLabel("玩家名稱");
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setBounds(45, 60, 80, 25);
		panel.add(nameLabel);

		playerName=new JTextField();
		playerName.setBounds(135, 60, 220, 25);
		if(loginMember!=null) playerName.setText(loginMember.getName());
		playerName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) search();
			}
		});
		panel.add(playerName);

		JButton searchButton=new JButton("查詢");
		searchButton.setBounds(375, 60, 87, 25);
		searchButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				search();
			}
		});
		panel.add(searchButton);

		JButton allPlayersButton=new JButton("所有玩家");
		allPlayersButton.setBounds(480, 60, 100, 25);
		allPlayersButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				resultArea.setText(grsi.showAllPlayerNames());
				resultArea.setCaretPosition(0);
				printButton.setEnabled(true);
			}
		});
		panel.add(allPlayersButton);

		// 列印按鈕
		printButton=new JButton("列印");
		printButton.setBounds(647, 15, 87, 25);
		printButton.setEnabled(false);
		printButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					resultArea.print();
				} catch (PrinterException e1) {
					e1.printStackTrace();
				}
			}
		});
		panel.add(printButton);

		JButton backButton=new JButton("返回主頁");
		backButton.setBounds(620, 60, 100, 25);
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Tool.writeObject(loginMember, "login_member_data.txt");
				new PokerHomeUi().setVisible(true);
				dispose();
			}
		});
		panel.add(backButton);

		resultArea=new JTextArea();
		resultArea.setEditable(false);
		resultArea.setFont(new Font("Monospaced", Font.PLAIN, 15));
		resultArea.setBackground(new Color(190, 223, 226));
		resultArea.setLineWrap(true);
		resultArea.setWrapStyleWord(true);

		JScrollPane scrollPane=new JScrollPane(resultArea);
		scrollPane.setBounds(10, 105, 724, 446);
		panel.add(scrollPane);

		setResizable(false);
	}

	// 查詢歷史紀錄 top
	private void search()
	{
		String name=playerName.getText().trim();
		if(name.equals(""))
		{
			JOptionPane.showMessageDialog(this, "請輸入玩家名稱");
			return;
		}

		resultArea.setText(grsi.showByPlayerName(name));
		resultArea.setCaretPosition(0);
		printButton.setEnabled(true);
	}
	// 查詢歷史紀錄 end
}
