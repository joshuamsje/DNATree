package dnaTree;

import java.text.DecimalFormat;

/**
 * This class is the implementation of a new
 * type of tree data structure that stores DNA
 * sequences, remove sequences, searches for specific
 * sequences, and searches for sequences.
 * 
 * @author Kurt Karpin (kkarp9)
 * @author Joshua Matthew (josh827)
 * @version 2020-03-29
 *
 */
public class SequenceTree {
    private DNANode root;
    private DNAFlyweightNode empty;
    private int nodeVisits;


    /**
     * Creates the the tree that stores in DNA sequences.
     * We start by having the root set to a FlyWeight node since
     * we have no DNA sequences to be stored yet.
     */
    public SequenceTree() {
        empty = new DNAFlyweightNode();
        root = empty; // set root to the FlyweightNode
        nodeVisits = 0;
    }


    /**
     * Returns the size of the tree i.e. number of LeafNodes
     * that has sequences in them.
     * 
     * @return size
     */

    /**
     * Inserts the sequence into the tree. There are
     * three conditions we must account of the root,
     * either the root is a Flyweight node, a DNALeaf node,
     * or InternalNode
     * 
     * @param sequence
     *            DNA sequence to be inserted
     * @return the level of the node to be inserted
     *         -1 is returned if the node cannot be inserted
     *         due to having an existing one in the tree
     */
    public int insert(String sequence) {

        if (root instanceof DNALeafNode) {
            // the root is a leaf node meaning there is only one
            // sequence added to the tree; this is where we begin to
            // insert Internal Nodes
            if (((DNALeafNode)root).getSequence().equals(sequence)) {
                return -1;
            }
            DNANode temp = new DNAInternalNode(root.getLevel(), empty);

            // sets the level of the root to be one higher due to the
            // new internal node
            root.setLevel(root.getLevel() + 1);

            // casts the temp to act as an Internal node and then sets the
            // character level at the first sequence letter to be root's
            // sequence
            ((DNAInternalNode)temp).setCharLeaf(root, ((DNALeafNode)root)
                .getSequence().charAt(0));

            // then resets root to be at beginning of the tree
            root = temp;

            // calls recursive function with the same sequence and root
            return insert(sequence, root);
            // inserts the sequence after initializing the internal node and
            // setting the original root that is the first leaf node
            // return 1;

        }

        if (root instanceof DNAFlyweightNode) {
            // if the root is still a FlyWeightNode, then the
            // sequence is the first sequence to add to the tree
            root = new DNALeafNode(0, sequence);
            return 0;
        }

        // has a recursive call now with the same sequence and root
        // and it returns an integer to indicate the level

        return insert(sequence, root);
    }


    /**
     * Removes a DNA sequence with the given sequence.
     * Cases occur depending on the type of node
     * the root is. We may also have to rearrange the tree
     * depending on how many neighboring child nodes remain
     * after the sequence node is removed.
     * 
     * @param sequence
     *            to be removed
     * @return true if successively remove
     *         false if not
     */
    public boolean remove(String sequence) {
        // checks to see if the root of the
        // tree is a leafNode with a potential
        // sequence
        if (root instanceof DNALeafNode) {
            // then checks to see if the sequence to be removed
            // is contained in the root
            if (sequence.equals(((DNALeafNode)root).getSequence())) {
                // if it is then set the root to be the flyweight node
                // and return true
                root = empty;
                return true;
            }

            return false;
        }

        // checks to see that the root of the
        // sequence tree is empty meaning
        // that the tree is empty
        if (root instanceof DNAFlyweightNode) {
            // returns false since tree is empty
            return false;
        }

        // recursive call to the remove method with the
        // sequence and the same root
        return remove(sequence, root);
    }


