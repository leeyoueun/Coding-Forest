
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


class HelpDialog extends JDialog {
	
	private JPanel north_p = new JPanel() {
			private  ImageIcon back_img = new ImageIcon("img/north.png");
			public void paintComponent(Graphics g) {
				g.drawImage(back_img.getImage(), 0, 0, null);
				}
			};
	private JPanel center_p = new JPanel();
			
	private JPanel south_p = new JPanel(){
		private  ImageIcon back_img = new ImageIcon("img/south.png");
		public void paintComponent(Graphics g) {
			g.drawImage(back_img.getImage(), -18, -55, null);
			}
		};
	
	private JLabel lb1= new JLabel("",JLabel.CENTER);
	private JLabel lb2= new JLabel("",JLabel.CENTER);
	private JLabel lb3= new JLabel("",JLabel.CENTER);
	private JLabel lb4= new JLabel("",JLabel.CENTER);

	public void init() {
	
		Container con = this.getContentPane();
		con.setLayout(new BorderLayout());
		north_p.setPreferredSize(new Dimension(600,200));
		con.add("North", north_p);
		
		con.add("Center", center_p);
		center_p.setLayout(new GridLayout(4,1));
		
		center_p.setBackground(Color.white);
		center_p.add(lb1); 
		lb1.setFont(new Font("", Font.BOLD, 13));
		lb1.setText("* ���������� ä��, bgm����, ģ���������̷롤������ ������ �����մϴ�.");
		
		lb2.setFont(new Font("", Font.BOLD, 13));
		center_p.add(lb2);
		lb2.setText("*���̷뿡���� ���� ���� Ȯ�� �� ���� Ȯ���� �����մϴ�.");
		
		lb3.setFont(new Font("", Font.BOLD, 13));
		center_p.add(lb3);
		lb3.setText("*�ٸ�ģ�� �濡 ����� ��ư�� ���� ģ�� ����Ȯ�� �� ���� �ۼ��� �����մϴ�.");
		
		lb4.setFont(new Font("", Font.BOLD, 13));
		center_p.add(lb4);
		lb4.setText("*���ӿ����� ģ������� ��ŷ����� ��� �� �ֽ��ϴ�.");
		
		south_p.setPreferredSize(new Dimension(600,80));
		con.add("South", south_p);		
	}
		
	public HelpDialog(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
		
		this.init();
		
		this.setSize(600, 600);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int)(screen.getWidth()/2 - this.getWidth()/2);
		int ypos = (int)(screen.getHeight()/2 - this.getHeight()/2);
		this.setLocation(xpos, ypos);
		this.setResizable(false);
	}
}






