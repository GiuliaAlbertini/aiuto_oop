package a02a.e2;

import javax.swing.*;

import org.junit.jupiter.api.Disabled;

import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {

    private final Map<JButton, Pair<Integer,Integer>> cells = new HashMap<>();
    Logic logic;
    public GUI(int size) {
        this.logic= new LogicImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(70 * size, 70 * size);
    
        JPanel panel = new JPanel(new GridLayout(size, size));
        this.getContentPane().add(panel);

        ActionListener al = e -> {
            var jb = (JButton) e.getSource();
            var pos= cells.get(jb);
            logic.hit(pos.e1(), pos.e2());
            updatecell();
        };

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final JButton jb = new JButton();
                this.cells.put(jb, new Pair<Integer,Integer>(i, j));
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        logic.initialize();
        updatecell();
        this.setVisible(true);
        
    }
    void updatecell(){
            for (var elem : cells.entrySet()) {
                var button = elem.getKey();
                var pos= elem.getValue();
                int tmp= logic.SetCells(pos.e1(), pos.e2());
                if (tmp==9){
                    button.setEnabled(false);
                }else if (tmp==1){
                    button.setText("*");
                }else if (tmp==2){
                    button.setText("o");
                }else{
                    button.setText("");
                }
            }
    }
}
