package com.ctbc.fuck;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

// com.ctbc.fuck.MouseMoveCircle
// com/ctbc/fuck/MouseMoveCircle

public class MouseMoveCircle {

	private JFrame frameWindow;
	
	private static Thread th1 = new Thread(new Runnable() {
		@Override
		public void run() {
			while (true) {
				moveCircle(); //-------------  mouse move 
			}
		}
	});

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					MouseMoveCircle window = new MouseMoveCircle();
					window.frameWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * Create the application.
	 */
	public MouseMoveCircle() {
		initialize();
	}

	private static boolean IS_STARTING = false;

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frameWindow = new JFrame();
		frameWindow.setTitle("Window");
		frameWindow.setResizable(false);
		frameWindow.setBounds(100, 100, 277, 220);
		frameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameWindow.getContentPane().setLayout(null);
		frameWindow.setLocationRelativeTo(null); // 蹦出視窗時置中
		
		JButton myButton = new JButton("Start");
		myButton.setFont(new Font("微軟正黑體", Font.BOLD, 15));
		myButton.setBounds(31, 118, 210, 44);
		frameWindow.getContentPane().add(myButton);

		JLabel infoLabel = new JLabel("狀態列");
		infoLabel.setForeground(new Color(224, 255, 255));
		infoLabel.setBackground(new Color(0, 139, 139));
		infoLabel.setOpaque(true);
		infoLabel.setFont(new Font("微軟正黑體", Font.BOLD, 15));
		infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		infoLabel.setBounds(44, 27, 173, 57);
		frameWindow.getContentPane().add(infoLabel);

		//------------------------------------------------------
		//------------------------------------------------------
		//------------------------------------------------------

		myButton.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyCode() == 0) {
					System.out.println("=== keyTyped === ");
					IS_STARTING = !IS_STARTING;
					
					String msg1 = IS_STARTING ? "STOP(空白鍵停止)" : "START";
					myButton.setText(msg1);
					
					String msg2 = IS_STARTING ? "運行中...(空白鍵停止)" : "停止中...";
					infoLabel.setText(msg2);
					
					//------------------------------------
					try {
						th1.start();
					} catch (IllegalThreadStateException e1) {
						System.out.println(" >>> IllegalThreadStateException <<< ");
						// e1.printStackTrace();
					}
			    }
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				System.out.println("=== keyReleased ===");
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				System.out.println("=== keyPressed ===");
			}
		});
		
//		myButton.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				isStarting = !isStarting;
//
//				String msg1 = isStarting ? "STOP(空白鍵停止)" : "START";
//				myButton.setText(msg1);
//
//				String msg2 = isStarting ? "運行中...(空白鍵停止)" : "停止中...";
//				infoLabel.setText(msg2);
//
//				//------------------------------------
//				try {
//					th1.start();
//				} catch (IllegalThreadStateException e1) {
//					System.out.println(" >>> IllegalThreadStateException <<< ");
//					// e1.printStackTrace();
//				}
//			}
//		});

	}

	private static String moveCircle() {

		class MyPoint {

			double posX;
			double posY;

			public MyPoint(double posX, double posY) {
				this.posX = posX;
				this.posY = posY;
			}

			@Override
			public String toString() {
				return "x = " + this.posX + "  , y = " + this.posY;
			}
		}

		Robot robot = null;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}

		List<MyPoint> pointList = new ArrayList<>();
		MyPoint orign = new MyPoint(1000d, 500d);
		int rr = 100;
		for (int deg = 0 ; deg < 360 ; deg++) {
			double x = rr * Math.cos(angToRad(deg)) + orign.posX;
			double y = rr * Math.sin(angToRad(deg)) + orign.posY;
			pointList.add(new MyPoint(x, y));
		}

//		for (MyPoint myPoint : pointList) {
//			System.out.println(myPoint);
//		}

		int i = 0;
		while (true) {
			if (IS_STARTING == false) {
				// System.out.println("stop");
				return "STOP";
			}
			robot.mouseMove((int) pointList.get(i).posX, (int) pointList.get(i).posY);
			i++;

			if (i == 360) {
				i = 0;
			}

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	private static double angToRad(int ang) {
		return ang * (Math.PI / 180);
	}
}
