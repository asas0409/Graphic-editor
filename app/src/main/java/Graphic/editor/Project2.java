package Graphic.editor;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.MouseInfo;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;


public class Project2 extends JFrame {

    JPanel gui_panel, paint_panel; 
    // 현 그림판 프레임은 GUI구성 패널, 그려지는 패널로 구성
    
    JTextField txt = new JTextField() ;
    
   
    ArrayList<Info> nextline = new ArrayList<Info>();
    ArrayList<Info> line = new ArrayList<Info>();
    ArrayList<String> nextmode = new ArrayList<String>();
    ArrayList<Info> next = new ArrayList<Info>();
    ArrayList<Info> location = new ArrayList<Info>();
    ArrayList<String> Mo = new ArrayList<String>();
    JButton P;
    JButton N;
    JButton Polyline;
    JButton heart;
    JButton pencil_bt, eraser_bt; // 연필,지우개 도구를 선택하는 버튼
    JButton colorSelect_bt; // 색선택 버튼
    JButton rec_bt; // 사각형 버튼
    JButton circle_bt; // 원 버튼
    String mode= " ";
    boolean isFirst = true;
    JButton RubberBand;
    JButton Pointer;
    JButton Text;
    JButton plus;
    JButton minus;
    JButton fill;

    JLabel thicknessInfo_label, label; // 도구굵기 라벨

    JTextField thicknessControl_tf; // 도구굵기가 정해질 텍스트필드
    
    Color selectedColor; 
    // 현 변수에 컬러가 저장되어 추후에 펜색상을 정해주는 변수의 매개변수로 사용된다.
    
    Graphics graphics; // Graphics2D 클래스의 사용을 위해 선언
    Graphics2D g;
    // Graphics2D는 쉽게 말해 기존 graphics의 상위버전이라고 생각하시먄 됩니다.
    
    int thickness = 10; // 현 변수는 그려지는 선의 굵기를 변경할때 변경값이 저장되는 변수
    int startX; // 마우스클릭시작의 X좌표값이 저장될 변수
    int startY; // 마우스클릭시작의 Y좌표값이 저장될 변수
    int endX; // 마우스클릭종료의 X좌표값이 저장될 변수
    int endY; // 마우스클릭종료의 Y좌표값이 저장될 변수
    int a,b,c,d=0;
    int i=0;
    
    boolean tf = false; 
    /* 변 boolean 변수는 처음에 연필로 그리고 지우개로 지운다음 다시 연필로 그릴때
     * 기본색인 검은색으로 구분시키고 만약 프로그램 시작시 색선택후 그 선택된 색이
     * 지우개로 지우고 다시 연필로 그릴때 미리 정해진 색상으로 구분하는 변수인데..
     * 뭐 그리 중요한 변수는 아니다..
     */
    
    class Info{
       int x1, x2 ,y1, y2;
       
       Info(int x1,int y1 ,int x2, int y2){
          this.x1 = x1;
          this.x2 = x2;
          this.y1 = y1;
          this.y2 = y2;
       }
       
    }
    
  
    
