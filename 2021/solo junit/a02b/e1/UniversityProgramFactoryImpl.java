package a02b.e1;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.print.DocFlavor.READER;

import a02b.e1.UniversityProgram.Sector;

public class UniversityProgramFactoryImpl implements UniversityProgramFactory {

    @Override
    public UniversityProgram flexible() {
        Map<String, Pair<Sector, Integer>> map = new HashMap<>();

        return new UniversityProgram() {

            @Override
            public void addCourse(String name, Sector sector, int credits) {
                Pair<Sector, Integer> key = new Pair<Sector, Integer>(sector, credits);
                map.put(name, key);
            }

            @Override
            public boolean isValid(Set<String> courseNames) {
                int somma = 0;

                for (String course : courseNames) { // programmazione
                    for (Map.Entry<String, Pair<Sector, Integer>> coppia : map.entrySet()) {
                        Pair<Sector, Integer> key = coppia.getValue();
                        if (course == coppia.getKey()) {
                            somma += key.get2();
                        }
                    }
                }

                if (somma == 60) {
                    return true;
                }
                return false;
            }

        };
    }

    @Override
    public UniversityProgram scientific() {
        Map<String, Pair<Sector, Integer>> map = new HashMap<>();
        return new UniversityProgram() {

            @Override
            public void addCourse(String name, Sector sector, int credits) {
                Pair<Sector, Integer> key = new Pair<Sector, Integer>(sector, credits);
                map.put(name, key);
            }

            @Override
            public boolean isValid(Set<String> courseNames) {
                int somma = 0;
                int sumMath = 0;
                int sumCS = 0;
                int sumPS = 0;
                for (String course : courseNames) { // programmazione
                    for (Map.Entry<String, Pair<Sector, Integer>> coppia : map.entrySet()) {
                        if (course == coppia.getKey()) {
                            Pair<Sector, Integer> key = coppia.getValue();
                            somma += key.get2();

                            if (key.get1() == Sector.MATHEMATICS) {
                                sumMath += key.get2();
                            }

                            if (key.get1() == Sector.COMPUTER_SCIENCE) {
                                sumCS += key.get2();
                            }

                            if (key.get1() == Sector.PHYSICS) {
                                sumPS += key.get2();
                            }
                        }

                    }
                }
                if (somma == 60 && sumMath >= 12 && sumCS >= 12 && sumPS >= 12) {
                    return true;
                }
                return false;
            }

        };
    }

    @Override
    public UniversityProgram shortComputerScience() {
        Map<String, Pair<Sector, Integer>> map = new HashMap<>();

        return new UniversityProgram() {

            @Override
            public void addCourse(String name, Sector sector, int credits) {
                Pair<Sector, Integer> key = new Pair<Sector, Integer>(sector, credits);
                map.put(name, key);
            }

            @Override
            public boolean isValid(Set<String> courseNames) {
                int somma = 0;
                int sumCE = 0;
                int sumCS = 0;
                int total_sum=0;
                for (String course : courseNames) { // programmazione
                    for (Map.Entry<String, Pair<Sector, Integer>> coppia : map.entrySet()) {
                        if (course == coppia.getKey()) {
                            Pair<Sector, Integer> key = coppia.getValue();
                            somma += key.get2();

                            if (key.get1() == Sector.COMPUTER_ENGINEERING) {
                                sumCE += key.get2();
                            }

                            if (key.get1() == Sector.COMPUTER_SCIENCE) {
                                sumCS += key.get2();
                            }
                        }

                    }
                }
                total_sum=sumCE+sumCS;
                if (somma >= 45 && total_sum>=30) {
                    return true;
                }
                return false;
            }
            
        };
    }

    @Override
    public UniversityProgram realistic() {
        return new UniversityProgram() {
            Map<String, Pair<Sector, Integer>> map = new HashMap<>();

            @Override
            public void addCourse(String name, Sector sector, int credits) {
                Pair<Sector, Integer> key = new Pair<Sector, Integer>(sector, credits);
                map.put(name, key);
            }

            @Override
            public boolean isValid(Set<String> courseNames) {
                int somma=0;
                int sumCE = 0;
                int sumMath=0;
                int sumCS = 0;
                int sumPS=0;
                int total_sum1=0;
                int total_sum2=0;
                int Thesis=0;
                for (String course : courseNames) { // programmazione
                    for (Map.Entry<String, Pair<Sector, Integer>> coppia : map.entrySet()) {
                        if (course == coppia.getKey()) {
                            Pair<Sector, Integer> key = coppia.getValue();
                            somma += key.get2();

                            if (key.get1() == Sector.COMPUTER_ENGINEERING) {
                                sumCE += key.get2();
                            }

                            if (key.get1() == Sector.COMPUTER_SCIENCE) {
                                sumCS += key.get2();
                            }
                            if (key.get1() == Sector.MATHEMATICS) {
                                sumMath += key.get2();
                            }

                            if (key.get1() == Sector.PHYSICS) {
                                sumPS += key.get2();
                            }
                        }

                    }
                }
                total_sum1=sumCE+sumCS;
                total_sum2=sumMath+sumPS;
                if (somma == 120 && total_sum1>=60 && total_sum2<=18 ) {
                    return true;
                }
                return false;
            }
            
        };
    }

}
