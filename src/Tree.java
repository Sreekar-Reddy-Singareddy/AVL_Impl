public class Tree {
    private Node root;

    public void insert(Node current, Node node) {
        if (current == null) {
            /* No tree yet. This node will be the root node */
            root = node;
            return;
        }

        // Is this node greater or lesser than the current node
        if (node.getData() < current.getData()) {
            // Left subtree
            if (current.getLeft() == null) {
                current.setLeft(node);
                return;
            }
            insert(current.getLeft(), node);
        }
        else {
            // Right subtree
            if (current.getRight() == null) {
                current.setRight(node);
                return;
            }
            insert(current.getRight(), node);
        }
    }

    public void inOrderTraverse (Node current) {
        if (current == null) return;
        inOrderTraverse(current.getLeft());
        System.out.print(current.getData()+" ");
        inOrderTraverse(current.getRight());
    }

    public void inOrderTraverseDetailed (Node current) {
        if (current == null) return;
        inOrderTraverseDetailed(current.getLeft());
        if (current.getLeft() != null && current.getRight() != null)
            System.out.println("P -> "+current.getData()+" L -> "+current.getLeft().getData()+" R -> "+current.getRight().getData());
        else if (current.getLeft() != null)
            System.out.println("P -> "+current.getData()+" L -> "+current.getLeft().getData()+" R -> N/A");
        else if (current.getRight() != null)
            System.out.println("P -> "+current.getData()+" L -> N/A"+" R -> "+current.getRight().getData());
        else
            System.out.println("P -> "+current.getData()+" L -> N/A"+" R -> N/A");
        inOrderTraverseDetailed(current.getRight());
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }
}
