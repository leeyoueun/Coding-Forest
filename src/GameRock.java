
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class MyImage04 extends Canvas{
	private Image img;
	public MyImage04(Image img) {
		this.img = img;
	}
	public void setImg(Image img) {
		this.img = img;
	}
	public void paint(Graphics g) {
		g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
	}
}

public class GameRock extends JDialog implements ActionListener {
	Image img = Toolkit.getDefaultToolkit().getImage("img/game/rpsss.gif");
	MyImage04 mib = new MyImage04(img);

	private int win;
	
	protected int score = 0;

	private JButton bt1 = new JButton("����"); 
	private JButton bt2 = new JButton("����");
	private JButton bt3 = new JButton("��");
	private JButton bt4 = new JButton("����");
	protected JButton bt6 = new JButton("�ڶ��ϱ�");
	private JButton bt5 = new JButton("����");
	private JLabel msg_lb = new JLabel("���� ��ư�� �����ּ���", JLabel.CENTER);
	private JLabel victory = new JLabel("���� : 0 ȸ");
	
	private JPanel north_p = new JPanel();

	private JLabel mainLb = new JLabel("Rock Scissor Papper", JLabel.CENTER);
	private JPanel blank = new JPanel();
	private JPanel center_p = new JPanel();
	private JPanel center_left_p = new JPanel();
	private JPanel center_left_bottom_p = new JPanel();
	private JPanel center_right_p = new JPanel();
	private JPanel center_right_left_p = new JPanel();
	private JPanel p_p = new JPanel();
	private JPanel p_p2 = new JPanel();
	private JTextArea rule_area = new JTextArea();
	private JTextArea ranking_area = new JTextArea();
	
	private JLabel rank_jlb = new JLabel("���������� ��ŷ", JLabel.CENTER);
	
	public void init() {
		
		Container con = this.getContentPane();
		con.setLayout(new BorderLayout());
		con.add("North", north_p);
		north_p.setLayout(new GridLayout(3,1));
		north_p.setPreferredSize(new Dimension(70,70));
		north_p.setBackground(Color.black);
		mainLb.setFont(new Font("", Font.BOLD, 30));
		mainLb.setForeground(Color.YELLOW);
		blank.setBackground(Color.black);
		north_p.add(blank);
		north_p.add(mainLb);
		
		
		con.add("Center", center_p);
		center_p.setLayout(new GridLayout(1,2));
		center_p.add(center_left_p);
		
		
		center_left_p.setLayout(new BorderLayout());
		center_left_p.setBackground(Color.yellow);

		center_left_p.add("North", msg_lb);
		
		center_left_p.add("Center", mib);
		
		center_left_p.add("South", center_left_bottom_p);
		center_left_bottom_p.setBackground(Color.yellow);
		center_left_bottom_p.add(victory);
		
		center_p.add(center_right_p);
		center_right_p.setLayout(new BorderLayout());
		center_right_p.add("West", center_right_left_p);
		center_right_left_p.setLayout(new GridLayout(5,1));
		center_right_left_p.setPreferredSize(new Dimension(100,300));
		center_right_left_p.setLayout(new GridLayout(6,1));
		center_right_left_p.add(bt1);	
		center_right_left_p.add(bt2);	
		center_right_left_p.add(bt3);	
		center_right_left_p.add(bt4);	
		center_right_left_p.add(bt5);	
		center_right_left_p.add(bt6);	
		
		bt1.addActionListener(this);
		bt2.addActionListener(this);
		bt3.addActionListener(this);
		bt4.addActionListener(this);
		bt5.addActionListener(this);
		bt6.addActionListener(this);
		
		center_right_p.add("Center", p_p);
		p_p.setLayout(new GridLayout(2,1));
		rule_area.setForeground(Color.black);
		rule_area.setFont(new Font("", Font.BOLD, 12));
		rule_area.setBackground(Color.YELLOW);
		p_p.add(rule_area);
		rule_area.setText("");
		rule_area.append("\n");
		rule_area.append("                  **������������**\n");
		rule_area.append("\n");
		rule_area.append("  1. ���۹�ư�� ������\n");
		rule_area.append("  2. �¸��� �ٽ� ���������� ������ \n");
		rule_area.append("  3. �й�� �ٽ� ���� \n");
		rule_area.append("  ** ������ ������� �ڶ��ϱ� Ŭ��! \n");
		rule_area.setEditable(false);

		ranking_area.setForeground(Color.YELLOW);
		ranking_area.setFont(new Font("", Font.BOLD, 12));
		ranking_area.setBackground(Color.black);
		p_p.add(p_p2);
		p_p2.setLayout(new BorderLayout());
		p_p2.add("North", rank_jlb);
		p_p2.add("Center", ranking_area);

		ranking_area.setEditable(false);

	}
	
