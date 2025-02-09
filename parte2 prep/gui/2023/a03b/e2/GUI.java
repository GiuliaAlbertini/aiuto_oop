package a03b.e2;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Pair<Integer,Integer>> cells = new HashMap<>();
    Logic logic;
    public GUI(int width, int height) {
        this.logic= new LogicImpl(height, width);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(70*width, 70*height);
        
        JPanel panel = new JPanel(new GridLayout(height,width));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
            var jb = (JButton)e.getSource();
            var pos = cells.get(jb);
            logic.hit(pos.getX(), pos.getY());
            update();
        };
                
        for (int i=0; i<height; i++){
            for (int j=0; j<width; j++){
            	var pos = new Pair<>(j,i);
                final JButton jb = new JButton();
                this.cells.put(jb, pos);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }

    void update(){
        for (var elem : cells.entrySet()) {
            var button = elem.getKey();
            var pos = elem.getValue();
            var tmp= logic.updateCells(pos.getX(), pos.getY());
            if (tmp){
                button.setText("**");
            }else{
                button.setText("");
            }
        }

    }
    
}
