package com.example.BatchJob.model;

public class Employee {
    private Integer empId;
    private String name;
    private String address;
    private Double salary;
    private Integer hraPer;
    private Double hra;

    public Integer getHraPer() {
        return hraPer;
    }

    public void setHraPer(Integer hraPer) {
        this.hraPer = hraPer;
    }

    // Getters and Setters added
    public Integer getEmpId() { return empId; }
    public void setEmpId(Integer empId) { this.empId = empId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Double getSalary() { return salary; }
    public void setSalary(Double salary) { this.salary = salary; }
    public Double getHra() {
        return hra;
    }
    public void setHra(Double hra) {
        this.hra = hra;
    }
}
