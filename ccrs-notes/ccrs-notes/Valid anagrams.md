Freqensycounting wiht hashmap

```java
class Solution {
    public boolean isAnagram(String s, String t) {
        Map<Character, Integer> freq = new HashMap<>();
        for(char c : s.toCharArray()) freq.merge(c, 1, Integer::sum);
        for(char c : t.toCharArray()) freq.merge(c, -1, Integer::sum);
        return freq.values().stream().allMatch(v -> v == 0) ? true : false;
    }
}
```