    /**
     * 
     * @param sequence
     * @param node
     * @return
     */
    private boolean remove(String sequence, DNANode node) {

        char findPosition;

        // checks to see if the length of the sequence is
        // less than the level meaning that it could be
        // a prefix in the terminator position

        if (node.getLevel() > sequence.length() - 1) {
            findPosition = '$';
        }
        // otherwise find the character in a sequence at the
        // recursive node pointer's level
        else {
            findPosition = sequence.charAt(node.getLevel());
        }

        // creates a node that points to the node at the sequence's first
        // character position
        DNANode thisNode = ((DNAInternalNode)node).getCharLeaf(findPosition);

        // checks to see if first thisNode pointer is an instance
        // of a DNAflyweightnode
        if (thisNode instanceof DNAFlyweightNode) {
            return false;
        }

        if (thisNode instanceof DNALeafNode) {
            if (((DNALeafNode)thisNode).getSequence().equals(sequence)) {
                ((DNAInternalNode)node).setCharLeaf(empty, findPosition);
                
                if ((numFlyweightNode((DNAInternalNode)node) == 4) && (node.getLevel() == 0))
                //if (node.getLevel() == 0)
                {
                    System.out.println("Check");
                    char newPos = ' ';
                    int counterSeq = 0;
                    if (((DNAInternalNode)node).getCharLeaf(
                        'A') instanceof DNALeafNode) {
                        counterSeq++;
                        newPos = 'A';
                    }
                    if (((DNAInternalNode)node).getCharLeaf(
                        'C') instanceof DNALeafNode) {
                        counterSeq++;
                        newPos = 'C';
                    }
                    if (((DNAInternalNode)node).getCharLeaf(
                        'G') instanceof DNALeafNode) {
                        counterSeq++;
                        newPos = 'G';
                    }
                    if (((DNAInternalNode)node).getCharLeaf(
                        'T') instanceof DNALeafNode) {
                        counterSeq++;
                        newPos = 'T';
                    }
                    if (((DNAInternalNode)node).getCharLeaf(
                        '$') instanceof DNALeafNode) {
                        counterSeq++;
                        newPos = '$';
                    }
                    if (counterSeq == 1)
                    {
                        
                        DNANode replace = ((DNAInternalNode)node).getCharLeaf(newPos);
                        replace.setLevel(replace.getLevel() - 1);
                        root = replace;
                    }
                }
                
                return true;
            }
            return false;
        }

        if (remove(sequence, thisNode)) {
            char newPos = ' ';
            int counterSeq = 0;
            if (((DNAInternalNode)thisNode).getCharLeaf(
                'A') instanceof DNALeafNode) {
                counterSeq++;
                newPos = 'A';
            }
            if (((DNAInternalNode)thisNode).getCharLeaf(
                'C') instanceof DNALeafNode) {
                counterSeq++;
                newPos = 'C';
            }
            if (((DNAInternalNode)thisNode).getCharLeaf(
                'G') instanceof DNALeafNode) {
                counterSeq++;
                newPos = 'G';
            }
            if (((DNAInternalNode)thisNode).getCharLeaf(
                'T') instanceof DNALeafNode) {
                counterSeq++;
                newPos = 'T';
            }
            if (((DNAInternalNode)thisNode).getCharLeaf(
                '$') instanceof DNALeafNode) {
                counterSeq++;
                newPos = '$';
            }
            // if there is only one sequence left must
            // replace the internal node with the leaf
            // node with that sequence
            if (counterSeq == 1) {

                // creates a dnaNode pointer pointing to the
                // leaf node with the sequence

                DNANode replace = ((DNAInternalNode)thisNode).getCharLeaf(
                    newPos);

                // sets the new level back
                // sets the head of the internal node
                // to be the leafnode
                // ((DNAInternalNode)node).setCharLeaf(replace,
                // newPos);
                // System.out.println(thisNode.getLevel());
                remove(((DNALeafNode)replace).getSequence());
                removeBranch(thisNode, ((DNALeafNode)replace).getSequence());

                replace.setLevel(insert(((DNALeafNode)replace).getSequence()));

            }
            return true;
        }

        return false;
    }


    /**
     * Removes a row of nodes depending if the child nodes
     * of the internal node are all FlyweightNodes, we
     * recursively call this function until we reach a branch
     * that is does not consist of all Flyweight nodes
     * 
     * @param node
     *            internal node to remove branch
     * @param sequence
     *            reference to the path to the internal node
     */
    private void removeBranch(DNANode node, String sequence) {
        if (numFlyweightNode(((DNAInternalNode)node)) == 5) {
            DNANode parent;
            if (node.getLevel() - 1 == 0) // this means the parent is the root
            {
                // we do not include the internal node that the root is
                // connected
                // to, so we know that the node to add will be the root
                if (numFlyweightNode(((DNAInternalNode)root)) == 4) {
                    root = empty;
                }
                else {
                    // the root has other child nodes occupied
                    ((DNAInternalNode)root).setCharLeaf(empty, sequence.charAt(
                        0));
                }
            }
            else {
                // gets the parent of the current node
                parent = node.getParentNode(sequence.substring(0, node
                    .getLevel()), ((DNAInternalNode)root), node.getLevel());
                // removes the internal node itself from the parent, so
                // the branch will be removed
                ((DNAInternalNode)parent).setCharLeaf(empty, sequence.charAt(
                    parent.getLevel()));
                // recursively calls for the next branch to be removed
                removeBranch(parent, sequence.substring(0, parent.getLevel()));
            }
            // sets the level of the internal node
            // each time a branch gets removed
            node.setLevel(node.getLevel() - 1);

        }
    }


