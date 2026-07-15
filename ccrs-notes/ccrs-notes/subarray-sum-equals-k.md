# Problem
Интересното за този проблем е че трябва да се помили за 
# Solution
```java
class Solution {
    public int subarraySum(int[] nums, int k) {
        int n = nums.length;
        int[] prefix = new int[n];
        prefix[0] = nums[0];
        for (int i = 1; i < n; i++) {
           prefix[i] = prefix[i - 1] + nums[i];
        }

        int result = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (calcSum(prefix, i, j) == k) {
                    result++;
                }
            }
        }
        return result
    }

    private int calcSum(int[] prefix, int left, int right) {
        if (left == 0) {
            return prefix[right];
        }
        return prefix[right] - prefix[left - 1];
    }

}
```