
import java.util.*;
import java.util.Objects;


public class Relation  {

      public  int state;
      public  char ch;
      public  int ID;

      public Relation(){}

      public   Relation(int state , char ch){
              this.state = state;
              this.ch = ch;
              this.ID = state * 119 + ch;
          };

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof Relation)) return false;

        Relation relation = (Relation) obj;
        return  ID == ((Relation) obj).ID;
    }

    @Override
    public int hashCode() {
        return ID;
    }
}
