package com.myhash.object;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

public class Database {
	
	public static void makeBookcase() {
		File bookcaseFile = new File("bookcase.txt");
		
		try {
			FileWriter writer = new FileWriter(bookcaseFile);
			writer.write("");
			writer.close();
		}catch(IOException e) {
        	JOptionPane.showMessageDialog(null, "Unable to create file", "CREATE ERROR", JOptionPane.ERROR_MESSAGE);
		}
		
	}

	public static void makeFile(String filename){
		File problemFile = new File(filename + "Problem.txt");
        File tagFile = new File(filename + "Tag.txt");
        try {
            FileWriter fw1 = new FileWriter(problemFile);
            FileWriter fw2 = new FileWriter(tagFile);

            fw1.write("");
            fw1.close();

            fw2.write("");
            fw2.close();
        }catch(IOException e) {
        	JOptionPane.showMessageDialog(null, "Unable to create file", "CREATE ERROR", JOptionPane.ERROR_MESSAGE);
        }
	}
	
	public static void removeFile(String filename) {
		Path problemPath = Paths.get(filename + "Problem.txt");
		Path tagPath = Paths.get(filename + "Tag.txt");
        try {
            Files.delete(problemPath);
            Files.delete(tagPath);
            
        } catch (NoSuchFileException e) {
        	JOptionPane.showMessageDialog(null, "The file has already been deleted or could not be found.", "DELETE ERROR", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
        	JOptionPane.showMessageDialog(null, "The file has already been deleted or could not be found.", "DELETE ERROR", JOptionPane.ERROR_MESSAGE);
        }
	}

	public static ArrayList<Workbook> loadWorkbookList(String filepath) {
		ArrayList<Workbook> loadWorkbookList = new ArrayList<Workbook>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filepath));
			String line;
			while((line = reader.readLine()) != null) {
				Workbook newWorkbook = new Workbook(line);
				loadWorkbookList.add(newWorkbook);
			}
		}catch(IOException e) {
			JOptionPane.showMessageDialog(null, "The Workbook.txt file could not be found. Create a new one.", "LOAD ERROR", JOptionPane.ERROR_MESSAGE);
			makeBookcase();
		}
		return loadWorkbookList;
	}
	
	public static ArrayList<Problem> loadProblemList(String filePath) {
		ArrayList<Problem> loadProblems = new ArrayList<Problem>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String title = parts[0].replace("\"", "");
                String fileloc = parts[1].replace("\"", "");;
                String solve = parts[2].replace("\"", "");;
                String memo = parts[3].replace("\"", "");;
                String tag = parts[4].replace("\"", "");;
                String history = parts[5].replace("\"", "");;
                ArrayList<History> htemp = Problem.stringtoHistory(history);
                Set<String> ttemp = Problem.stringtoTag(tag);
                Problem problem = new Problem(title, fileloc, solve, memo, ttemp, htemp);
                
                loadProblems.add(problem);
            }
            reader.close();
        } catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Could not find the file to load", "LOAD ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return loadProblems;
    }
    
    public static Map<String, Tag> loadTagList(String filepath) {
        Map<String, Tag> loadTagList = new HashMap<String, Tag>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filepath));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                String name = parts[0];
                int num = Integer.valueOf(parts[1]);
                Tag tag = new Tag(name, num);
                loadTagList.put(tag.getName(), tag);
            }
            reader.close();
        } catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Could not find the file to load", "LOAD ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return loadTagList;
    }
    
    public static void saveWorkbookList(ArrayList<Workbook> workbookList) {
   		try {
   	       File file = new File("bookcase.txt");
           FileWriter writer = new FileWriter(file);

      		for(Workbook workbook : workbookList) {
      			writer.write(workbook.getName()+"\n");
      		}
			writer.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Could not find the file to save", "SAVE ERROR", JOptionPane.ERROR_MESSAGE);

		}
    }

    public static void saveTagList(Map<String, Tag> tagList, String filename) {
    	
    	try {
    		File file = new File(filename+"Tag.txt");
    		FileWriter writer = new FileWriter(file);
    		
    		for(String tagName : tagList.keySet()) {
    			writer.write(tagList.get(tagName).toString() + "\n");
    		}
    		writer.close();
    	}catch(IOException e) {
			JOptionPane.showMessageDialog(null, "Could not find the file to save", "SAVE ERROR", JOptionPane.ERROR_MESSAGE);
    	}
    }
    
    public static void saveProblemList(ArrayList<Problem> problemList, String filename) {
        try {
        	File file = new File(filename+"Problem.txt");
        	FileWriter writer = new FileWriter(file);
        	
        	for(Problem problem : problemList) {
        		writer.write(problem.toString() + "\n");
        	}
        	writer.close();
        } catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Could not find the file to save", "SAVE ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
}
