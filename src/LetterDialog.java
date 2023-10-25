
import java.awt.*;
import javax.swing.*;

//��ư�� ������ ���� �� ������ Dialog
public class LetterDialog extends JDialog{
	// �̹����� �����ϴ� jtb
	ImageIcon ii1 = new ImageIcon("img/myroom/letter1.png");
	ImageIcon ii2 = new ImageIcon("img/myroom/letter2.png");
	ImageIcon ii3 = new ImageIcon("img/myroom/letter3.png");
	ImageIcon ii4 = new ImageIcon("img/myroom/letter4.png");
	
	private JToggleButton jtb1 = new JToggleButton(ii1);
	private JToggleButton jtb2 = new JToggleButton(ii2);
	private JToggleButton jtb3 = new JToggleButton(ii3);
	private JToggleButton jtb4 = new JToggleButton(ii4);
	
	ButtonGroup bg = new ButtonGroup();
	
	private JPanel center_jp = new JPanel();
	private JPanel jp2 = new JPanel();
	private JPanel jp3 = new JPanel();
	private JPanel south_jp = new JPanel();
	
	// �޼��� �Է�â jtf
	private JTextField jtf = new JTextField();
	
	// ������� ��� ��ư
	protected JButton ok_jbt = new JButton("������");
	protected JButton cancel_jbt = new JButton("���");
	
	public void init() {
		Container con = this.getContentPane();
		con.setLayout(new BorderLayout());
		con.add("Center", center_jp);
		center_jp.setLayout(new BorderLayout());
		center_jp.add(jp2);
		
		// ���� ����Ʈ���� ������ ȭ�� ����
		jp2.setLayout(new GridLayout(1,4));
		jtb1.setToolTipText("�� ����Ʈ��");	// ���콺�� �ø��� �ߴ� �޼���
		bg.add(jtb1);
		jp2.add(jtb1);
		
		jtb2.setToolTipText("��Ʈ ����Ʈ��");	// ���콺�� �ø��� �ߴ� �޼���
		bg.add(jtb2);
		jp2.add(jtb2);
		
		jtb3.setToolTipText("Ŭ�ι� ����Ʈ��");	// ���콺�� �ø��� �ߴ� �޼���
		bg.add(jtb3);
		jp2.add(jtb3);
		
		jtb4.setToolTipText("������ ����Ʈ��");	// ���콺�� �ø��� �ߴ� �޼���
		bg.add(jtb4);
		jp2.add(jtb4);
		
		// �޼����� ���� jtf ����
		center_jp.add("South", jp3);
		jp3.setLayout(new BorderLayout());
		jp3.add(jtf);
		
		// ������, ��ҹ�ư ����
		con.add("South", south_jp);
		south_jp.setLayout(new FlowLayout());
		south_jp.add(ok_jbt);
		south_jp.add(cancel_jbt);
		
	}
	
	public void clearText() { //tf �����
		jtf.setText("");
	}
	
	public String selectButton() { //���õ� ��ư�� �� ��ȯ
		String select;
		if(jtb1.isSelected()) {
			return select = "1"; //��
		}else if(jtb2.isSelected()) {
			return select = "2"; //��Ʈ
		}else if(jtb3.isSelected()) {
			return select = "3"; //Ŭ�ι�
		}else if(jtb4.isSelected()) {
			return select = "4"; //������
		}else {
			return null;
		}
	}
	
	public String sendText() { //���� ����/����Ʈ�� ��������
		String letter = selectButton() + '#'+ jtf.getText(); //1#���� �������� ��ȯ
		clearText();
		return letter;
	}
	
	
	public LetterDialog(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
		
		this.init();
		
		this.setSize(700, 400);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int)(screen.getWidth()/2 - this.getWidth()/2);
		int ypos = (int)(screen.getHeight()/2 - this.getHeight()/2);
		this.setLocation(xpos, ypos);
		this.setResizable(false);
	}
}
//��ư�� ������ ���� �� �ߴ� Dialog
class ViewDialog extends JDialog{
	
	private JTextArea jta = new JTextArea();
	private JScrollPane jsp = new JScrollPane(jta);
	protected JButton bt_exit = new JButton("������");
	
	private String text;
	
	public void setText(String text) { //���� ���� ����
		String[] letter = text.split("#");
		jta.setText(letter[1]);
	}
	public void clearText() { //���� â �ٽ� clear
		jta.setText("");
	}

	public void init() {
		this.setLayout(new BorderLayout());
		this.add("Center",jsp);
		jta.setEnabled(false);
		this.add("South",bt_exit);
		
	}
	public ViewDialog(Frame owner, String title, boolean modal) {
		super(owner,title, modal);
		this.init();
		
		this.setSize(200, 200);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int)(screen.getWidth()/2 - this.getWidth()/2);
		int ypos = (int)(screen.getHeight()/2 - this.getHeight()/2);
		this.setLocation(xpos, ypos);
		this.setResizable(false);
	}
}
