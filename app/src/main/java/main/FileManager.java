package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Provides methods for loading from and writing to file.
 */
public class FileManager {

	/**
	 * Saves Serializable object to file.
	 *
	 * @param data Object to be saved.
	 * @param file Destination file.
	 */
	public static void save(Serializable data, File file) throws FileNotFoundException, IOException {
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
		objectOutputStream.writeObject(data);
		objectOutputStream.close();
	}

	/**
	 * Loads Serializable object from file.
	 *
	 * @param file Source file.
	 * @return Loaded object.
	 */
	public static Object load(File file) throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
		Object object = objectInputStream.readObject();
		objectInputStream.close();
		return object;
	}
}
