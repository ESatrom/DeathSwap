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
			writer.write(Main.isGRDoubleFirst()+" ");
			writer.write(Main.isGRAnyStart()+" ");
			writer.write(Main.isGRFreeze()+" ");
			writer.write(Main.getGRRadius()+" ");
			writer.write(Main.getGRInterval()+" ");
			writer.write(Main.getGRKillInterval()+" ");
			writer.close(); //cleaning up, less resource draw this way.
		} catch (Exception e) { System.err.println("DeathSwap settings may not have saved correctly."); } //Catch-alls like this are bad practice, but it will be easier on the eyes of a user who won't be able to change the code.
	}
	public static void load(String path) {
		File target = new File(path+"\\rules.txt"); //Establishes filepath
		if(!target.exists()) { return; } //Don't try loading a non-existant file
		try {
			Scanner scan = new Scanner(target); //Establishes scanner. Following lines load in game settings
			Main.setGRDoubleFirst(scan.nextBoolean());
			Main.setGRAnyStart(scan.nextBoolean());
			Main.setGRFreeze(scan.nextBoolean());
			Main.setGRRadius(scan.nextInt());
			Main.setGRInterval(scan.nextInt());
			Main.setGRKillInterval(scan.nextInt());
			scan.close(); //cleaning up, less resource draw this way.
		} catch (Exception e) { System.err.println("DeathSwap settings may not have loaded correctly. This can be caused by updating the plugin."); } //Catch-alls like this are bad practice, but it will be easier on the eyes of a user who won't be able to change the code.
	}
}
