package org.skypro.hw.service;

import org.skypro.hw.entity.Employee;
import org.skypro.hw.exception.EmployeeAlreadyAddedException;
import org.skypro.hw.exception.EmployeeNotFoundException;
import org.skypro.hw.exception.EmployeeStorageIsFullException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmployeeService {

    private final Map<String, Employee> employeeByFullName = new HashMap<>();
    private final int MAX_SIZE = 2;

    public Employee add(String firstName, String lastName) {

        if (employeeByFullName.size() >= MAX_SIZE) {
            throw new EmployeeStorageIsFullException("Массив сотрудников переполнен");
        }

        Employee newEmployee = new Employee(firstName, lastName);

        String fullName = getFullName(firstName, lastName);

        if (employeeByFullName.containsKey(getFullName(firstName, lastName))) {
            throw new EmployeeAlreadyAddedException("Сотрудник " + newEmployee + " уже существует");
        }

        employeeByFullName.put(fullName, newEmployee);
        return newEmployee;
    }

    public Employee find(String firstName, String lastName) {
        String fullName = getFullName(firstName, lastName);
        checkExistence(fullName);

        return employeeByFullName.get(fullName);
    }

    public Employee remove(String firstName, String lastName) {
        String fullName = getFullName(firstName, lastName);
        checkExistence(fullName);

        return employeeByFullName.remove(fullName);
    }

    public Collection<Employee> getAll() {
        return employeeByFullName.values();
    }

    private String getFullName(String firstName, String lastName) {
        return firstName + lastName;
    }

    private void checkExistence(String fullName) {
        if (!employeeByFullName.containsKey(fullName)) {
            throw new EmployeeNotFoundException("Такого сотрудника нет");
        }
    }
}