	public void setRank(String rank) {
		ranking_area.setText("");
		System.out.println("�����غ�");
		System.out.println(rank);
		if(rank == null) {
			ranking_area.append("");
		}else {
			String[] text = rank.split("@");
			System.out.println(text.length);
			if(text.length < 3) {
				for(int i=0; i<text.length; ++i) {
					String[] data = text[i].split("#");
					ranking_area.append(data[1] + "  " + data[2] + "\n");
				 }
			}else {
				for(int i=0; i<3; ++i) {
					String[] data = text[i].split("#");
					ranking_area.append(data[1] + "  " + data[2] + "\n");
				}
			} 
		}
	}
	
	public int sendRank() {
		return score;
	}
	
	public void clearScore() {
		score = 0;
		victory.setText("���� : " + score + "ȸ");
	}
	
	public GameRock(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
		
		this.init();
		
		this.setSize(640, 400);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int)(screen.getWidth()/2 - this.getWidth()/2);
		int ypos = (int)(screen.getHeight()/2 - this.getHeight()/2);
		this.setLocation(xpos, ypos);
		this.setResizable(false);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		int ransu = (int)(Math.random()*3) + 1;
		Image img2 = null;
		switch(ransu) {
		case 1 : img2 = Toolkit.getDefaultToolkit().getImage("img/game/ssi.png"); break;
		case 2 : img2 = Toolkit.getDefaultToolkit().getImage("img/game/rock1.png"); break;
		case 3 : img2 = Toolkit.getDefaultToolkit().getImage("img/game/paper1.png");
		}
		mib.setImg(img2);
		if (e.getSource()==bt1) {
			if (ransu==1) {
				msg_lb.setText("�����ϴ�.");
			}else if (ransu==2) {
				msg_lb.setText("��ǻ�Ͱ� �̰���ϴ�.");
				bt1.setEnabled(false);
				bt2.setEnabled(false);
				bt3.setEnabled(false);
			}else {
				msg_lb.setText("����� �̰���ϴ�.");
				score++;
				victory.setText("���� :" + score +"ȸ");

			}
		}else if (e.getSource()==bt2) {
			if (ransu==2) {
				msg_lb.setText("�����ϴ�.");
			}else if (ransu==3) {
				msg_lb.setText("��ǻ�Ͱ� �̰���ϴ�.");
				bt1.setEnabled(false);
				bt2.setEnabled(false);
				bt3.setEnabled(false);
			}else {
				msg_lb.setText("����� �̰���ϴ�.");
				score++;
				victory.setText("���� :" + score +"ȸ");
			}
		}else if (e.getSource()==bt3) {
			if (ransu==3) {
				msg_lb.setText("�����ϴ�.");
			}else if (ransu==1) {
				msg_lb.setText("��ǻ�Ͱ� �̰���ϴ�.");
				bt1.setEnabled(false);
				bt2.setEnabled(false);
				bt3.setEnabled(false);
			}else {
				msg_lb.setText("����� �̰���ϴ�.");
				score++;
				victory.setText("���� :" + score +"ȸ");
			}
		}else if (e.getSource()==bt4) {	// �����ϱ�
			mib.setImg(img);
			bt1.setEnabled(true);
			bt2.setEnabled(true);
			bt3.setEnabled(true);
			score=0;
			victory.setText("���� :" + score +"ȸ");
		}
		
		mib.repaint();
		
		if (e.getSource()==bt5) {	// �����ư
			dispose();
		} 
	}
}
