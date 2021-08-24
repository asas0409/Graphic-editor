package Graphic.editor;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.JMenuBar;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.JToolBar;

import java.awt.BorderLayout;
import java.awt.Button;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import java.awt.ScrollPane;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import java.awt.Choice;
import java.awt.SystemColor;

public class 그림판 {

   private JFrame frame,colorMenu;
   
   Graphics a;
   
   JPanel canvas = new JPanel();
   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               그림판 window = new 그림판();
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      });
   }

   public 그림판() {
      initialize();
   }

   /**
    * Initialize the contents of the frame.
    */
   private void initialize() {
      
      
      
      frame = new JFrame();
      frame.setBackground(UIManager.getColor("ScrollBar.thumbShadow"));
      frame.getContentPane().setBackground(new Color(0, 0, 255));
      frame.setBounds(100, 100, 922, 609);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.getContentPane().setLayout(null);
      frame.setVisible(true);
      
      JButton MoreColor1 = new JButton("");
      MoreColor1.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            Color color=JColorChooser.showDialog(null, "Color Menu", Color.black);
         }
      });
      MoreColor1.setBackground(new Color(255, 255, 255));
      MoreColor1.setIcon(new ImageIcon("C:\\Users\\win10\\Desktop\\\uADF8\uB9AC\uAE30\uB3C4\uAD6C\\color.PNG"));
      MoreColor1.setBounds(498, 15, 65, 60);
      frame.getContentPane().add(MoreColor1);
      
      Button Red = new Button("");
      Red.setBackground(Color.RED);
      Red.setBounds(328, 15, 28, 27);
      frame.getContentPane().add(Red);
      
      Button Orange = new Button("");
      Orange.setBackground(Color.ORANGE);
      Orange.setBounds(362, 15, 28, 27);
      frame.getContentPane().add(Orange);
      
      Button Yellow = new Button("");
      Yellow.setBackground(Color.YELLOW);
      Yellow.setBounds(396, 15, 28, 27);
      frame.getContentPane().add(Yellow);
      
      Button Green = new Button("");
      Green.setBackground(Color.GREEN);
      Green.setBounds(430, 15, 28, 27);
      frame.getContentPane().add(Green);
      
      Button Blue = new Button("");
      Blue.setBackground(Color.BLUE);
      Blue.setBounds(328, 48, 28, 27);
      frame.getContentPane().add(Blue);
      
      Button Navy = new Button("");
      Navy.setBackground(new Color(0, 0, 128));
      Navy.setBounds(362, 48, 28, 27);
      frame.getContentPane().add(Navy);
      
      Button Purple = new Button("");
      Purple.setBackground(new Color(128, 0, 128));
      Purple.setBounds(396, 48, 28, 27);
      frame.getContentPane().add(Purple);
      
      Button White = new Button("");
      White.setBackground(new Color(255, 255, 255));
      White.setBounds(430, 48, 28, 27);
      frame.getContentPane().add(White);
      
      Button Black = new Button("");
      Black.setBackground(Color.BLACK);
      Black.setBounds(464, 15, 28, 27);
      frame.getContentPane().add(Black);
      
      Button Grey = new Button("");
      Grey.setBackground(new Color(192, 192, 192));
      Grey.setBounds(464, 48, 28, 27);
      frame.getContentPane().add(Grey);
      
      
      Canvas canvas_1 = new Canvas();
      canvas_1.setBackground(SystemColor.menu);
      canvas_1.setBounds(318, 0, 258, 83);
      frame.getContentPane().add(canvas_1);
      
      JButton btnLine = new JButton("");
      btnLine.setIcon(new ImageIcon("C:\\Users\\win10\\Desktop\\\uADF8\uB9AC\uAE30\uB3C4\uAD6C\\Line.PNG"));
      btnLine.setBackground(Color.WHITE);
      btnLine.setBounds(0, 5, 35, 37);
      frame.getContentPane().add(btnLine);
      
      JButton btnCircle = new JButton("");
      btnCircle.setIcon(new ImageIcon("C:\\Users\\win10\\Desktop\\\uADF8\uB9AC\uAE30\uB3C4\uAD6C\\Circle.PNG"));
      btnCircle.setBackground(Color.WHITE);
      btnCircle.setBounds(0, 46, 35, 37);
      frame.getContentPane().add(btnCircle);
      
      JButton btnRect = new JButton("");
      btnRect.setIcon(new ImageIcon("C:\\Users\\win10\\Desktop\\\uADF8\uB9AC\uAE30\uB3C4\uAD6C\\Rect.PNG"));
      btnRect.setBackground(Color.WHITE);
      btnRect.setBounds(45, 5, 35, 37);
      frame.getContentPane().add(btnRect);
      
      JButton btnEraser = new JButton("");
      btnEraser.setIcon(new ImageIcon("C:\\Users\\win10\\Desktop\\\uADF8\uB9AC\uAE30\uB3C4\uAD6C\\Eraser.PNG"));
      btnEraser.setBackground(Color.WHITE);
      btnEraser.setBounds(45, 46, 37, 37);
      frame.getContentPane().add(btnEraser);
      
      JButton btnSelect = new JButton("");
      btnSelect.setIcon(new ImageIcon("C:\\Users\\win10\\Desktop\\\uADF8\uB9AC\uAE30\uB3C4\uAD6C\\Select.PNG"));
      btnSelect.setBackground(Color.WHITE);
      btnSelect.setBounds(92, 5, 35, 37);
      frame.getContentPane().add(btnSelect);
      
      JButton btnSketch = new JButton("");
      btnSketch.setIcon(new ImageIcon("C:\\Users\\win10\\Desktop\\\uADF8\uB9AC\uAE30\uB3C4\uAD6C\\Sketch.PNG"));
      btnSketch.setBackground(Color.WHITE);
      btnSketch.setBounds(92, 46, 35, 37);
      frame.getContentPane().add(btnSketch);
      
      JComboBox BoldSize = new JComboBox();
      BoldSize.addItem(new ImageIcon("C:\\Users\\win10\\Desktop\\그리기도구\\1px.PNG"));
      BoldSize.addItem(new ImageIcon("C:\\Users\\win10\\Desktop\\그리기도구\\3px.PNG"));
      BoldSize.addItem(new ImageIcon("C:\\Users\\win10\\Desktop\\그리기도구\\5px.PNG"));
      BoldSize.addItem(new ImageIcon("C:\\Users\\win10\\Desktop\\그리기도구\\8px.PNG"));
      BoldSize.setBounds(152, 5, 133, 83);
      frame.getContentPane().add(BoldSize);
      
      
     // JPanel canvas = new JPanel();
      canvas.setBounds(0, 88, 898, 489);
      canvas.setForeground(UIManager.getColor("ScrollBar.trackForeground"));
      canvas.setBackground(new Color(1, 255, 255));
      frame.getContentPane().add(canvas);
      
      a=canvas.getGraphics();
      
      
      JLabel lblNewLabel = new JLabel("New label");
      lblNewLabel.setIcon(new ImageIcon("C:\\Users\\win10\\Desktop\\\uADF8\uB9AC\uAE30\uB3C4\uAD6C\\background.jpg"));
      lblNewLabel.setBounds(0, 0, 908, 577);
      frame.getContentPane().add(lblNewLabel);

   }

}
