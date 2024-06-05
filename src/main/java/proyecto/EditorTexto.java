package proyecto;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class EditorTexto extends JFrame implements ActionListener {
    JTextPane textPane;
    JFileChooser fileChooser;
    JComboBox<Integer> sizeComboBox;
    JButton boldButton, italicButton, underlineButton;

    public EditorTexto() {
        setTitle("Editor de Texto Simple");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        textPane = new JTextPane();
        JScrollPane scrollPane = new JScrollPane(textPane);
        add(scrollPane, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Archivo");
        JMenuItem openItem = new JMenuItem("Abrir");
        JMenuItem saveItem = new JMenuItem("Guardar");
        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        JPanel formatPanel = new JPanel();
        boldButton = new JButton("Negrita");
        italicButton = new JButton("Cursiva");
        underlineButton = new JButton("Subrayar");
        boldButton.setFont(boldButton.getFont().deriveFont(Font.BOLD));
        italicButton.setFont(italicButton.getFont().deriveFont(Font.ITALIC));
        underlineButton.setFont(underlineButton.getFont().deriveFont(Font.PLAIN));

        boldButton.addActionListener(this);
        italicButton.addActionListener(this);
        underlineButton.addActionListener(this);

        formatPanel.add(boldButton);
        formatPanel.add(italicButton);
        formatPanel.add(underlineButton);

        sizeComboBox = new JComboBox<>(new Integer[]{8, 10, 12, 14, 16, 18, 20, 24, 28, 32, 36, 40, 48, 56, 64, 72});
        sizeComboBox.addActionListener(this);
        formatPanel.add(sizeComboBox);

        add(formatPanel, BorderLayout.NORTH);

        fileChooser = new JFileChooser();
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Abrir")) {
            int returnValue = fileChooser.showOpenDialog(this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    FileReader fileReader = new FileReader(selectedFile);
                    BufferedReader reader = new BufferedReader(fileReader);
                    textPane.read(reader, null);
                    reader.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else if (command.equals("Guardar")) {
            int returnValue = fileChooser.showSaveDialog(this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    FileWriter fileWriter = new FileWriter(selectedFile);
                    textPane.write(fileWriter);
                    fileWriter.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else if (e.getSource() == boldButton) {
            toggleStyle(StyleConstants.Bold);
        } else if (e.getSource() == italicButton) {
            toggleStyle(StyleConstants.Italic);
        } else if (e.getSource() == underlineButton) {
            toggleStyle(StyleConstants.Underline);
        } else if (e.getSource() == sizeComboBox) {
            setFontSize();
        }
    }

    private void toggleStyle(Object style) {
        StyledDocument doc = textPane.getStyledDocument();
        int start = textPane.getSelectionStart();
        int end = textPane.getSelectionEnd();
        if (start != end) {
            MutableAttributeSet attr = new SimpleAttributeSet(doc.getCharacterElement(start).getAttributes());
            boolean current = (Boolean) attr.getAttribute(style) == Boolean.TRUE;
            StyleConstants.setBold(attr, style == StyleConstants.Bold ? !current : StyleConstants.isBold(attr));
            StyleConstants.setItalic(attr, style == StyleConstants.Italic ? !current : StyleConstants.isItalic(attr));
            StyleConstants.setUnderline(attr, style == StyleConstants.Underline ? !current : StyleConstants.isUnderline(attr));
            doc.setCharacterAttributes(start, end - start, attr, false);
        }
    }

    private void setFontSize() {
        StyledDocument doc = textPane.getStyledDocument();
        int start = textPane.getSelectionStart();
        int end = textPane.getSelectionEnd();
        if (start != end) {
            int fontSize = (Integer) sizeComboBox.getSelectedItem();
            MutableAttributeSet attr = new SimpleAttributeSet();
            StyleConstants.setFontSize(attr, fontSize);
            doc.setCharacterAttributes(start, end - start, attr, false);
        }
    }

    public static void main(String[] args) {
        EditorTexto editor = new EditorTexto();
        editor.setVisible(true);
    }
}
