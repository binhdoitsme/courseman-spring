package com.hanu.courseman.domain.services;

import com.hanu.courseman.domain.models.Enrolment;
import com.hanu.courseman.domain.models.Module;
import com.hanu.courseman.domain.models.Student;

public interface EnrolmentService {
    Enrolment createEnrolment(Student student, Module module);
    
}
