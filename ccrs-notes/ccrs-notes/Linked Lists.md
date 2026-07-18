# Fast & Slow Pointers (Floyd's)
Detect cycles, find the middle, find the Nth-from-end node

```java
// Detect cycle
ListNode slow = head, fast = head;
while (fast != null && fast.next != null) {
    slow = slow.next;
    fast = fast.next.next;
    if (slow == fast) return true; // cycle found
}
return false;
```

mine :( 6ms
```java
public class Solution {
    public boolean hasCycle(ListNode head) {
        List<ListNode> knownNodes = new ArrayList<>();
        while(head != null){
            if(knownNodes.contains(head)){
                return true;
            }
            knownNodes.add(head);
            head = head.next;
        }
        return false;
    }
}
```

