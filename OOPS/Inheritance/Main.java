package Inheritance;

class Employee{
	private String name;
	private double salary;
	
	Employee(String n,double s){
		name = n;
		salary = s;
	}

    public String getName(){
        return name;
    }
    public void setName(String n){
        name = n;
    }

    public double getSalary(){
        return salary;
    }
    public void setSalary(double s){
        salary = s;
    }

    public void raise(double perc){
        salary += salary * perc/100;
    }

}

class Manager extends Employee {
    double bonus;

    Manager(String n,double s,double b){
        super(n, s);
        bonus = b;
    }


}

public class Main {
    public static void main(String[] args) {
        Employee Emp = new Employee("Leo Das", 50000.00);
        Manager Mg = new Manager("Jeevan", 60000.00, 5000);

        Emp.raise(10);

        System.out.println(Emp.getSalary());

        System.out.println(Mg.getSalary());
    }
}
