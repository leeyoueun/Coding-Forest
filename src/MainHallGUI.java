
import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class MainHallGUI extends JFrame
		implements Runnable, ActionListener, KeyListener, WindowListener, MouseListener {
	private Container con;
	// ȭ���� ���ʿ� ���� �׸� �г�
	private JPanel main_jp1 = new JPanel() {
		private ImageIcon back_img = new ImageIcon("img/main/main1.png");

		public void paintComponent(Graphics g) {
			g.drawImage(back_img.getImage(), -50, -50, null);
		}
	};
	private int mainWidth = 584;
	private int mainHeight = 561;

	// ȭ���� �����ʿ� �� �г�
	private JPanel main_jp2 = new JPanel();

	// ä��â�� ������â�� ���� �г�
	private JPanel chat_jp = new JPanel();
	private JPanel user_jp = new JPanel();

	// ä��
	private JLabel chat_jlb = new JLabel("ä��", JLabel.CENTER);
	private JTextArea chat_jta = new JTextArea();
	private JScrollPane chat_jsp = new JScrollPane(chat_jta); // ��ũ�ѹ� �߰�

	// ä�þȿ� �� jtf, ��ư
	private JPanel chat_jp2 = new JPanel();
	private JTextField chat_jtf = new JTextField();
	private JButton ok_jbt = new JButton("������");

	// ������
	private JLabel user_jlb = new JLabel("������", JLabel.CENTER);
	private JTextArea user_jta = new JTextArea();
	private JScrollPane user_jsp = new JScrollPane(user_jta); // ��ũ�ѹ� �߰�

	// �� ĳ���� �г� �� ���� ����
	private CharacterLabel myCharaLabel;
	private Member myMember;
	private String myNickname;
	private int mycharaNum;

	// �ǹ� ������
	private MapIcon gameCenter = new MapIcon("���Ӽ���", 1, 50, 50);
	private MapIcon myRoom = new MapIcon("���̷�", 2, 350, 200);

	// ��Ʈ��ũ ����
	private InetAddress ia;
	private Socket soc;
	private PrintWriter pw;
	private BufferedReader br;

	// ���� ������(ȭ�鿡 �����ϴ� ĳ����)
	private Hashtable<String, CharacterLabel> characterHt = new Hashtable<>(); // <�г���, ĳ���� �г�>

	// ���콺 Ŀ�� �̹��� �ֱ�
	private Image cursorimg = Toolkit.getDefaultToolkit().getImage("img/main/cursor.png");
	private Point point = new Point(30, 30);

	// help��ư
	private Image img_help = Toolkit.getDefaultToolkit().getImage("img/main/helpbt.png");
	private MyButton01 mb2 = new MyButton01(img_help);
	
	// ģ���߰� ��ư
	private Image img_naver = Toolkit.getDefaultToolkit().getImage("img/friend.png");
	private MyButton01 mb3 = new MyButton01(img_naver);

	// ȸ������&�α����� ���� ����
	String login; // �α��������� Ȯ���ϴ� ����
	String id; // id(�г����� ���� String ����

	// ���̷��� ���� ����
	String imfo; // id �� �ش� ������ ���� ����(,id,chara, ��������)
	String bt_num; // ��� ��ư�� �������� ���� ����
	String friend_id; // ��� ģ���� ���̵� �Է¹ޱ� ���� ����
	String f_imfo; // ��� ģ���� ������ �޾ƿ� ����(f,id,chara, ��������)
	String f_bt_num; // ģ���� ��� ��ư �������� ���� ����

	// ������ ���� ����
	String gd = "dudu";
	String gz = "zero";
	String gr = "rock";
	String rank_d;
	String rank_r;
	String rank_z;
	
	//ģ���߰� ����
	String mbi = "memberInformation";

	// �뷡
	Music introMusic;

// ���̾�α�
//----------------------------------------------------------------------------------------------	
	// ����
	private HelpDialog help_dlg = new HelpDialog(this, "����", false);
	
	//ģ���߰�
	private Naver2 naver = new Naver2(); 

	// ȸ������, �α���
	private LoginDialog login_dlg = new LoginDialog(this, "�α���", false);
	private JoinDialog join_dlg = new JoinDialog(this, "ȸ������", false);

	// ���ӿ�
	private GameDialog game_dlg = new GameDialog(this, "����", false);
	private GameRock rock_dlg = new GameRock(this, "����������", false);
	private GameZero zero_dlg = new GameZero(this, "ȭ��ã��", false);
	private GameDudu dudu_dlg = new GameDudu(this, "�ʱ�������", false);

	// ���̷��
	private MyRoomDialog myroom_dlg = new MyRoomDialog(this, "���̷�", true);
	private MyRoomDialog friendroom_dlg = new MyRoomDialog(this, "���̷�", true);
	private LetterDialog letter_dlg = new LetterDialog(this, "���Ͼ���", true);
	private ViewDialog view_dlg = new ViewDialog(this, "�����б�", true);
	private LetterDialog f_letter_dlg = new LetterDialog(this, "ģ�� ���Ͼ���", true);
	private ViewDialog f_view_dlg = new ViewDialog(this, "ģ�� �����б�", true);

// ���� ������
//----------------------------------------------------------------------------------------------	
	public static void main(String[] args) {
		new MainHallGUI("��Ϳ� �ڵ��� ��");
	}

// ������ (�ʱ⼳��, UI)
//----------------------------------------------------------------------------------------------	
	public MainHallGUI(String title) {
		super(title);

		login_dlg.setVisible(true);

		this.setSize(800, 600);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int) (screen.getWidth() / 2 - this.getWidth() / 2);
		int ypos = (int) (screen.getHeight() / 2 - this.getHeight() / 2);
		this.setLocation(xpos, ypos);
		this.setResizable(false);

		this.init();
		this.addListeners();

		con.setFocusable(true);
		con.requestFocus();

		start();

		introMusic = new Music(true);
		introMusic.start();
	}

	public void init() {
		con = this.getContentPane();

		Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorimg, point, "");
		con.setCursor(cursor);

		con.setLayout(new BorderLayout());
		con.add("Center", main_jp1);
		con.add("East", main_jp2);

		// ���� ����
		main_jp1.setLayout(null);
		mb2.setBounds(0, 0, 55, 55);
		main_jp1.add(mb2);
		mb3.setBounds(57, 0, 55, 55);
		main_jp1.add(mb3);

		// ä�� â
		main_jp2.setLayout(new GridLayout(2, 1));
		chat_jp.setPreferredSize(new Dimension(200, 300));
		chat_jp.setBackground(new Color(216, 198, 234));
		main_jp2.add(chat_jp);
		chat_jp.setLayout(new BorderLayout());
		chat_jlb.setFont(new Font("", Font.TYPE1_FONT, 15));
		chat_jp.add("North", chat_jlb);
		chat_jta.setFont(new Font("", Font.ITALIC, 13));
		chat_jp.add("Center", chat_jsp); // ��ũ�ѹ� �� �� �ְ�
		chat_jp.add("South", chat_jp2);
		chat_jp2.setLayout(new GridLayout(1, 2));
		chat_jp.setPreferredSize(new Dimension(200, 300));
		chat_jp2.add(chat_jtf);
		chat_jp2.add(ok_jbt);
		chat_jta.setEditable(false); // ä��â���� �Է����� ���ϰ�

		// ������â
		user_jp.setBackground(new Color(216, 198, 234));
		main_jp2.add(user_jp);
		user_jp.setLayout(new BorderLayout());
		user_jlb.setFont(new Font("", Font.TYPE1_FONT, 15));
		user_jp.add("North", user_jlb);
		user_jta.setFont(new Font("", Font.ITALIC, 13));
		user_jp.add("Center", user_jsp); // ��ũ�ѹ� �� �� �ְ�
		user_jta.setEditable(false); // ������â���� �Է����� ���ϰ�
		
		//���� ���� �ʺ� ���ϱ�
		//mainWidth = main_jp1.getWidth();
		//mainHeight = main_jp1.getHeight();

	}

	// �̺�Ʈ������ �߰�
	private void addListeners() {
		this.addWindowListener(this);
		con.addKeyListener(this);
		main_jp1.addMouseListener(this);

		// help ��ư
		mb2.addActionListener(this);
		// ģ���߰� ��ư
		mb3.addActionListener(this);
		//ģ�� ��� ���̾�α�
		naver.search.addActionListener(this);
		naver.co_bt.addActionListener(this);
		naver.ok_bt.addActionListener(this);

		// ä��â
		chat_jtf.addActionListener(this);
		ok_jbt.addActionListener(this);

		// �α��� : ȸ�����Թ�ư, �α��ι�ư
		login_dlg.addWindowListener(this);
		login_dlg.join_jbt.addActionListener(this);
		login_dlg.login_jbt.addActionListener(this);
		login_dlg.pw_jtf.addActionListener(this);

		// ȸ������ : Ȯ�ι�ư, ��ҹ�ư
		join_dlg.ok_jbt.addActionListener(this);
		join_dlg.color_jtf.addActionListener(this);
		join_dlg.cancel_jbt.addActionListener(this);

		// ����â
		game_dlg.bt1.addActionListener(this);
		game_dlg.bt2.addActionListener(this);
		game_dlg.bt3.addActionListener(this);

		// ���̷�
		myroom_dlg.friend_jbt.addActionListener(this);
		myroom_dlg.exit_jbt.addActionListener(this);
		for (int i = 0; i < myroom_dlg.mbt.length; ++i) { // ���� ��ư
			myroom_dlg.mbt[i].addActionListener(this);
		}

		// ģ����
		friendroom_dlg.friend_jbt.addActionListener(this);
		friendroom_dlg.exit_jbt.addActionListener(this);
		for (int i = 0; i < friendroom_dlg.mbt.length; ++i) { // ���� ��ư
			friendroom_dlg.mbt[i].addActionListener(this);
		}

		// ���̷� - letter ����
		letter_dlg.ok_jbt.addActionListener(this);
		letter_dlg.cancel_jbt.addActionListener(this);

		// ���̷� - view ����
		view_dlg.bt_exit.addActionListener(this);

		// ģ���� - letter ����
		f_letter_dlg.ok_jbt.addActionListener(this);
		f_letter_dlg.cancel_jbt.addActionListener(this);

		// ģ���� - view ����
		f_view_dlg.bt_exit.addActionListener(this);

		// ����
		dudu_dlg.rank_jbt.addActionListener(this);
		rock_dlg.bt6.addActionListener(this);
		zero_dlg.rank_jbt.addActionListener(this);

	}

	// â�� Ȱ��ȭ�Ǹ� ȣ��
	public void start() {
		try {
			// �ؽ�Ʈ ���� ����
			ia = InetAddress.getByName("localhost");
			soc = new Socket(ia, 12345);
			Thread th = new Thread(this);
			th.start();
		} catch (Exception e) {e.printStackTrace();}
	}

