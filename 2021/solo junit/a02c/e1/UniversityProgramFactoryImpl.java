package a02c.e1;

import static a02c.e1.UniversityProgram.Group.OPTIONAL;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import a02c.e1.UniversityProgram.Group;

public class UniversityProgramFactoryImpl implements UniversityProgramFactory {

    @Override
    public UniversityProgram flexible() {
        Map<String, Pair<Set<Group>, Integer>> map = new HashMap<>();
        return new UniversityProgram() {

            @Override
            public void setCourses(Map<String, Pair<Set<Group>, Integer>> courses) {
                for (Map.Entry<String, Pair<Set<Group>, Integer>> course : courses.entrySet()) {
                    map.put(course.getKey(), course.getValue());
                }
            }

            @Override
            public boolean isValid(Set<String> courseNames) {
                int somma=0;
                for (String corso : courseNames) {
                    for (Map.Entry<String, Pair<Set<Group>, Integer>> course : map.entrySet()) {
                        if (corso==course.getKey()){
                            Pair<Set<Group>, Integer> key= course.getValue();
                            somma+= key.get2();
                        }
                    }
                }
                if (somma==48){
                    return true;
                }
                return false;
            }
        };
    }

    @Override
    public UniversityProgram fixed() {
        Map<String, Pair<Set<Group>, Integer>> map = new HashMap<>();

        return new UniversityProgram() {

            @Override
            public void setCourses(Map<String, Pair<Set<Group>, Integer>> courses) {
                for (Map.Entry<String, Pair<Set<Group>, Integer>> course : courses.entrySet()) {
                    map.put(course.getKey(), course.getValue());
                }
            }

            @Override
            public boolean isValid(Set<String> courseNames) {
                int somma=0;
                int sumMAN=0;
                int optional=0;
                for (String corso : courseNames) {
                    for (Map.Entry<String, Pair<Set<Group>, Integer>> course : map.entrySet()) {
                        if (corso==course.getKey()){
                            Pair<Set<Group>, Integer> key= course.getValue();
                            somma+= key.get2();
                            
                            for (Group section : key.get1()) {
                                if(section == Group.MANDATORY){
                                    sumMAN += key.get2();
                                }
                                if(section == Group.OPTIONAL){
                                    optional += key.get2();
                                }
                            }
                        }
                    }
                }
                if (somma==60 && sumMAN==12 && optional==36){
                    return true;
                }
                return false;
            }
            
        };
    }

    @Override
    public UniversityProgram balanced() {
        Map<String, Pair<Set<Group>, Integer>> map = new HashMap<>();
        return new UniversityProgram() {

            @Override
            public void setCourses(Map<String, Pair<Set<Group>, Integer>> courses) {
                for (Map.Entry<String, Pair<Set<Group>, Integer>> course : courses.entrySet()) {
                     map.put(course.getKey(), course.getValue());
                }
            }

            @Override
            public boolean isValid(Set<String> courseNames) {
                int somma=0;
                int sumMAN=0;
                int optional=0;
                int free=0;
                for (String corso : courseNames) {
                    for (Map.Entry<String, Pair<Set<Group>, Integer>> course : map.entrySet()) {
                        if (corso==course.getKey()){
                            Pair<Set<Group>, Integer> key= course.getValue();
                            somma+= key.get2();
                            
                            for (Group section : key.get1()) {
                                if(section == Group.MANDATORY){
                                    sumMAN += key.get2();
                                }
                                if(section == Group.OPTIONAL){
                                    optional += key.get2();
                                }
                                if(section == Group.FREE){
                                    free += key.get2();
                                }
                            }
                        }
                    }
                }
                if (somma==60 && sumMAN==24 && optional>=24 && free <=12){
                    return true;
                }
                return false;
            }
            
        };
    }

    @Override
    public UniversityProgram structured() {
        Map<String, Pair<Set<Group>, Integer>> map = new HashMap<>();

        return new UniversityProgram() {

            @Override
            public void setCourses(Map<String, Pair<Set<Group>, Integer>> courses) {
                for (Map.Entry<String, Pair<Set<Group>, Integer>> course : courses.entrySet()) {
                    map.put(course.getKey(), course.getValue());
               }
            }

            @Override
            public boolean isValid(Set<String> courseNames) {
                int somma=0;
                int somma2=0;
                int sumMAN=0;
                int optional_A=0;
                int optional_B=0;
                int free=0;
                for (String corso : courseNames) {
                    for (Map.Entry<String, Pair<Set<Group>, Integer>> course : map.entrySet()) {
                        if (corso==course.getKey()){
                            Pair<Set<Group>, Integer> key= course.getValue();
                            somma+= key.get2();
                            
                            for (Group section : key.get1()) {
                                if(section == Group.MANDATORY){
                                    sumMAN += key.get2();
                                }
                                if(section == Group.OPTIONAL_A ){
                                    optional_A += key.get2();
                                    break;
                                }
                                if(section == Group.OPTIONAL_B){
                                    optional_B += key.get2();
                                }
                                if(section == Group.FREE){
                                    free += key.get2();
                                }
                                if(section == Group.THESIS){
                                    free += key.get2();
                                }
                            }
                        }
                    }
                }
                somma2= optional_A + optional_B;
                if (somma==60 && sumMAN==12 && optional_A>=6 && optional_B>=6 && somma2==30 && free==18){
                    return true;
                }
                return false;
            }
            
        };
    }

}
