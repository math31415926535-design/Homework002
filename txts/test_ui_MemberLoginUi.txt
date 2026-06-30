package test_ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import entity.Member;
import service.impl.MemberServiceImpl;
import util.Tool;

public class MemberLoginUi extends JFrame {
    private static final long serialVersionUID = 1L;
    private final JTextField username = new JTextField();
    private final JPasswordField password = new JPasswordField();
    private final MemberServiceImpl memberService = new MemberServiceImpl();

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            UiTheme.install();
            new MemberLoginUi().setVisible(true);
        });
    }

    public MemberLoginUi() {
        UiTheme.install();
        setTitle("田忌撲克 · 登入");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(920, 590);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UiTheme.CREAM);
        setContentPane(root);
        root.add(createBrandPanel(), BorderLayout.WEST);
        root.add(createFormPanel(), BorderLayout.CENTER);
        getRootPane().setDefaultButton(findLoginButton);
        UiTheme.centerWindow(this);
    }

    private JButton findLoginButton;

    private JPanel createBrandPanel() {
        UiTheme.BrandPanel panel = new UiTheme.BrandPanel();
        panel.setLayout(null);
        panel.setPreferredSize(new java.awt.Dimension(390, 590));

        UiTheme.SuitMark suits = new UiTheme.SuitMark();
        suits.setBounds(74, 85, 240, 62);
        panel.add(suits);

        JLabel title = new JLabel("田忌撲克", SwingConstants.CENTER);
        title.setFont(UiTheme.font(Font.BOLD, 38));
        title.setForeground(Color.WHITE);
        title.setBounds(40, 180, 310, 55);
        panel.add(title);

        JLabel english = new JLabel("TJ POKER", SwingConstants.CENTER);
        english.setFont(UiTheme.font(Font.BOLD, 14));
        english.setForeground(UiTheme.GOLD);
        english.setBounds(40, 238, 310, 24);
        panel.add(english);

        JLabel copy = new JLabel("<html><div style='text-align:center'>三輪佈局，一次決勝。<br>"
                + "用有限的手牌，打出最好的安排。</div></html>", SwingConstants.CENTER);
        copy.setFont(UiTheme.font(Font.PLAIN, 16));
        copy.setForeground(new Color(221, 229, 224));
        copy.setBounds(55, 315, 280, 70);
        panel.add(copy);

        JLabel rule = new JLabel("3 張  ·  5 張  ·  5 張", SwingConstants.CENTER);
        rule.setFont(UiTheme.font(Font.BOLD, 17));
        rule.setForeground(UiTheme.GOLD);
        rule.setBounds(65, 425, 260, 30);
        panel.add(rule);
        return panel;
    }

    private JPanel createFormPanel() {
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setBackground(UiTheme.CREAM);
        outer.setBorder(new EmptyBorder(40, 58, 40, 58));

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;

        JLabel eyebrow = UiTheme.eyebrow("WELCOME BACK");
        c.gridy = 0;
        c.insets = new Insets(0, 0, 8, 0);
        form.add(eyebrow, c);

        JLabel heading = new JLabel("歡迎回來");
        heading.setFont(UiTheme.DISPLAY);
        heading.setForeground(UiTheme.INK);
        c.gridy = 1;
        c.insets = new Insets(0, 0, 8, 0);
        form.add(heading, c);

        JLabel intro = new JLabel("登入後繼續你的牌局。");
        intro.setFont(UiTheme.BODY);
        intro.setForeground(UiTheme.MUTED);
        c.gridy = 2;
        c.insets = new Insets(0, 0, 28, 0);
        form.add(intro, c);

        form.add(fieldLabel("帳號"), constraints(3, 0, 7));
        UiTheme.styleField(username);
        c = constraints(4, 0, 18);
        c.ipady = 10;
        form.add(username, c);

        form.add(fieldLabel("密碼"), constraints(5, 0, 7));
        UiTheme.styleField(password);
        c = constraints(6, 0, 26);
        c.ipady = 10;
        form.add(password, c);

        findLoginButton = UiTheme.primaryButton("登入並開始");
        findLoginButton.addActionListener(e -> login());
        c = constraints(7, 0, 12);
        c.ipady = 12;
        form.add(findLoginButton, c);

        JButton signUp = UiTheme.secondaryButton("建立新帳號");
        signUp.addActionListener(e -> {
            new MemberSignUpUi().setVisible(true);
            dispose();
        });
        c = constraints(8, 0, 0);
        c.ipady = 10;
        form.add(signUp, c);

        outer.add(form);
        return outer;
    }

    private JLabel fieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(UiTheme.BODY_BOLD);
        label.setForeground(UiTheme.INK);
        return label;
    }

    private GridBagConstraints constraints(int y, int top, int bottom) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = y;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(top, 0, bottom, 0);
        return c;
    }

    private void login() {
        Member member = memberService.login(username.getText().trim(), new String(password.getPassword()));
        if (member == null) {
            JOptionPane.showMessageDialog(this, "帳號或密碼不正確，請再試一次。", "登入失敗",
                    JOptionPane.WARNING_MESSAGE);
            password.selectAll();
            password.requestFocusInWindow();
            return;
        }

        Tool.writeObject(member, "login_member_data.txt");
        new PokerHomeUi().setVisible(true);
        dispose();
    }
}
