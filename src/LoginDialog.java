
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class LoginDialog extends JDialog {
	
	// �������� ���� �г�
	private JPanel jp1 = new JPanel() {
		private  ImageIcon back_img = new ImageIcon("img/login.png");
		public void paintComponent(Graphics g) {
			g.drawImage(back_img.getImage(), -215, -105, null);
		}
	};
	
	private JPanel jp1_2 = new JPanel();
	
	// ���̵�, ��й�ȣ, ȸ������ ��ư, �α��� ��ư�� ������ �г�	
	private JPanel jp2 = new JPanel();
	private JPanel jp2_2 = new JPanel();
	
	// ���̵�, ��й�ȣ�� �󺧰� jtf
	private JLabel id_jlb = new JLabel("���̵�", JLabel.CENTER);
	JTextField id_jtf = new JTextField();
	private JLabel pw_jlb = new JLabel("��й�ȣ", JLabel.CENTER);
	JTextField pw_jtf = new JTextField();
	
	// ȸ�����԰� �α����� ��ư
	JButton join_jbt = new JButton("ȸ������");
	JButton login_jbt = new JButton("�α���");
	
	public void init() {
		Container con = this.getContentPane();
		// �̹����� ���� �г�
		con.add(jp1);
		jp1.setLayout(new BorderLayout());
		jp1_2.setBackground(new Color(255, 0, 0, 0));
		jp1.add("Center", jp1_2);

		// ���̵�, ��й�ȣ�� ������ �г�
		jp2.setBackground(new Color(255, 0, 0, 0));		// �г� ����
		jp1.add("South", jp2);
		jp2.setLayout(new FlowLayout());
		jp2_2.setBackground(new Color(255, 0, 0, 0));	// �г� ����
		
		jp2.add(jp2_2);
		
		// ���̵�, ��й�ȣ �󺧰� jtf
		jp2_2.setLayout(new GridLayout(3,2));
		jp2_2.add(id_jlb);
		jp2_2.add(id_jtf);
		
		jp2_2.add(pw_jlb);
		jp2_2.add(pw_jtf);
		
		// ȸ�����԰� �α��� ��ư
		jp2_2.add(join_jbt);
		jp2_2.add(login_jbt);
		
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // â�� x�� ������ ���� ����. ����� ���� �����ؾ���.
	}
	
	public String getID() {
		return id_jtf.getText();
	}
	public String getPW() {
		return pw_jtf.getText();
	}
	public void clear() {
		id_jtf.setText("");
		pw_jtf.setText("");				
	}
	
	public LoginDialog(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
		
		this.init();
		
		this.setSize(600, 500);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int)(screen.getWidth()/2 - this.getWidth()/2);
		int ypos = (int)(screen.getHeight()/2 - this.getHeight()/2);
		this.setLocation(xpos, ypos);
		this.setResizable(false);

	}

}
