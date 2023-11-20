package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.io.IOException;

import shared.Message;

public class ClientGUI {
    JFrame mainWindow = new JFrame("聊天窗口");
    JButton sendButton = new JButton("发送");//发送按钮
    private JTextArea displayTextArea = new JTextArea(14,40); //消息展示框
    private JTextArea inputTextArea = new JTextArea(4,30); //消息发送框

    public void init(String room) {
        mainWindow.setTitle("网上聊天室: "+room);

        // 底部输入区
        JPanel bottomPanel=new JPanel();
        mainWindow.add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.add(inputTextArea);
        bottomPanel.add(sendButton);

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取用户发送的消息
                String messageStr = inputTextArea.getText();
                sendMessage(messageStr);
                inputTextArea.setText("");
            }
        };
        sendButton.addActionListener(listener);

        // 顶部发送区
        JPanel centerPanel = new JPanel();
        mainWindow.add(centerPanel,BorderLayout.CENTER);
        centerPanel.add(displayTextArea);

        mainWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainWindow.pack(); //拥有关闭窗口的功能
        mainWindow.setVisible(true);
        
    }

    private void sendMessage(String messageStr) {
        ChatroomClient.sendMessage(messageStr);
    }
    public void displayMessage(Message messageSource) throws IOException{
        String message = messageSource.output();
        displayTextArea.append(message+'\n');
    }
    public boolean getWindowStatus()
    {
        return mainWindow.isShowing();
    }
    public String getInputInfo(String message,String defaultStr) {
        String result = JOptionPane.showInputDialog(mainWindow, message);
        if(!result.isEmpty())
            return result;
        else
            return defaultStr;
    }
    public void displayInfo(String info) {
        JOptionPane.showMessageDialog(mainWindow, info, info, 0);
    }
}
