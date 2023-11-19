package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.io.IOException;

import shared.Message;

public class ClientGUI implements Runnable{
    JFrame mainWindow = new JFrame("聊天窗口");
    JButton sendButton = new JButton("发送");//发送按钮
    private JTextArea displayTextArea = new JTextArea(14,40); //消息展示框
    private JTextArea inputTextArea = new JTextArea(4,40); //消息发送框

    @Override
    public void run() {
        mainWindow.setTitle("网上聊天室");

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

        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.pack(); //拥有关闭窗口的功能
        mainWindow.setVisible(true);
    }

    public void sendMessage(String messageStr) {
        ChatroomClient.sendMessage(messageStr);
    }
    public void displayMessage(Message messageSource) throws IOException{
        String message = messageSource.output();
        displayTextArea.append(message+'\n');
    }
}
