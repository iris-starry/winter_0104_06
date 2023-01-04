package winter_0104_06;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class runshow extends JFrame {
	JLabel[] runch = new JLabel[3];
	runThread[] rts = new runThread[runch.length];
	int[] winnerIndex = new int[runch.length];
	int index;
	String[] comboStr = {"1번: 대학생", "2번: 사회인", "3번: 연예인"};
	JComboBox<String> combo = new JComboBox<String>(comboStr);
	int betingIndex;
	
	public runshow() {		
		JPanel pan = new JPanel(null);
		pan.setBackground(Color.white);
		ImageIcon icon = null;
		JLabel lineLbl = new JLabel(new ImageIcon("image/line.png"));
		lineLbl.setBounds(600, 5, 5, 550);
		JLabel flagLbl = new JLabel(new ImageIcon("image/flag.png"));
		flagLbl.setBounds(600, 10, 20, 27);
		pan.add(lineLbl);
		pan.add(flagLbl);
		
		JPanel panN = new JPanel();
		panN.setBackground(Color.RED);
		JButton btnBeting = new JButton("게임배팅");
		JButton btnStart = new JButton("게임시작");
		btnBeting.addActionListener(btnL);
		btnStart.addActionListener(btnL);
		panN.add(combo);
		panN.add(btnBeting);
		panN.add(btnStart);
		
		
		for (int i = 0; i < runch.length; i++) {
			icon = new ImageIcon("image/image"+i+".gif");
			runch[i] = new JLabel(icon);
			runch[i].setLocation(0, 10 + (i*170));
			runch[i].setSize(200, 150);
			pan.add(runch[i]);
		}
		
		
		add(pan, "Center");
		add(panN, "North");
		setTitle("마라톤대회");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(50, 30, 800, 600);
		setVisible(true);
		setResizable(false);		
	}

	public static void main(String[] args) {
		new runshow();
	}
	
	ActionListener btnL = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
			case "게임배팅":
				betingIndex = combo.getSelectedIndex();
				break;
			case "게임시작":
				for (int i = 0; i < runch.length; i++) {
					runThread t = new runThread(runch[i],"stip"+i, i);
					t.start();
				}
				break;
			}
			
		}
	};

	public class runThread extends Thread{
		JLabel lblrun;
		String stopImageName;
		int randomValue;
		int runIndex;
		
		public runThread(JLabel lblrun, String stopImageName, int runIndex) {
			this.lblrun = lblrun;
			this.stopImageName = stopImageName;
			this.runIndex = runIndex;
		}

		@Override
		public void run() {
			while (true) {
				lblrun.setLocation(lblrun.getX()+5, lblrun.getY());
				if(lblrun.getX()==540) {
					lblrun.setIcon(new ImageIcon("image/"+stopImageName+".gif"));
					winnerIndex[index++] = runIndex;
					if(index == runch.length-1) {
						JOptionPane.showMessageDialog(runshow.this, (winnerIndex[0]+1)+"번 분이 우승!!!");
						if(winnerIndex[0]==betingIndex)
							JOptionPane.showMessageDialog(runshow.this, "축하합니다. 배팅에 성공하였습니다.");
						else
							JOptionPane.showMessageDialog(runshow.this, "다음에 다시 배팅 부탁드려요~. 배팅에 실패하였습니다.");
						index = 0;
						for (int i = 0; i < runch.length; i++) {
							runch[i].setLocation(0, runch[i].getY());
							runch[i].setIcon(new ImageIcon("image/image"+i+".gif"));
						}
						
					}
					break;
				}
				try {
					Random random = new Random();
					randomValue = random.nextInt(10);
					sleep(10 * randomValue);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}			
		}
	}
}