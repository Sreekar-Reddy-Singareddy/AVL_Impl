public class Play {
    public static void main (String ... args) {
        Tree BST = new Tree();
        int [] arr = {10,34,23,12,56,24,90,21,8};
        for (int i=0; i<arr.length; i++) {
            BST.insert(BST.getRoot(), new Node(arr[i]));
        }
        BST.inOrderTraverse(BST.getRoot());
        BST.inOrderTraverseDetailed(BST.getRoot());
    }
}
