package a01a.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    Map<JButton, Pair< Integer,Integer>> cells = new HashMap<>();
    Logic logic;
    
    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        this.logic= new LogicImpl(size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
            var jb = (JButton)e.getSource();

            var pos = cells.get(jb);
            
            logic.hit(pos.getX(), pos.getY());//
            updateCells();//
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

    //chiave bottone - valore posizione
    void updateCells(){
        //guardo le accoppiate chiave valore (bottone-posizione)
        for(var elem : cells.entrySet()){
           var button=elem.getKey(); //chiave
           var pos=elem.getValue();//posizione
           int tmp= logic.getState(pos.getX(), pos.getY()); //valore in quella posizione
            if(tmp != 0){
                button.setText(String.valueOf(tmp).toString());
            }else{
                button.setText(" ");
            }
        }
    }
}
