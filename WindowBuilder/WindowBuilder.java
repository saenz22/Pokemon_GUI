import javax.swing.*;
import java.awt.Color;
import helper_classes.*;

public class WindowBuilder {
  public static void main(String[] args) {

     JFrame frame = new JFrame("My Awesome Window");
     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     frame.setSize(994, 536);
     JPanel panel = new JPanel();
     panel.setLayout(null);
     panel.setBackground(Color.decode("#f4c064"));

     JLabel element1 = new JLabel("");
     element1.setBounds(160, 23, 222, 23);
     element1.setFont(CustomFontLoader.loadFont("./resources/fonts/Lexend.ttf", 15));
     element1.setForeground(Color.decode("#000"));
     panel.add(element1);

     JTextField element2 = new JTextField("");
     element2.setBounds(390, 269, 163, 38);
     element2.setFont(CustomFontLoader.loadFont("./resources/fonts/Lexend.ttf", 25));
     element2.setBackground(Color.decode("#ffe7bf"));
     element2.setForeground(Color.decode("#73664e"));
     element2.setBorder(new RoundedBorder(2, Color.decode("#000"), 1));
     OnFocusEventHelper.setOnFocusText(element2, "poke2", Color.decode("#000"),   Color.decode("#73664e"));
     panel.add(element2);

     JTextField element3 = new JTextField("");
     element3.setBounds(383, 152, 167, 24);
     element3.setFont(CustomFontLoader.loadFont("./resources/fonts/Lexend.ttf", 25));
     element3.setBackground(Color.decode("#ffe7bf"));
     element3.setForeground(Color.decode("#73664e"));
     element3.setBorder(new RoundedBorder(2, Color.decode("#000"), 1));
     OnFocusEventHelper.setOnFocusText(element3, "poke1", Color.decode("#000"),   Color.decode("#73664e"));
     panel.add(element3);

     JTextField element4 = new JTextField("");
     element4.setBounds(385, 385, 167, 24);
     element4.setFont(CustomFontLoader.loadFont("./resources/fonts/Lexend.ttf", 25));
     element4.setBackground(Color.decode("#ffe7bf"));
     element4.setForeground(Color.decode("#73664e"));
     element4.setBorder(new RoundedBorder(2, Color.decode("#000"), 1));
     OnFocusEventHelper.setOnFocusText(element4, "poke3", Color.decode("#000"),   Color.decode("#73664e"));
     panel.add(element4);

     JLabel element5 = new JLabel("Pokemon 3");
     element5.setBounds(387, 339, 185, 39);
     element5.setFont(CustomFontLoader.loadFont("./resources/fonts/Lexend.ttf", 30));
     element5.setForeground(Color.decode("#000"));
     panel.add(element5);

     JLabel element6 = new JLabel("Ingresa los nombres de tus pokemones");
     element6.setBounds(110, 10, 808, 55);
     element6.setFont(CustomFontLoader.loadFont("./resources/fonts/Lexend.ttf", 40));
     element6.setForeground(Color.decode("#000"));
     panel.add(element6);

     JLabel element7 = new JLabel("Pokemon 1");
     element7.setBounds(386, 110, 174, 37);
     element7.setFont(CustomFontLoader.loadFont("./resources/fonts/Lexend.ttf", 30));
     element7.setForeground(Color.decode("#000"));
     panel.add(element7);

     JLabel element8 = new JLabel("Pokemon 2");
     element8.setBounds(385, 219, 187, 42);
     element8.setFont(CustomFontLoader.loadFont("./resources/fonts/Lexend.ttf", 30));
     element8.setForeground(Color.decode("#000"));
     panel.add(element8);

     JButton element9 = new JButton("Continuar");
     element9.setBounds(869, 446, 106, 30);
     element9.setBackground(Color.decode("#bca8e4"));
     element9.setForeground(Color.decode("#000"));
     element9.setFont(CustomFontLoader.loadFont("./resources/fonts/Lexend.ttf", 14));
     element9.setBorder(new RoundedBorder(4, Color.decode("#3d364a"), 1));
     element9.setFocusPainted(false);
     OnClickEventHelper.setOnClickColor(element9, Color.decode("#7c6f97"), Color.decode("#bca8e4"));
     panel.add(element9);

     frame.add(panel);
     frame.setVisible(true);

  }
}