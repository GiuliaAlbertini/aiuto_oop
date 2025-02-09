package a05.e2;

import javax.swing.*;

import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {

    /*
     * LA GUI OPERA IN MODO SEMPLICE
     * PASSA LA POSIZONE DEL BOTTONE PREMUTO ALLA LOGIC.HIT
     * AGGIORNA LE CELLE CON UPDATECELLS
     * VERIFICA SE L'APPLICAZIONE TERMINA CON LA LOGIC.GETISOVER
     */
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Pair<Integer, Integer>> cells = new HashMap<>();
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
            if (logic.getIsOver()) {
                System.exit(0);
            }

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
        this.setVisible(true);
    }

    /*
     * QUA ANDIAMO A SCORRERE TUTTA CELLS
     * MI SALVO OGNI VOLTA IL JB (JBUTTON) E LA RELATIVA POS
     * SE IN CORRISPONDENZA DELLA POSIZIONE DI QUEL JB NELLA MAP IN LOGICIMPL CE QUALCOSA ALLORA VADO A SETTARE 
     * IL TESTO DEL JB A QUELLA COSA, OTTENUTA USANDO LOGIC.GETVALUE(POS) PASSANDOGLI LA POSIZIONE DEL BOTTONE
     * SE NON CE NULLA LO SETTO VUOTO
     */
    void updateCells(){


        for (var elem : cells.entrySet()) {
            var jb = elem.getKey();
            var pos = elem.getValue();
            if (logic.getValue(pos) != " ") {
                jb.setText(logic.getValue(pos));
            } else {
                jb.setText(" ");
            }
        }

    }
    
}
