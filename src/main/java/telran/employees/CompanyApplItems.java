package telran.employees;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import terlan.view.InputOutput;
import terlan.view.Item;

//public void addEmployee(Employee empl) ;
//public Employee getEmployee(long id) ;
//public Employee removeEmployee(long id) ;
//public int getDepartmentBudget(String department) ;
//public String[] getDepartments() ;
//public Manager[] getManagersWithMostFactor() ;

public class CompanyApplItems {
	static Company company;
	static HashSet<String> departments;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Item> getCompanyItems(Company company, HashSet<String> departments) {
		CompanyApplItems.company = company;
		CompanyApplItems.departments = departments;
		Item[] items = { Item.of("add employee", CompanyApplItems::addEmployee),
				Item.of("display employee data", CompanyApplItems::getEmployee),
				Item.of("remove employee", CompanyApplItems::removeEmployee),
				Item.of("display department budget", CompanyApplItems::getDepartmentBudget),
				Item.of("display departments", CompanyApplItems::getDepartments),
				Item.of("display managers with most factor", CompanyApplItems::getManagersWithMostFactor), };
		return new ArrayList(List.of(items));

	}

	static void addEmployee(InputOutput io) {
		Employee empl = readEmployee(io);
		String type = io.readStringOptions("Enter employee type", "Wrong Employee Type",
				new HashSet<String>(List.of("WageEmployee", "Manager", "SalesPerson")));
		Employee result = switch (type) {
		case "WageEmployee" -> getWageEmployee(empl, io);
		case "Manager" -> getManager(empl, io);
		case "SalesPerson" -> getSalesPerson(empl, io);
		default -> null;
		};
		company.addEmployee(result);
		io.writeLine("Employee has been added");
	}

	private static Employee getSalesPerson(Employee empl, InputOutput io) {
		WageEmployee wageEmployee = (WageEmployee) getWageEmployee(empl, io);
		float percents = io.readNumberRange("Enter percents", "Wrong percents value", 0.5, 2).floatValue();
		long sales = io.readNumberRange("Enter sales", "Wrong sales value", 500, 50000).longValue();
		return new SalesPerson(empl.getId(), empl.getBasicSalary(), empl.getDepartment(), wageEmployee.getHours(),
				wageEmployee.getWage(), percents, sales);
	}

	private static Employee getManager(Employee empl, InputOutput io) {

		float factor = io.readNumberRange("Enter factor", "Wrong factor value", 1.5, 5).floatValue();
		return new Manager(empl.getId(), empl.getBasicSalary(), empl.getDepartment(), factor);
	}

	private static Employee getWageEmployee(Employee empl, InputOutput io) {

		int hours = io.readNumberRange("Enter working hours", "Wrong hours value", 10, 200).intValue();
		int wage = io.readNumberRange("Enter hour wage", "Wrong wage value", 100, 1000).intValue();
		;
		return new WageEmployee(empl.getId(), empl.getBasicSalary(), empl.getDepartment(), hours, wage);
	}

	private static Employee readEmployee(InputOutput io) {

		long id = io.readNumberRange("Enter id value", "Wrong id value", 1000, 10000).longValue();
		int basicSalary = io.readNumberRange("Enter basic salary", "Wrong basic salary", 2000, 20000).intValue();
		String department = io.readStringOptions("Enter department " + departments, "Wrong department", departments);
		return new Employee(id, basicSalary, department);
	}

	static void getEmployee(InputOutput io) {
		long id = io.readObject("Enter employee ID", "Invalid ID", Long::parseLong);
		Employee employee = company.getEmployee(id);
		if (employee != null) {
			io.writeLine("Employee" + employee.getId());
		} else {
			io.writeLine("No employee found with ID: " + id);
		}
	}

	static void removeEmployee(InputOutput io) {
		long id = io.readObject("Empll ID to remove", "Invalid ID", Long::parseLong);
		Employee removedEmployee = company.removeEmployee(id);
		if (removedEmployee != null) {
			io.writeLine("Empl with ID" + id + " has benn removed.");
		} else {
			io.writeLine("No employee found with ID: " + id);
		}
	}

	static void getDepartmentBudget(InputOutput io) {
		String department = io.readStringOptions("Enter department name", "Invalid department name",
				new HashSet<>(List.of(company.getDepartments())));
		int budget = company.getDepartmentBudget(department);
		io.writeLine("The budget for department " + department + " is: " + budget);
	}

	static void getDepartments(InputOutput io) {
		String[] departmentsList = company.getDepartments();

		if (departmentsList.length == 0) {
			io.writeLine("No departments available.");
			return;
		}
		io.writeLine("Departments:");
		for (String department : departmentsList) {
			io.writeLine(department);
		}
	}

	static void getManagersWithMostFactor(InputOutput io) {
        
	}
}