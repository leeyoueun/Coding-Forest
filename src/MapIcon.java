
import javax.swing.*;
import java.awt.*;

public class MapIcon extends JLabel {
	// �̹��� ������: 100*90
	// �г� ��ü ������: 100*110
	public ImageIcon img; // �����̹���

	private JLabel sign;
	public JLabel icon;

	public String iconName;
	public int imageNum;
	private int x;
	private int y;

	// ������ (�̹��� ��ȣ�� �Ű������� �޾Ƽ� ����)
	public MapIcon(String iconName, int imageNum, int x, int y) {
		this.iconName = iconName;
		this.imageNum = imageNum;
		this.x = x;
		this.y = y;

		imgSetting(imageNum); // ĳ���� �̹��� ����
		this.sign = new JLabel(iconName, JLabel.CENTER);
		icon = new JLabel(img);

		this.setLayout(null);
		this.add(this.sign);
		this.sign.setLocation(0, 0);
		this.add(icon);
		icon.setLocation(0, 20);

		sign.setSize(100, 20);
		icon.setSize(100, 90);
		this.setSize(100, 110);

		this.setBackground(new Color(255, 0, 0, 0));

	}

	private void imgSetting(int num) {
		// 1�� ��
		if (num == 1) {
			img = setImageIconSize(new ImageIcon("img/main/house1.png"));
		}
		// 2�� ��
		if (num == 2) {
			img = setImageIconSize(new ImageIcon("img/main/house2.png"));
		}
		// 3�� ��
		if (num == 3) {
			img = setImageIconSize(new ImageIcon("img/main/house3.png"));
		}
	}

	// �̹��� ������ ����
	private ImageIcon setImageIconSize(ImageIcon ii) {
		Image img = ii.getImage().getScaledInstance(100, 90, Image.SCALE_FAST);
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

}
