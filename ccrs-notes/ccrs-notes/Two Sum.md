

# Simple solution
```java
class Solution {

    public int[] twoSum(int[] nums, int target) {
        int[] solution = {};
        for(int num = 0; num< nums.length; num++){
            for(int num2=num+1; num2<nums.length; num2 ++){
                if(nums[num] + nums[num2] == target){
                    return new int[]{num, num2};
                }
            }
        }
        return solution;
    }
}
```