package controller;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entity.Member;
import service.impl.MemberServiceImpl;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MemberSignUpUi extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField username;
	private JTextField password;
	private JTextField name;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MemberSignUpUi frame = new MemberSignUpUi();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MemberSignUpUi() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 360, 260);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(227, 252, 255));
		panel.setBounds(0, 0, 344, 221);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel title = new JLabel("註冊新帳號");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setBounds(128, 25, 87, 23);
		panel.add(title);

		JLabel lblNewLabel = new JLabel("帳號");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(45, 70, 46, 15);
		panel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("密碼");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(45, 110, 46, 15);
		panel.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("姓名");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(45, 150, 46, 15);
		panel.add(lblNewLabel_2);

		username = new JTextField();
		username.setBounds(140, 70, 120, 21);
		panel.add(username);
		username.setColumns(10);

		password = new JTextField();
		password.setBounds(140, 110, 120, 21);
		panel.add(password);
		password.setColumns(10);

		name = new JTextField();
		name.setColumns(10);
		name.setBounds(140, 150, 120, 21);
		panel.add(name);

		MemberServiceImpl msi=new MemberServiceImpl();

		JButton ok = new JButton("確定");
		ok.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String Username=username.getText();
				String Password=password.getText();
				String Name=name.getText();
				if(msi.usernameIsUsable(Username))
				{
					Member member=new Member(Username,Password,Name);
					msi.createMember(member);
					JOptionPane.showMessageDialog(null, "註冊成功");
					MemberLoginUi mlu=new MemberLoginUi();
					mlu.setVisible(true);
					dispose();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "帳號已被使用");
				}
			}
		});
		ok.setBounds(128, 190, 87, 23);
		panel.add(ok);
	}
}
