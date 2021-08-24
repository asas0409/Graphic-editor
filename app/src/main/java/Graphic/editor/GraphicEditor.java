package Graphic.editor;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

class p implements Serializable{
	int x1,y1,x2,y2;
	int tx, ty, bx, by;
	int stroke;
	String shape;
	Color col;
	int penNum;
	boolean isFilled = false;
	
	p(int x1,int y1,int x2,int y2,String shape,Color col,int stroke) {
		this.x1=x1;
		this.y1=y1;
		this.x2=x2;
		this.y2=y2;
		this.shape = shape;
		this.col = col;
		this.stroke = stroke;
	}
	
	p(String shape, int stroke, Color col, int penNum){
		this.shape = shape;
		this.stroke = stroke;
		this.col = col;
		this.penNum = penNum;
	}
}

class point implements Serializable{
	int x1, y1;
	
	point(int x1, int y1){
		this.x1 = x1;
		this.y1 = y1;
	}
}



public class GraphicEditor extends JFrame implements MouseListener, ActionListener,MouseMotionListener{
	String[] mode = {"line","rect","oval","sketch","fill","arrow"};
	String drawMode = "line";
	JPanel paint_panel = new JPanel();
	JPanel menu = new JPanel();
	JButton line = new JButton("/");
	JButton rect = new JButton("ㅁ");
	JButton oval = new JButton("O");
	JButton sketch = new JButton("✏");
	JButton color = new JButton("color");
	JButton fill = new JButton("fill");
	JButton undo = new JButton("undo");
	JButton redo = new JButton("redo");
	JButton arrow = new JButton("arrow");
	JButton save = new JButton("save");
	JButton open = new JButton("open");
	JButton test = new JButton("test");
	JTextField size = new JTextField("5");
	ArrayList<p> history = new ArrayList<p>();
	ArrayList<p> fixedHistory = new ArrayList<p>();
	ArrayList<point> penLog= new ArrayList<point>();
	ArrayList<ArrayList<point>> penHistory = new ArrayList<ArrayList<point>>();
	int count = 0;
	int penCounter = 0;
	
	
	Graphics a;
	Graphics2D g;
	Color selectedColor = Color.BLACK;
	int x1,y1,x2,y2;
	Point toFill;
	
	
	
	
	public static void main(String[] args) {
		GraphicEditor myGraphicEditor = new GraphicEditor();
		myGraphicEditor.setDefaultCloseOperation(EXIT_ON_CLOSE); 
	}
	
	GraphicEditor(){
		setSize(950,600);
		setVisible(true);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		getContentPane().add(menu);
		getContentPane().add(paint_panel);
		try {
		UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		SwingUtilities.updateComponentTreeUI (this);
		}catch(Exception e) {
			
		}
		
		paint_panel.addMouseListener(this);
		paint_panel.addMouseMotionListener(this);
		
		menu.setBounds(0,0,950,50);
		menu.setLayout(null);
		
		paint_panel.setBounds(0,50,950,550);
		paint_panel.setBackground(Color.WHITE);
		
	    line.setBounds(0,0,70,50);
	    line.setFont(new Font("굴림",Font.BOLD,20));
	    line.setFocusable(false);
	    line.addActionListener(this);
	    
	    rect.setBounds(70,0,70,50);
	    rect.setFont(new Font("굴림",Font.BOLD,20));
	    rect.setFocusable(false);
	    rect.addActionListener(this);
	    
	    oval.setBounds(140,0,70,50);
	    oval.setFont(new Font("굴림",Font.BOLD,20));
	    oval.setFocusable(false);
	    oval.addActionListener(this);
	    
	    sketch.setBounds(210,0,70,50);
	    sketch.setFont(new Font("함초롬돋움",Font.BOLD,30));
	    sketch.setFocusable(false);
	    sketch.addActionListener(this);

	    color.setBounds(350,0,70,50);
	    color.setFocusable(false);
	    color.addActionListener(this);
	    
	    size.setBounds(490,0,70,50);
	    size.setHorizontalAlignment(JTextField.RIGHT);
	    size.setFont(new Font("고딕",Font.ITALIC,20));

	    fill.setBounds(420,0,70,50);
	    fill.setFocusable(false);
	    fill.addActionListener(this);
	    
	    undo.setBounds(560,0,70,50);
	    undo.setFocusable(false);
	    undo.addActionListener(this);
	
	    redo.setBounds(630,0,70,50);
	    redo.setFocusable(false);
	    redo.addActionListener(this);

	    arrow.setBounds(280,0,70,50);
	    arrow.setFocusable(false);
	    arrow.addActionListener(this);
	    
	    save.setBounds(700,0,70,50);
	    save.setFocusable(false);
	    save.addActionListener(this);
	    
	    open.setBounds(770,0,70,50);
	    open.setFocusable(false);
	    open.addActionListener(this);
	    
	    test.setBounds(840,0,70,50);
	    test.setFocusable(false);
	    test.addActionListener(this);
	    
	    menu.add(line);
	    menu.add(rect);
	    menu.add(oval);
	    menu.add(sketch);
	    menu.add(color);
	    menu.add(size);
	    menu.add(fill);
	    menu.add(undo);
	    menu.add(redo);
	    menu.add(arrow);
	    menu.add(save);
	    menu.add(open);
	    menu.add(test);
	    

	     
	    a = paint_panel.getGraphics();
	    g = (Graphics2D)a;
	}
	
