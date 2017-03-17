package daywork.day14;

import java.util.ArrayList;
import java.util.List;

/**
 * desc:
 * Created by jiarh on 17/3/10 15:34.
 */

public class StudentManager  {

    List<Student > students = new ArrayList<>();

    public void add(Student student){
        students.add(student);
    }

    public void notiyStop(){

       for (Student student : students)
           student.Stopwork();
    }

    public void work(){
        for (Student student:students)
            student.work();
    }


}
