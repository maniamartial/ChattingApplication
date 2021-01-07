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

public class Client extends JFrame{
private JPanel pan1,pan2,pan3,pan4;
private JButton button1,mode,letterbutton;
private JTextField text;
private JTextArea area;
private ImageIcon con;
private JLabel lb,video,dat,Online,call,menu;
private ObjectInputStream input;
private ObjectOutputStream output;
private Socket socket;
private String serverIp;

Client(){
    pan1=new JPanel();
    pan1.setBounds(0,0,450,80);
    pan1.setBackground(new Color(7,94,84));
    add(pan1);
    
    mode=new JButton("DarkMode");
    mode.setBounds(20,40,100,70);
    mode.setForeground(Color.red);
    
    pan1.add(mode);
    
      lb=new JLabel("Melany");
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
area.setVisible(true);
add(new JScrollPane(area));
area.setText("");    
area.setBackground(Color.white);
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
pan3.setBackground(Color.DARK_GRAY);
pan3.setBounds(2,442,448,600);
pan3.setLayout(new FlowLayout());
for (int i=0;i<80;i++){
    Character letter=(char)(i+'A');
    if (Objects.equals(letter, " ") || letter.equals("None")){
        letter='=';
    }
     letterbutton=new JButton(letter.toString());
    letterbutton.setSize(10,30);
    
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
      
  @Override
  public void actionPerformed(ActionEvent e){
   if(e.getSource()==mode)
       
   {
       pan1.setBackground(Color.black);
       mode.setForeground(Color.white);
       lb.setForeground(Color.WHITE);
       Online.setForeground(Color.white);
       call.setForeground(Color.white);
       video.setForeground(Color.white);
      menu.setForeground(Color.white);
       dat.setForeground(Color.white);
       area.setForeground(Color.white);
       area.setBackground(Color.BLACK);
       text.setBackground(Color.LIGHT_GRAY);
       text.setForeground(Color.WHITE);
       pan3.setBackground(Color.BLACK);
       letterbutton.setBackground(Color.black);
       letterbutton.setForeground(Color.WHITE);   
   
   }   
   else if(e.getSource()==button1){
       String message=text.getText();
      showMessage(message);
      sendMessage(message);
      text.setText("");
      
   }
}
  }
  public void startRunning(){
      
      try{
           ConnectToServer();
           streamConnection();
           whileChatting();
          
      }catch(EOFException io){
          sendMessage("Client Terminated");
      }catch(IOException i){
          i.printStackTrace();
      }finally{
          closeCrap();
      }
  }
  
  private void ConnectToServer() throws IOException
  {
      showMessage("\n Waiting for server acceptance...");
socket=new Socket(InetAddress.getByName(serverIp),8080);
      showMessage("\n Connnected to server..."+socket);
  }
  private void streamConnection() throws IOException
  {
      output=new ObjectOutputStream(socket.getOutputStream());
      output.flush();
      input=new ObjectInputStream(socket.getInputStream());
      showMessage("\nStreams connected");
  }
       private void whileChatting() throws IOException
         {
      String message="Connected now you can bond.client talking..";
      sendMessage(message);
      ableToType(true);
      do{
          try{
              message=(String) input.readObject();
              showMessage(message);
          }catch(ClassNotFoundException cn){
              showMessage("\n Cannot send such shit!!");
          }
      }while(!message.equals("Exite"));
  }
  public void closeCrap(){
      showMessage("\n Closing the connection....");
      ableToType(false);
      try{
          output.close();
          input.close();
          socket.close();
      }catch(IOException io){
          io.printStackTrace();
      }
  }
  public void showMessage(final String text){
      SwingUtilities.invokeLater(
        new Runnable (){
            @Override
            public void run(){
                area.append("\n"+text);
                
            }
        
    });
    }
@SuppressWarnings("Convert2Lambda")
  public void ableToType(boolean tof){
  SwingUtilities.invokeLater(
  new Runnable(){
      @Override
      public void run(){
          text.setEditable(tof);
      }
  });
      
  }
public void sendMessage(String msg){
        try{
            output.writeObject(msg);
            output.flush();
            showMessage("\nCLIENT-"+msg);
        }catch(IOException io){
            showMessage("\n I cannot send the text");
        }
    
  }
public static void main(String args[]){
    Client cl=new Client();
cl.setLocation(400,100);
    cl.setVisible(true);
    cl.setSize(450,900);
            cl.startRunning();
}
}
