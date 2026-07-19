# DFS (recursive)
When to use it? Explore all paths, tree traversal, path sums.
Seen in: Max Depth of Binary Tree, Path Sum
```java
// Max depth of binary tree
int maxDepth(TreeNode node) {
    if (node == null) return 0;
    return 1 + Math.max(maxDepth(node.left), maxDepth(node.right));
}
```

| static int | [max](https://docs.oracle.com/javase/8/docs/api/java/lang/Math.html#max-int-int-)(int a, int b) | Returns the greater of two `int` values. |
| ---------- | ----------------------------------------------------------------------------------------------- | ---------------------------------------- |
# BFS (queue-based)
When to use it? Shortest path, level-by-level processing.
Seen in: Binary Tree Level Order Traversal, Shortest Path in Binary Matrix
```java
// Level order traversal
Queue<TreeNode> queue = new LinkedList<>();
queue.add(root);
while (!queue.isEmpty()) {
    int size = queue.size();
    for (int i = 0; i < size; i++) {
        TreeNode node = queue.poll();
        // process node
        if (node.left != null) queue.add(node.left);
        if (node.right != null) queue.add(node.right);
    }
}
```

