package a01a.e2;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
     Map<JButton, Pair<Integer,Integer>> cells = new HashMap<>();
     Logic logic;
    
    public GUI(int size) {
        this.logic= new LogicImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(50*size, 50*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
        	var button = (JButton)e.getSource();
            //prendo la posizione premuta
            var pos= new Pair<>(cells.get(button).getX(), cells.get(button).getY());
            logic.hit(pos.getX(), pos.getY());
            update();
            
        if (logic.isOver()) {
            System.exit(0);
        }
           

        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(" ");
                this.cells.put(jb, new Pair<Integer,Integer>(i, j));
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }
    void update(){
        var set = logic.getSelectedCells();
        var list= logic.getList();

        for (Map.Entry<JButton, Pair<Integer,Integer>> pair : cells.entrySet()) {
                JButton jb = pair.getKey(); //bottone
                Pair<Integer,Integer> pos= pair.getValue(); // posizione che sto guardando

                for (Pair<Integer,Integer> elem : list) {
                    if(elem.equals(pos)){
                        jb.setText(String.valueOf(1));;
                    }
                }
                for (var elem : set) {
                    if(elem.equals(pos)){
                        jb.setText("*");
                    }
                }
        }
    }
}
