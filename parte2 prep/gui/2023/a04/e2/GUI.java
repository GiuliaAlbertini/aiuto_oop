package a04.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Pair<Integer, Integer>> cells = new HashMap<>();
    Logic logic;
    public GUI(int width) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(70*width, 70*width);
        this.logic= new LogicImpl(width);
       
        JPanel panel = new JPanel(new GridLayout(width,width));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
            var jb = (JButton)e.getSource();
            var pos= cells.get(jb);
            logic.hit(pos.getX(), pos.getY());
            if (logic.isOver()){
                disabilita();
            }
            updateCells();
        
        	//jb.setText(String.valueOf(cells.indexOf(jb)));
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
        logic.metti();
        updateCells();
        this.setVisible(true);
       
    }
    
    void updateCells(){
        for (Map.Entry<JButton, Pair<Integer, Integer>> elem : cells.entrySet()) {
            var button = elem.getKey();
            var pos = elem.getValue();
            var tmp = logic.SetCells(pos.getX(), pos.getY());
            if (tmp){
                button.setText(String.valueOf("*").toString());
            }else{
                button.setText(String.valueOf("").toString());
            }
        }
    }

    void disabilita(){
        for (Map.Entry<JButton, Pair<Integer, Integer>> elem : cells.entrySet()) {
            var button = elem.getKey();
            if (logic.isOver()){
                  button.setEnabled(false);
            }
        }
    }
}
