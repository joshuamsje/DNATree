package dnaTree;
// -------------------------------------------------------------------------
/**
 * Abstract class that has subclasses of nodes
 * that are either Internal nodes, leaf nodes, or 
 * flyweight nodes
 * @author Kurt Karpin (kkarp9)
 * @author Joshua Matthew (josh827)
 * @version 2020-03-29
 *
 */
public abstract class DNANode {

    private int level;

    /**
     * Creates the DNANode 
     * and sets the level to 0
     */
    public DNANode() {
        level = 0;
    }

    /**
     * Getter method that returns the 
     * level of the tree
     * @return level
     */
    public int getLevel() {
        return level;
    }
    /**
     * Returns the parent node of this DNANode.
     * Recursion will be called until the parent
     * node is reached
     * @param sequence pathway to parent node 
     * @param root root of tree
     * @param branch current level of the root
     * @return parent node 
     */
    public DNANode getParentNode(String sequence, 
        DNAInternalNode root, int branch)
    {
        DNANode parent = root.getCharLeaf(sequence.charAt(root.getLevel()));
        if (parent.getLevel() == branch - 1)
        {
            return parent;
        }
        return getParentNode(sequence, ((DNAInternalNode) parent), branch);
            
    }

    /**
     * Sets the level of the DNANode
     * @param newLevel new level
     */
    public void setLevel(int newLevel) {
        level = newLevel;
    }
    

}
