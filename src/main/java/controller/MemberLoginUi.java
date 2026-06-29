package controller;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import entity.Member;
import service.impl.MemberServiceImpl;
import util.Tool;

public class MemberLoginUi extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField username;
	private JTextField password;
	private MemberServiceImpl msi=new MemberServiceImpl();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MemberLoginUi frame = new MemberLoginUi();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MemberLoginUi() {
		setTitle("田忌撲克 - 登入");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 347, 260);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(227, 252, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(227, 252, 255));
		panel.setBounds(0, 0, 331, 221);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel title = new JLabel("田忌撲克登入");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setBounds(100, 35, 130, 23);
		panel.add(title);

		JLabel usernameLabel = new JLabel("帳號");
		usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		usernameLabel.setBounds(47, 80, 46, 15);
		panel.add(usernameLabel);

		JLabel passwordLabel = new JLabel("密碼");
		passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		passwordLabel.setBounds(47, 120, 46, 15);
		panel.add(passwordLabel);

		username = new JTextField();
		username.setBounds(140, 80, 96, 21);
		panel.add(username);
		username.setColumns(10);

		password = new JTextField();
		password.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) login();
			}
		});
		password.setBounds(140, 120, 96, 21);
		panel.add(password);
		password.setColumns(10);

		JButton login = new JButton("登入");
		login.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				login();
			}
		});
		login.setBounds(80, 170, 87, 23);
		panel.add(login);

		JButton signUp = new JButton("註冊");
		signUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MemberSignUpUi msuu=new MemberSignUpUi();
				msuu.setVisible(true);
				dispose();
			}
		});
		signUp.setBounds(180, 170, 87, 23);
		panel.add(signUp);
	}

	private void login() {
		String Username=username.getText();
		String Password=password.getText();
		Member member=msi.login(Username, Password);
		if(member!=null)
		{
			Tool.writeObject(member, "login_member_data.txt");
			PokerHomeUi phu=new PokerHomeUi();
			phu.setVisible(true);
			dispose();
		}
		else
		{
			JOptionPane.showMessageDialog(null, "登入失敗");
		}
	}
}
