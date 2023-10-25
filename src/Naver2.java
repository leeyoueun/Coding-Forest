
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class MyButton04 extends Button {	// �δ��� �̹��� �ֱ�
	private Image img;
	
	public MyButton04(Image img) {
		this.img = img;
	}
	
	public void setImg(Image img) {
		this.img = img;
	}
	
	public void paint(Graphics g) {
		g.drawImage(img, 0, 0, this.getWidth()-3, this.getHeight()-3, this);
	}
}

	
public class Naver2 extends JFrame {
	
	//private Search sdg = new Search();
	
	//ģ����õâ 
	private Image img1 = Toolkit.getDefaultToolkit().getImage("img/search.png");
	MyButton04 search = new MyButton04(img1); 
	private JPanel north_p = new JPanel() {
		private  ImageIcon back_img = new ImageIcon("img/north2.png");
		public void paintComponent(Graphics g) {
			g.drawImage(back_img.getImage(), 0, 0, null);
			}
		};
	private JPanel center_p = new JPanel();
	JTextArea ta= new JTextArea();
	
	private JPanel search_p = new JPanel(); 
	
	// ģ���˻�â
	JDialog dlg = new JDialog();
	
	private JPanel dlg_north_p = new JPanel() {
		private  ImageIcon back_img = new ImageIcon("img/reco.png");
		public void paintComponent(Graphics g) {
			g.drawImage(back_img.getImage(), 0, 0, null);
			}
		};
	private JPanel dlg_center_p = new JPanel();
	private JPanel dlg_south_p = new JPanel();	
	JButton co_bt = new JButton("�����ϱ�");
	
	
	JTextArea dlg_ta= new JTextArea();
	
	
	
	//ģ������â
	JDialog dlg_co = new JDialog();
	private JPanel dlg_co_north_p = new JPanel(){
		private  ImageIcon back_img = new ImageIcon("img/susu.png");
		public void paintComponent(Graphics g) {
			g.drawImage(back_img.getImage(), 3, 0, null);
			}
		};
	private JPanel dlg_co_center_p = new JPanel();
	
	JLabel dlg_co_center_left_lb = new JLabel("�г���: ", JLabel.CENTER);
	JTextArea dlg_co_center_left_ta= new JTextArea();
	
	JLabel dlg_co_center_right_lb = new JLabel("�޸�: ", JLabel.CENTER);
	JTextArea dlg_co_center_right_ta= new JTextArea();
	
	JPanel dlg_co_center_left_p = new JPanel();
	JPanel dlg_co_center_right_p = new JPanel();
	JPanel dlg_co_south_p = new JPanel();
	JButton ok_bt = new JButton("����");
	
	private JLabel dlg_co_north_lb = new JLabel("", JLabel.CENTER);
	//
	private JLabel lb1= new JLabel("",JLabel.CENTER);
	
	String juljul;
	String ddg="*";
	
	public void init() {
		//��ģ�� ���â
		Container con = this.getContentPane();
		con.setLayout(new BorderLayout());
		north_p.setPreferredSize(new Dimension(300,40));
		con.add("North", north_p);
		
		con.add("Center", center_p);
		center_p.setLayout(new BorderLayout());
		
		center_p.setBackground(new Color(248,247,229));
		
		search_p.setPreferredSize(new Dimension(300,40));
		center_p.add("North", search_p);
		search_p.setLayout(new BorderLayout());
		search_p.add(search);
		
		ta.setBackground(new Color(248,247,229));
		center_p.add("Center", ta);
		ta.setEditable(false);
		//248 247 229
		
		//ģ����õ â
		dlg.setLayout(new BorderLayout());
		dlg_north_p.setPreferredSize(new Dimension(200,40));
		dlg.add("North", dlg_north_p);
		
		dlg_center_p.setBackground(new Color(248,247,229));
		dlg.add("Center", dlg_center_p);
		dlg_center_p.setLayout(new BorderLayout());
		
		dlg_ta.setBackground(new Color(248,247,229));
		dlg_ta.setFont(new Font("�ձٸ��", Font.BOLD, 13));
		dlg_center_p.add(dlg_ta);
		dlg_ta.append("\n");
		dlg_ta.append("\n");
		dlg_ta.setEditable(false);
		//lb1.setFont(new Font("", Font.BOLD, 13));

		dlg_south_p.setBackground(new Color(248,247,229));
		dlg.add("South", dlg_south_p);
		dlg_south_p.setLayout(new FlowLayout());
		dlg_south_p.add(co_bt);
		
		
		//�˻���� ���̾�α�
		dlg_co.setLayout(new BorderLayout());
		dlg_co_north_p.setPreferredSize(new Dimension(280,40));
		dlg_co.add("North", dlg_co_north_p);
		dlg_co_north_p.add(dlg_co_north_lb);
		
		dlg_co_center_p.setBackground(new Color(248,247,229));
		dlg_co.add("Center", dlg_co_center_p);
		dlg_co_center_p.setLayout(new GridLayout(1,4));
		dlg_co_center_p.add(dlg_co_center_left_lb);
		dlg_co_center_p.add(dlg_co_center_left_ta);
		dlg_co_center_p.add(dlg_co_center_right_lb);
		dlg_co_center_p.add(dlg_co_center_right_ta);
		
		dlg_co_south_p.setBackground(new Color(248,247,229));
		dlg_co.add("South", dlg_co_south_p);
		dlg_co_south_p.add(ok_bt);
		
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	
	public String send(String text) {
		juljul = text;
		String juljul[] = text.split("m");
		for (int i=0; i<juljul.length; ++i) {
			System.out.println(juljul[i]);
			ddg += juljul[i] + "\n" ;
		}
		
		return ddg;
	}
	
	public void clearText() {
		dlg_co_center_left_ta.setText("");
		dlg_co_center_right_ta.setText("");
		
	}
	
	public Naver2() {
		
		this.init();
		this.setSize(300, 300);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int)(screen.getWidth()/2 - this.getWidth()/2);
		int ypos = (int)(screen.getHeight()/2 - this.getHeight()/2);
		this.setLocation(xpos, ypos);
		this.setResizable(false);
		//this.setVisible(true);
		
		dlg.setBounds(xpos+20, ypos+20,230,250);
		dlg.setResizable(false);
		
		dlg_co.setBounds(xpos+30, ypos+30,300,150);
		dlg_co.setResizable(false);
	}

}




