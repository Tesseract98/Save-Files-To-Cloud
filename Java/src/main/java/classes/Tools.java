package classes;

import java.util.Random;
import java.util.Set;

public class Tools {

    private Tools(){}

    public static Integer getUniqueIdx(Set<Integer> uniqueSet){
        Random random = new Random();
        int varUnique;
        int i = 0;
        do{
            varUnique = random.nextInt(100);
            i++;
        } while (uniqueSet.contains(varUnique) && i < 100);
        uniqueSet.add(varUnique);
        return varUnique;
    }

}