//���� ���
//----------------------------------------------------------------------------------------------
	// ȭ�鿡 ĳ���� �߰�
	private void NewCharacterIn(CharacterLabel cl) {
		main_jp1.add(cl);
		cl.setSize(60, 95);
		cl.setX(getRandomPosition(mainWidth));
		cl.setY(getRandomPosition(mainHeight));
		setCharacterLocation(cl);
	}

	// ��ǥ�� ����� ������ ���̴� ��ġ �缳��
	private void setCharacterLocation(CharacterLabel cl) {
		cl.setLocation(cl.getX(), cl.getY());
	}

	// ������ǥ ���
	private int getRandomPosition(int size) {
		return (int) ((size - 80) * Math.random() + 1);
	}
	
	// ������ â ����
	private void setUserJta() {
		user_jta.setText("��" + myNickname + "\n");
		Enumeration<String> enu = characterHt.keys();
		while (enu.hasMoreElements()) {
			String nicks = enu.nextElement();
			user_jta.append("��" + nicks + "\n");
		}
	}
	
	// �� ���� �� ���ġ
	private void reAddHouse() {
		main_jp1.remove(gameCenter);
		main_jp1.remove(myRoom);
		
		main_jp1.add(gameCenter);
		gameCenter.setSize(100, 110);
		gameCenter.setLocation(gameCenter.getX(), gameCenter.getY());
		main_jp1.add(myRoom);
		myRoom.setSize(100, 110);
		myRoom.setLocation(myRoom.getX(), myRoom.getY());
	}

