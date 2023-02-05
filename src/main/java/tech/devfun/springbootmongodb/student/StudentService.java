package tech.devfun.springbootmongodb.student;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class StudentService {

    private final StudentRepository studentRepository;
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public void addStudent(Student student) {
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new StudentNotFoundException("Student with email {} not found", student.getEmail());
        }
        studentRepository.save(student);
    }

    public Student getStudentByEmail(String email) {
        return studentRepository.findStudentByEmail(email)
                .orElseThrow(() -> new StudentNotFoundException("Student with email {} not found", email));
    }

    public void updateStudent(Student student) {
        studentRepository.findById(student.getId())
                .map(s -> {
                    s.setFirstName(student.getFirstName());
                    s.setLastName(student.getLastName());
                    s.setEmail(student.getEmail());
                    s.setGender(student.getGender());
                    s.setTotalSpentInBooks(student.getTotalSpentInBooks());
                    return studentRepository.save(s);
                }).orElseThrow(() -> new StudentNotFoundException("Student with Id {} not found", student.getId()));
    }

    public void deleteStudent(String studentId) {
        studentRepository.deleteById(studentId);
    }
}