    public Project2() { // Paint클래스의 디폴트(Default)생성자로 기본적인 GUI구성을 해주는 역할을 한다.
        setLayout(null); // 기본 프레임의 레이아웃을 초기화 시켜 패널을 개발자가 직접 다룰수 있게 됨
        setTitle("DrawingBoard"); // 프레임 타이틀 지정
        setSize(900,750); // 프레임 사이즈 지정
        setLocationRelativeTo(null); // 프로그램 실행시 화면 중앙에 출력
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        // 프레임 우측상단에 X버튼을 눌렀을떄의 기능 정의
       
        gui_panel = new JPanel(); // 프레임 상단에 버튼, 텍스트필드, 라벨등이 UI가 들어갈 패널
        gui_panel.setBackground(Color.GRAY); // 패널의 배경색을 회색으로 지정
        gui_panel.setLayout(null);
        // gui_panel의 레이아웃을 null지정하여 컴포넌트들의 위치를 직접 지정해줄수 있다.
        
        fill=new JButton("fill");
        fill.setFont(new Font("맑은 고딕", Font.BOLD, 20)); // 버튼 폰트및 글씨 크기 지정
        fill.setBackground(Color.LIGHT_GRAY); // 연필버튼 배경색 밝은회색으로 지정  
       
        Text=new JButton("Text");
        Text.setFont(new Font("맑은 고딕", Font.BOLD, 20)); // 버튼 폰트및 글씨 크기 지정
        Text.setBackground(Color.LIGHT_GRAY); // 연필버튼 배경색 밝은회색으로 지정  
        
        Pointer=new JButton("Pointer");
        Pointer.setFont(new Font("맑은 고딕", Font.BOLD, 13)); // 버튼 폰트및 글씨 크기 지정
        Pointer.setBackground(Color.LIGHT_GRAY); // 연필버튼 배경색 밝은회색으로 지정     
        
        RubberBand=new JButton("Rubber Band");
        RubberBand.setFont(new Font("맑은 고딕", Font.BOLD, 10)); // 버튼 폰트및 글씨 크기 지정
        RubberBand.setBackground(Color.LIGHT_GRAY); // 연필버튼 배경색 밝은회색으로 지정     
        
        N=new JButton("👉");
        N.setFont(new Font("함초롱돋움", Font.BOLD, 30)); // 버튼 폰트및 글씨 크기 지정
        N.setBackground(Color.LIGHT_GRAY); // 연필버튼 배경색 밝은회색으로 지정     
        
        P=new JButton("👈");
        P.setFont(new Font("함초롱돋움", Font.BOLD, 30)); // 버튼 폰트및 글씨 크기 지정
        P.setBackground(Color.LIGHT_GRAY); // 연필버튼 배경색 밝은회색으로 지정
        
        Polyline=new JButton("Polyline");
        Polyline.setFont(new Font("함초롱돋움", Font.BOLD, 13)); // 버튼 폰트및 글씨 크기 지정
        Polyline.setBackground(Color.LIGHT_GRAY); // 연필버튼 배경색 밝은회색으로 지정
        
        heart=new JButton("♥");
        heart.setFont(new Font("함초롱돋움", Font.BOLD, 40)); // 버튼 폰트및 글씨 크기 지정
        heart.setBackground(Color.LIGHT_GRAY); // 연필버튼 배경색 밝은회색으로 지정
        
        circle_bt=new JButton("○/⏺"); // 원 버튼 생성
        circle_bt.setFont(new Font("함초롱돋움", Font.BOLD, 26)); // 버튼 폰트및 글씨 크기 지정
        circle_bt.setBackground(Color.LIGHT_GRAY); // 연필버튼 배경색 밝은회색으로 지정
        
        rec_bt=new JButton("□/⏹"); // 사각형 버튼 생성
        rec_bt.setFont(new Font("함초롱돋움", Font.BOLD, 26)); // 버튼 폰트및 글씨 크기 지정
        rec_bt.setBackground(Color.LIGHT_GRAY); // 연필버튼 배경색 밝은회색으로 지정
        
        pencil_bt = new JButton("✏"); // 연필 버튼 생성
        pencil_bt.setFont(new Font("함초롱돋움", Font.BOLD, 30)); // 버튼 폰트및 글씨 크기 지정
        pencil_bt.setBackground(Color.LIGHT_GRAY); // 연필버튼 배경색 밝은회색으로 지정
        
        eraser_bt = new JButton("Eraser"); // 지우개 버튼 생성
        eraser_bt.setFont(new Font("함초롱돋움", Font.BOLD, 17)); // 버튼 폰트및 글씨 크기  지정
        eraser_bt.setBackground(Color.LIGHT_GRAY);  // 지우개 버튼 배경색 희색으로 지정
        
        colorSelect_bt = new JButton("🎨"); // 선색상 버튼 생성
        colorSelect_bt.setBackground(Color.LIGHT_GRAY); // 선색상 버튼 배경색 분홍색으로 지정
        colorSelect_bt.setFont(new Font("함초롱돋움", Font.BOLD, 30));
        
        thicknessInfo_label = new JLabel("도구굵기"); 
        // 도구굴기 라벨 지정 / 밑에서 나올 텍스트필드의 역할을 설명
        thicknessInfo_label.setFont(new Font("함초롬돋움", Font.BOLD, 12));
        // 도구굵기 라벨 폰트및 글씨 크기 지정
        
        thicknessControl_tf = new JTextField("6", 5); // 도구굵기 입력 텍스트필드 생성
        thicknessControl_tf.setHorizontalAlignment(JTextField.CENTER); 
          // 텍스트필드 라인에 띄어지는 텍스트 중앙 정렬
        thicknessControl_tf.setFont(new Font("궁서체", Font.PLAIN, 25)); 
          // 텍스트필드 X길이 및 폰트 지정
        
        label = new JLabel("원하는 도구를 선택하세요.");
        label.setFont(new Font("맑은고딕", Font.BOLD, 14));
      label.setForeground(Color.WHITE);
   
      fill.setBounds(525,75,80,40); 
      Text.setBounds(610,75,80,40); 
      Pointer.setBounds(695,75,80,40);
      RubberBand.setBounds(780,70,100,45); 
        N.setBounds(650,10,65,55); 
        P.setBounds(580,10,65,55); 
        Polyline.setBounds(485,10,90,55); 
        heart.setBounds(390,10,90,55); 
        circle_bt.setBounds(295,10,90,55); 
        rec_bt.setBounds(200,10,90,55); // 사각형 버튼 위치 지정
        pencil_bt.setBounds(10,10,90,55); // 연필 버튼 위치 지정
        eraser_bt.setBounds(105,10,90,55); // 지우개 버튼 위치 지정
        colorSelect_bt.setBounds(785,10,90,55); // 선색상 버튼 위치 지정
        thicknessInfo_label.setBounds(723,36,50,55); // 도구굵기 라벨 위치 지정
        thicknessControl_tf.setBounds(723,15,50,35); // 도구굵기 텍스트필드 위치 지정
        label.setBounds(20,37,900,115);
        
        gui_panel.add(fill);
        gui_panel.add(Text);
        gui_panel.add(Pointer);
        gui_panel.add(RubberBand);
        gui_panel.add(N);
        gui_panel.add(P);
        gui_panel.add(Polyline);
        gui_panel.add(heart);
        gui_panel.add(circle_bt); // gui_panel에 원 버튼 추가
        gui_panel.add(rec_bt); // gui_panel에 사각형 버튼 추가
        gui_panel.add(pencil_bt); // gui_panel에 연필 버튼 추가
        gui_panel.add(eraser_bt); // gui_panel에 지우개 버튼 추가
        gui_panel.add(colorSelect_bt); // gui_panel에 선색상 버튼 추가
        gui_panel.add(thicknessInfo_label); // gui_panel에 도구굵기 라벨 추가
        gui_panel.add(thicknessControl_tf); // gui_panel에 도구굵기 텍스트필드 추가
        gui_panel.add(label); 
        gui_panel.setBounds(0,0,900,120); // gui_panel이 프레임에 배치될 위치 지정
       
        ////////////////////////////////////////////////// ↑ 패널 구분 ↓
        
        paint_panel = new JPanel(); // 그림이 그려질 패널 생성
        paint_panel.setBackground(Color.WHITE); // 패널의 배경색 하얀색
        paint_panel.setLayout(null); 
        // paint_panel의 레이아웃을 null해줘 패널 자체를 setBounds로 위치를 조정할수 있다.
        
        paint_panel.setBounds(0,120,900,620); // paint_panel의 위치 조정
        
        
        add(gui_panel); // 메인프레임에 gui패널 추가 - 위치는 위에서 다 정해줌
        add(paint_panel); // 메인프레임에 paint패널 추가 - 위치는 위에서 다 정해줌
      
        setVisible(true); // 메인프레임을 보이게 한다.
        
        graphics = paint_panel.getGraphics(); // 그래픽초기화
        g = (Graphics2D)graphics; 
        // 기존의 graphics변수를 Graphics2D로 변환후 Graphics2D에 초기화
        // 일반적인 Graphics가 아닌 Graphics2D를 사용한 이유는 펜의 굴기와 관련된 기능을
        //수행하기 위하여 Graphics2D 클래스를 객체화함
        
        g.setColor(selectedColor); 
        // 그려질 선(=선도 그래픽)의 색상을 selectedColor의 값으로 설정
        
        /////////////////////////////////////////////////// ↓ 액션 처리부분
       
        // 그림 그리는 패널
        paint_panel.addMouseListener(new MouseListener() { 
            // paint_panel에서의 MouseListener 이벤트 처리
            public void mousePressed(MouseEvent e) { 
            // paint_panel에 마우스 눌림의 액션이 있을떄 밑 메소드 실행
               if(mode.equals("Polyline")&&isFirst==true) {
                startX = endX = e.getX(); // 마우스가 눌렸을때 그때의 X좌표값으로 초기화
                startY = endY = e.getY(); // 마우스가 눌렸을때 그때의 Y좌표값으로 초기화
                isFirst = false;
               }
               else {
               startX = e.getX(); // 마우스가 눌렸을때 그때의 X좌표값으로 초기화
               startY = e.getY(); // 마우스가 눌렸을때 그때의 Y좌표값으로 초기화
               }
                if(mode.equals("Polyline"))
                   paint();               
             
            }
            public void mouseClicked(MouseEvent e) {
            	int a = e.getX();
            	int b = e.getY();
               if(mode.equals("fill")) {

                    for(int i=0;i<Mo.size();i++) {
                       if(Mo.get(i).equals("rectangle")) {
                          if(( a>location.get(i).x1 ) && ( b>location.get(i).y1 ) && ( a < location.get(i).x2+location.get(i).x1) && ( b<location.get(i).y2+location.get(i).y1) ) {
                             
                               g.setColor(selectedColor);  // 그려지는 색상을 selectedColor변수의 값으로 지정
                             g.fillRect(location.get(i).x1, location.get(i).y1, location.get(i).x2, location.get(i).y2);
                          }
                          
                       }
                       
                    }
                   
                }
            } // 클릭이벤트 처리
            public void mouseEntered(MouseEvent e) {
                  
            } // paint_panel범위 내에 진입시 이벤트 처리
            public void mouseExited(MouseEvent e) {
               
            }
            public void mouseReleased(MouseEvent e) {
               endX=e.getX();
               endY=e.getY();
               paint();
               if (mode.equals("RubberBand")){
                   g.drawLine(startX, startY, endX, endY); 
                  a=startX;
                   b=startY;
                   c=endX;
                   d=endY;
                   location.add(new Info(a,b,c,d));
                   Mo.add("RubberBand");
               }
            }
            
            public void mouseMoved(MouseEvent e) {
               
            }
        });  
        
        // 전체 지우기
        class MyMouse extends MouseAdapter {
          public void mousePressed(MouseEvent e) {   
             if (e.getClickCount() == 2) {
                g.fillRect(0,0,900,2000);
               isFirst = true;
                label.setText("모두 지우기");
             }      
             else if (e.getClickCount() == 1) {
                label.setText("지우개 툴을 선택하셨습니다.");  
              }    
          }
       }
        
        // 사각형 채우기
        class Rec extends MouseAdapter {
          public void mousePressed(MouseEvent e) {   
             if (e.getClickCount() == 2) {
                g.fillRect(a,b,c,d);
                mode="fillRect";
                Mo.add("fillRect");
                location.add(new Info(a,b,c,d));
                nextmode.add("fillRect");
                next.add(new Info(a,b,c,d));
                label.setText("사각형 채우기");
             }   
             else if(e.getClickCount() == 1) {
                label.setText("사각형 툴을 선택하셨습니다.");
             }
          }
       }
        
        // 원 채우기 
        class Cir extends MouseAdapter {
          public void mousePressed(MouseEvent e) {   
             if (e.getClickCount() == 2) {
                g.fillOval(a,b,c,d);
                mode="fillCir";
                Mo.add("fillCir");
                location.add(new Info(a,b,c,d));
                nextmode.add("fillCir");
                next.add(new Info(a,b,c,d));
                label.setText("원 채우기");
             }  
             else if(e.getClickCount() == 1) {
                label.setText("원 툴을 선택하셨습니다.");
             }
          }
       }
 
      

        // P 버튼
        class Pre extends MouseAdapter {
          public void mousePressed(MouseEvent e) {   
             label.setText("이전");
             if (Mo.get(Mo.size()-1).equals("rectangle")) {
                  int x1 = location.get(location.size()-1).x1;
                  int y1 = location.get(location.size()-1).y1;
                  int x2= location.get(location.size()-1).x2;
                  int y2 = location.get(location.size()-1).y2;
                   g.setColor(Color.WHITE);
                   g.drawRect(x1,y1,x2,y2);  
                   nextmode.add("rectangle");
                   next.add(new Info(x1,y1,x2,y2));
                   location.remove(location.size()-1);
                   Mo.remove(Mo.size()-1);                
             }
             else if (Mo.get(Mo.size()-1).equals("circle")) {
                int x1 = location.get(location.size()-1).x1;
                  int y1 = location.get(location.size()-1).y1;
                  int x2= location.get(location.size()-1).x2;
                  int y2 = location.get(location.size()-1).y2;
                   g.setColor(Color.WHITE);
                   g.drawOval(x1,y1,x2,y2);   
                   nextmode.add("circle");
                   next.add(new Info(x1,y1,x2,y2));
                   location.remove(location.size()-1);
                   Mo.remove(Mo.size()-1);
             }
             else if (Mo.get(Mo.size()-1).equals("heart")) {
                int x1 = location.get(location.size()-1).x1;
                  int y1 = location.get(location.size()-1).y1;
                  int x2= location.get(location.size()-1).x2;
                  int y2 = location.get(location.size()-1).y2;
                   g.setColor(Color.WHITE);
                   g.drawString("♥", x1, y1);  
                   nextmode.add("heart");
                   next.add(new Info(x1,y1,x2,y2));
                   location.remove(location.size()-1);
                   Mo.remove(Mo.size()-1);
             }
             else if(Mo.get(Mo.size()-1).equals("fillRect")) {
                int x1 = location.get(location.size()-1).x1;
                  int y1 = location.get(location.size()-1).y1;
                  int x2= location.get(location.size()-1).x2;
                  int y2 = location.get(location.size()-1).y2;
                   g.setColor(Color.WHITE);
                   g.fillRect(x1,y1,x2,y2); 
                   nextmode.add("fillRect");
                   next.add(new Info(x1,y1,x2,y2));
                   location.remove(location.size()-1);
                   Mo.remove(Mo.size()-1);
             }
             else if(Mo.get(Mo.size()-1).equals("fillCir")) {
                int x1 = location.get(location.size()-1).x1;
                  int y1 = location.get(location.size()-1).y1;
                  int x2= location.get(location.size()-1).x2;
                  int y2 = location.get(location.size()-1).y2;
                   g.setColor(Color.WHITE);
                   g.fillOval(x1,y1,x2,y2); 
                   nextmode.add("fillCir");
                   next.add(new Info(x1,y1,x2,y2));
                   location.remove(location.size()-1);
                   Mo.remove(Mo.size()-1);
             }
             else if(Mo.get(Mo.size()-1).equals("RubberBand")) {
                int x1 = location.get(location.size()-1).x1;
                  int y1 = location.get(location.size()-1).y1;
                  int x2= location.get(location.size()-1).x2;
                  int y2 = location.get(location.size()-1).y2;
                   g.setColor(Color.WHITE);
                   g.drawLine(x1, y1, x2, y2);
                   nextmode.add("RubberBand");
                   next.add(new Info(x1,y1,x2,y2));
                   location.remove(location.size()-1);
                   Mo.remove(Mo.size()-1);
             }
             else if(Mo.get(Mo.size()-1).equals("Text")) {
                txt.setVisible(false);
                nextmode.add("Text");
                Mo.remove(Mo.size()-1);
             }
             else if(Mo.get(Mo.size()-1).equals("pencil")) {
                for(i=0;i<line.size();i++) {
                   int x1 = line.get(i).x1;
                      int y1 = line.get(i).y1;
                      int x2= line.get(i).x2;
                      int y2 = line.get(i).y2;
                      g.setColor(Color.WHITE);
                      g.drawLine(x1, y1, x2, y2);
                }
                  nextmode.add("pencil");
                  Mo.remove(Mo.size()-1);
             }
             
            
          }    
          
        }
       
        // N 버튼
        class Nex extends MouseAdapter{
           public void mousePressed(MouseEvent e) {  
              label.setText("되돌리기");
              if (nextmode.get(nextmode.size()-1).equals("rectangle")) {
                 int x = next.get(next.size()-1).x1;
                    int y = next.get(next.size()-1).y1;
                    int z= next.get(next.size()-1).x2;
                    int w = next.get(next.size()-1).y2;
                    g.setColor(Color.BLACK);
                    g.drawRect(x,y,z,w); 
                    next.remove(next.size()-1);
                    nextmode.remove(nextmode.size()-1);
                    i++;
                    Mo.add("rectangle");
                    a=x;
                    b=y;
                    c=z;
                    d=w;
                    location.add(new Info(a,b,c,d));  
               }
             else if(nextmode.get(nextmode.size()-1).equals("circle")) {
                 int x = next.get(next.size()-1).x1;
                    int y = next.get(next.size()-1).y1;
                    int z= next.get(next.size()-1).x2;
                    int w = next.get(next.size()-1).y2;
                    g.setColor(Color.BLACK);
                    g.drawOval(x,y,z,w); 
                    next.remove(next.size()-1);
                    nextmode.remove(nextmode.size()-1);
                    i++;
                    Mo.add("circle");
                    a=x;
                    b=y;
                    c=z;
                    d=w;
                    location.add(new Info(a,b,c,d)); 
              }
             else if(nextmode.get(nextmode.size()-1).equals("heart")) {
               int x = next.get(next.size()-1).x1;
                  int y = next.get(next.size()-1).y1;
                  int z= next.get(next.size()-1).x2;
                  int w = next.get(next.size()-1).y2;
                  g.setColor(Color.BLACK);
                  g.drawString("♥", x, y);
                  next.remove(next.size()-1);
                  nextmode.remove(nextmode.size()-1);
                  i++;
                  Mo.add("heart");
                  a=x;
                  b=y;
                  c=z;
                  d=w;
                  location.add(new Info(a,b,c,d)); 
               }
             else if(nextmode.get(nextmode.size()-1).equals("fillRect")) {
                 int x = next.get(next.size()-1).x1;
                    int y = next.get(next.size()-1).y1;
                    int z= next.get(next.size()-1).x2;
                    int w = next.get(next.size()-1).y2;
                    g.setColor(Color.BLACK);
                    g.fillRect(x,y,z,w); 
                    next.remove(next.size()-1);
                    nextmode.remove(nextmode.size()-1);
                    i++;
                    Mo.add("fillRect");
                    a=x;
                    b=y;
                    c=z;
                    d=w;
                    location.add(new Info(a,b,c,d)); 
              }
             else if(nextmode.get(nextmode.size()-1).equals("fillCir")) {
                 int x = next.get(next.size()-1).x1;
                  int y = next.get(next.size()-1).y1;
                  int z= next.get(next.size()-1).x2;
                  int w = next.get(next.size()-1).y2;
                  g.setColor(Color.BLACK);
                  g.fillOval(x,y,z,w); 
                  next.remove(next.size()-1);
                  nextmode.remove(nextmode.size()-1);
                  i++;
                  Mo.add("fillCir");
                  a=x;
                  b=y;
                  c=z;
                  d=w;
                  location.add(new Info(a,b,c,d)); 
             }
             else if(nextmode.get(nextmode.size()-1).equals("RubberBand")) {
                 int x = next.get(next.size()-1).x1;
                  int y = next.get(next.size()-1).y1;
                  int z= next.get(next.size()-1).x2;
                  int w = next.get(next.size()-1).y2;
                  g.setColor(Color.BLACK);
                  g.drawLine(x, y, z, w); 
                  next.remove(next.size()-1);
                  nextmode.remove(nextmode.size()-1);
                  i++;
                  Mo.add("RubberBand");
                  a=x;
                  b=y;
                  c=z;
                  d=w;
                  location.add(new Info(a,b,c,d)); 
             }
             else if(nextmode.get(nextmode.size()-1).equals("Text")) {
                txt.setVisible(true);
                  nextmode.remove(nextmode.size()-1);
                  i++;
                  Mo.add("Text");
             }
             else if(nextmode.get(nextmode.size()-1).equals("pencil")) {
                for(i=0;i<line.size();i++) {
                   int x1 = line.get(i).x1;
                      int y1 = line.get(i).y1;
                      int x2= line.get(i).x2;
                      int y2 = line.get(i).y2;
                      g.setColor(Color.BLACK);
                      g.drawLine(x1, y1, x2, y2);
                }
                nextmode.remove(nextmode.size()-1);
                  Mo.add("pencil");
                 
             }
           
           }   
                
        }
    
        fill.addActionListener(new ToolActionListener());
        
        Text.addActionListener(new ToolActionListener());
       
        Pointer.addActionListener(new ToolActionListener());
        
        RubberBand.addActionListener(new ToolActionListener());
        
        P.addMouseListener(new Pre());
        P.addActionListener(new ToolActionListener());
        
        N.addMouseListener(new Nex());
        N.addActionListener(new ToolActionListener());

        Polyline.addActionListener(new ToolActionListener());
        
        heart.addActionListener(new ToolActionListener());
        
        circle_bt.addActionListener(new ToolActionListener());
        circle_bt.addMouseListener(new Cir());
        
        rec_bt.addActionListener(new ToolActionListener());
        rec_bt.addMouseListener(new Rec());
        
        paint_panel.addMouseMotionListener(new PaintDraw());
          // paint_panel에 마우스 모션리스너 추가
        
        pencil_bt.addActionListener(new ToolActionListener()); // 연필버튼 액션처리

        eraser_bt.addActionListener(new ToolActionListener()); // 지우개버튼 액션처리
        eraser_bt.addMouseListener(new MyMouse());
        
        colorSelect_bt.addActionListener(new ActionListener() {
          // 선색상버튼 액션처리를 익명클래스로 작성
            public void actionPerformed(ActionEvent e) { // 오버라이딩
               label.setText("색상변경");
                tf = true; // 위에서 변수 설명을 했으므로 스킵..
                JColorChooser chooser = new JColorChooser(); // JColorChooser 클래스객체화
                selectedColor = chooser.showDialog(null, "Color", Color.ORANGE); 
                // selectedColor에 선택된색으로 초기화
                g.setColor(selectedColor);
                        // 그려지는 펜의 색상을 selectedColor를 매개변수로 하여 지정
                
            }
        });
    }
    
  
    