    /**
     * Helper method that checks the number of Flyweight Node
     * in the current DNAInternal node.
     * 
     * @param parent
     *            the internal node
     * @return num of flyweight nodes
     */
    private int numFlyweightNode(DNAInternalNode parent) {
        int total = 0;
        if (parent.getCharLeaf('A') instanceof DNAFlyweightNode) {
            total++;
        }
        if (parent.getCharLeaf('C') instanceof DNAFlyweightNode) {
            total++;
        }
        if (parent.getCharLeaf('G') instanceof DNAFlyweightNode) {
            total++;
        }
        if (parent.getCharLeaf('T') instanceof DNAFlyweightNode) {
            total++;
        }
        if (parent.getCharLeaf('$') instanceof DNAFlyweightNode) {
            total++;
        }
        return total;
    }


    /**
     * Recursive private function for insert
     * to handle the internal nodes.
     * 
     * @param sequence
     *            String
     * @param node
     *            a casted DNAInternalNode
     * @return
     */
    private int insert(String sequence, DNANode node) {

        char findPosition;
        // a check to see if the sequence is a prefix of another
        // sequence to make sure that the character position
        // should be terminator

        if (node.getLevel() > sequence.length() - 1) {

            findPosition = '$';

        }
        // finds the position at the root's level in the sequence
        else {

            findPosition = sequence.charAt(node.getLevel());

            // System.out.println(findPosition);
        }

        // creates a pointer node that results from the parameter
        // nodes level and the character at that level from the sequence
        DNANode thisNode = ((DNAInternalNode)node).getCharLeaf(findPosition);

        if (thisNode instanceof DNAFlyweightNode) {
            // if the location of the node we insert the seq is a flyweight,
            // then we can simply insert it here
            DNALeafNode newNode = new DNALeafNode(node.getLevel() + 1,
                sequence);

            // sets the character leaf to be the new leafNode at the correct
            // position
            ((DNAInternalNode)node).setCharLeaf(newNode, findPosition);
            // then returns the node get level
            // System.out.println(newNode.getLevel());
            return newNode.getLevel();
        }

        // checks to see if the pointer node is a leafNode
        if (thisNode instanceof DNALeafNode) { // the spot node is full
            DNALeafNode leaf = (DNALeafNode)thisNode;
            // then checks to see if sequence at the leaf node is the same
            // sequence to be inserted meaning it can't be inserted
            String leafSeq = ((DNALeafNode)thisNode).getSequence();
            if (sequence.equals(leafSeq)) {
                return -1;
            }

            // creates a new Temp that should be an internal node at the
            // same level as the pointer with 5 empty flyweights
            DNANode newTemp = new DNAInternalNode(thisNode.getLevel(), empty);
            // System.out.println(printTree("none"));
            // We check if the the sequence in the leaf node is a prefix
            // to the given sequence, that way if it is, we'll
            // put the leaf node to the terminator of the new internal node

            if (sequence.contains(leaf.getSequence()) && (thisNode
                .getLevel() == leaf.getSequence().length())) {
                String hold = leaf.getSequence();
                DNANode newTemp1 = new DNAInternalNode(thisNode.getLevel(),
                    empty);
                DNALeafNode newNode = new DNALeafNode(thisNode.getLevel() + 1,
                    sequence);
                DNALeafNode newNode2 = new DNALeafNode(thisNode.getLevel() + 1,
                    hold);
                ((DNAInternalNode)newTemp1).setCharLeaf(newNode, sequence
                    .charAt(thisNode.getLevel()));
                ((DNAInternalNode)newTemp1).setCharLeaf(newNode2, '$');
                ((DNAInternalNode)node).setCharLeaf(newTemp1, sequence.charAt(
                    node.getLevel()));

                return thisNode.getLevel() + 1;

            }
            else {
                ((DNAInternalNode)newTemp).setCharLeaf(thisNode, leaf
                    .getSequence().charAt(thisNode.getLevel()));

                // ^^
                // then uses the newTemp to set its character leaf
                // to have the thisNode's sequence at the same level as
                // thisNode
                thisNode.setLevel(thisNode.getLevel() + 1);

                // then uses the original node from the function to add the
                // internal
                // node branch to wherever node was previously to complete the
                // tree
                ((DNAInternalNode)node).setCharLeaf(newTemp, sequence.charAt(
                    node.getLevel()));

                // then sets a new level for thisNode to be one higher since
                // it got downshifted

                // then sets the thisNode pointer to be the head of the internal
                // node
                thisNode = newTemp;
            }

        }

        // recursive call with the sequence and pointer this node
        // that should be a InternalNode at this point
        return insert(sequence, thisNode);

    }


