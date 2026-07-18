
# Simple solution
```java
for (int i = 0; i < nums.length; i++) { 
	for (int j = i + 1; j < nums.length; j++) {
		 if (nums[i] + nums[j] == target) return new int[]{i, j}; 
	}
}
```

# Optimum solution
```java
import java.util.HashMap;

class Solution {
    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> seen = new HashMap<>(); // value -> index
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (seen.containsKey(complement)) {
                return new int[]{seen.get(complement), i};
            }
            seen.put(nums[i], i);
        }
        return new int[]{}; // unreachable given problem guarantees
    }
}
```