package telran.io.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import telran.io.service.DirectiryFilesCopy;
import telran.view.InputOutput;
import telran.view.Item;
public class DirectoryFilesCopyActions {
private static DirectiryFilesCopy dfc;
private static ArrayList<Item> items;
private DirectoryFilesCopyActions() {
	
}
	public static  ArrayList<Item> getItems(DirectiryFilesCopy dfc){
		DirectoryFilesCopyActions.dfc = dfc;
		if(items == null) {
			items = new ArrayList<>();
			items.add(Item.of("Print directory Tree", DirectoryFilesCopyActions::printDirectoryTree));
			items.add(Item.of("Copy file and copy speed info", DirectoryFilesCopyActions::copyFile));
			items.add(Item.exit());
		}
		return items;
	}
	private static void printDirectoryTree(InputOutput io) {
		int maxDepth = io.readInt("Enter depth");
		String path = io.readString("Enter path to directory");
		dfc.displayDirectoryContent(path, maxDepth, io);
	}
	private static void copyFile(InputOutput io) {
		try {
			Set<String> flags = new HashSet<>(Arrays.asList("yes","no"));
			boolean isOverwritten = io.readStringOption("overwritten file? "+ flags, flags).equals("yes")? true: false;
			String fromAdres = io.readString("Write adres and file name for target file ");
			String toAdres = io.readString( "Write adres and file name for destination file");
			io.writeObjectLine("The speed of coppiing is "+ dfc.copyFiles(fromAdres,toAdres, isOverwritten) +" bites/millisecond");
		} catch (IOException e) {
			io.writeObjectLine(e.getMessage());
		}
	}
}
