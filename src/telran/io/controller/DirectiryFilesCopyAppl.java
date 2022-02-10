package telran.io.controller;

import java.util.ArrayList;

import telran.employees.controller.EmployeeActions;
import telran.employees.services.EmployeesMethods;
import telran.employees.services.EmployeesMethodsMapsImpl;
import telran.io.service.DirectiryFilesCopy;
import telran.io.service.DirectoryFilesCopyImpl;
import telran.view.ConsoleInputOutput;
import telran.view.InputOutput;
import telran.view.Item;
import telran.view.Menu;

public class DirectiryFilesCopyAppl {

	public static void main(String[] args) {
		InputOutput io = new ConsoleInputOutput();
		DirectiryFilesCopy dfc = new DirectoryFilesCopyImpl();
		ArrayList<Item> items = DirectoryFilesCopyActions.getItems(dfc);
		
		Menu menu = new Menu("Work with files", items);
		
		menu.perform(io);
		

	}

}