    // paint 함수
    public void paint() {
        if(mode.equals("rectangle")) {
           thickness = Integer.parseInt(thicknessControl_tf.getText());
           g.setStroke(new BasicStroke(thickness, BasicStroke.CAP_ROUND,0)); //선굵기
           if(tf == false) g.setColor(Color.BLACK); // 그려지는 색상을 검은색 지정
            else {
               g.setColor(selectedColor);  // 그려지는 색상을 selectedColor변수의 값으로 지정
            }
           a=startX;
           b=startY;
           c=endX-startX;
           d=endY-startY;
           g.drawRect(startX, startY, endX-startX, endY-startY);
           Mo.add("rectangle");
           location.add(new Info(a,b,c,d));
           label.setText("사각형 그리는 중");
           
        }
        
        else if(mode.equals("circle")) {
           thickness = Integer.parseInt(thicknessControl_tf.getText());
           g.setStroke(new BasicStroke(thickness, BasicStroke.CAP_ROUND,0)); //선굵기
           if(tf == false) g.setColor(Color.BLACK); // 그려지는 색상을 검은색 지정
            else g.setColor(selectedColor);  // 그려지는 색상을 selectedColor변수의 값으로 지정
           a=startX;
           b=startY;
           c=endX-startX;
           d=endY-startY;
           g.drawOval(startX, startY, endX-startX, endY-startY);
           Mo.add("circle");
           location.add(new Info(a,b,c,d)); 
           label.setText("원 그리는 중");
        }
        
        else if(mode.equals("heart")) {
           if(tf == false) g.setColor(Color.BLACK); // 그려지는 색상을 검은색 지정
            else g.setColor(selectedColor);  // 그려지는 색상을 selectedColor변수의 값으로 지정
           g.setFont(new Font("함초롱돋움", Font.BOLD, 50));
           a=startX;
           b=startY;
           c=endX;
           d=endY;
           g.drawString("♥", a, b);
           Mo.add("heart");
           location.add(new Info(a,b,c,d));
           label.setText("하트 스티커 사용 중");
        }
        
        else if(mode.equals("Polyline")) {
           thickness = Integer.parseInt(thicknessControl_tf.getText());
           g.setStroke(new BasicStroke(thickness, BasicStroke.CAP_ROUND,0)); //선굵기
           if(tf == false) g.setColor(Color.BLACK); // 그려지는 색상을 검은색 지정
           else g.setColor(selectedColor);  // 그려지는 색상을 selectedColor변수의 값으로 지정
           a=startX;
           b=startY;
           c=endX;
           d=endY; 
           int xArray[]= {startX,endX};
           int yArray[]= {startY,endY};
            
           g.drawPolyline(xArray,yArray,2);
           label.setText("Polyline 그리는 중");   
        }
        else if(mode.equals("Text")) {
           Mo.add("Text");
              paint_panel.add(txt);            
              txt.setBounds(startX, startY, 100, 30);
              txt.setFont(new Font("굴림체", Font.BOLD, 15));
        }
        
      
    }

    
    