//�ֿ� ���
//----------------------------------------------------------------------------------------------	
	// ���κ��� �ʱ⼳��
	private void MainHallStart() throws Exception {
		// �� ĳ���� ����
		myNickname = id;
		mycharaNum = Integer.parseInt(myMember.getChara());
		myCharaLabel = new CharacterLabel(myNickname, mycharaNum, 0, 0); // db���� �޾ƿ;��� �г���, ĳ���͹�ȣ (x��ǥ, y��ǥ)-�̰� �ȹ޾Ƶε�
		NewCharacterIn(myCharaLabel);

		// �ǹ� ��ġ
		main_jp1.add(gameCenter);
		gameCenter.setSize(100, 110);
		gameCenter.setLocation(gameCenter.getX(), gameCenter.getY());
		main_jp1.add(myRoom);
		myRoom.setSize(100, 110);
		myRoom.setLocation(myRoom.getX(), myRoom.getY());

		// ������ �˸� �� �ʱ� ��ġ ����
		pw = new PrintWriter(soc.getOutputStream(), true);
		pw.println("#" + myNickname + ":" + myCharaLabel.getX() + ":" + myCharaLabel.getY() + ":" + mycharaNum);
		pw.flush();
	}

	// ������ �ؽ�Ʈ ����
	private void SendText(String str) throws Exception {
		pw = new PrintWriter(soc.getOutputStream(), true);
		pw.println(str);
		pw.flush();
	}

	// ��ǥ ��ȭ ����
	private void sendChangePosStr() {
		try {
			SendText("$" + myNickname + ":" + myCharaLabel.getX() + ":" + myCharaLabel.getY() + ":"
					+ myCharaLabel.getCharaNum());
		} catch (Exception ex) {ex.printStackTrace();}
	}

	// ���� �˸� ����
	private void sendExitStr() {
		try {
			SendText("%" + myNickname);
		} catch (Exception ex) {ex.printStackTrace();}
	}

	// �����κ��� �ؽ�Ʈ ����
	@Override
	public void run() {
		try {
			br = new BufferedReader(new InputStreamReader(soc.getInputStream()));
			while (true) {
				String msg = br.readLine();
				if (msg == null)
					break;
				AnalizeGotText(msg);
			}
		} catch (Exception e) {e.printStackTrace();}
	}

	// ���� �ؽ�Ʈ �ص� �� ����
	private void AnalizeGotText(String msg) {
		if (msg.charAt(0) == '#') { // �� ĳ���� ���� �˸� ���� //#�г���:x��ǥ:y��ǥ:ĳ���͹�ȣ
			String[] arr = msg.substring(1).split(":");
			String name = arr[0];
			int xpos = Integer.parseInt(arr[1]);
			int ypos = Integer.parseInt(arr[2]);
			int charNum = Integer.parseInt(arr[3]);

			if (!name.equals(myNickname)) {
				// ���ο� (����) ĳ���� ����
				characterHt.put(name, new CharacterLabel(name, charNum, xpos, ypos));
				CharacterLabel cl = characterHt.get(name);
				main_jp1.add(cl);
				cl.setSize(60, 95);
				setCharacterLocation(cl);
				
				reAddHouse();

				// �����ϼ̽��ϴ� �޽��� �����ֱ�
				chat_jta.append("[ " + name + " ]�� ���� * * * * * * * * * *\n");
			}
			setUserJta();
			
		} else if (msg.charAt(0) == '$') { // ��ġ ��ȭ �˸� ���� //$�г���:x��ǥ:y��ǥ:ĳ���͹�ȣ
			String[] arr = msg.substring(1).split(":");
			String name = arr[0];
			int xpos = Integer.parseInt(arr[1]);
			int ypos = Integer.parseInt(arr[2]);

			CharacterLabel cl = characterHt.get(name);
			cl.setX(xpos);
			cl.setY(ypos);
			setCharacterLocation(cl);
		} else if (msg.charAt(0) == '%') { // ���� �˸� ���� //%�г���
			String name = msg.substring(1);
			CharacterLabel cl = characterHt.get(name);
			main_jp1.remove(cl);
			this.repaint();
			characterHt.remove(name);
			setUserJta();
		} else if (msg.charAt(0) == '^') { // ���� ĳ���� ��ġ ����(�������ӽ�) //^�г���:x��ǥ:y��ǥ:ĳ���͹�ȣ
			String[] arr = msg.substring(1).split(":");
			String name = arr[0];
			int xpos = Integer.parseInt(arr[1]);
			int ypos = Integer.parseInt(arr[2]);
			int charNum = Integer.parseInt(arr[3]);
			
			//���� ĳ���� �߰�
			characterHt.put(name, new CharacterLabel(name, charNum, xpos, ypos));
			CharacterLabel cl = characterHt.get(name);
			main_jp1.add(cl);
			cl.setSize(60, 95);
			setCharacterLocation(cl);
			
			reAddHouse();
			
		} else if (msg.charAt(0) == '!') { // ä�� ����
			chat_jta.append(msg.substring(1) + "\n");
		}

		// ����
		else if (msg.charAt(0) == '@') {
			if (msg.charAt(1) == 'd') {
				rank_d = msg.substring(1);
				dudu_dlg.setRank(rank_d);
				gameDuduRank(gr);
			} else if (msg.charAt(1) == 'r') {
				rank_r = msg.substring(1);
				rock_dlg.setRank(rank_r);
				gameDuduRank(gz);
			} else if (msg.charAt(1) == 'z') {
				rank_z = msg.substring(1);
				zero_dlg.setRank(rank_z);
			}
		} else if (msg.substring(0, 2).equals("����")) {
			gameDuduRank(gd); // �ٽ� ���ӵ��� ��ŷ�����͸� �޾ƿ´�.
		}

		// ���̷�
		else if (msg.charAt(0) == ',') { // �ʱ� ������ ���� ��, �ޱ��
			if (msg.charAt(1) == ',') { // ģ��
				f_imfo = msg.substring(2);
				System.out.println(f_imfo);
				friendroom_dlg.firstSetting(f_imfo);
				friendroom_dlg.friendbtSetting();
				friendroom_dlg.setVisible(true);
			} else {
				imfo = msg.substring(1);
				myroom_dlg.firstSetting(imfo);
			}

		} else if (msg.substring(0, 2).equals("����")) { // ���Ͼ���, db���� �Ϸ� �� �������� "����"�� ������, �ٽ� ���̷��� �����Ѵ�.
			String[] data = msg.split(":");
			if (data[1].equals(id)) { // �� ����
				dataStart();
			} else {
				friendData(); // ģ�� ����
			}
		}
		
		//ģ�� �߰�â
		else if (msg.charAt(0) == 'm') {
			System.out.println(msg);
			naver.send(msg);	
		}

		// ȸ������&�α���
		//���Լ���
		else if (msg.equals("���Լ���")) {
			join_dlg.clear();
			join_dlg.setVisible(false);
			login_dlg.setVisible(true);
		
		//�α��� ����
		} else if (msg.equals("�α��κ������")) {
			JOptionPane.showMessageDialog(null, "��й�ȣ�� �ٽ� �Է����ּ���.", "�˸�", JOptionPane.ERROR_MESSAGE);
		} else if (msg.equals("�α��ξ��̵����")) {
			JOptionPane.showMessageDialog(null, "���� ȸ���� �ƴմϴ�.", "�˸�", JOptionPane.ERROR_MESSAGE);
		} else if (msg.equals("�̹� ������")) {
			JOptionPane.showMessageDialog(null, "�̹� �������Դϴ�.", "�˸�", JOptionPane.ERROR_MESSAGE);
		} else if (msg.equals("ģ����Ͼ���")) {
			JOptionPane.showMessageDialog(null, "ģ������� �����ϴ�.", "�˸�", JOptionPane.ERROR_MESSAGE);
		} else if (msg.equals("ģ������")) {
			JOptionPane.showMessageDialog(null, "�ش� id ģ���� �����ϴ�.", "�˸�", JOptionPane.ERROR_MESSAGE);
		}
		//�α��� ����
		else {
			String[] arr = msg.split("@");
			if (arr[0].equals("�α��μ���")) {
				myMember = new Member(arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9],
						arr[10], arr[11], arr[12], arr[13], arr[14]);
				JOptionPane.showMessageDialog(null, id + "�� ȯ���մϴ�.", "�˸�", JOptionPane.INFORMATION_MESSAGE);
				dataStart();
				login_dlg.setVisible(false);
				try {MainHallStart();} catch (Exception e) {e.printStackTrace();}
				this.setTitle(id + "�� �ڵ��� ����");
				this.setVisible(true);
			}
		} 
	}

