package a01b.e2;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Pair<Integer,Integer>> cells = new HashMap<>();
    Logic logic;
    public GUI(int size) {
        this.logic= new LogicImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
            var jb = (JButton)e.getSource();
            var pos= cells.get(jb);
            logic.hit(pos.getX(), pos.getY());
        	updateCells();
            if (logic.isOver()){
                System.out.println(0);
            }
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
            	var pos = new Pair<>(j,i);
                final JButton jb = new JButton();
                this.cells.put(jb, pos);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }
    
    void updateCells(){
        for (Map.Entry<JButton, Pair<Integer,Integer>> elem : cells.entrySet()) {
            var bottone= elem.getKey();
            var pos= elem.getValue();
            var tmp= logic.getstate(pos.getX(), pos.getY());
            if (tmp!= 0){
                bottone.setText(String.valueOf(tmp).toString());
            } else{
                bottone.setText("");
            }
        }
    }

}
