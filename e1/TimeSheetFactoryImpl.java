package a01c.e1;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TimeSheetFactoryImpl implements TimeSheetFactory{

    public class TimeSheetImpl implements TimeSheet {

        List<Pair<String, String>> data;
        
        public TimeSheetImpl(List<Pair<String, String>> data) {
            this.data = data;
        }

        @Override
        public Set<String> activities() {
                return data.stream().map(e->e.get1()).collect(Collectors.toSet());
        }

        @Override
        public Set<String> days() {
            return data.stream().map(e->e.get2()).collect(Collectors.toSet());
        }

        @Override
        public int getSingleData(String activity, String day) {
            long ris = data.stream().filter(e->e.get2().equals(day) && e.get1().equals(activity)).count();
            return (int)ris;
        }

        @Override
        public boolean isValid() {
           return false;
        }
        
    }

        @Override
        public TimeSheet ofRawData(List<Pair<String, String>> data) {
            
            return new TimeSheet() {
    
                @Override
                public Set<String> activities() {
                    if(isValid()){
                        return data.stream().map(e->e.get1()).collect(Collectors.toSet());
                    }else{
                        return Set.of();
                    }
                }
    
                @Override
                public Set<String> days() {
                    if(isValid()){
                        return data.stream().map(e->e.get2()).collect(Collectors.toSet());
                    }else{
                        return Set.of();
                    }
                    
                }
    
                @Override
                public int getSingleData(String activity, String day) {
                   if(isValid()){
                        long ris = data.stream().filter(e->e.get2().equals(day) && e.get1().equals(activity)).count();
                        return (int)ris;
                   }else{
                        return -1;
                   }
                }

                @Override
                public boolean isValid() {
                    if(data.isEmpty()){
                        return false;
                    }else{
                        return true;
                    }
                }
    
                
            };
        }

        @Override
    public TimeSheet withBounds(List<Pair<String, String>> data, Map<String, Integer> boundsOnActivities,
            Map<String, Integer> boundsOnDays) {
       
        return new TimeSheet() {
            TimeSheetFactoryImpl o = new TimeSheetFactoryImpl();
            TimeSheetImpl p = new TimeSheetImpl(data);
            @Override
            public Set<String> activities() {
                    return p.activities();
            }

            @Override
            public Set<String> days() {
                return p.days();
            }

            @Override
            public int getSingleData(String activity, String day) {
                return p.getSingleData(activity, day);
            }

            @Override
            public boolean isValid() {
                if(o.withBoundsPerActivity(data,boundsOnActivities).isValid() 
                   && o.withBoundsPerDay(data,boundsOnDays).isValid()){
                    return true;
                }else{
                    return false;
                }
            }
            
        };
    }

    @Override
    public TimeSheet withBoundsPerActivity(List<Pair<String, String>> data, Map<String, Integer> boundsOnActivities) {
        return new TimeSheet() {

            TimeSheetImpl p = new TimeSheetImpl(data);
            @Override
            public Set<String> activities() {
                    return p.activities();
            }

            @Override
            public Set<String> days() {
                return p.days();
            }

            @Override
            public int getSingleData(String activity, String day) {
                return p.getSingleData(activity, day);
            }

            @Override
            public boolean isValid() {
                for (String activity : boundsOnActivities.keySet()) {
                    long ris=data.stream().filter(e->e.get1().equals(activity)).count();
                    if(ris>boundsOnActivities.get(activity)){
                        return false;
                    }
               }
               return true;
            }
        };
    }

    @Override
    public TimeSheet withBoundsPerDay(List<Pair<String, String>> data, Map<String, Integer> boundsOnDays) {
        return new TimeSheet() {

            TimeSheetImpl p = new TimeSheetImpl(data);
            @Override
            public Set<String> activities() {
                    return p.activities();
            }

            @Override
            public Set<String> days() {
                return p.days();
            }

            @Override
            public int getSingleData(String activity, String day) {
                return p.getSingleData(activity, day);
            }

            @Override
            public boolean isValid() {
               for (String day : boundsOnDays.keySet()) {
                    long ris=data.stream().filter(e->e.get2().equals(day)).count();
                    if(ris>boundsOnDays.get(day)){
                        return false;
                    }
               }
               return true;
            }
            
        };
    }
    
}
