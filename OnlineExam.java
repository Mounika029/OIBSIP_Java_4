import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class OnlineExam extends JFrame implements ActionListener {

    CardLayout cl;
    JPanel mainPanel;

    JTextField userField;
    JPasswordField passField;

    JLabel questionLabel, timerLabel;
    JRadioButton opt1, opt2, opt3, opt4;
    ButtonGroup bg;

    JButton nextBtn, submitBtn;

    int currentQ = 0;
    int score = 0;
    int timeLeft = 30;

    Timer timer;

    String[][] questions = {
        {"Java is?", "Language", "OS", "Browser", "Hardware", "Language"},
        {"2 + 2 = ?", "3", "4", "5", "6", "4"},
        {"HTML stands for?", "HyperText Markup Language", "HighText Machine", "None", "Code", "HyperText Markup Language"}
    };

    public OnlineExam() {
        setTitle("Online Examination");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        cl = new CardLayout();
        mainPanel = new JPanel(cl);

        mainPanel.add(loginPanel(), "login");
        mainPanel.add(examPanel(), "exam");

        add(mainPanel);
        setVisible(true);
    }

    // 🔐 Login Panel
    JPanel loginPanel() {
        JPanel p = new JPanel(new GridLayout(3,2));

        p.add(new JLabel("Username:"));
        userField = new JTextField();
        p.add(userField);

        p.add(new JLabel("Password:"));
        passField = new JPasswordField();
        p.add(passField);

        JButton loginBtn = new JButton("Login");
        loginBtn.addActionListener(this);
        p.add(loginBtn);

        return p;
    }

    // 📝 Exam Panel
    JPanel examPanel() {
        JPanel p = new JPanel(new BorderLayout());

        questionLabel = new JLabel();
        p.add(questionLabel, BorderLayout.NORTH);

        JPanel options = new JPanel(new GridLayout(4,1));
        opt1 = new JRadioButton();
        opt2 = new JRadioButton();
        opt3 = new JRadioButton();
        opt4 = new JRadioButton();

        bg = new ButtonGroup();
        bg.add(opt1); bg.add(opt2); bg.add(opt3); bg.add(opt4);

        options.add(opt1);
        options.add(opt2);
        options.add(opt3);
        options.add(opt4);

        p.add(options, BorderLayout.CENTER);

        JPanel bottom = new JPanel();

        timerLabel = new JLabel("Time: 30");
        bottom.add(timerLabel);

        nextBtn = new JButton("Next");
        nextBtn.addActionListener(this);
        bottom.add(nextBtn);

        submitBtn = new JButton("Submit");
        submitBtn.addActionListener(this);
        bottom.add(submitBtn);

        p.add(bottom, BorderLayout.SOUTH);

        loadQuestion();

        return p;
    }

    void loadQuestion() {
        if (currentQ < questions.length) {
            questionLabel.setText(questions[currentQ][0]);

            opt1.setText(questions[currentQ][1]);
            opt2.setText(questions[currentQ][2]);
            opt3.setText(questions[currentQ][3]);
            opt4.setText(questions[currentQ][4]);

            bg.clearSelection();
        }
    }

    String getSelected() {
        if (opt1.isSelected()) return opt1.getText();
        if (opt2.isSelected()) return opt2.getText();
        if (opt3.isSelected()) return opt3.getText();
        if (opt4.isSelected()) return opt4.getText();
        return "";
    }

    void checkAnswer() {
        if (getSelected().equals(questions[currentQ][5])) {
            score++;
        }
    }

    void startTimer() {
        timer = new Timer(1000, e -> {
            timeLeft--;
            timerLabel.setText("Time: " + timeLeft);

            if (timeLeft <= 0) {
                timer.stop();
                submitExam();
            }
        });
        timer.start();
    }

    void submitExam() {
        JOptionPane.showMessageDialog(this, "Score: " + score);
        System.exit(0);
    }

    public void actionPerformed(ActionEvent e) {

        String cmd = e.getActionCommand();

        // LOGIN
        if (cmd.equals("Login")) {
            String user = userField.getText();
            String pass = new String(passField.getPassword());

            if (user.equals("admin") && pass.equals("1234")) {
                cl.show(mainPanel, "exam");
                startTimer();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Login");
            }
        }

        // NEXT
        else if (cmd.equals("Next")) {
            checkAnswer();
            currentQ++;
            loadQuestion();
        }

        // SUBMIT
        else if (cmd.equals("Submit")) {
            checkAnswer();
            submitExam();
        }
    }

    public static void main(String[] args) {
        new OnlineExam();
    }
}