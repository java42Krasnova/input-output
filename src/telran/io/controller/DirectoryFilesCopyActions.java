package telran.io.controller;

import java.io.File;
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

	public static ArrayList<Item> getItems(DirectiryFilesCopy dfc) {
		DirectoryFilesCopyActions.dfc = dfc;
		
			items = new ArrayList<>();
			items.add(Item.of("Print directory Tree", DirectoryFilesCopyActions::printDirectoryTree));
			items.add(Item.of("Copy file and copy speed info", DirectoryFilesCopyActions::copyFile));
			items.add(Item.exit());
		
		return items;
	}

	private static void printDirectoryTree(InputOutput io) {
		int maxDepth = io.readInt("Enter depth");
		if (maxDepth <= 0) {
			maxDepth = Integer.MAX_VALUE;
		}
		String path = io.readString("Enter path to directory");
		File currentDirrectory = new File(path);
		if (!currentDirrectory.exists()) {
			io.writeObjectLine("Current dirrectory did't exists");
			return;
		}
		if (!currentDirrectory.isDirectory()) {
			io.writeObjectLine("This is no directory");
			return;
		}
		dfc.displayDirectoryContent(path, maxDepth, io);
	}

	private static void copyFile(InputOutput io) {
		try {
			String pathFileSrc = io.readString("Write adres and file name for target file ");
			File target = new File(pathFileSrc);
			if (!target.exists()) {
				io.writeObjectLine("No such a file or directory");
				return;
			}
			boolean flOwerride = io.readString("overwritten file? " + "Y/N").matches("[Yy][\\w]*")
					? true
					: false;
			io.writeObjectLine(flOwerride);
			String pathFileDestination = io.readString("Write adres and file name for destination file");
			File destFile = new File(pathFileDestination);
			if (!flOwerride && destFile.exists()) {
					io.writeObjectLine("current file can't be overritten");
					return;
				}
			io.writeObjectLine("The speed of copying is "
					+ dfc.copyFiles(pathFileSrc, pathFileDestination, flOwerride) + " bites/millisecond");
		} 
		catch (IOException e) {
			io.writeObjectLine(e.getMessage());
		}
	}
}
