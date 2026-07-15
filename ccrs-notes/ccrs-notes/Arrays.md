
С тези алгоритми гоним constant time n(1).x
# Two Pointers
Array is sorted (or can be sorted), and you're looking for pairs/triplets matching a condition.
"pair that sums to," "sorted array," "palindrome check"
```java
// Two Sum II - sorted array
int left = 0, right = nums.length - 1;
while (left < right) {
    int sum = nums[left] + nums[right];
    if (sum == target) return new int[]{left, right};
    else if (sum < target) left++;
    else right--;
}
```
Това е подход който гледаме да изпълним критерия с по-структорирано търсене.
Решен проблем с прилагене на алторитама: [[Container With Most Water]]
More Example problem: Two Sum II, 3Sum
# Sliding Window
Contiguous subarray/substring, looking for max/min/count matching a condition, as window grows/shrinks

```java
// Longest substring without repeating characters
Set<Character> window = new HashSet<>();
int left = 0, maxLen = 0;
for (int right = 0; right < s.length(); right++) {
    while (window.contains(s.charAt(right))) {
        window.remove(s.charAt(left));
        left++;
    }
    window.add(s.charAt(right));
    maxLen = Math.max(maxLen, right - left + 1);
}
```

Example problem: Longest Substring Without Repeating Characters, Max Sum Subarray of size K

# Prefix Sum
Need repeated range-sum queries, or "subarray sums to X"
```java
// Range sum queries
int[] prefix = new int[nums.length + 1];
for (int i = 0; i < nums.length; i++) {
    prefix[i + 1] = prefix[i] + nums[i];
}
// sum of range [i, j] inclusive:
int rangeSum = prefix[j + 1] - prefix[i];
```

[[subarray-sum-equals-k]]
