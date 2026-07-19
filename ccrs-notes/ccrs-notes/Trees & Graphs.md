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

