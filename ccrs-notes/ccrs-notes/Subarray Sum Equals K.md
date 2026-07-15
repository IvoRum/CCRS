# Problem

# Solution
```java
class Solution {
    public int subarraySum(int[] nums, int k) {
        Map<Integer, Integer> prefixCount = new HashMap<>();
        prefixCount.put(0, 1); // empty prefix, handles subarrays starting at index 0
        int count = 0;
        int sum = 0;
        for (int num : nums) {
            sum += num;
            count += prefixCount.getOrDefault(sum - k, 0);
            prefixCount.merge(sum, 1, Integer::sum);
        }
        return count;
    }
}
```
**The correct approach: prefix sum + HashMap**

Core idea: if you keep a running sum (`prefixSum`) as you scan left to right, then a subarray from index `i+1` to `j` sums to `k` exactly when `prefixSum[j] - prefixSum[i] == k`, i.e. `prefixSum[i] == prefixSum[j] - k`. So at each step, you check: _"have I seen a prefix sum equal to `currentSum - k` before?"_ — if yes, that many subarrays ending here sum to `k`.
