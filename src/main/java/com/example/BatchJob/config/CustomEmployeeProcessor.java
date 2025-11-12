package com.example.BatchJob.config;

import com.example.BatchJob.model.Employee;
import org.springframework.batch.item.ItemProcessor;

public class CustomEmployeeProcessor implements ItemProcessor<Employee,Employee> {
    @Override
    public Employee process(Employee employee) throws Exception {
        // write transform logic here
        // calculate hra here
        Integer hraPer = employee.getHraPer();
        Double hra = employee.getSalary()-hraPer*100; // 12000-12%12000
        employee.setHra(hra);
        return employee;
    }
}
