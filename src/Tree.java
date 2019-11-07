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
            current.setBf(current.getBf()+1);
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
            current.setBf(current.getBf()-1);
            if (current.getBf() == -2 && !doneBalancing) {
                // Imbalanced
                Node child = current.getRight();
                Node grandChild = node.getData() < child.getData() ? child.getLeft() : child.getRight();
                balanceTheNode(current, child, grandChild);
                doneBalancing = true;
            }
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
    }

    private int computeHeight(Node node) {
        if (node == null) {
            return 0;
        }

        int lHeight = computeHeight(node.getLeft());
        int rHeight = computeHeight(node.getRight());
        node.setHeight(Math.max(lHeight, rHeight)+1);
        node.setBf(lHeight-rHeight);
        return node.getHeight();
    }

    private void rotateRR(Node pin, Node child, Node gChild) {
        Node pinParent = getParent(root, pin);
        if (pinParent != null) pinParent.setRight(child);
        else root = child;
        pin.setRight(child.getLeft());
        child.setLeft(pin);
    }

    private void rotateLL(Node pin, Node child, Node gChild) {
        Node pinParent = getParent(root, pin);
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

    public Node getParent(Node root, Node node) {
        if (node == root) return null;
        if (root.getLeft() == node || root.getRight() == null) return root;
        Node t1 = getParent(root.getLeft(), node);
        Node t2 = getParent(root.getRight(), node);
        if (t1 == null) return t2;
        else return t1;
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

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }
}
