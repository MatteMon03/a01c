package a01c.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<Position,JButton> cells = new HashMap<>();
    Logic logic = new LogicImpl(cells);
    
    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(70*size, 70*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
            var jb = (JButton)e.getSource();
        	if(!logic.setClick(jb,logic.getkeyByValue(jb))){
                
                System.exit(0);
            }
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton();
                this.cells.put(new Position(i, j),jb);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }
    
}
