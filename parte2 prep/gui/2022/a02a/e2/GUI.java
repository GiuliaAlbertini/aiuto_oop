package a02a.e2;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {
    
    private final Map<JButton,Pair<Integer,Integer>> cells = new HashMap<>();
    Logic logic;
    public GUI(int size) {
        this.logic= new LogicImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = new ActionListener(){
            public void actionPerformed(ActionEvent e){
        	    var button = (JButton)e.getSource();
        	    var position = cells.get(button);
                logic.hit(position.getX(), position.getY());
                updateCells();

                if (logic.isOver()){
                    System.exit(1);
                }

            }
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(" ");
                this.cells.put(jb, new Pair<>(i, j));
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }    

    void updateCells(){
        for (var elem : cells.entrySet()) {
            var button= elem.getKey();
            var pos= elem.getValue();
            var tmp= logic.selectCells(pos.getX(), pos.getY());
            if (tmp==0){
                button.setEnabled(true);
                button.setText("");
            }else if (tmp==1){
                button.setText("B");
            }else if (tmp==3){
                button.setEnabled(false);
            }else if (tmp==4){
                button.setEnabled(true);
            }
        }
    }
}
