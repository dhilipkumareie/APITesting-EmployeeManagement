package com.api.model;

public class EmployeeDTO
{
    private EmployeeDetailsDTO employee;

    private String employeeid;

    public EmployeeDetailsDTO getEmployee ()
    {
        return employee;
    }

    public void setEmployee (EmployeeDetailsDTO employee)
    {
        this.employee = employee;
    }

    public String getEmployeeid ()
    {
        return employeeid;
    }

    public void setEmployeeid (String employeeid)
    {
        this.employeeid = employeeid;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [employee = "+employee+", employeeid = "+employeeid+"]";
    }
}