    /**
     * Prints the tree depending on the given command
     * from the command file. More info on the private
     * method.
     * 
     * @param cmd
     *            style to print tree
     * @return tree representation
     */
    public String printTree(String cmd) {
        return ("tree dump:\n" + printTree(root, cmd, 2));
        // 2 is the number of indents for child nodes
        // so we add 2 indents for every child nodes
    }


    /**
     * This prints the tree of all nodes in it. Here, we
     * must indent by 2 spaces for every child of the node
     * and 4 spaces for grandchildren, etc.
     * We also have commands to determine how we should
     * print the trees:
     * 
     * *No command* - just print the tree with labeling
     * and proper indents of children nodes stated above.
     * Leaf Nodes as the sequences in them, Internal nodes
     * as 'I', and FlyWeight nodes as 'E'
     * 
     * Lengths - print the tree with the labels as well as
     * the lengths of each sequence i.e. number of letters.
     * 
     * Stats - labed tree with a break down of each letter
     * for each sequence by percentage, i.e. determining the
     * percentage of A's in AAT, etc.
     * 
     * 
     * @param node
     *            curret node of tree to print
     * @param cmd
     *            command of how we print the tree
     * @param indent
     *            the number of indents to push the print;
     *            indicates the children nodes, grandkid nodes, etc
     * @return string representation of tree
     */
    private String printTree(DNANode node, String cmd, int indent) {
        StringBuilder build = new StringBuilder();
        if (node instanceof DNAInternalNode) {
            StringBuilder numIndents = new StringBuilder();
            // loop is made to determine number of indents
            // note for every child/descendant we add 2
            for (int i = 0; i < indent; i++) {
                numIndents.append(" ");
            }
            // check each child node in the internal node
            String totalIndents = numIndents.toString();
            build.append("I\n");
            build.append(totalIndents + printTree(((DNAInternalNode)node)
                .getCharLeaf('A'), cmd, indent + 2));
            build.append(totalIndents + printTree(((DNAInternalNode)node)
                .getCharLeaf('C'), cmd, indent + 2));
            build.append(totalIndents + printTree(((DNAInternalNode)node)
                .getCharLeaf('G'), cmd, indent + 2));
            build.append(totalIndents + printTree(((DNAInternalNode)node)
                .getCharLeaf('T'), cmd, indent + 2));
            build.append(totalIndents + printTree(((DNAInternalNode)node)
                .getCharLeaf('$'), cmd, indent + 2));
        }
        else if (node instanceof DNALeafNode) {
            String sequence = ((DNALeafNode)node).getSequence();
            build.append(sequence);
            // check how we print it by commands here
            if (cmd.equals("lengths")) {
                build.append(" " + sequence.length());
            }
            else if (cmd.equals("stats")) {
                // format to 2 decimal places
                DecimalFormat df = new DecimalFormat("0.00");
                build.append(" A:" + df.format(charPercent(sequence, 'A'))
                    + " ");
                build.append("C:" + df.format(charPercent(sequence, 'C'))
                    + " ");
                build.append("G:" + df.format(charPercent(sequence, 'G'))
                    + " ");
                build.append("T:" + df.format(charPercent(sequence, 'T')));
            }
            build.append("\n");
        }
        else {
            // assumes the node is a FlyWeight, just print E
            build.append("E\n");
        }
        return build.toString();
    }


