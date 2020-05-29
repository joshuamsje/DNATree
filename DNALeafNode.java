package dnaTree;

/**
 * Either an empty node or contains DNA sequence
 * 
 * @author Kurt Karpin (kkarp9)
 * @author Joshua Mathew (josh827)
 *
 * @version 2020.3.29
 */
public class DNALeafNode extends DNANode {

   
    private String seq;
    /**
     * DNALeafNode constructor
     * @param level the level to be made at
     * @param element the sequence to store
     */
    public DNALeafNode(int level, String element)
    {
        setLevel(level);
        seq = element;
    }
    
    /**
     * getter method for the sequence
     * @return String with the node's sequence
     */
    public String getSequence()
    {
        return seq;
    }
    
   
}
