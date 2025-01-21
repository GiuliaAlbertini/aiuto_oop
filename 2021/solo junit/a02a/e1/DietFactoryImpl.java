package a02a.e1;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import a02a.e1.Diet.Nutrient;

public class DietFactoryImpl implements DietFactory {

    @Override
    public Diet standard() {
        Set<Pair<String, Integer>> list = new HashSet<>();
        return new Diet() {

            @Override
            public void addFood(String name, Map<Nutrient, Integer> nutritionMap) {
                // popola la lista
                int totalCalories = 0;
                for (int value : nutritionMap.values()) {
                    totalCalories += value;
                }

                Pair<String, Integer> key = new Pair<String, Integer>(name, totalCalories);
                list.add(key);

            }

            @Override
            public boolean isValid(Map<String, Double> dietMap) {
                double somma = 0;

                for (Map.Entry<String, Double> key : dietMap.entrySet()) {
                    var foodname = key.getKey();
                    var grams = key.getValue();

                    for (var elem : list) {
                        if (elem.get1() == foodname) {
                            somma += elem.get2() * grams / 100;
                        }
                    }
                }

                if (somma >= 1500 && somma <= 2000) {
                    return true;
                } else {
                    return false;
                }
            }
        };
    }

    @Override
    public Diet lowCarb() {
        // mappa per 100 grammi
        Map<String, Map<Nutrient, Integer>> map = new HashMap<>();
        return new Diet() {

            @Override
            public void addFood(String name, Map<Nutrient, Integer> nutritionMap) {
                map.put(name, nutritionMap);
            }

            @Override
            public boolean isValid(Map<String, Double> dietMap) { // mappa che mi passa

                double carbsCalorie = 0;
                double calorieTotal = 0;

                for (Map.Entry<String, Double> diet_map : dietMap.entrySet()) {
                    for (Map.Entry<String, Map<Nutrient, Integer>> elem : map.entrySet()) {
                        Map<Nutrient, Integer> my_map = elem.getValue(); //metto la mappa
                        if (elem.getKey() == diet_map.getKey()) { // i nomi sono uguali

                            for (Nutrient nutrient : my_map.keySet()) { //scorro i nutrienti
                                if (nutrient == Nutrient.CARBS) {
                                    carbsCalorie += my_map.get(nutrient) * diet_map.getValue() / 100;
                                }
                                calorieTotal += my_map.get(nutrient) * diet_map.getValue() / 100;
                            }
                        }
                    }
                }
                
                if (calorieTotal >= 1000 && calorieTotal <= 1500 && carbsCalorie <= 300) {
                    return true;
                }
                return false;

            }

        };
    }

    @Override
    public Diet highProtein() {
        return new Diet() {

            Map<String, Map<Nutrient, Integer>> map= new HashMap<>();
            @Override
            public void addFood(String name, Map<Nutrient, Integer> nutritionMap) {
                map.put(name, nutritionMap); //metto i valori dentro la mappa
            }

            @Override
            public boolean isValid(Map<String, Double> dietMap) {
                double cal_testmap;
                double valcarb=0;
                double valprot=0;
                double totalCalories=0;
                for (Map.Entry<String,Double> test_map : dietMap.entrySet()) {
                    cal_testmap= test_map.getValue(); //mi segno i grammi mangiati della mappa
                    double moltiplicatore= cal_testmap/100;

                    for (Map.Entry<String, Map<Nutrient, Integer>> my_map : map.entrySet()) {
                         Map<Nutrient, Integer> mapnutrients = my_map.getValue();
                        if(test_map.getKey()== my_map.getKey()){ //guardo se il nome è uguale
                            for (Nutrient nutrients : mapnutrients.keySet()) {
                                if (nutrients == Nutrient.CARBS){
                                    valcarb=moltiplicatore*mapnutrients.get(nutrients);
                                }
                                if (nutrients == Nutrient.PROTEINS){
                                    valprot=moltiplicatore*mapnutrients.get(nutrients);
                                }
                                totalCalories+=mapnutrients.get(nutrients)*moltiplicatore;
                            }
                        }
                    }
                }
                if (totalCalories>=2000 && totalCalories<=2500 && valcarb<=300 && valprot >= 1300){
                    return true;
                }
                return false;
            }
            
        };
    }

    @Override
    public Diet balanced() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'balanced'");
    }

}
