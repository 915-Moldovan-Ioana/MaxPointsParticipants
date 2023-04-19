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

import static org.junit.Assert.*;

public class TestService {
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
    }
    @Test
    public void testAddValidStudent() {
        assertEquals(0, service.saveStudent(String.valueOf(13),"Gloria",935));
    }

    @Test
    public void testAddStudentWithNegativeID() {
        assertEquals(1, service.saveStudent(String.valueOf(-1),"Gloria",935));
    }

    @Test
    public void testAddStudentWithExistingID() {
        assertEquals(0, service.saveStudent(String.valueOf(20),"Gloria",935));
        assertEquals(1, service.saveStudent(String.valueOf(20),"Gigi",935));
    }

    @Test
    public void testAddStudentWithEmptyID() {
        assertEquals(1, service.saveStudent("","Gloria",935));
    }

    @Test
    public void testAddStudentWithNullID() {
        assertEquals(1, service.saveStudent(null,"Gloria",935));
    }

    @Test
    public void testAddStudentWithEmptyName() {
        assertEquals(1, service.saveStudent(String.valueOf(13),"",935));
    }

    @Test
    public void testAddStudentWithInvalidName() {
        assertEquals(1, service.saveStudent(String.valueOf(13),null,935));
    }

    @Test
    public void testAddStudentWithGroupToSmall() {
        assertEquals(1, service.saveStudent(String.valueOf(13),"Gloria",110));
    }

    @Test
    public void testAddStudentWithGroupToBig() {
        assertEquals(1, service.saveStudent(String.valueOf(13),"Gloria",938));
    }

    @Test
    public void testAddValidAssignment() {
        assertEquals(0, service.saveTema(String.valueOf(31),"Tema 1", 8, 7));
    }

    @Test
    public void testAddInvalidAssignment() {
        assertEquals(1, service.saveTema(String.valueOf(""),"Tema 2", 9, 8));
    }

    @Test
    public void testAddAssignment() {
        assertEquals(0, service.saveTema(String.valueOf(33),"Tema 3", 10, 8));
    }

    @Test
    public void testAddInvalidAssignmentNullName() {
        assertEquals(1, service.saveTema(String.valueOf(40),null, 9, 8));
    }

    @After
    public void cleanUp() {
        service.deleteStudent("13");
        service.deleteStudent("20");
        service.deleteTema("31");
        service.deleteTema("13");
        service.deleteTema("33");
    }

}
