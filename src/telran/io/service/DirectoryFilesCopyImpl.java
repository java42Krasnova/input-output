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
	private static final int BOOFER_SIZE = 16*1024*1024;

	InputOutput io;
	// V.R. maxDepth is real data member. As io.
	// int maxDepth;
	@Override
	public void displayDirectoryContent(String directoryPath, int maxDepth, InputOutput io) {
		// V.R. This check has to be done earlier
		if (maxDepth <= 0) {
			maxDepth = Integer.MAX_VALUE;
		}
		this.io = io;
		// V.R. this.maxDepth = maxDepth;
		
		File currentDirrectory = new File(directoryPath);
		// V.R. This check has to be done earlier
		if (!currentDirrectory.exists()) {
			io.writeObjectLine("Current dirrectory did't exists");
			return;
		}
		// V.R. This check has to be done earlier
		if (!currentDirrectory.isDirectory()) {
			io.writeObjectLine("This is no directory");
			return;
		}

		printDir(currentDirrectory, 1, maxDepth);

	}

	private void printDir(File currentDirrectory, int level, int maxDepth) {
		// V.R. The variable spaces is redundant, may be skipped
		int spases = level ;
		boolean flIsDir = currentDirrectory.isDirectory();
		if (!flIsDir) {
			/* V.R. May be 
			 * " ".repeat(level);
			 *  is simplier 
			 */
			for (int i = 0; i < spases; i++) {
				io.writeObject(" ");
			}
		}
		io.writeObjectLine(String.format(" %s, %s", currentDirrectory.getName(), flIsDir ? " dir" : "file"));
		if (flIsDir && level <= maxDepth) {
			io.writeObjectLine("___________________");
			for (File f : currentDirrectory.listFiles()) {
				printDir(f, level+1, maxDepth);
			}
		}
	}

	@Override
	public long copyFiles(String pathFileSrc, String pathFileDestination, boolean flOwerride) throws IOException {
		File target = new File(pathFileSrc);
		// V.R. The checking has to be executed in DirectoryFilesCopyActions
		if(!target.exists()) {
			throw new IOException("current file did't exists");
		}
		InputStream is = new FileInputStream(target);
		File destFile = new File(pathFileDestination);
		if(destFile.exists() ) {
			destFile.setWritable(flOwerride);
		} 
		if(!flOwerride) {
			throw new IOException("current file can't be overritten");
		}
		Instant start = Instant.now();
		OutputStream os = new FileOutputStream(destFile);
		byte buffer[] = new byte[BOOFER_SIZE];
		int nByts= 0;
		long resByts = 0;
		while((nByts = is.read(buffer))>0) {
			os.write(buffer, 0, nByts);
			resByts += nByts;
		}
		is.close();
		os.close();
		
		return resByts/ChronoUnit.MILLIS.between(start, Instant.now());
	}

}
