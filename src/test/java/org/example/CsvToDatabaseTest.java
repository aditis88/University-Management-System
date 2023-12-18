package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CsvToDatabaseTest {
        CsvToDatabase ctod = new CsvToDatabase();
    @Test
    void removeSpaces() {
    }

    @Test
    void fillUserTable() {
        ctod.fillInstructorTable();
        ctod.fillDepartmentTable();
        ctod.fillUserTable();
    }

    @Test
    void fillDepartmentTable() {
    }

    @Test
    void fillInstructorTable() {
    }

    @Test
    void fillCourseCatalogueTable() {
        ctod.fillCourseCatalogueTable();
    }

    @Test
    void fillCourseTable() {
        ctod.fillCourseTable();
    }

    @Test
    void fillPrereqTable() {
        ctod.fillPrereqTable();
    }

    @Test
    void fillStudentTable() {
        ctod.fillStudentTable();
    }

    @Test
    void main() {
    }
}