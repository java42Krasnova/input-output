package telran.io;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.time.Instant;
import java.util.Arrays;
class FileTest {
	File nodeFile = new File("file.txt");
	File nodeDir = new File("dir1/dir2");

	@BeforeEach
	void setUp() throws Exception {
		nodeFile.delete();
		nodeDir.delete();
	}

	@Test
	void test() throws IOException {

assertFalse(nodeFile.exists());
assertFalse(nodeDir.exists());
nodeFile.createNewFile();
nodeDir.mkdirs();
assertTrue(nodeFile.exists());
assertTrue(nodeDir.exists());
assertTrue(nodeFile.isFile());
assertTrue(nodeDir.isDirectory());
File nodeFile1 = new File("dir1/file1.txt");
nodeFile1.createNewFile();
//File nodeDir1 = new File("dir1");
File nodeDir2 = new File("dir2/dir2");
nodeDir2.mkdirs();
File nodeDir1 = new File("dir1/dir2");
nodeDir1.mkdirs();

//System.out.println(nodeDir1.getName());
System.out.println(nodeDir2.getName());

Arrays.stream(nodeDir2.listFiles()).forEach(n -> System.out.printf("  %s: %s\n", n.getName(), n.isDirectory()? "dir" : "file"));
}
 
	
	@Test
	void copyTest() throws IOException{
		InputStream is = new FileInputStream("srcFile.txt");
		File destFile = new File("destFile1.txt");
		//destFile.createNewFile();
		System.out.printf("file %s exists : %s\n", destFile.getName(), destFile.exists());
		OutputStream os = new FileOutputStream(destFile);
		byte[] buffer = new byte[is.available()];
		//works only for small files
		System.out.printf("read from input stream returns: %d\n", is.read(buffer));
		os.write(buffer);
		is.close();
		os.close();
		re();
	}
	
	public long re() throws IOException {
		File target = new File("/Users/jt/Downloads/Lection 38.mp4");
	
		InputStream is = new FileInputStream(target);
		File destFile = new File("/Users/jt/Desktop/Lection 38.mp4");
		if(destFile.exists()) {
			destFile.setWritable(true);
		}
		//Instant start = Instant.now();
		OutputStream os = new FileOutputStream(destFile);
		byte buffer[] = new byte[1024*1024*16];
		int nByts= 0;
		long resByts = 0;
		while((nByts = is.read(buffer))>0) {
			os.write(buffer, 0, nByts);
			resByts += nByts;
		}
		is.close();
		os.close();
		return resByts;
		
	}
}
