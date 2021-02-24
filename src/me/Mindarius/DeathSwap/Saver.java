package me.Mindarius.DeathSwap;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Saver {
	public static void save(String path) {
		File target = new File(path+"\\rules.txt"); //Establishes filepath
		try {
			target.delete();
			target.createNewFile(); //Clears the file
		} catch(Exception e) { System.err.println("Unable to properly create DeathSwap settings file. Non-critical, but your configuration may not be saved."); }
		FileWriter writer; 
		try {
			writer = new FileWriter(target); //Establishes writer, following lines add game settings to file
			writer.write(Main.doubleFirstInterval+" ");
			writer.write(Main.anyStart+" ");
			writer.write(Main.startFreeze+" ");
			writer.write(Main.radius+" ");
			writer.write(Main.intervalSeconds+" ");
			writer.write(Main.killInterval+" ");
			writer.close(); //cleaning up, less resource draw this way.
		} catch (Exception e) { System.err.println("DeathSwap settings may not have saved correctly."); } //Catch-alls like this are bad practice, but it will be easier on the eyes of a user who won't be able to change the code.
	}
	public static void load(String path) {
		File target = new File(path+"\\rules.txt"); //Establishes filepath
		if(!target.exists()) { return; } //Don't try loading a non-existant file
		try {
			Scanner scan = new Scanner(target); //Establishes scanner. Following lines load in game settings
			Main.doubleFirstInterval = scan.nextBoolean();
			Main.anyStart = scan.nextBoolean();
			Main.startFreeze = scan.nextBoolean();
			Main.radius = scan.nextInt();
			Main.intervalSeconds = scan.nextInt();
			Main.killInterval = scan.nextInt();
			scan.close(); //cleaning up, less resource draw this way.
		} catch (Exception e) { System.err.println("DeathSwap settings may not have loaded correctly. This can be caused by updating the plugin."); } //Catch-alls like this are bad practice, but it will be easier on the eyes of a user who won't be able to change the code.
	}
}
