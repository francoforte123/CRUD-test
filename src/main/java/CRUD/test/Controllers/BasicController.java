package CRUD.test.Controllers;

import CRUD.test.Entities.Student;
import CRUD.test.Repository.StudentRepository;
import CRUD.test.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class BasicController {

    @Autowired
    StudentService studentService;

    @Autowired
    StudentRepository studentRepository;

    @PostMapping("")
    public @ResponseBody Student createNewStudent(@RequestParam Student student){
        return studentRepository.save(student);
    }

    @GetMapping("")
    public @ResponseBody List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    @GetMapping("{id}")
    public @ResponseBody Student findStudent(@PathVariable long id){
        Optional<Student> studentById = studentRepository.findById(id);

        if(studentById.isPresent()){
            return studentById.get();
        }else{
            return null;
        }
    }

    @PutMapping("{id}")
    public @ResponseBody Student updateStudentById(@PathVariable long id, @RequestBody Student student){
        student.setId(id);
        return studentRepository.saveAndFlush(student);
    }

    @PutMapping("/status/{id}")
    public @ResponseBody Student updateValueOfWorking(@PathVariable long id, @RequestBody boolean isWorking){
        Student studentById = studentRepository.getReferenceById(id);
        studentService.changeValueOnVariable(studentById, isWorking);
        return studentById;
    }

    @DeleteMapping("{id}")
    public @ResponseBody Student deletedStudentById(@PathVariable long id){
        studentRepository.deleteById(id);
        Student student = studentRepository.getReferenceById(id);
        return student;
    }

}
