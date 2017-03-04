package fivechess;

import java.awt.BorderLayout;
import java.awt.Color; 
import java.awt.Dimension;
import java.awt.Font; 
import java.awt.Graphics;
import java.awt.Point; 
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 
import java.awt.event.MouseAdapter; 
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon; 
import javax.swing.JButton;
import javax.swing.JFrame; 
import javax.swing.JLabel; 
import javax.swing.JMenu; 
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;  
	public class MainFrame extends JFrame{ 
		private static int BIG=15; 
		private static final int ONE=25;    
		private static final int START=10; 
		private static int X=BIG*START/2;  
		private static int Y=BIG*START/2; 
		private int HL=BIG*ONE+START;   
		private static boolean isBlack; 
		CanvasFrame canvas=new CanvasFrame(); 
		CanvasFrame can; 
		Graphics g; 
		public MainFrame(){
			init();
			}    
		private Point point;  
		private ArrayList<Point> blacklist=new ArrayList<Point>(); 
		private ArrayList<Point> whitelist=new ArrayList<Point>(); 
		private void init() {
			JMenuBar bar=new JMenuBar(); 
			setJMenuBar(bar);   
			setSize(600,600);  
			setTitle("五子棋");  
			JMenu menu=new JMenu("五子棋"); 
			bar.add(menu);    
			JMenuItem start=new JMenuItem("start");  
			JMenuItem end=new JMenuItem("棋盘大小");   
			end.addActionListener(new ActionListener() {   
				public void actionPerformed(ActionEvent e) {	
				}   
				});  
			JMenuItem renew=new JMenuItem("重新开始"); 
			JMenuItem exit=new JMenuItem("退出");   
			exit.addActionListener(new ActionListener(){ 
				public void actionPerformed(ActionEvent e) { 
					System.exit(0);  
					}   
				});      
			renew.addActionListener(new ActionListener(){ 
				public void actionPerformed(ActionEvent e) {  
					blacklist.clear();     
					whitelist.clear();   
					}     
				});   
			menu.add(start);  
			menu.add(renew);   
			menu.add(end);   
			menu.add(exit);   
			setLayout(new BorderLayout());      
			add(BorderLayout.CENTER,createCenter());    
			add(BorderLayout.EAST,createEast());  
			}    
		private boolean isWin(ArrayList<Point> list){
			int x;  
			int y;  
			int idx=0;
			loop:for (Point p : list) {
				x=p.x;    
				y=p.y;   
				idx=0;   
				while(list.contains(new Point(x,y))){  
					idx++;   
					x=x-ONE;   
					if(idx==5)  
						break loop;    
					}      
				x=p.x;   
				y=p.y;    
				idx=0;  
				while(list.contains(new Point(x,y))){   
					idx++;      
					y=y-ONE;    
					if(idx==5)   
						break loop; 
					}       
				x=p.x;       
				y=p.y;       
				idx=0;       
				while(list.contains(new Point(x,y))){  
					idx++;      
					x=x-ONE;   
					y=y-ONE;   
					if(idx==5) 
						break loop;    
					}   
				x=p.x;      
				y=p.y;      
				idx=0;      
				while(list.contains(new Point(x,y))){ 
					idx++;   
					x=x-ONE; 
					y=y+ONE; 
               if(idx==5)     
            	   break loop;  
               }     
				}       
			return idx==5;  
			}     
		private JPanel createEast() {  
			JPanel pane=new JPanel(); 
			BorderLayout border=new BorderLayout();
			pane.setLayout(border);   
			pane.setBorder(new TitledBorder("玩家"));    
			Icon mm=new ImageIcon(getClass().getResource("mm.jpg"));  
			pane.add(BorderLayout.NORTH,new JLabel(mm,JLabel.CENTER));  
			pane.add(BorderLayout.CENTER,ballColor());    
			pane.add(BorderLayout.SOUTH,button());    
			return pane;   
			}      
		private JPanel ballColor() {  
			JPanel pane=new JPanel();   
			pane.setLayout(new BorderLayout());
			can=new CanvasFrame(){  
				public void paint(Graphics g){ 
					g.setColor(Color.PINK);    
					g.fillRect(1, 1,200,300);  
					g.setFont(new Font("sans",Font.BOLD,20)); 
					g.setColor(Color.BLACK);       
					g.fillOval(55, 20,25,30);     
					g.drawString("黑子", 10, 43);  
					g.setColor(Color.WHITE);      
					g.fillOval(55, 60,25,30);     
					g.drawString("白子", 10, 83);  
					if(isBlack){                  
						g.setColor(Color.BLACK);   
						g.drawString("黑方落子", 5, 120); 
						}else{               
							g.setColor(Color.WHITE);  
							g.drawString("白方落子",5,120); 
							}          
					can.repaint(); 
					}      
				};      
				pane.add(BorderLayout.CENTER,can);    
				return pane;    
				}     
		private JPanel button() { 
			JPanel pane=new JPanel();  
			BorderLayout bor=new BorderLayout();  
			JButton start=new JButton("开始");   
			JButton end=new JButton("结束"); 
			end.addActionListener(new ActionListener(){ 
				public void actionPerformed(ActionEvent e) {  
					System.exit(0);    
					}});    
			pane.setLayout(bor);   
			bor.setVgap(5);      
			pane.setBorder(new TitledBorder(""));    
			pane.add(BorderLayout.NORTH,start); 
			pane.add(BorderLayout.SOUTH,end);  
			return pane;   
			}    
		private JPanel createCenter() {  
			JPanel pane=new JPanel();   
			canvas.setPreferredSize(new Dimension(HL+10,HL+10)); 
			canvas.addMouseListener(new MouseAdapter() { 
				public void mouseClicked(MouseEvent e){ 
					loop:if(e.getButton()==MouseEvent.BUTTON1){ 
						X=e.getX();          
						Y=e.getY();         
						int x=(X/ONE)*ONE;  
						int y=(Y/ONE)*ONE;  
						point=new Point(x,y);
						if(!(blacklist.contains(point)||whitelist.contains(point))){  
							if(isBlack){    
								blacklist.add(point);   
								if(isWin(blacklist)){   
									JOptionPane.showMessageDialog(null, "黑方胜利！");  
									blacklist.clear();       
									whitelist.clear();      
                          }       
								}else{    
									whitelist.add(point);  
									if(isWin(whitelist)){  
										JOptionPane.showMessageDialog(null, "白方胜利！");  
										blacklist.clear();
										whitelist.clear();   
										}       
									}  
							}       
						else{  
							JOptionPane.showMessageDialog(null, "这里己有子了,换个地方落子吧!");    
							break loop;     
							} 
						canvas.repaint(); 
						isBlack=!isBlack; 
						}           
				}  
				});
			pane.add(canvas);  
			return pane;  
			}      
		public static void main(String[] args) {  
			MainFrame frame=new MainFrame();
			frame.center(frame);   
			frame.pack();      
			frame.setVisible(true); 
			}  
		public  void center(JFrame frame){ 
			//Toolkit 是当前 绘图系统集合工具包       
			Toolkit toolkit=Toolkit.getDefaultToolkit();   
			Dimension screen=toolkit.getScreenSize();//屏幕的宽高    
			int frameWidth=frame.getWidth();      
			int frameHigh=frame.getHeight();   
			int x=(screen.width-frameWidth)/2;   
			int y=(screen.height-frameHigh)/2;    
			frame.setLocation(x, y);   
			}  
		class CanvasFrame extends JPanel {  
			public void paint(Graphics g){ 
				g.setColor(Color.PINK);   
				g.fillRect(0, 0, HL+20, HL+20); 
				g.setColor(Color.BLACK);    
				for(int i=START;i<=HL;i=i+ONE){  
					for(int j=START;j<=HL;j=j+ONE){ 
						g.drawLine(i, j, HL, j);     
						}     
					}          
				for(int i=START;i<=HL;i=i+ONE){  
					for(int j=START;j<=HL;j=j+ONE){   
						g.drawLine(i, j, i, HL);   
						}        
					}      
				g.setColor(Color.YELLOW);  
				g.drawRect(START/2, START/2, HL+1, HL+1);   
				g.setColor(Color.BLACK);     
				for(Point point : blacklist){  
					g.fillOval(point.x, point.y, 20, 25);   
					}     
				g.setColor(Color.WHITE);  
				for(Point point : whitelist){  
					g.fillOval(point.x, point.y, 20, 25);    
					}  
				}   
			} 
		}
		
			
		