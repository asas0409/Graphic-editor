package Graphic.editor;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Canvas extends JFrame implements ActionListener, MouseListener{
	int x1, x2, y1, y2 ;
	JButton btnLine, btnRect, btnCircle, btnEraser, btnPen ;
	String btnMODE = "LINE";
	JPanel panel = new JPanel();
	//Graphics a ;
	//Graphics2D g ;
	
	Graphics a ;
	Graphics2D g ;
	
	public static void main(String[] args) {
		new Canvas();	
	
	}
	/*
	 * Create the panel.
	 */
	public Canvas() {
		setVisible(true);
		getContentPane().setLayout(null);
		setBounds(100, 100, 800, 500);
		
		btnLine = new JButton("LINE");
		btnLine.setBounds(6, 6, 60, 60);
		btnLine.addActionListener(this);
		getContentPane().add(btnLine);
		
		btnRect = new JButton("RECT");
		btnRect.setBounds(78, 6, 60, 60);
		btnRect.addActionListener(this);
		getContentPane().add(btnRect);
		
		btnCircle = new JButton("ONE");
		btnCircle.setBounds(150, 6, 60, 60);
		btnCircle.addActionListener(this);
		getContentPane().add(btnCircle);
		
		btnEraser = new JButton("ERASER");
		btnEraser.setBounds(222, 6, 60, 60);
		btnEraser.addActionListener(this);
		getContentPane().add(btnEraser);
		
		btnPen = new JButton("PEN");
		btnPen.setBounds(294, 6, 60, 60);
		btnPen.addActionListener(this);
		getContentPane().add(btnPen);
		
		panel.setBounds(6, 72, 788, 394);
		panel.addMouseListener(this);
		getContentPane().add(panel);
		
		a = panel.getGraphics() ;
		g = (Graphics2D)a ;
		
	}
	
	public void paint() {
		g.setColor(Color.black);
		if(btnMODE.equals("LINE"))
			g.drawLine(x1, y1, x2, y2);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//if(e.getSource()==btnLine) {
		//	btnMODE="LINE";
		//}
		String pressButton = e.getActionCommand() ;
		btnON(pressButton) ;
		
	}
	
	public void btnON(String s) {
		btnMODE = s ;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		x1 = e.getX() ;
		y1 = e.getY() ;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		x2 = e.getX() ;
		y2 = e.getY() ;
		paint() ;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
