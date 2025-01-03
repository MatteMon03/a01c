package a01c.e2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;

public class LogicImpl implements Logic{
    Map<Position,JButton> cells = new HashMap<>();
    int cont=0;
    Position p1;
    Position p2;
    int i=1;
    Position p11=new Position(-1, -1);
    Position p22=new Position(-1, -1);
    
    public LogicImpl(Map<Position, JButton> cells) {
        this.cells = cells;
    }

    @Override
    public boolean setClick(JButton jb, Position p) {
        
        cont++;
        
        if(cont==1){
            p1=p;
            jb.setText("1");
            return true;
        }else if(cont==2){
            jb.setText("2");
            p2=p;
            return true;
        }else if(cont==3){
                genRet(p1,p2);
                
                return true;
        }else if(cont>3 && quit()){
          
            genZero(p1,p2,i);
            i++;
            return true;
        }else{
            return false;
        }
    }
            
    private void genZero(Position p1, Position p2,int i){
        p11 = new Position(p1.x()-i, p1.y()-i);
        p22 = new Position(p2.x()+i, p2.y()+i);
        genRet(p11, p22);
    }

    private void genRet(Position p1, Position p2) {
        List<Position> l = new ArrayList<>();
        l=cells.keySet().stream().filter(e->p1.x()<=e.x() && e.x()<=p2.x())
       .filter(e2->p1.y()<=e2.y() && e2.y()<=p2.y()).toList();
       l.forEach(e->cells.get(e).setText("0"));
       cells.get(p1).setText("1");
       cells.get(p2).setText("2");
    }

    @Override
    public Position getkeyByValue(JButton jb) {
        for (Map.Entry<Position, JButton> entry : cells.entrySet()) {
            if (entry.getValue().equals(jb)) {
                return entry.getKey();
            }
        }
        return null; // Nessuna chiave trovata per il valore specificato
    }

    private boolean quit(){
        if(p11.x() == 0 || p22.x()==9 || p11.y()==0 || p22.y()==9){
           
            return false;
        }
        return true;
    }
}
