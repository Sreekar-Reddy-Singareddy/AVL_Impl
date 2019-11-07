public class Play {
    public static void main (String ... args) {
        Tree tree = new Tree();
        int [] arr = {20,10,30,5,8,3,2};
        for (int i=0; i<arr.length; i++) {
            tree.insert(tree.getRoot(), new Node(arr[i]));
        }
        tree.preOrderTraverse(tree.getRoot());

    }
}
