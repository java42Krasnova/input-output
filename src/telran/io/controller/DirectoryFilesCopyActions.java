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
		// V.R. This check is redundant. The method is called only once, so...
		if(items == null) {
			items = new ArrayList<>();
			items.add(Item.of("Print directory Tree", DirectoryFilesCopyActions::printDirectoryTree));
			items.add(Item.of("Copy file and copy speed info", DirectoryFilesCopyActions::copyFile));
			items.add(Item.exit());
		}
		return items;
	}
	private static void printDirectoryTree(InputOutput io) {
		// V.R. The maxDepth cannot be less than 1. It also cannot be to big 
		int maxDepth = io.readInt("Enter depth");
		// V.R. The path has to exist. It is necessary to check existing
		String path = io.readString("Enter path to directory");
		dfc.displayDirectoryContent(path, maxDepth, io);
	}
	private static void copyFile(InputOutput io) {
		try {
			Set<String> flags = new HashSet<>(Arrays.asList("yes","no"));
			// V.R. "Yes" has to be tested deeper
			boolean isOverwritten = io.readStringOption("overwritten file? "+ flags, flags).equals("yes")? true: false;
			// V.R. from address exists?
			String fromAdres = io.readString("Write adres and file name for target file ");
			// V.R. If isOverwritten==true, then toAdres has to exist
			String toAdres = io.readString( "Write adres and file name for destination file");
			io.writeObjectLine("The speed of coppiing is "+ dfc.copyFiles(fromAdres,toAdres, isOverwritten) +" bites/millisecond");
		} catch (IOException e) {
			io.writeObjectLine(e.getMessage());
		}
	}
}
