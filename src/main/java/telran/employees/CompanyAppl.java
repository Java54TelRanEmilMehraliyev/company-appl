package telran.employees;

import telran.io.Persistable;
import terlan.view.Item;
import terlan.view.Menu;
import terlan.view.SystemInputOutput;

import java.util.*;
public class CompanyAppl {

	private static final String FILE_NAME = "employeesTest.data";

	public static void main(String[] args) {
		Company company = new CompanyMapsImpl();
		try {
			((Persistable)company).restore(FILE_NAME);
		} catch (Exception e) {
			
		}
		List<Item> companyItems =
				CompanyApplItems.getCompanyItems(company,
						new HashSet<String>(List.of("Audit", "Development", "QA")));
		companyItems.add(Item.of("Exit & save",
				io -> ((Persistable)company).save(FILE_NAME), true));
		companyItems.add(Item.ofExit());
		Menu menu = new Menu("Company CLI Application",
				companyItems.toArray(Item[]::new));
		menu.perform(new SystemInputOutput());

	}

}