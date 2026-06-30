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
import javax.swing.border.EmptyBorder;

import entity.Member;
import service.impl.MemberServiceImpl;

public class MemberSignUpUi extends JFrame {
    private static final long serialVersionUID = 1L;
    private final JTextField username = new JTextField();
    private final JPasswordField password = new JPasswordField();
    private final JTextField name = new JTextField();
    private final MemberServiceImpl memberService = new MemberServiceImpl();

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            UiTheme.install();
            new MemberSignUpUi().setVisible(true);
        });
    }

    public MemberSignUpUi() {
        UiTheme.install();
        setTitle("田忌撲克 · 建立帳號");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(680, 650);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UiTheme.CREAM);
        root.setBorder(new EmptyBorder(34, 76, 34, 76));
        setContentPane(root);

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        GridBagConstraints c = base();

        JLabel eyebrow = UiTheme.eyebrow("NEW PLAYER");
        form.add(eyebrow, at(c, 0, 0, 7));

        JLabel title = new JLabel("加入牌桌");
        title.setFont(UiTheme.DISPLAY);
        title.setForeground(UiTheme.INK);
        form.add(title, at(c, 1, 0, 7));

        JLabel subtitle = new JLabel("建立專屬帳號，保存每一局勝負。");
        subtitle.setFont(UiTheme.BODY);
        subtitle.setForeground(UiTheme.MUTED);
        form.add(subtitle, at(c, 2, 0, 26));

        addField(form, "帳號", username, 3);
        addField(form, "密碼", password, 5);
        addField(form, "玩家名稱", name, 7);

        JButton submit = UiTheme.primaryButton("建立帳號");
        submit.addActionListener(e -> signUp());
        c = at(c, 9, 8, 12);
        c.ipady = 12;
        form.add(submit, c);

        JButton back = UiTheme.secondaryButton("返回登入");
        back.addActionListener(e -> backToLogin());
        c = at(c, 10, 0, 0);
        c.ipady = 10;
        form.add(back, c);

        root.add(form, BorderLayout.CENTER);
        getRootPane().setDefaultButton(submit);
        UiTheme.centerWindow(this);
    }

    private void addField(JPanel form, String title, JTextField field, int row) {
        JLabel label = new JLabel(title);
        label.setFont(UiTheme.BODY_BOLD);
        label.setForeground(UiTheme.INK);
        form.add(label, at(base(), row, 0, 7));

        UiTheme.styleField(field);
        GridBagConstraints c = at(base(), row + 1, 0, 17);
        c.ipady = 10;
        form.add(field, c);
    }

    private GridBagConstraints base() {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        return c;
    }

    private GridBagConstraints at(GridBagConstraints source, int y, int top, int bottom) {
        GridBagConstraints c = (GridBagConstraints) source.clone();
        c.gridy = y;
        c.insets = new Insets(top, 0, bottom, 0);
        return c;
    }

    private void signUp() {
        String account = username.getText().trim();
        String secret = new String(password.getPassword());
        String playerName = name.getText().trim();
        if (account.isEmpty() || secret.isEmpty() || playerName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "請完整填寫帳號、密碼與玩家名稱。", "資料尚未完成",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!memberService.usernameIsUsable(account)) {
            JOptionPane.showMessageDialog(this, "這個帳號已有人使用，請換一個帳號。", "帳號無法使用",
                    JOptionPane.WARNING_MESSAGE);
            username.selectAll();
            username.requestFocusInWindow();
            return;
        }

        memberService.createMember(new Member(account, secret, playerName));
        JOptionPane.showMessageDialog(this, "帳號建立完成，現在可以登入了。", "註冊成功",
                JOptionPane.INFORMATION_MESSAGE);
        backToLogin();
    }

    private void backToLogin() {
        new MemberLoginUi().setVisible(true);
        dispose();
    }
}
