package proyecto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class EditorTexto extends JFrame implements ActionListener {
    JTextArea textArea;
    JFileChooser fileChooser;

    public EditorTexto() {
        setTitle("Simple Text Editor");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem saveItem = new JMenuItem("Save");
        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        fileChooser = new JFileChooser();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Open")) {
            int returnValue = fileChooser.showOpenDialog(this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    FileReader fileReader = new FileReader(selectedFile);
                    BufferedReader reader = new BufferedReader(fileReader);
                    String line;
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    reader.close();
                    textArea.setText(stringBuilder.toString());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else if (e.getActionCommand().equals("Save")) {
            int returnValue = fileChooser.showSaveDialog(this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    FileWriter fileWriter = new FileWriter(selectedFile);
                    fileWriter.write(textArea.getText());
                    fileWriter.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        EditorTexto editor = new EditorTexto();
        editor.setVisible(true);
    }
}