	public void repaint(int n) {
		int  a1,b1,a2,b2;
		Color c;
		int bold;
		int penNum;
		for(int i = 0; i<n;i++) {
			a1 = fixedHistory.get(i).x1;
			b1 = fixedHistory.get(i).y1;
			a2 = fixedHistory.get(i).x2;
			b2 = fixedHistory.get(i).y2;
			c = fixedHistory.get(i).col;
			bold = fixedHistory.get(i).stroke;
			penNum = fixedHistory.get(i).penNum;
			if(fixedHistory.get(i).shape.equals("rect")) {
				g.setColor(c);
				g.setStroke(new BasicStroke(bold));
				g.drawRect(a1, b1, a2-a1, b2-b1);
			}else if(fixedHistory.get(i).shape.equals("oval")) {
				g.setColor(c);
				g.setStroke(new BasicStroke(bold));
				g.drawOval(a1, b1, a2-a1, b2-b1);
			}else if(fixedHistory.get(i).shape.equals("line")) {
				g.setColor(c);
				g.setStroke(new BasicStroke(bold));
				g.drawLine(a1, b1, a2, b2);
			}else if(fixedHistory.get(i).shape.equals("arrow")) {
				g.setColor(c);
				g.setStroke(new BasicStroke(bold));
				drawArrow(a1, b1, a2, b2);
			}else if(fixedHistory.get(i).shape.equals("sketch")) {
				int x1 = 0,y1=0,x2,y2;
				g.setColor(c);
				g.setStroke(new BasicStroke(bold));
				ArrayList<point> temp = penHistory.get(penNum-1);
				for(int j=0;j<temp.size();j++) {
					if(j==0) {
						x1 = temp.get(j).x1;
						y1 = temp.get(i).y1;
						continue;
					}else {
						x2 = temp.get(j).x1;
						y2 = temp.get(j).y1;
	             	}
					g.setStroke(new BasicStroke(bold));
					g.drawLine(x1, y1, x2, y2);
					x1=x2;
					y1=y2;
				}
			}
			g.setColor(selectedColor);
			
		}
	}
	public void paint() {
		g.setStroke(new BasicStroke(Integer.parseInt(size.getText())));
		if(drawMode.equals("line")) {
			history.add(new p(x1,y1,x2,y2,"line",selectedColor,Integer.parseInt(size.getText())));
			fixedHistory.add(new p(x1,y1,x2,y2,"line",selectedColor,Integer.parseInt(size.getText())));
			count++;
			g.drawLine(x1,y1,x2,y2);
		}
		else if(drawMode.equals("rect")) {
			history.add(new p(x1,y1,x2,y2,"rect",selectedColor,Integer.parseInt(size.getText())));
			fixedHistory.add(new p(x1,y1,x2,y2,"rect",selectedColor,Integer.parseInt(size.getText())));
			count++;
			g.drawRect(x1, y1, x2-x1, y2-y1);
		}
		else if(drawMode.equals("oval")) {
			history.add(new p(x1,y1,x2,y2,"oval",selectedColor,Integer.parseInt(size.getText())));
			fixedHistory.add(new p(x1,y1,x2,y2,"oval",selectedColor,Integer.parseInt(size.getText())));
			count++;
			g.drawOval(x1, y1, x2-x1, y2-y1);
		}else if(drawMode.equals("arrow")) {
			history.add(new p(x1,y1,x2,y2,"arrow",selectedColor,Integer.parseInt(size.getText())));
			fixedHistory.add(new p(x1,y1,x2,y2,"arrow",selectedColor,Integer.parseInt(size.getText())));
			count++;
			drawArrow(x1,y1,x2,y2);
		}
	}
	
