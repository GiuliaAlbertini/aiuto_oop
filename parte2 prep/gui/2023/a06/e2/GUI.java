package a06.e2;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Pair<Integer,Integer>> cells = new HashMap<>();
    private Logic logic;
    
    public GUI(int width) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(70*width, 70*width);
        this.logic = new LogicImpl(width);
        
        JPanel panel = new JPanel(new GridLayout(width,width));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
            var jb = (JButton)e.getSource();
            var pos = cells.get(jb);
            logic.hit(pos);
        	updateCells();
        };
                
        for (int i=0; i<width; i++){
            for (int j=0; j<width; j++){
            	var pos = new Pair<>(j,i);
                final JButton jb = new JButton();
                this.cells.put(jb, pos);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        logic.initialMap();
        this.setVisible(true);
        
    }
    
    void updateCells(){
        var list = logic.getList();
        var set = logic.getSet();
        for (var elem : cells.entrySet()) {
            var button = elem.getKey();
            var pos = elem.getValue();
            for (var val : list) {
                if (val.equals(pos)) {
                    button.setText(String.valueOf(logic.getValue(pos)));
                }else{
                    button.setText("");
                }
            }
            for (var cazzo : set) {
                if (cazzo.equals(pos)) {
                    button.setEnabled(false);
                }
            }
        }
       
    }
}