//ȸ������&�α���
//----------------------------------------------------------------------------------------------	
	// ȸ������, �α��� �� ������ ��û ����
	public void joinData(String joinData) {
		try {
			pw = new PrintWriter(soc.getOutputStream(), true);
			pw.println(joinData);
			System.out.println("ȸ������ �� �α��� ��û");
			pw.flush();
		} catch (Exception e) {e.printStackTrace();}
	}
	
	// ȸ������ ��й�ȣ ����
	public boolean checkInputOnlyNumberAndAlphabet(String textInput) {
		char chrInput;
		for (int i = 0; i < textInput.length(); i++) {
			chrInput = textInput.charAt(i); // �Է¹��� �ؽ�Ʈ���� ���� �ϳ��ϳ� �����ͼ� üũ
			if (chrInput >= 0x61 && chrInput <= 0x7A) {
				// ����(�ҹ���) OK!
			} else if (chrInput >= 0x41 && chrInput <= 0x5A) {
				// ����(�빮��) OK!
			} else if (chrInput >= 0x30 && chrInput <= 0x39) {
			} // ���� OK!
			else {
				return false; // �����ڵ� �ƴϰ� ���ڵ� �ƴ�!
			}
		}
		return true;
	}
	
//���̷�
//----------------------------------------------------------------------------------------------	
	public void dataStart() {// �ʱ� ������ ���� ������ ��û
		pw.println("," + id);
		System.out.println("," + id + "���̷뵥���� ��û");
		pw.flush();
	}

	public void friendData() {// ģ�� ���̷� ������ ���� ������ ��û
		pw.println(",," + friend_id);
		pw.flush();
	}

	public void sendData(String letter, String id) { // ���� ����, ������ ���� ������
		String text = ':' + id + ':' + letter;
		pw.println(text); // :�̸�:��ȣ:2#����
		pw.flush();
	}

