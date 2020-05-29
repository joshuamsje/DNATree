package dnaTree;

/**
 * Organizes the structure of the tree.
 * Creates an Internal Node preset with 5 flyweight 
 * nodes.
 * 
 * @author Kurt Karpin (kkarp9)
 * @author Joshua Mathew (josh827)
 *
 * @version 2020.3.29
 */
public class DNAInternalNode extends DNANode {

    
    private DNANode charA;
    private DNANode charC;
    private DNANode charG;
    private DNANode charT;
    private DNANode terminator;
    
    /**
     * Constructor for the Internal Node object
     * 
     * @param level indicates level of the node
     * @param empty creates the 5 flyweight nodes
     */
    public DNAInternalNode(int level, DNAFlyweightNode empty)
    {
        
        setLevel(level);
        
        charA = empty;
        charC = empty;
        charG = empty;
        charT = empty;
        terminator = empty;
    }
    
   
   /**
    * gets the node at the current position
    * @param position character set
    * @return DNANode at position
    */
    public DNANode getCharLeaf(char position)
    {
        if (position == 'A')
        {
            return charA;
        }
        if (position == 'C')
        {
            return charC;
        }
        if (position == 'G')
        {
            return charG;
        }
        if (position == 'T')
        {
            return charT;
        }
        if (position == '$')
        {
            return terminator;
        }
        return null;
    }
    
    /**
     * sets the Leaf Node while checking what is the position
     * @param content DNANode to set
     * @param position to check where Node should be put
     */
    public void setCharLeaf(DNANode content, char position)
    {
        if (position == 'A')
        {
            charA = content;
        }
        if (position == 'C')
        {
            charC = content;
        }
        if (position == 'G')
        {
            charG = content;
        }
        if (position == 'T')
        {
            charT = content;
        }
        if (position == '$')
        {
            terminator = content;
        }
    }


   
  
}

