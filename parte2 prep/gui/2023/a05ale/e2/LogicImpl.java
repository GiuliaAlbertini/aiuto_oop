package a05.e2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LogicImpl implements Logic {

    /*
     * L'IDEA è QUESTA: AL PRIMO CLICK POSIZIONO P E N EVITANDO CHE SIANO DIACENTI
     * A OGNI CLICK SUCCESIVO SE IN UNA DELLE CELLE ADIACENTI DI P MUOVO P
     * CONTROLLO SE P FINISCE IN UNA CELLA ADIACENTE DI N E NEL CASO MUOVO N
     * N LA MUOVO IN UNA DELLE SUE CELLE ADIACENTI CHE NON SIA ANCHE CELLA ADIACENTE
     * DI P
     * SE P E N COMBACIANO DI POSIZIONE TERMINA
     */

    /*
     * MAP DI POSIZIONI E STRINGHE, STRINGHE PER FACILITà ESSENDO CHE LAVORIAMO SU P
     * E N
     * START è IL BOOLEANO CHE INDICA IL PRIMO CLICK
     * ENEMYPOS E PLAYERPOS SONO LE POSIZIONI DI P E N
     * RAND SERVE PER USARE I NUMERI RANDOM
     */

    int size;
    Map<Pair<Integer, Integer>, String> map = new HashMap<>();
    boolean start = true;
    Random rand = new Random();
    Pair<Integer, Integer> enemyPos;
    Pair<Integer, Integer> playerPos;
    boolean isOver = false;

    /*
     * INIZIALIZZO TUTTO VUOTO NEL COSTRUTTORE
     */

    public LogicImpl(int size) {
        this.size = size;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                var pos = new Pair<>(i, j);
                map.put(pos, " ");
            }
        }
    }

    @Override
    public void hit(Pair<Integer, Integer> pos) {
        /*
         * GESTISCO IL PRIMO CLICK OVVERO START QUANDO è TRUE
         * ASSEGNO UNA POSIZIONE CASUALE A PLAYER
         * PER EVITARE CHE P E N ABBIANO LA POSIZIONE ADIACENTE USO UN DO WHILE
         * RIASSEGNO LA POSIZIONE A N FINCHE è ADIACENTE A P
         * APPENA N NON SARà ADIACENTE A P USCIRà DAL DO-WHILE E START VERRA MESSO A
         * FALSE
         * COSI DA NON RIENTRARE PIU QUI
         */
        if (start) {
            playerPos = new Pair<>(rand.nextInt(size), rand.nextInt(size));
            do {
                enemyPos = new Pair<>(rand.nextInt(size), rand.nextInt(size));
            } while (areNear(playerPos, enemyPos));

            map.put(enemyPos, "N");
            map.put(playerPos, "P");
            start = false;
        }

        /*
         * QUA ENTRIAMO DOPO IL PRIMO CLICK
         * SFRUTTO IL METODO CHE AVEVO CREATO PER VERIFICARE SE DUE CELLE SONO ADIACENTI
         * CONTROLLO SE IL CLICK è IN UNA POSIZIONE ADIACENTE A P
         * SE LO è ASSEGNO P ALLA POSIZIONE DEL CLICK E PULISCO LA VECCHIA POSIZIONE DI
         * P
         * SALVO LA NUOVA POSIZIONE DI P
         */
        if (!start) {
            if (areNear(pos, playerPos)) {
                map.put(pos, "P");
                map.put(playerPos, " ");
                playerPos = pos;
            }
            /*
             * QUI VERIFICO IL CASO IN CUI P FINISCA SU UNA CELLA ADIACENTE DI N
             * SFRUTTO SEMPRE IL MIO METODO CHE MI DICE SE DUE CELLE SONO ADUACENTI
             * PASSANDOGLI P E N
             * SE LO SONO AZIONO IL METODO CHE SI OCCUPA DI MUOVERE N
             */
            if (areNear(playerPos, enemyPos)) {
                moveEnemy();
            }
            /*
             * QUI VERIFICO IL CASO IN CUI P E N FINISCANO SULLA STESSA POSIZONE
             * METTO ISOVER A TRUE CHE INDICA LA FINE DEL GIOCO
             */
            if (playerPos == enemyPos) {
                this.isOver = true;
            }
        }

    }

    /*
     * QUESTO METODO VERIFICA SE DUE POSIZIONI SONO ADIACENTI
     * SE SONO ADICENTI IL VALORE ASSOLUTO DELLA DIFFEREZA DELLE LORO X E Y SARA 1 O
     * 0
     * AD ESEMPIO POS 2,3 RISULTA ADIACENTE A 3,3, OTTERREMO UN DX = 1 E UN DY = 0
     */
    private boolean areNear(Pair<Integer, Integer> pos1, Pair<Integer, Integer> pos2) {
        int dx = Math.abs(pos1.getX() - pos2.getX());
        int dy = Math.abs(pos1.getY() - pos2.getY());
        return dx <= 1 && dy <= 1;
    }

    /*
     * METODO CHE USO PER RITORNARE IL CONTENUTO DI UNA POSIZIONE
     * MI SERVIRà PER LA GUI
     */
    @Override
    public String getValue(Pair<Integer, Integer> pos) {
        return map.get(pos);
    }

    /*
     * METODO CHE GESTISCE IL MOVIMENTO DI N QUANDO P ENTRA IN UNA CELLA ADIACENTE
     * CREO UNA LISTA DI MOSSE POSSIBILI PARTENDO DA TUTTE LE CELLE ADIACENTI A N
     * FILTRO LE CELLE CHE RIENTRANO NELLA GRIGLIA E NON SONO ADIACENTI A P
     * SE CI SONO MOSSE DISPONIBILI, SCELGO UNA CASUALE, AGGIORNO LA POSIZIONE DI N
     * E PULISCO LA VECCHIA POSIZIONE
     */
    private void moveEnemy() {
        var possibleMoves = new ArrayList<Pair<Integer, Integer>>();
        /*
         * SCORRO TUTTE LE CELLE ADIACENTI A N
         * SE SONO DENTRO LA GRIGLIA E NON ADIACENTI A P, LE AGGIUNGO ALLE MOSSE
         * POSSIBILI
         */
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) // EVITO DI CONSIDERARE LA POSIZIONE STESSA DI N
                    continue;
                Pair<Integer, Integer> newPos = new Pair<>(enemyPos.getX() + dx, enemyPos.getY() + dy);
                if (isInBounds(newPos) && !areNear(newPos, playerPos)) {
                    possibleMoves.add(newPos);
                }
            }
        }
        /*
         * SE CI SONO MOSSE DISPONIBILI, SCElgo UNA CASUALE
         * PULISCO LA VECCHIA POSIZIONE DI N E AGGIORNO LA MAPPA CON LA NUOVA POSIZIONE
         */

        if (!possibleMoves.isEmpty()) {
            map.put(enemyPos, " ");
            enemyPos = possibleMoves.get(rand.nextInt(possibleMoves.size()));
            map.put(enemyPos, "N");
        }
    }

    /*
     * METODO CHE MI DICE SE UNA POSIZONE è NELLA GRIGLIA
     */
    private boolean isInBounds(Pair<Integer, Integer> pos) {
        return pos.getX() >= 0 && pos.getX() < size && pos.getY() >= 0 && pos.getY() < size;
    }

    /*
     * METODO CHE RITORNA ISOVER
     * SERVIRà NELLA GUI
     */
    @Override
    public boolean getIsOver() {
        return this.isOver;
    }

}
