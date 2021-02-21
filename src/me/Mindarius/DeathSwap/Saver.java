package me.Mindarius.DeathSwap;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Saver {
	public static void save(String path) {
		File target = new File(path+"\\rules.txt");
		try {
			target.delete();
			target.createNewFile();
		} catch(Exception e) { System.err.println("Unable to properly create DeathSwap settings file. Non-critical, but your configuration may not be saved."); }
		FileWriter writer;
		try {
			writer = new FileWriter(target);
			writer.write(Main.doubleFirstInterval+" ");
			writer.write(Main.anyStart+" ");
			writer.write(Main.startFreeze+" ");
			writer.write(Main.radius+" ");
			writer.write(Main.intervalSeconds+" ");
			writer.close();
		} catch (Exception e) { System.err.println("DeathSwap settings may not have saved correctly."); } //Catch-alls like this are bad practice, but it will be easier on the eyes of a user who won't be able to change the code.
	}
	public static void load(String path) {
		File target = new File(path+"\\rules.txt");
		if(!target.exists()) { return; }
		try {
			Scanner scan = new Scanner(target);
			Main.doubleFirstInterval = scan.nextBoolean();
			Main.anyStart = scan.nextBoolean();
			Main.startFreeze = scan.nextBoolean();
			Main.radius = scan.nextInt();
			Main.intervalSeconds = scan.nextInt();
			scan.close();
		} catch (Exception e) { System.err.println("DeathSwap settings may not have loaded correctly."); }
	}
}
