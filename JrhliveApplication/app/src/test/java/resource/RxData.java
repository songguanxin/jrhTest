/*
package resource;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

*/
/**
 * desc:
 * Created by jiarh on 16/12/13 16:16.
 *//*


public class RxData {
     List<Student> students = new ArrayList<>();
    String[] student = {"a","b","c","d"};
    String[] subject = {"语文","数学","英语","物理","化学"};
    public List<Student> getStudent(){
       final List<Subject> subjects = new ArrayList<>();
        Observable.from(subject)
                .map(new Func1<String, Subject>() {
                    @Override
                    public Subject call(String s) {
                        return new Subject(s);
                    }
                }).subscribe(new Action1<Subject>() {
            @Override
            public void call(Subject subject) {
                subjects.add(subject);
            }
        });

        Observable.from(student)
                .map(new Func1<String, Student>() {
                    @Override
                    public Student call(String s) {
                        return new Student(s,subjects);
                    }
                }).subscribe(new Action1<Student>() {
            @Override
            public void call(Student student) {
                student.setType(new Random().nextInt(2));
                students.add(student);
            }
        });

        return students;
    }

    @Test
    public void testScan(){
        Observable.just(1,2,3,4,4,3,2,2,4,5)
                .distinct()
                .flatMap(new Func1<Integer, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(Integer integer) {
                        System.out.print(integer);
                        return Observable.just(integer);
                    }
                })
                .scan(new Func2<Integer, Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer, Integer integer2) {
                        return integer+integer2;
                    }
                }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                System.out.println(integer);
            }
        });


    }




}
*/