	public void drawArrow(int x1,int y1,int x2,int y2) {
		g.drawLine(x1, (y1+y2)/2, (x1+x2)/2, y1);
		g.drawLine(x1, (y1+y2)/2, (x1+x2)/2, y2);
		g.drawLine( (x1+x2)/2, y1,(x1+x2)/2, (y2+2*y1)/3);
		g.drawLine( (x1+x2)/2, (2*y2+y1)/3, (x1+x2)/2, y2);
		g.drawLine((x1+x2)/2,(y2+2*y1)/3, x2, (y2+2*y1)/3);
		g.drawLine((x1+x2)/2,(2*y2+y1)/3, x2,(2*y2+y1)/3);
		g.drawLine(x2, (y2+2*y1)/3, x2,(2*y2+y1)/3);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(drawMode.equals("fill")) {
		toFill = e.getPoint();
		for(int i = 0; i<count;i++) {
			int bold = history.get(i).stroke;
			history.get(i).tx = history.get(i).x1+bold/2;
			history.get(i).ty = history.get(i).y1+bold/2;
			history.get(i).bx = history.get(i).x2-bold/2;
			history.get(i).by = history.get(i).y2-bold/2;
			String type = history.get(i).shape;
			if( (toFill.getX()>history.get(i).tx&&toFill.getX()<history.get(i).bx) && ((toFill.getY()>history.get(i).ty)&&(toFill.getY()<history.get(i).by))) {
				if(type.equals("rect")) {
					g.fillRect(history.get(i).tx,history.get(i).ty,history.get(i).bx-history.get(i).tx,history.get(i).by-history.get(i).ty);
					history.get(i).isFilled = true;
				}
				if(type.equals("oval")) {
					g.fillOval(history.get(i).tx,history.get(i).ty,history.get(i).bx-history.get(i).tx,history.get(i).by-history.get(i).ty);
					history.get(i).isFilled = true;
				}
			}
		}
		}
	}
	
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		if(drawMode.equals("sketch")) {
			x2 = e.getX();
			y2 = e.getY();
			penLog.add(new point(x2,y2));
			g.setStroke(new BasicStroke(Integer.parseInt(size.getText())));
			g.drawLine(x1, y1, x2, y2);
			x1=x2;
			y1=y2;
		}else if(drawMode.equals("line")) {
			g.setColor(Color.white);
			g.setStroke(new BasicStroke(Integer.parseInt(size.getText())));
			g.drawLine(x1, y1, x2, y2);
			x2 = e.getX();
			y2 = e.getY();
			g.setColor(selectedColor);
			repaint(count);
			g.setStroke(new BasicStroke(Integer.parseInt(size.getText())));
			g.drawLine(x1,y1,x2,y2);
		}else if(drawMode.equals("rect")) {
			g.setColor(Color.white);
			g.setStroke(new BasicStroke(Integer.parseInt(size.getText())));
			g.drawRect(x1, y1, x2-x1, y2-y1);
			x2 = e.getX();
			y2 = e.getY();
			g.setColor(selectedColor);
			repaint(count);
			g.setStroke(new BasicStroke(Integer.parseInt(size.getText())));
			g.drawRect(x1,y1,x2-x1,y2-y1);
		}else if(drawMode.equals("oval")) {
			g.setColor(Color.white);
			g.setStroke(new BasicStroke(Integer.parseInt(size.getText())));
			g.drawOval(x1, y1, x2-x1, y2-y1);
			x2 = e.getX();
			y2 = e.getY();
			g.setColor(selectedColor);
			repaint(count);
			g.setStroke(new BasicStroke(Integer.parseInt(size.getText())));
			g.drawOval(x1,y1,x2-x1,y2-y1);
		}else if(drawMode.equals("arrow")) {
			g.setColor(Color.white);
			g.setStroke(new BasicStroke(Integer.parseInt(size.getText())));
			drawArrow(x1, y1, x2, y2);
			x2 = e.getX();
			y2 = e.getY();
			g.setColor(selectedColor);
			repaint(count);
			g.setStroke(new BasicStroke(Integer.parseInt(size.getText())));
			drawArrow(x1, y1, x2, y2);
		}
		
	}
	

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		x1 = e.getX();
		y1 = e.getY();
		if(drawMode.equals("sketch")) {
			penLog.add(new point(x1,y1));
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		x2 = e.getX();
		y2 = e.getY();
		paint();
		if(drawMode.equals("sketch")) {
			penCounter++;
			history.add(new p("sketch",Integer.parseInt(size.getText()),selectedColor,penCounter));
			fixedHistory.add(new p("sketch",Integer.parseInt(size.getText()),selectedColor,penCounter));
			penHistory.add((ArrayList<point>) penLog.clone());
			penLog.clear();
			count++;
		}
			//repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == line) {
			drawMode = mode[0];
		}else if(e.getSource() == rect) {
			drawMode = mode[1];
		}else if(e.getSource() == oval) {
			drawMode = mode[2];
		}else if(e.getSource() == sketch) {
			drawMode = mode[3];
		}else if(e.getSource() == color) {
			selectedColor = JColorChooser.showDialog(null, "Color", Color.BLACK);
			g.setColor(selectedColor);
		}else if(e.getSource() == fill) {
			drawMode=mode[4];
		}else if(e.getSource() == undo) {
			if(count>0) {
			int bold =  history.get(count-1).stroke;
			x1 = history.get(count-1).x1;
			y1 = history.get(count-1).y1;
			x2 = history.get(count-1).x2;
			y2 = history.get(count-1).y2;
			if(history.get(count-1).shape.equals("rect")) {
				g.setColor(Color.white);
				g.setStroke(new BasicStroke(bold));
				g.drawRect(x1, y1, x2-x1, y2-y1);
				g.fillRect(history.get(count-1).tx,history.get(count-1).ty,history.get(count-1).bx-history.get(count-1).tx,history.get(count-1).by-history.get(count-1).ty);
			}else if(history.get(count-1).shape.equals("oval")) {
				g.setColor(Color.white);
				g.setStroke(new BasicStroke(bold));
				g.drawOval(x1, y1, x2-x1, y2-y1);
				g.fillOval(history.get(count-1).tx,history.get(count-1).ty,history.get(count-1).bx-history.get(count-1).tx,history.get(count-1).by-history.get(count-1).ty);
			}else if(history.get(count-1).shape.equals("line")) {
				g.setColor(Color.white);
				g.setStroke(new BasicStroke(bold));
				g.drawLine(x1, y1, x2, y2);
			}else if(history.get(count-1).shape.equals("arrow")) {
				g.setColor(Color.white);
				g.setStroke(new BasicStroke(bold));
				drawArrow(x1, y1, x2, y2);
			}else if(history.get(count-1).shape.equals("sketch")) {
				if(penCounter>0) {
				g.setColor(Color.white);
				g.setStroke(new BasicStroke(bold));
				ArrayList<point> a = penHistory.get(penCounter-1);
				for(int i=0;i<a.size();i++) {
					if(i==0) {
						x1 = a.get(i).x1;
						y1 = a.get(i).y1;
						continue;
					}else {
						x2 = a.get(i).x1;
						y2 = a.get(i).y1;
	             	}
					g.setStroke(new BasicStroke(Integer.parseInt(size.getText())));
					g.drawLine(x1, y1, x2, y2);
					x1=x2;
					y1=y2;
				}
				penCounter--;
				g.setColor(selectedColor);
				g.setStroke(new BasicStroke(Integer.parseInt(size.getText())));
				}
			}
			repaint(count-1);
			g.setColor(selectedColor);
			fixedHistory.remove(count-1);
			count--;
			}
		}else if(e.getSource()==redo) {
			if(count<history.size()) {
				count ++;
				int  a1,b1,a2,b2;
				Color c;
				int bold;
				a1 = history.get(count-1).x1;
				b1 = history.get(count-1).y1;
				a2 = history.get(count-1).x2;
				b2 = history.get(count-1).y2;
				c = history.get(count-1).col;
				bold = history.get(count-1).stroke;
					if(history.get(count-1).shape.equals("rect")) {
						System.out.println(history.get(count-1).shape);
						g.setColor(c);
						g.setStroke(new BasicStroke(bold));
						g.drawRect(a1, b1, a2-a1, b2-b1);
						fixedHistory.add(new p(a1,b1,a2,b2,"rect",c,bold));
					}else if(history.get(count-1).shape.equals("oval")) {
						g.setColor(c);
						g.setStroke(new BasicStroke(bold));
						g.drawOval(a1, b1, a2-a1, b2-b1);
						fixedHistory.add(new p(a1,b1,a2,b2,"oval",c,bold));
					}else if(history.get(count-1).shape.equals("line")) {
						g.setColor(c);
						g.setStroke(new BasicStroke(bold));
						g.drawLine(a1, b1, a2, b2);
						fixedHistory.add(new p(a1,b1,a2,b2,"line",c,bold));
					}else if(history.get(count-1).shape.equals("arrow")) {
						g.setColor(c);
						g.setStroke(new BasicStroke(bold));
						drawArrow(a1, b1, a2, b2);
						fixedHistory.add(new p(x1,y1,x2,y2,"arrow",c,bold));
					}else if(history.get(count-1).shape.equals("sketch")) {
						{
					    g.setColor(c);
					    g.setStroke(new BasicStroke(bold));
						ArrayList<point> a = penHistory.get(penCounter);
						for(int i=0;i<a.size();i++) {
							if(i==0) {
								x1 = a.get(i).x1;
								y1 = a.get(i).y1;
								continue;
							}else {
								x2 = a.get(i).x1;
								y2 = a.get(i).y1;
			             	}
							g.setStroke(new BasicStroke(Integer.parseInt(size.getText())));
							g.drawLine(x1, y1, x2, y2);
							x1=x2;
							y1=y2;
						}
						g.setColor(selectedColor);
					}
					penCounter++;
					fixedHistory.add(new p("sketch",bold,c,penCounter));
					}
					repaint(count-1);
					g.setColor(selectedColor);
					g.setStroke(new BasicStroke(Integer.parseInt(size.getText())));
			}
		}else if(e.getSource() == arrow) {
			drawMode = mode[5];
		}else if(e.getSource() == save) {
			 FileDialog fsaveDlg = new FileDialog(this, "File Save", FileDialog.SAVE);
	         fsaveDlg.setVisible(true);
	            
	         String dir = fsaveDlg.getDirectory();
	         String file = fsaveDlg.getFile();
	            
	         if(dir == null || file == null) {
	               return;
	         }
	            
	         try {
	             ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File(dir,file))));
	             oos.writeObject(fixedHistory);
	             oos.close();
	            } catch(IOException ee) {
	            }
		}else if(e.getSource() == open) { // 파일 열기 (불러오는 데이터는 Vector.)
            FileDialog fopenDlg = new FileDialog(this, "File Open", FileDialog.LOAD);
            fopenDlg.setVisible(true);
            
            String dir = fopenDlg.getDirectory();
            String file = fopenDlg.getFile();

            if(dir == null || file == null) {
                return;
            }
            
            try {
                ObjectInputStream oos = new ObjectInputStream(
                    new BufferedInputStream(new FileInputStream(new File(dir,file))));
                fixedHistory = (ArrayList<p>)oos.readObject();
                history = (ArrayList<p>) fixedHistory.clone();
                count = fixedHistory.size();
                oos.close();
            } catch(IOException ee) {
            } catch(ClassNotFoundException ee) {}
            g.setColor(Color.white);
            g.fillRect(0, 0, 900, 550);
            repaint(fixedHistory.size());
            g.setColor(selectedColor);
		}else if(e.getSource()==test) {
			g.setColor(Color.white);
			for(int i=0;i<penLog.size();i++) {
				if(i==0) {
					x1 = penLog.get(i).x1;
					y1 = penLog.get(i).y1;
					continue;
				}else {
					x2 = penLog.get(i).x1;
					y2 = penLog.get(i).y1;
             	}
				g.setStroke(new BasicStroke(Integer.parseInt(size.getText())));
				g.drawLine(x1, y1, x2, y2);
				x1=x2;
				y1=y2;
			}
			g.setColor(selectedColor);
			penLog.clear();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
