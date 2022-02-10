package telran.io.service;

import java.io.IOException;

import telran.view.InputOutput;

public interface DirectiryFilesCopy {
	/*
	 * 
	 * @param directoryPath
	 * @param maxDepth(if maxDepth <0, no depth limitation
	 * example for max depth 4
	 * Dir.name
	 * 		name1 dir
	 * 		  name11 file
	 * 	   	  name12 dir
	 * 			name121 file
	 * 		  name13 file
	 *        name14 dir
	 *          name141 dir
	 *           name1411 file
	 *           name 1412 dir
	 * 		name15 file
	 *	name2 file
*/
void displayDirectoryContent(String directoryPath,int maxDepth, InputOutput io);
/**
 * copies file from pathFileSrc to pathFileDest
 * @param pathFileSrc
 * @param pathPathDestination
 * @param flOwerride if true an existing destination will be overwritten, otherwise operation will be denied
 * @return byte rate - numbers of bytes on one millisecond
 * @throws IOException 
 */
long copyFiles(String pathFileSrc, String pathPathDestination, boolean flOwerride) throws IOException;

}
