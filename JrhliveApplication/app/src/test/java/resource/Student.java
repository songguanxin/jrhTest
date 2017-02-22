package resource;

import java.util.List;

/**
 * desc:
 * Created by jiarh on 16/12/13 16:17.
 */

public class Student {
    String name;
    int type;
    List<Subject> subjects;

    public Student(String name, List<Subject> subjects) {
        this.name = name;
        this.subjects = subjects;
    }

    public String getName() {
        return name;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