//����
//----------------------------------------------------------------------------------------------
	public void gameDuduRank(String game) { // ���Ӻ��� ��ŷ �����Ϳ�û
		pw.println("g" + game); // gdudu gzero grock
		pw.flush();
	}

	public void gameDuduSend(String game, int rank) { // 3�� ���� ��ũ ����
		String su = String.valueOf(rank);
		String text = '.' + game + ',' + id + ',' + su;
		pw.println(text); // .gr.�̸�.����
		pw.flush();
	}
	
//ģ���߰� ��û
//--------------------------------------------------------
public void naverData(String infor) { //������ ������ ��û
	try {
		pw.println(infor);		
		pw.flush();
		System.out.println("��û���ۿϷ�");
	} catch (Exception e) {
		e.printStackTrace();
	}
}

//�̺�Ʈ
//----------------------------------------------------------------------------------------------
	@Override
	public void actionPerformed(ActionEvent e) {
		// ä��â ok��ư ������
		if (e.getSource() == ok_jbt || e.getSource() == chat_jtf) {
			String msg = "!" + myNickname + ":";
			msg += chat_jtf.getText();
			pw.println(msg);
			pw.flush();
			chat_jtf.setText("");
			chat_jta.append(myNickname + " : " + msg.split(":")[1] + "\n");

			con.setFocusable(true);
			con.requestFocus();
		}

		//help ��ư
		else if (e.getSource() == mb2) {
			help_dlg.setVisible(true);
		}
		
		//ģ���߰� ��ư
		else if(e.getSource() == mb3) {	// ģ����� �������� ������ ��
			naver.setVisible(true);	
			naverData(mbi);
		}else if(e.getSource()==naver.search) {
			naver.dlg.setVisible(true);
			naver.dlg_ta.setText(naver.ddg);
		}else if (e.getSource()==naver.co_bt) {
			naver.dlg_co.setVisible(true);
			
		}else if (e.getSource()==naver.ok_bt) {
			//�Է��� �ֵ��� / ó�� ģ����õ â �ؽ�Ʈ���� �߰� �ϰ� �����ϱ�
			naver.dlg_co.setVisible(false);
			String memo1 = naver.dlg_co_center_left_ta.getText();
			String memo2 = naver.dlg_co_center_right_ta.getText();
			naver.ta.append(memo1+"\t");
			naver.ta.append(memo2);
			naver.ta.append("\n");
			naver.clearText();
		}

		// ����
		else if (e.getSource() == game_dlg.bt1) { // ���������� ���ӹ�ư
			rock_dlg.clearScore(); // ���������� ���Ӽ��� �ʱ�ȭ
			rock_dlg.setVisible(true);
		} else if (e.getSource() == rock_dlg.bt6) { // ���������� �ڶ��ϱ�
			int rank = rock_dlg.sendRank(); // ���� ���������� ��ũ���� ��������
			gameDuduSend(gr, rank); // ������ rank ������ ������

		} else if (e.getSource() == game_dlg.bt2) { // ȭ��ã�� ���ӹ�ư
			zero_dlg.clearBoomb(); // ȭ��ã�� ���Ӽ��� �ʱ�ȭ
			zero_dlg.clearScore(); // ȭ��ã�� ���Ӽ��� �ʱ�ȭ
			zero_dlg.setVisible(true);

		} else if (e.getSource() == zero_dlg.rank_jbt) { // ȭ��ã�� �ڶ��ϱ�
			int rank = zero_dlg.sendRank(); // ���� ȭ��ã�� ��ũ���� ��������
			gameDuduSend(gz, rank); // ������ rank ������ ������

		} else if (e.getSource() == game_dlg.bt3) { // �δ��� ���ӹ�ư
			dudu_dlg.clearScore();
			dudu_dlg.setVisible(true);

		} else if (e.getSource() == dudu_dlg.rank_jbt) { // �δ��� �ڶ��ϱ�
			int rank = dudu_dlg.sendRank(); // �δ����� ��ũ���� ��������
			gameDuduSend(gd, rank);

		// ȸ������
		} else if (e.getSource() == login_dlg.join_jbt) {
			join_dlg.setVisible(true);
		} else if (e.getSource() == join_dlg.ok_jbt || e.getSource() == join_dlg.color_jtf) {
			login = "0";// (������)
			String chara = join_dlg.getChara();
			id = join_dlg.getID();
			String pw = join_dlg.getPW();
			String name = join_dlg.getName();
			String mbti = join_dlg.getMbti();
			String hobby = join_dlg.getHobby();
			String food = join_dlg.getFood();
			String color = join_dlg.getColor();
			String memo1 = "0";
			String memo2 = "0";
			String memo3 = "0";
			String memo4 = "0";
			String memo5 = "0";

			if (chara.equals("0")) {
				JOptionPane.showMessageDialog(null, "ĳ���͸� ���� ���ּ���.", "�˸�", JOptionPane.ERROR_MESSAGE);
			} else if (id.length() == 0) {
				JOptionPane.showMessageDialog(null, "���̵� �Է� ���ּ���.", "�˸�", JOptionPane.ERROR_MESSAGE);
			} else if (pw.length() == 0) {
				JOptionPane.showMessageDialog(null, "��й�ȣ�� �Է� ���ּ���.", "�˸�", JOptionPane.ERROR_MESSAGE);
			} else if (!checkInputOnlyNumberAndAlphabet(pw)) {
				JOptionPane.showMessageDialog(null, "��й�ȣ�� �������� �Է����ּ���.", "�˸�", JOptionPane.ERROR_MESSAGE);
			} else if (name.length() == 0) {
				JOptionPane.showMessageDialog(null, "�̸��� �Է� ���ּ���.", "�˸�", JOptionPane.ERROR_MESSAGE);
			} else if (mbti.length() == 0) {
				JOptionPane.showMessageDialog(null, "MBTI�� �Է� ���ּ���.", "�˸�", JOptionPane.ERROR_MESSAGE);
			} else if (hobby.length() == 0) {
				JOptionPane.showMessageDialog(null, "��̸� �Է� ���ּ���.", "�˸�", JOptionPane.ERROR_MESSAGE);
			} else if (food.length() == 0) {
				JOptionPane.showMessageDialog(null, "�����ϴ� ������ �Է� ���ּ���.", "�˸�", JOptionPane.ERROR_MESSAGE);
			} else if (color.length() == 0) {
				JOptionPane.showMessageDialog(null, "�����ϴ� ������ �Է� ���ּ���.", "�˸�", JOptionPane.ERROR_MESSAGE);
			} else {
				joinData("j@" + login + "@" + chara + "@" + id + "@" + pw + "@" + name + "@" + mbti + "@" + hobby + "@"
						+ food + "@" + color + "@" + memo1 + "@" + memo2 + "@" + memo3 + "@" + memo4 + "@" + memo5);

				JOptionPane.showMessageDialog(null, "���� �Ǿ����ϴ�.", "�˸�", JOptionPane.INFORMATION_MESSAGE);
			}
		} else if (e.getSource() == join_dlg.cancel_jbt) { // ȸ������ ���
			join_dlg.setVisible(false);

		// �α���
		} else if (e.getSource() == login_dlg.login_jbt || e.getSource() == login_dlg.pw_jtf) {
			id = login_dlg.id_jtf.getText().trim(); // �α���â���� id�Է��Ѱ�
			String pw = login_dlg.pw_jtf.getText().trim(); // �α���â���� pw�Է��Ѱ�
			if (id.length() == 0) {
				JOptionPane.showMessageDialog(null, "���̵� �Է� ���ּ���.", "�˸�", JOptionPane.ERROR_MESSAGE);
			} else if (pw.length() == 0) {
				JOptionPane.showMessageDialog(null, "��й�ȣ�� �Է� ���ּ���.", "�˸�", JOptionPane.ERROR_MESSAGE);
			} else {
				joinData("l@" + id + "@" + pw); // login@id@pw �������� ���� (�α��νõ�)
			}
		}

		// ���̷�
		else if (e.getSource() == myroom_dlg.exit_jbt) { // ���̷��� ������ ��ư
			myroom_dlg.setVisible(false);

		} else if (e.getSource() == letter_dlg.ok_jbt) { // ���̷� - �������� â Ȯ��
			String letter = bt_num + ':' + letter_dlg.sendText(); // ��ȣ:2#���� ������ ����
			System.out.println(letter); // �׽�Ʈ
			sendData(letter, id); // ������ �ش� ���� ����
			letter_dlg.setVisible(false);

		} else if (e.getSource() == letter_dlg.cancel_jbt) { // ���̷� - �������� â ���
			letter_dlg.clearText();
			letter_dlg.setVisible(false);

		} else if (e.getSource() == myroom_dlg.friend_jbt) { // ���̷��� ����� ��ư
			friend_id = JOptionPane.showInputDialog(this, "ģ���� id �Է� : ", "�Է�", JOptionPane.QUESTION_MESSAGE);
			friendData();

		} else if (e.getSource() == view_dlg.bt_exit) { // ���̷� �������� â Ȯ��
			view_dlg.setVisible(false);

		} else if (e.getSource() == friendroom_dlg.exit_jbt) { // ģ���� ������ ��ư
			friendroom_dlg.setVisible(false);

		} else if (e.getSource() == f_letter_dlg.ok_jbt) { // ģ���� �������� â Ȯ��
			String letter = f_bt_num + ':' + f_letter_dlg.sendText(); // ��ȣ:2#���� ������ ����
			sendData(letter, friend_id); // ������ �ش� ���� ����
			f_letter_dlg.setVisible(false);

		} else if (e.getSource() == f_letter_dlg.cancel_jbt) { // ģ���� �������� â ���
			f_letter_dlg.clearText();
			f_letter_dlg.setVisible(false);

		} else if (e.getSource() == f_view_dlg.bt_exit) { // ģ���� �����б� â ������
			f_view_dlg.setVisible(false);

		} else {
			// ���̷��� ���� ��ư�� ������ ��
			for (int i = 0; i < 5; ++i) {
				if (e.getSource() == myroom_dlg.mbt[i]) {
					boolean result = myroom_dlg.buttonTrue(i);
					if (result) { // ��ư�� ���� ������
						String[] data = imfo.split(",");
						view_dlg.setText(data[i + 6]);
						view_dlg.setVisible(true);
					} else if (!result) { // ��ư�� ���� ������
						int su = i + 1;
						bt_num = String.valueOf(su);
						letter_dlg.setVisible(true);
					}
				// ģ���� ���� ��ư�� ������ ��
				} else if (e.getSource() == friendroom_dlg.mbt[i]) {
					boolean result = friendroom_dlg.buttonTrue(i);
					if (result) {
						String[] data = f_imfo.split(",");
						f_view_dlg.setText(data[i + 6]);
						f_view_dlg.setVisible(true);
					} else if (!result) { // ��ư�� ���� ������
						int su = i + 1;
						f_bt_num = String.valueOf(su);
						f_letter_dlg.setVisible(true);
					}
				}
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// WASD�� ����Ű�� �̵�
		if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (myCharaLabel.getX() >= -10) {
				myCharaLabel.setX(myCharaLabel.getX() - 10);
				setCharacterLocation(myCharaLabel);
				sendChangePosStr();
			} else {}
		} else if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (myCharaLabel.getX() <= (int) main_jp1.getWidth() - 50) {
				myCharaLabel.setX(myCharaLabel.getX() + 10);
				setCharacterLocation(myCharaLabel);
				sendChangePosStr();
			} else {}
		} else if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
			if (myCharaLabel.getY() >= 10) {
				myCharaLabel.setY(myCharaLabel.getY() - 10);
				setCharacterLocation(myCharaLabel);
				sendChangePosStr();
			} else {}
		} else if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (myCharaLabel.getY() <= (int) main_jp1.getHeight() - 100) {
				myCharaLabel.setY(myCharaLabel.getY() + 10);
				setCharacterLocation(myCharaLabel);
				sendChangePosStr();
			} else {}

			// �����̽��� �ǹ� ����
		} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			if (myCharaLabel.getX() >= myRoom.getX() - 80 && myCharaLabel.getX() <= myRoom.getX() + 80) {
				if (myCharaLabel.getY() >= myRoom.getY() - 100 && myCharaLabel.getY() <= myRoom.getY() + 100) {
					// ���̷����� �̵�
					myroom_dlg.firstSetting(imfo); // �ʱ� ������ ���̷��� ����
					System.out.println("���̷� ����!!");
					myroom_dlg.setVisible(true);
				}
			} else if (myCharaLabel.getX() >= gameCenter.getX() - 80 && myCharaLabel.getX() <= gameCenter.getX() + 80) {
				if (myCharaLabel.getY() >= gameCenter.getY() - 100 && myCharaLabel.getY() <= gameCenter.getY() + 100) {
					// ���Ӽ��ͷ� �̵�
					gameDuduRank(gd); // �δ��� ��ũ ��û (�δ�����ũ�� �޾�����, �ٸ����ӵ� ������� �����´�)
					System.out.println("���Ӽ��� ����!!");
					game_dlg.setVisible(true);
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) {
		if(e.getSource()==this) {
			introMusic.isLoop = false;
			sendExitStr();
			System.exit(0);
		}
		else if(e.getSource()==login_dlg) {
			introMusic.isLoop = false;
			System.exit(0);
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void windowClosed(WindowEvent e) {}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowDeactivated(WindowEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {
		con.setFocusable(true);
		con.requestFocus();
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
	
}

class MyButton01 extends Button { // button�� ��ӹ���
	private Image img;

	public MyButton01(Image img) {
		this.img = img;
	}

	public void paint(Graphics g) {
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
	}
}