package CRUD.test;

import CRUD.test.Controllers.BasicController;
import CRUD.test.Entities.Student;
import CRUD.test.Repository.StudentRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "test")
class StudentControllerTest {

    @Autowired
    private BasicController basicController;

    @Autowired
    private StudentRepository studentRepository;

    private ObjectMapper objectMapper;


    @Autowired
    private MockMvc mockMvc;

    private Student createNewStudent() throws Exception {
        Student student = new Student();
        student.setName("Emanuele");
        student.setSurname("La Duca");
        student.setWorking(true);
        return student;
    }

    private String createAStudentAsAJSON() throws Exception {
        Student student = createNewStudent();
        String studentJSON = objectMapper.writeValueAsString(student);
        return studentJSON;
    }

    private String createAStudentWithMockMvc() throws Exception {
        String student = createAStudentAsAJSON();
        String studentJSON = objectMapper.writeValueAsString(student);
        MvcResult result = mockMvc.perform(post("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentJSON)).andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        return studentJSON;
    }

    @Test
    void contextLoad(){
        assertThat(basicController).isNotNull();
    }

    @Test
    void createTest() throws Exception {
        String studentJSON = createAStudentAsAJSON();
        MvcResult result = mockMvc.perform(post("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentJSON)).andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Student studentresult = objectMapper.readValue(result.getResponse().getContentAsString(),Student.class);
        assertThat(studentresult).isNotNull();
    }

    @Test
    void GetAListTest() throws Exception {
        createNewStudent();
        MvcResult result = mockMvc.perform(get("/student"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        List<Student> studentListResult = objectMapper.readValue(result.getResponse().getContentAsString(),List.class);
        assertThat(studentListResult.size()).isNotZero();
    }

    @Test
    void GetASingleTest() throws Exception {
        createAStudentWithMockMvc();
        MvcResult result = mockMvc.perform(get("/student{"+createNewStudent().getId()+"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        Student studentResult = objectMapper.readValue(result.getResponse().getContentAsString(),Student.class);
        assertThat(studentResult.getId()).isEqualTo(createNewStudent().getId());
    }

    @Test
    void updateTest() throws Exception {
        String studentJSON = createAStudentWithMockMvc();
        MvcResult result = mockMvc.perform(put("/student{"+createNewStudent().getId()+"}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentJSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        Student studentResult = objectMapper.readValue(result.getResponse().getContentAsString(),Student.class);
        assertThat(studentResult.getId()).isEqualTo(createNewStudent().getId());
    }

    @Test
    void deleteTest() throws Exception {
        String studentJSON = createAStudentWithMockMvc();
        MvcResult result = mockMvc.perform(delete("/student{"+createNewStudent().getId()+"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        Student studentResult = objectMapper.readValue(result.getResponse().getContentAsString(), Student.class);
        assertThat(studentResult).isNull();
    }
}