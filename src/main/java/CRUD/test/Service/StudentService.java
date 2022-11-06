package CRUD.test.Service;

import CRUD.test.Entities.Student;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    public void changeValueOnVariable(Student student, boolean working){
        student.setWorking(working);
    }
}