    public class PaintDraw implements MouseMotionListener {
        // 위에서 paint_panel에 MouseMotionListener액션 처리가 될때 현 클래스로 넘어와서 밑 문장을 실행

        @Override
        public void mouseDragged(MouseEvent e) { 
            // paint_panel에서 마우스 드래그 액션이처리될떄 밑 메소드 실행
           if(mode.equals("pencil")||mode.equals("e")) {
            thickness = Integer.parseInt(thicknessControl_tf.getText());
            // 텍스트필드부분에서 값을 값고와 thickness변수에 대입
    
             
                    label.setText("그리는 중");
                    endX = e.getX(); 
                     endY = e.getY(); 
                 
                     a=startX;
                     b=startY;
                     c=endX;
                     d=endY; 
                     line.add(new Info(a,b,c,d));
                     
                     g.setStroke(new BasicStroke(thickness, BasicStroke.CAP_ROUND,0)); //선굵기
                     g.drawLine(startX, startY, endX, endY); // 라인이 그려지게 되는부분   

                     startX = endX; 
                     startY = endY;
              
                     
           }
           else if (mode.equals("RubberBand")){
              if(tf == false) g.setColor(Color.BLACK); // 그려지는 색상을 검은색 지정
               else g.setColor(selectedColor); 
              label.setText("RubberBand 그리는 중");
              endX = e.getX(); 
              endY = e.getY(); 
              g.setStroke(new BasicStroke(thickness, BasicStroke.CAP_ROUND,0)); //선굵기
               g.drawLine(startX, startY, endX, endY); // 라인이 그려지게 되는부분   
               repaint();
           }
          
 

        }
        
