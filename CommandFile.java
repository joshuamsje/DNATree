package dnaTree;


import java.io.File;
import java.util.Scanner;

/**
 * Reads the given command file and calls the command
 * functions from the file to DNATree
 * 
 * @author Kurt Karpin (kkarp9)
 * @author Joshua Matthew (josh827)
 *
 * @version 2020.3.29
 */
public class CommandFile {

    private SequenceTree seqTree;


    /**
     * Constructor for CommandFile that contains the file
     * to read.
     * 
     * @param file
     *            command file to read
     */
    public CommandFile(String file) {
        seqTree = new SequenceTree();
        inputReader(file);
    }


    /**
     * Checks if a letter is one of the subtree
     * DNA letters or the terminator indicator
     * 
     * @param sequence
     * @return boolean result
     */
    private boolean letterChecker(String sequence) {
        if (sequence.isEmpty())
        {
            return false;
        }
        for (int i = 0; i < sequence.length(); i++) {
            if (sequence.charAt(i) != 'A' && sequence.charAt(i) != 'C'
                && sequence.charAt(i) != 'G' && sequence.charAt(i) != 'T') {
                return false;
            }
        }
        return true;
    }


    /**
     * Input Reader method which parses
     * the input file and does the appropriate
     * methods needed 
     * @param file input file
     */
    public void inputReader(String file) {
        try {
            Scanner scan = new Scanner(new File(file));
            while (scan.hasNextLine()) {
                
                
                String cmd = scan.nextLine();
                cmd = cmd.trim();
                if (cmd.length() != 0)
                {
                    //System.out.println(cmd);
                    //System.out.print(cmd.length() + " ");
                    String[] array = cmd.split("\\s+");
                    switch (array[0]) {
                    //switch (cmd) {
                        case "insert":
                            //String sequence = scan.next();
                            String sequence = array[1];
                            if (!letterChecker(sequence)) {
                                System.out.println("sequence " + sequence
                                    + " is invalid");
                            }
                            else {
                                int result = seqTree.insert(sequence);
                                if (result == -1) {
                                    System.out.println("sequence " + sequence
                                        + " already exists");
                                }
                                else {
                                    System.out.println("sequence " + sequence
                                        + " inserted at level " + result);
                                }
                            }
                            break;
                        case "remove":
                            String remSequence = array[1];
                            if (letterChecker(remSequence))
                            {
                                if (seqTree.remove(remSequence))
                                {
                                    System.out.println("sequence " + 
                                        remSequence + " removed");
                                }
                                else
                                {
                                    System.out.println("sequence " + 
                                        remSequence + 
                                        " does not exist");
                                }
                            }
                            else
                            {
                                System.out.println("sequence " + remSequence + 
                                    " is invalid");
                            }
                            break;
                            
                        case "print":
                            // print
                            boolean inBounds = (1 >= 0) && (1 < array.length);
                            if (!inBounds) {
                                // print tree
                                System.out.print(seqTree.printTree("NoCmd"));
                            }
                            else if (array[1].equals("lengths") 
                                || array[1].equals("stats") ) {
                                String printCmd = array[1];
                                
                                System.out.print(seqTree.printTree(printCmd));
                            }
                            
                            break;
                        case "search":
                            String search = array[1];
                            boolean check = false;
                            if (search.charAt(search.length() - 1) == '$')
                            {
                                check = letterChecker(search.substring(0, 
                                    search.length() - 1));
                            }
                            if (letterChecker(search) || check) {
                                System.out.print(seqTree.search(search));
                            }
                            else
                            {
                                System.out.println("sequence " + search
                                    + " is invalid");
                            }
                            break;
                        default:// Found an unrecognized command
                            break;    
                    }
                }
               

            }
            scan.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
