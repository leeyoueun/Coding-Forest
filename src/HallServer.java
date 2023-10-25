
import java.net.*;
import java.io.*;
import java.util.*;

public class HallServer {
	private MemberDAO dao;
	private GameDAO GameDAO;
	private NaverDAO NaverDAO;

	private ServerSocket ss;
	private Socket soc;
	private PrintWriter pw;
	private BufferedReader br;

	private Hashtable<String, Socket> ht_soc; // <�г���, ����>
	private Hashtable<String, String> ht_pos = new Hashtable<>(); // <�г���, x��ǥ:y��ǥ:ĳ���͹�ȣ>

	// ���� ��ǥ�� ������, ���� ���� �� ��ǥ�� #name#x��ǥ#y��ǥ �� �ѹ��� �޾ƾ���.
	// ���� ������ �޼ҵ� #name#ĳ���� ��ȣ #x��ǥ#y��ǥ (�̰� ����Ʈ�� �������� �� �� ���� ����)
	// �޴� �ʿ��� ���� ��� �ڿ� ĳ���� ����.(����Ʈ ��ġ��)

	public HallServer() {
		dao = new MemberDAO();
		GameDAO = new GameDAO();
		NaverDAO = new NaverDAO();

		try {
			ss = new ServerSocket(12345);
			ht_soc = new Hashtable<>();

			while (true) {
				soc = ss.accept();
				OnlineClient oc = new OnlineClient(soc);
				oc.start();
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	// ���̷�&���� �� Ŭ���̾�Ʈ���� ������ ���� ��
	public void sendData(Socket soc, String str) throws Exception {
		pw = new PrintWriter(soc.getOutputStream(), true);
		pw.println(str);
		pw.flush();
	}

	// ������ ��ŷ������ �������� ���� �޼ҵ�
	public String view(String game) {
		String str = "";
		List<Game> list = GameDAO.listGame(game);
		for (Game g : list) {
			String text = '@' + g.getGame() + '#' + g.getID() + '#' + g.getTot();
			str = str + text;
		}
		return str;
	}
	
	//ģ���߰� â �޼ҵ�
	public String memberView() { 
		System.out.println("�������Ʈ �޾ƿ�");
		String str = "";
		List<Member> list = dao.listMember();
		for(Member g : list) {
			String text = 'm' + g.getID() + " - " + g.getName();
			System.out.println(text);
			str = str + text;
			System.out.println(str);
		}
		return str;
	}
		
	//ģ���߰�â �޼ҵ�
	public void memberData(Socket soc, String str) throws Exception{	
		pw = new PrintWriter(soc.getOutputStream(),true);
		pw.println(str);
		pw.flush();	
	}

//-------------------------------------------------------------------------------------------------------------------------------------------
	// Ŭ���̾�Ʈ �����ο��Լ� �ؽ�Ʈ�� ����
	class OnlineClient extends Thread {
		Socket soc;

		Member mb; // ����
		Member mb2; // ģ��
		Game game;

		OnlineClient(Socket soc) {
			this.soc = soc;
		}

		@Override
		public void run() {
			while (true) {
				try {
					br = new BufferedReader(new InputStreamReader(soc.getInputStream()));
					String str = br.readLine();
					if (str == null)
						break;
					if (str.charAt(0) == '$') {// ���� ĳ���� ��ġ��ȭ
						sendText(str);

						String[] arr = str.substring(1).split(":"); // �̸�:x��ǥ:y��ǥ:ĳ���͹�ȣ
						String name = arr[0];
						int xpos = Integer.parseInt(arr[1]);
						int ypos = Integer.parseInt(arr[2]);
						int charNum = Integer.parseInt(arr[3]);

						ht_pos.put(name, xpos + ":" + ypos + ":" + charNum);
					} else if (str.charAt(0) == '#') {// ���ο� ĳ���� ����
						String[] arr = str.substring(1).split(":"); // �̸�:x��ǥ:y��ǥ:ĳ���͹�ȣ
						String name = arr[0];
						int xpos = Integer.parseInt(arr[1]);
						int ypos = Integer.parseInt(arr[2]);
						int charNum = Integer.parseInt(arr[3]);

						// ���� ��Ͽ� �߰�
						ht_soc.put(name, soc);
						ht_pos.put(name, xpos + ":" + ypos + ":" + charNum);

						// �ش� Ŭ���̾�Ʈ���� ���� ������ ���� ����
						pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(soc.getOutputStream())));
						Enumeration<String> enu = ht_pos.keys();
						while (enu.hasMoreElements()) {
							String nicks = enu.nextElement();
							if (nicks.equals(name))
								continue;
							pw.println("^" + nicks + ":" + ht_pos.get(nicks)); // ^���г���:x��ǥ:y��ǥ:ĳ���͹�ȣ
							pw.flush();
						}

						sendTextAll(str);
					} else if (str.charAt(0) == '%') {// Ŭ���̾�Ʈ ����
						sendText(str);
						ht_soc.remove(str.substring(1));
						ht_pos.remove(str.substring(1));
						dao.LogOff (str.substring(1));
					} else if (str.charAt(0) == '!') {// ä���� ����
						sendText(str);
					}
					
					//ȸ������&�α���
					else if (str.charAt(0) == 'j') { // ȸ�����Խ� join@login@���·� �ޱ�
						String[] join = str.split("@");
						mb = new Member(join[1], join[2], join[3], join[4], join[5], join[6], join[7], join[8], join[9],
								join[10], join[11], join[12], join[13], join[14]);
						int res = dao.insertMember(mb);
						if (res > 0) {
							System.out.println(mb.getID() + "���� DB�� ����Ͽ����ϴ�.");
							sendData(soc, "���Լ���");

							List<Member> list = dao.listMember();
							for (int i = 0; i < list.size(); i++) {
								String[] data = list.get(i).toString().split("/");
							}
						} else {
							System.out.println(mb.getID() + "���� DB�� ��� �� ���� �߻�");
						}
					} else if (str.charAt(0) == 'l') { // �α��νõ� login@id@pw
						String[] login = str.split("@");
						String id = login[1];
						String pw = login[2];

						String login_ok = "";

						Member member = dao.getMember(id);

						if (member == null) {
							sendData(soc, "�α��ξ��̵����");
						} else {
							if (id.equals(member.getID()) && pw.equals(member.getPW())) {
								if (member.getLogin().equals("0")) {
									System.out.println(id + "," + member.getID() + "," + pw + "," + member.getPW() + ","+ member.getLogin());
									login_ok = "����";
									Member login_update = dao.getMember(id);
									login_update.setLogin("1");
									int res = dao.updatelogin(login_update);
									if (res > 0) {
										System.out.println(id + "���� �����ϼ̽��ϴ�.");
										sendData(soc, "�α��μ���"+"@"+login_update.getLogin()+"@"+login_update.getChara()+"@"+login_update.getID()
										+"@"+login_update.getPW()+"@"+login_update.getName()+"@"+login_update.getMbti()+"@"+login_update.getHobby()
										+"@"+login_update.getFood()+"@"+login_update.getColor()+"@"+login_update.getMemo1()+"@"+login_update.getMemo2()
										+"@"+login_update.getMemo3()+"@"+login_update.getMemo4()+"@"+login_update.getMemo5());
									} else {
										System.out.println(id + "�� ���ӽ� ���� �߻�!");
									}
								}else {
									System.out.println("�̹� ������");
									sendData(soc, "�̹� ������");
								}	
							} else {
								System.out.println("��й�ȣ ����");
								sendData(soc, "�α��κ������");
							}
						}
					
					// ���� 
					} else if (str.charAt(0) == 'g') { // ���� ��ŷ ������û�� ���� ��
						str = str.substring(1);

						if (str.equals("dudu")) {
							String text = view("dudu");
							System.out.println(text);
							sendData(soc, text);
							System.out.println("�εΰ��ӵ��������ۿϷ�");
						}

						else if (str.equals("rock")) {
							String text = view("rock");
							System.out.println(text);
							sendData(soc, text);
							System.out.println("�����ӵ��������ۿϷ�");
						}

						else if (str.equals("zero")) {
							String text = view("zero");
							sendData(soc, text);
							System.out.println("���ΰ��ӵ��������ۿϷ�");
						}

					} else if (str.charAt(0) == '.') { // �ڶ��ϱ�� ���� ������ ���� ��
						str = str.substring(1); // gr.�̸�.����
						String[] data = str.split(",");
						String game = data[0];
						String id = data[1];
						int tot = Integer.parseInt(data[2]);
						GameDAO.insertGame(game, id, tot);
						sendData(soc, "����");
					
					//���̷�
					} else if (str.charAt(0) == ':') { // ���� �ۼ����� DB ������Ʈ�� �ʿ��� ��
						str = str.substring(1); // :����
						String[] letter = str.split(":");
						mb = dao.getMember(letter[0]);

						if (letter[1].equals("1")) {
							mb.setMemo1(letter[2]);
						} else if (letter[1].equals("2")) {
							mb.setMemo2(letter[2]);
						} else if (letter[1].equals("3")) {
							mb.setMemo3(letter[2]);
						} else if (letter[1].equals("4")) {
							mb.setMemo4(letter[2]);
						} else {
							mb.setMemo5(letter[2]);
						}
						int res = dao.updateMemo(mb);
						if (res > 0) {
							System.out.println("���� �����Ϸ�");
							String send = "����:" + letter[0];
							sendData(soc, send);
						} else {
							System.out.println("���� ��������");
						}
						
					//ģ���߰�
					} else if (str.charAt(0) == 'm') {//����������̼�
						System.out.println("�Դ�");
						String ttt = memberView();
						memberData(soc, ttt);
						System.out.println("���ӵ��������ۿϷ�");
					} else if (str.charAt(0) == ',') { // ������ ������� �� ��
						if (str.charAt(1) == ',') { // ģ�� ������
							str = str.substring(2);
							mb = dao.getMember(str); // id�� �ش��ϴ� member
							String text = ",," + mb.getID() + ',' + mb.getChara() + ',' + mb.getMbti() + ','
									+ mb.getHobby() + ',' + mb.getFood() + ',' + mb.getColor() + ',' + mb.getMemo1()
									+ ',' + mb.getMemo2() + ',' + mb.getMemo3() + ',' + mb.getMemo4() + ','
									+ mb.getMemo5();
							sendData(soc, text);
							System.out.println("ģ�� ������ ���ۿϷ�");
						} else { // �� ������
							str = str.substring(1);
							mb = dao.getMember(str); // id�� �ش��ϴ� member
							String text = ',' + mb.getID() + ',' + mb.getChara() + ',' + mb.getMbti() + ','
									+ mb.getHobby() + ',' + mb.getFood() + ',' + mb.getColor() + ',' + mb.getMemo1()
									+ ',' + mb.getMemo2() + ',' + mb.getMemo3() + ',' + mb.getMemo4() + ','
									+ mb.getMemo5();
							sendData(soc, text);
							System.out.println("������Ʈ�� ������ ���ۿϷ�");
						}

					}

				} catch (Exception e) {e.getStackTrace();}

			}
		}
	}
	
//-------------------------------------------------------------------------------------------------------------------------------------------
	// ���� �ؽ�Ʈ�� ���� �� ���� �� �����ش�.
	private void sendText(String str) throws Exception {
		String[] arr = str.substring(1).split(":"); // �̸�:x��ǥ:y��ǥ:ĳ���͹�ȣ
		String nick = arr[0];
		Enumeration<String> enu = ht_soc.keys();
		while (enu.hasMoreElements()) {
			String nicks = enu.nextElement();
			if (nicks.equals(nick))
				continue; // ������ ���� Ŭ���̾�Ʈ�� ����
			Socket s = ht_soc.get(nicks);
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())));
			pw.println(str);
			pw.flush();
		}
	}

	// ���� �ؽ�Ʈ�� �� ���� ��ο��� �����ش�.
	private void sendTextAll(String str) throws Exception {
		Enumeration<String> enu = ht_soc.keys();
		while (enu.hasMoreElements()) {
			String nicks = enu.nextElement();
			Socket s = ht_soc.get(nicks);
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())));
			pw.println(str);
			pw.flush();
		}
	}

}