    /**
     * Helper method that calculates the letter breakdown
     * percentage of the given letter to the sequence.
     * 
     * @param sequence
     * @param letter
     * @return percent
     */
    private double charPercent(String sequence, char letter) {

        int numLetter = 0;
        for (int i = 0; i < sequence.length(); i++) {
            if (sequence.charAt(i) == letter) {
                // number of letters in sequence
                numLetter++;
            }
        }
        return (100.00 * numLetter / sequence.length());
    }


    /**
     * Searches for matches of sequences in the tree depending on what
     * type of forms: either we only find sequences that match with the
     * given sequence (denoted by a $ at the end of the sequence or
     * matches AND sequences where the given sequence
     * is the prefix i.e. AA is a prefix of AAG.
     * 
     * @param sequence
     *            searching sequence
     * @return string representation of
     *         -number of nodes visited
     *         -list of sequences that match depending on the form
     */
    public String search(String sequence) {
        nodeVisits = 0; // note this resets to 0 every time
        // the search method is called

        StringBuilder build = new StringBuilder();
        if (sequence.charAt(sequence.length() - 1) == '$') {
            build.append(search(root, sequence.substring(0, sequence.length()
                - 1), false));
        }
        else {
            build.append(search(root, sequence, true));
        }
        String result = "# of nodes visited: " + nodeVisits + "\n";
        String searchResult = build.toString();
        if (searchResult.isEmpty()) {
            result += "no sequence found" + "\n";
            return result;
        }
        result += searchResult;
        return result;
    }


    /**
     * Helper method that traverses through the tree to determine
     * if we find sequences that are matches and/or prefix match to
     * the given sequence.
     * 
     * @param node
     *            the node to remove
     *            current node of the tree
     * @param sequence
     *            sequence to find
     * @param prefix
     *            check if matching prefixes count
     *            this determiens the form of either we want
     *            to include searches for prefixes or not
     * @return string representation of nodes that match in the
     *         given deliverables
     */
    private String search(DNANode node, String sequence, boolean prefix) {
        StringBuilder build = new StringBuilder();
        if (node instanceof DNAInternalNode) {
            nodeVisits++; // visits DNAInternal node
            if (node.getLevel() < sequence.length()) {
                // this eliminates unnecessary visits to other nodes
                // in the level not
                // matching to the character of the given sequence
                char charIndex = sequence.charAt(node.getLevel());
                // uses the characters of the sequence to traverse tree
                build.append(search(((DNAInternalNode)node).getCharLeaf(
                    charIndex), sequence, prefix));
            }
            else // can no longer traverse through internal nodes
            {
                if (!prefix) // only finding exact matches
                {
                    build.append(search(((DNAInternalNode)node).getCharLeaf(
                        '$'), sequence, prefix)); // terminator may have a match
                }
                else // prefixes are included
                {
                    // here we have to visit all remaining
                    // nodes since we no longer
                    // have more characters to guide through the tree
                    build.append(search(((DNAInternalNode)node).getCharLeaf(
                        'A'), sequence, prefix));
                    build.append(search(((DNAInternalNode)node).getCharLeaf(
                        'C'), sequence, prefix));
                    build.append(search(((DNAInternalNode)node).getCharLeaf(
                        'G'), sequence, prefix));
                    build.append(search(((DNAInternalNode)node).getCharLeaf(
                        'T'), sequence, prefix));
                    build.append(search(((DNAInternalNode)node).getCharLeaf(
                        '$'), sequence, prefix));
                }

            }
        }
        else if (node instanceof DNALeafNode) // visits leaf node
        {
            nodeVisits++;
            String thisSeq = ((DNALeafNode)node).getSequence();
            if (sequence.equals(thisSeq)) // checks if they mtach
            {
                build.append("sequence: " + thisSeq + "\n");
            }
            // checks for prefix with the given boolean condition
            // of whether we must also find sequences for which
            // the given sequence is a prefix
            // we also can't allow longer sequences to "match" with
            // smaller sequences with the use of substrings
            // result in outofbounds exception
            else if (sequence.length() <= thisSeq.length() && prefix && sequence
                .equals(thisSeq.substring(0, sequence.length()))) {
                build.append("sequence: " + thisSeq + "\n");
            }
        }
        else {
            nodeVisits++; // visits flyweight node
            // nothing happens here...no comparison
        }
        return build.toString();
    }
}
