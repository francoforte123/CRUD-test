package CRUD.test;

import CRUD.test.Entities.Student;
import CRUD.test.Repository.StudentRepository;
import CRUD.test.Service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "test")
class StudentServiceTest {

    @Autowired
    private StudentRepository studentRepository;


    @Autowired
    private StudentService studentService;

    @Test
    void contextLoad(){
        assertThat(studentService).isNotNull();
    }

    @Test
    void checkStudentStatus(){

        Student student = new Student();
        student.setName("Emanuele");
        student.setSurname("La Duca");
        student.setWorking(true);

        Student studentFromDB = studentRepository.save(student);
        studentService.changeValueOnVariable(student,false);

        assertThat(studentFromDB.isWorking()).isEqualTo(false);
    }

}