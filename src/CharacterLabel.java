
import javax.swing.*;
import java.awt.*;

// �̹��� ������: 60*75
// �г� ��ü ������: 60*95
public class CharacterLabel extends JPanel{
	public ImageIcon img_default;
	
	public ImageIcon img;	// �����̹���
	
	private JLabel nick;
	public JLabel chara;
	
	public String nickName;
	public int charaNum;
	private int x;
	private int y;
	
	//������ (ĳ���� ��ȣ�� �Ű������� �޾Ƽ� ����)
	public CharacterLabel(String name, int charaNum, int x, int y){
		this.charaNum = charaNum;
		this.nickName = name;
		this.x = x;
		this.y = y;
		
		imgSetting(charaNum); //ĳ���� �̹��� ����
		this.nick = new JLabel(name, JLabel.CENTER);
		chara = new JLabel(img);
		
		this.setLayout(null);
		this.add(this.nick);
		this.nick.setLocation(0, 0);
		this.add(chara);
		chara.setLocation(0, 20);
		
		nick.setSize(60, 20);
		chara.setSize(60, 75);
		this.setSize(60, 95);
		
		this.setBackground(new Color(255, 0, 0, 0));
		
	}
	
	private void imgSetting(int num) {
		//1�� ĳ����
		if(num==1) {
			img_default = setImageIconSize(new ImageIcon("img/main/pants.png"));
			img = img_default;
		}
		//2�� ĳ����
		if(num==2){
			img_default = setImageIconSize(new ImageIcon("img/main/dress.png"));
			img = img_default;
		}
	}
	
	//�̹��� ������ ����
	private ImageIcon setImageIconSize(ImageIcon ii) {
		Image img = ii.getImage().getScaledInstance(60, 75, Image.SCALE_FAST);
		return new ImageIcon(img);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getCharaNum() {
		return charaNum;
	}

}