        @Override
        public void mouseMoved(MouseEvent e) {
           if(mode.equals("Pointer")) {
              label.setText("Pointer");
              PointerInfo a = MouseInfo.getPointerInfo();
               Point b = a.getLocation();
               int x = (int) b.getX();
               int y = (int) b.getY();
               g.drawString("●", x-330, y-190);
               g.setFont(new Font("맑은 고딕", Font.BOLD, 10));
                if(tf == false) g.setColor(Color.RED); // 그려지는 색상을 검은색 지정
                 else g.setColor(selectedColor); 
               repaint();
           }
        
        }
    } 
    

    public class ToolActionListener implements ActionListener {
       
        // 연필,지우개 버튼의 액션처리시 실행되는 클래스
        public void actionPerformed(ActionEvent e) {
            // 오버라이딩된 actionPerformed메소드 실행
            if(e.getSource() == pencil_bt) { // 연필버튼이 눌렸을떄 밑 if문장 블록범위내 문장 실행
                mode="pencil";
                Mo.add("pencil");
                if(tf == false) g.setColor(Color.BLACK); // 그려지는 색상을 검은색 지정
                else g.setColor(selectedColor);  // 그려지는 색상을 selectedColor변수의 값으로 지정
                label.setText("연필 툴을 선택하셨습니다.");
            } 
            else if(e.getSource() == eraser_bt) {
                 // 지우개버튼이 눌렸을떄 밑 if문장 블록범위내 문장 실행
                mode="e";

                g.setColor(Color.WHITE); 
         
            }
            
            else if(e.getSource() == rec_bt) {
               mode="rectangle";
            }
            else if(e.getSource() == circle_bt) {
               mode="circle";
            }
            else if(e.getSource() == heart) {
               mode="heart";
               label.setText("하트 스티커를 선택하셨습니다.");
              
            }
            
            else if(e.getSource() == Polyline) {
               mode="Polyline";
               label.setText("Polyline 툴을 선택하셨습니다.");
            }
            else if(e.getSource() == RubberBand ) {
               mode="RubberBand";
                label.setText("RubberBand 툴을 선택하셨습니다.");
            }
            else if(e.getSource() == Pointer ) {
               mode="Pointer";
                label.setText("Pointer 툴을 선택하셨습니다.");
            }
            else if(e.getSource() == Text ) {
               mode="Text";
                label.setText("Text 툴을 선택하셨습니다.");
            }
            else if(e.getSource() == fill ) {
               mode="fill";
                label.setText("채우기 툴을 선택하셨습니다.");
            }
       
        }
        
    }
    
    public static void main(String[] args) { // 메인메소드
        new Project2(); // Paint클래스의 디폴트(=Default)생성자 실행

    }
}