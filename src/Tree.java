public class Tree {
    private Node root;
    private boolean doneBalancing;

    public void insert(Node current, Node node) {
        if (current == null) {
            /* No tree yet. This node will be the root node */
            root = node;
            return;
        }

        // Is this node greater or lesser than the current node
        if (node.getData() < current.getData() && current.getLeft() == null) {
            current.setLeft(node);
            node.setBf(0);
            current.setBf(current.getBf()+1);
            doneBalancing = false;
            return;
        }
        else if (node.getData() >= current.getData() && current.getRight() == null) {
            current.setRight(node);
            node.setBf(0);
            current.setBf(current.getBf()-1);
            doneBalancing = false;
            return;
        }
        else if (node.getData() < current.getData() && current.getLeft() != null) {
            insert(current.getLeft(), node);
            if (!doneBalancing) current.setBf(current.getBf()+1);
            if (current.getBf() == 2 && !doneBalancing) {
                // Imbalanced
                Node child = current.getLeft();
                Node grandChild = node.getData() < child.getData() ? child.getLeft() : child.getRight();
                balanceTheNode(current, child, grandChild);
                doneBalancing = true;
            }
        }
        else if (node.getData() >= current.getData() && current.getRight() != null) {
            insert(current.getRight(), node);
            if (!doneBalancing) current.setBf(current.getBf()-1);
            if (current.getBf() == -2 && !doneBalancing) {
                // Imbalanced
                Node child = current.getRight();
                Node grandChild = node.getData() < child.getData() ? child.getLeft() : child.getRight();
                balanceTheNode(current, child, grandChild);
                doneBalancing = true;
            }
        }
    }

    // This method works ONLY for deletion using predecessor
    // Based on question, it is assumed that there will always be
    // a predecessor for node 100
    public void deleteNode(int val) {
        Node d = getNodeFor(val, root);
        Node dParent = getParentFor(root, d);
        Node p = getPredecessorFor(d);
        Node pParent = null;
        if (p != null) pParent = getParentFor(root, p); // No need to get parent if there is no child

        // Change the node links now
        if ((pParent != null && pParent != d)) {
            // Preserve the left sub-tree of the predecessor
            pParent.right = p.left;
        }
        if (p != null && d != null) {
            if (pParent != d) p.left = d.left; // If pParent and d are equal it results in self-reference by p
            p.right = d.right;
        }
        if (dParent != null) {
            // NOT a root node
            if (d.data > dParent.data) {
                // The deleted node is on right side
                // So update the dParent's right to predecessor
                dParent.right = p;
            }
            else if (d.data < dParent.data) {
                // The deleted node is on left side
                // So update the dParent's left to predecessor
                dParent.left = p;
            }
        }
        else {
            // The root node is deleted
            // Hence, the new root node is the predecessor
            root = p;
        }
        computeHeight(root);
    }

    private void balanceTheNode(Node pin, Node child, Node gChild) {
        if (child.equals(pin.getRight()) && gChild.equals(child.getRight())) { // CASE 1
            rotateRR(pin, child, gChild);
        }
        else if (child.equals(pin.getLeft()) && gChild.equals(child.getLeft())) { // CASE 2
            rotateLL(pin, child, gChild);
        }
        else if (child.equals(pin.getLeft()) && gChild.equals(child.getRight())) { // CASE 3
            rotateLR(pin, child, gChild);
        }
        else if (child.equals(pin.getRight()) && gChild.equals(child.getLeft())) { // CASE 4
            rotateRL(pin, child, gChild);
        }
        computeHeight(root);
    }

    private int computeHeight(Node node) {
        if (node == null) {
            return 0;
        }

        int lHeight = computeHeight(node.getLeft());
        int rHeight = computeHeight(node.getRight());
        node.setHeight(Math.max(lHeight, rHeight)+1);
        node.setBf(lHeight-rHeight);

        Node p, c = null, gc;
        if (Math.abs(node.getBf()) > 1) {
            if (node.getBf() == -2) {
                p = node;
                c = node.right;
            }
            else{
                p = node;
                c = node.left;
            }
            if (c.left.getHeight() > c.right.getHeight()) {
                gc = c.left;
            }
            else {
                gc = c.right;
            }
            balanceTheNode(p, c, gc);
        }
        return node.getHeight();
    }

    private void rotateRR(Node pin, Node child, Node gChild) {
        Node pinParent = getParentFor(root, pin);
        if (pinParent != null) pinParent.setRight(child);
        else root = child;
        pin.setRight(child.getLeft());
        child.setLeft(pin);
    }

    private void rotateLL(Node pin, Node child, Node gChild) {
        Node pinParent = getParentFor(root, pin);
        if (pinParent != null) pinParent.setLeft(child);
        else root = child;
        pin.setLeft(child.getRight());
        child.setRight(pin);
    }

    private void rotateLR(Node pin, Node child, Node gChild) {
        pin.setLeft(gChild);
        child.setRight(gChild.getLeft());
        gChild.setLeft(child);

        // Now do LL rotation for this
        rotateLL(pin, gChild, child);
    }

    private void rotateRL(Node pin, Node child, Node gChild) {
        pin.setRight(gChild);
        child.setLeft(gChild.getRight());
        gChild.setRight(child);

        // Now do RR rotation for this
        rotateRR(pin, gChild, child);
    }

    public Node getParentFor(Node root, Node n) {
        if (root == n) return null;
        else if (n.data > root.data) {
            if (root.right == n) return root;
            return getParentFor(root.right, n);
        }
        else if (n.data < root.data) {
            if (root.left == n) return root;
            return getParentFor(root.left, n);
        }
        return null;
    }

    public Node getNodeFor(int val, Node root) {
        if (root == null) {
            // No node found with this value
            return null;
        }
        else if (val < root.data) {
            // Go on left side
            return getNodeFor(val, root.left);
        }
        else if (val > root.data) {
            // Go on right side
            return getNodeFor(val, root.right);
        }
        else {
            return root;
        }
    }

    public void inOrderTraverse (Node current) {
        if (current == null) return;
        inOrderTraverse(current.getLeft());
        System.out.print(current.getData()+" ");
        inOrderTraverse(current.getRight());
    }

    public void preOrderTraverse (Node current) {
        if (current == null) return;
        if (current.getLeft() != null && current.getRight() != null)
            System.out.println("P -> "+current.getData()+" L -> "+current.getLeft().getData()+" R -> "+current.getRight().getData()+" H = "+current.getHeight()+" BF: "+current.getBf());
        else if (current.getLeft() != null)
            System.out.println("P -> "+current.getData()+" L -> "+current.getLeft().getData()+" R -> NULL"+" H = "+current.getHeight()+" BF: "+current.getBf());
        else if (current.getRight() != null)
            System.out.println("P -> "+current.getData()+" L -> NULL"+" R -> "+current.getRight().getData()+" H = "+current.getHeight()+" BF: "+current.getBf());
        else
            System.out.println("P -> "+current.getData()+" L -> NULL"+" R -> NULL"+" H = "+current.getHeight()+" BF: "+current.getBf());
        preOrderTraverse(current.getLeft());
        preOrderTraverse(current.getRight());
    }

    private Node getPredecessorFor(Node n) {
        if (n.left == null) return null;
        return searchPredecessor(n.left);
    }

    private Node searchPredecessor(Node node) {
        if (node.right == null) return node;
        return searchPredecessor(node.right);
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }
}
