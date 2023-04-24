import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ssvv.example.domain.Nota;
import ssvv.example.domain.Student;
import ssvv.example.domain.Tema;
import ssvv.example.repository.NotaXMLRepository;
import ssvv.example.repository.StudentXMLRepository;
import ssvv.example.repository.TemaXMLRepository;
import ssvv.example.service.Service;
import ssvv.example.validation.NotaValidator;
import ssvv.example.validation.StudentValidator;
import ssvv.example.validation.TemaValidator;
import ssvv.example.validation.Validator;

import static org.junit.Assert.assertEquals;

public class BigBangTest {
    Service service;
    @Before
    public void init(){
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Tema> temaValidator = new TemaValidator();
        Validator<Nota> notaValidator = new NotaValidator();

        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "studenti.xml");
        TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "teme.xml");
        NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "note.xml");
        service = new Service(fileRepository1, fileRepository2, fileRepository3);
        service.saveStudent(String.valueOf(5),"Gloria",935);
        service.saveTema(String.valueOf(5),"Tema 1", 8, 7);
    }
    @Test
    public void testAddValidStudent() {
        assertEquals(0, service.saveStudent(String.valueOf(13),"Gloria",935));
    }

    @Test
    public void testAddValidAssignment() {
        assertEquals(0, service.saveTema(String.valueOf(31),"Tema 1", 8, 7));
    }

    @Test
    public void testAddValidGrade() {
        assertEquals(0, service.saveNota(String.valueOf(5), String.valueOf(5), 2, 8, "Good"));
    }

    @Test
    public void testAll() {
        assertEquals(0, service.saveStudent(String.valueOf(6),"Gloria",935));
        assertEquals(0, service.saveTema(String.valueOf(6),"Tema 1", 8, 7));
        assertEquals(0, service.saveNota(String.valueOf(6), String.valueOf(6), 2, 8, "Good"));
    }

    @After
    public void cleanUp() {
        service.deleteStudent("13");
        service.deleteTema("31");
        service.deleteNota("5", "5");
        service.deleteStudent("5");
        service.deleteTema("5");
        service.deleteNota("6", "6");
        service.deleteStudent("6");
        service.deleteTema("6");
    }
}
