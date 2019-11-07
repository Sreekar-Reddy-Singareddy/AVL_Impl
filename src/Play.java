public class Play {
    public static void main (String ... args) {
        Tree tree = new Tree();
        int [] arr = {20,10,30,5,8,3,2,9,11,23};
        for (int i=0; i<arr.length; i++) {
            tree.insert(tree.getRoot(), new Node(arr[i]));
        }
//        tree.preOrderTraverse(tree.getRoot());

        tree.deleteNode(10);
        tree.deleteNode(30);
        tree.deleteNode(20);
        tree.preOrderTraverse(tree.getRoot());
        tree.inOrderTraverse(tree.getRoot());
    }
}
