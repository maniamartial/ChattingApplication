package chattingwhatsaptechmania;

import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
 
public class Server extends JFrame{
    String Message="";
    private JPanel pan1,pan2,pan3,pan4;
private JButton button1,letterbutton,mode;
private JTextField text;
private JTextArea area;
private ImageIcon con;
private JLabel dat,Online,video,menu,lb,call;
private ObjectInputStream input;
private ObjectOutputStream output;
private ServerSocket ss;
private Socket socket;

public Server() {
    
    
    
    
    /*con=new ImageIcon(ClassLoader.getSystemResource("\"E:\\pic\\IMG-20191201-WA0004.jpg\""));
    Image i1=con.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
    ImageIcon i2=new ImageIcon(i1);*/
    con=new ImageIcon("E:\\pic\\IMG-20191201-WA0004.jpg");
    //JLabel lb=new JLabel(con);
    //lb.setSize(50,50);
    
    pan1=new JPanel();
    pan1.setBounds(0,0,450,80);
    pan1.setBackground(new Color(7,94,84));
    add(pan1);
    
     mode=new JButton("DarkMode");
    mode.setBounds(20,40,100,70);
    mode.setForeground(Color.red);
    
    pan1.add(mode);
    
 lb=new JLabel("Mr.Dan");
    lb.setFont(new Font("Times New Roman",Font.PLAIN,24));
    lb.setForeground(Color.white);
    lb.setBounds(100,20,100,20);
    pan1.add(lb);
    
 Online=new JLabel("Online");
    Online.setFont(new Font("Times New Roman",Font.BOLD,24));
    Online.setBounds(100,40,90,20);
    Online.setForeground(Color.white);
    pan1.add(Online);
    
    Date dt=new Date();
    SimpleDateFormat fm=new SimpleDateFormat("yyyy/mm/dd hh:mm:ss");
    String date=fm.format(dt);
 dat=new JLabel(date);
    dat.setBounds(400,10,550,30);
    dat.setFont(new Font("Courier",Font.PLAIN,16));
    dat.setForeground(Color.white);
    
 video=new JLabel("Video");
    video.setFont(new Font("Times New Roman",Font.BOLD,20));
    video.setBounds(200,40,300,70);
    video.setForeground(Color.white);
    pan1.add(video);
    
 call=new JLabel("Call");
    call.setFont(new Font("Times New Roman",Font.BOLD,20));
    call.setBounds(350,40,450,70);
    call.setForeground(Color.white);
    pan1.add(call);
    
 menu=new JLabel("Menu");
    menu.setFont(new Font("Times New Roman",Font.BOLD,20));
    menu.setBounds(450,40,550,70);
    menu.setForeground(Color.white);
    pan1.add(menu);
    pan1.add(dat);
    
    
    
    pan2=new JPanel();
    pan2.setBounds(0,80,450,300);
    pan2.setBackground(Color.DARK_GRAY);
    
    area=new JTextArea();
  area.setEditable(false);
area.setBackground(Color.white);
area.setWrapStyleWord(true);
area.setText("");
area.setLineWrap(true);
area.setFont(new Font("Times New Roman",Font.PLAIN,20));
add(new JScrollPane(area));
area.setBounds(0,85,450,315);
add(area,BorderLayout.CENTER);

//Textfield
text=new JTextField();
text.setEditable(false);
text.setColumns(30);
text.setFont(new Font("Arial",Font.PLAIN,20));
text.setBounds(2,400,350,40);
add(text);

//Button ton be on the same row as textfield
button1=new JButton("SEND");
button1.setBackground(new Color(7,94,84));
button1.setForeground(Color.white);
button1.setFont(new Font("Times new Roman",Font.PLAIN,24));
button1.setBounds(355,400,100,40);
add(button1);
add(pan2);

 pan3=new JPanel();
pan3.setBackground(Color.yellow);
pan3.setBounds(2,442,448,600);
pan3.setLayout(new FlowLayout());
for (int i=0;i<80;i++){
    Character letter=(char)(i+'A');
    if (Objects.equals(letter, "") || letter.equals("None")){
        letter='=';
    }
     letterbutton=new JButton(letter.toString());
    letterbutton.setSize(10,20);
    letterbutton.setForeground(Color.red);
    pan3.add(letterbutton);
    
}
add(pan3);

 pan4=new JPanel();
pan4.setBackground(Color.cyan);
  pan4.setBounds(0,880,450,900);
  add(pan4);

  mode.addActionListener(new ButtonListener());
  button1.addActionListener(new ButtonListener());
  letterbutton.addActionListener(new ButtonListener());
  
}

  private class ButtonListener implements ActionListener{
     String message=text.getText();
  @Override
  public void actionPerformed(ActionEvent e){
   if(e.getSource()==mode){
       pan1.setBackground(Color.black);
       mode.setForeground(Color.white);
       mode.setText("Light mode");
       lb.setForeground(Color.WHITE);
       Online.setForeground(Color.white);
       call.setForeground(Color.WHITE);
       video.setForeground(Color.WHITE);
      menu.setForeground(Color.WHITE);
       dat.setForeground(Color.white);
       area.setForeground(Color.WHITE);
       area.setBackground(Color.BLACK);
       text.setBackground(Color.LIGHT_GRAY);
       text.setForeground(Color.WHITE);
       pan3.setBackground(Color.BLACK);
       letterbutton.setBackground(Color.black);
       letterbutton.setForeground(Color.WHITE);
       
   }
   
   else if(e.getSource()==button1){
        String messg=text.getText();
      showMessage(messg);
      sendMessage(messg);
      text.setText("");
       
   }
}
 
}
  
  public void startRunning(){
      
        try {
            ss=new ServerSocket(8080);
            while(true){
                try{
                    
                    waitConnection();
                    streamConnection();
                    whileChatting();
                }catch(EOFException eof){
                    showMessage("Server not Connected");
            }
            }
        } catch (IOException ex) {
                ex.printStackTrace();
                }
               
  }
  private void waitConnection() throws IOException{
      
      showMessage("Waiting for the Connection...");
      socket=ss.accept();
      showMessage("Connected...");
      
  }
  private void streamConnection() throws IOException{
      output=new ObjectOutputStream(socket.getOutputStream());
      output.flush();
      input=new ObjectInputStream(socket.getInputStream());
      
  }
  public void whileChatting() throws IOException{
  String message="Go on bonding...Server talking!!";
  sendMessage(message);
  ableToType(true);
 
do{
    try{
        message=(String)input.readObject();
        showMessage("\nSERVER-"+message);
    }catch(ClassNotFoundException cn){
        showMessage("\n Cannot send such a message");}
}while(!message.equals("Exite"));
  }
  
  public void closeCrap(){
      showMessage("Closing server-");
      ableToType(false);
      try{
          input.close();
          output.close();
          socket.close();
      }catch(IOException e){
          e.printStackTrace();
      }
  }
  public void  ableToType(boolean tof){
      SwingUtilities.invokeLater(()->
              text.setEditable(tof)
      );
  }
  public void showMessage(String text){
      SwingUtilities.invokeLater(()->
              area.append(text)
      );
  }
  public void sendMessage(final String mess){
      try{
          output.writeObject(mess);
          output.flush();
          showMessage("\n SERVER-"+mess);
      }catch(IOException io){
          showMessage("\n I cant send this Message!!");
      }
  }
public static void main(String args[])
{
    Server sv=new Server();
    
    sv.setVisible(true);
    sv.setSize(450,900);
    sv.startRunning();
  
    sv.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

}
}
