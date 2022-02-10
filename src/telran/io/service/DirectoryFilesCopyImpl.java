package telran.io.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import telran.view.InputOutput;

public class DirectoryFilesCopyImpl implements DirectiryFilesCopy {
	private static final int BOOFER_SIZE = 16 * 1024 * 1024;

	InputOutput io;
	int maxDepth;

	@Override
	public void displayDirectoryContent(String directoryPath, int maxDepth, InputOutput io) {
		this.io = io;
		this.maxDepth = maxDepth;
		File currentDirrectory = new File(directoryPath);
		printDir(currentDirrectory, 1);

	}

	private void printDir(File currentDirrectory, int level) {
		boolean flIsDir = currentDirrectory.isDirectory();
		if (!flIsDir) {
			io.writeObject(" ".repeat(level));
		}
		io.writeObjectLine(String.format(" %s, %s", currentDirrectory.getName(), flIsDir ? " dir" : "file"));
		if (flIsDir && level <= maxDepth) {
			io.writeObjectLine("___________________");
			for (File f : currentDirrectory.listFiles()) {
				printDir(f, level + 1);
			}
		}
	}

	@Override
	public long copyFiles(String pathFileSrc, String pathFileDest, boolean flOwerride) throws IOException {
		InputStream is = new FileInputStream(pathFileSrc);
		Instant start = Instant.now();
		OutputStream os = new FileOutputStream(pathFileDest);
		byte buffer[] = new byte[BOOFER_SIZE];
		int nByts = 0;
		long resByts = 0;
		while ((nByts = is.read(buffer)) > 0) {
			os.write(buffer, 0, nByts);
			resByts += nByts;
		}
		is.close();
		os.close();
		return resByts / ChronoUnit.MILLIS.between(start, Instant.now());
	}

}
