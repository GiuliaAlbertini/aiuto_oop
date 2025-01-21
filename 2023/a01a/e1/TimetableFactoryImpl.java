package a01a.e1;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

public class TimetableFactoryImpl implements TimetableFactory {

    public record Timetabledata (Set<String> activities, Set<String> days, Map<Pair<String,String>, Integer> data) 
    implements Timetable{

        @Override
        public Timetable addHour(String activity, String day) {
         Set<String> new_activities= new HashSet<>(activities);//creo nuovo set di attività
         new_activities.add(activity); //aggiungo l'attività se non esiste

         Set<String> new_days= new HashSet<>(days);
         new_days.add(day);
         Map<Pair<String,String>, Integer> new_data= new HashMap<>(data);//creo la nuova mappa
         Pair<String,String> key= new Pair<String,String>(activity, day); //creo la chiave
         //metto dentro alla tabella data e inserisco alla coppia (attività,giorno) o 0 se non c'è oppure 1
         new_data.put(key,new_data.getOrDefault(key, 0)+1);

         return new Timetabledata(Set.copyOf(new_activities), Set.copyOf(new_days), Map.copyOf(new_data));

        }

        @Override
        public Set<String> days() {
           Set<String> new_days= new HashSet<>(this.days);
           return new_days;
        }

        @Override
        public int getSingleData(String activity, String day) {
            Pair<String,String> key= new Pair<String,String>(activity, day); //creo la chiave
            return data.get(key)== null ? 0 : data.get(key);
        }

        @Override
        public int sums(Set<String> activities, Set<String> days) {
            int sum=0;//creo nuovo set di attività
            for (String activity : activities) {
                for (String day : days) {
                    Pair<String,String> key= new Pair<>(activity, day); //creo la chiave
                    
                    if (data.containsKey(key)){
                        sum+=data.get(key);
                    }
                }
            }
            return sum;

        }
    }




    @Override
    public Timetable empty() {
        return new Timetabledata(Set.of(), Set.of(), Map.of());
    }

    @Override
    public Timetable single(String activity, String day) {
        return new Timetabledata(Set.of(),Set.of(),Map.of()).addHour(activity, day);
    }

    @Override
    public Timetable join(Timetable table1, Timetable table2) {
            Set<String> activites1= new HashSet<>(table1.activities());
            Set<String> activites2= new HashSet<>(table2.activities());
            Set<String> days1= new HashSet<>(table1.days());
            Set<String> days2= new HashSet<>(table2.days());
            activites1.addAll(activites2);
            days1.addAll(days2);
            Map<Pair<String,String>, Integer> data = new HashMap<>();
            
            for (String activity : activites1) {
                for (String day : days1) {
                    Pair<String,String> key = new Pair<>(activity, day);
                    int i= table1.getSingleData(activity, day) + table2.getSingleData(activity, day);
                    data.put(key, i);
                }
            }
        return new Timetabledata(activites1, days1, data);
    }

    @Override
    public Timetable cut(Timetable table, BiFunction<String, String, Integer> bounds) {
        Set<String> activities= new HashSet<>(table.activities());
        Set<String> days= new HashSet<>(table.days());
        Map<Pair<String,String>, Integer> data = new HashMap<>();

        for (String activity : activities) {
           for (String day : days) {
            Pair<String,String> key = new Pair<>(activity, day);
            int hour= table.getSingleData(activity, day);
            int bound=bounds.apply(activity, day);
            int limit=Math.min(hour, bound);
            data.put(key, limit);
           } 
        }
        return new Timetabledata(activities, days, data);
    }
    
}
