package a01a.e2;
import java.util.HashMap;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {
    
    private final Map<JButton, Pair<Integer, Integer>> cells = new HashMap<>();
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
                var pos= cells.get(button);
                logic.hit(pos.getX(), pos.getY());
                updateCells();

                if (logic.isOver()){
                    System.exit(0);
                }
        	    //var position = cells.indexOf(button);
                //button.setText(""+position);
            }
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(" ");
                this.cells.put(jb, new Pair<Integer, Integer>(i,j));
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }    

    void updateCells(){
        for (Map.Entry<JButton, Pair<Integer, Integer>> elem : cells.entrySet()) {
            var bottone = elem.getKey();
            var value= elem.getValue();
            var tmp= logic.selecCells(value.getX(), value.getY());
            if (tmp){
                bottone.setText(String.valueOf("*").toString());
            }else{
                bottone.setText("");
            }
        }
    }
}
