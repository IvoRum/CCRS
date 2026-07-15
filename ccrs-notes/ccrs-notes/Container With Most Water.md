[Container With Most Water](https://leetcode.com/problems/container-with-most-water/)

solution
```java
class Solution {
    public int maxArea(int[] height) {
        int left = 0;
        int right = height.length -1;
        int maxWatter = 0;
        boolean leftIsBegger = false;
        while(left < right){
            int tankHight = height[left] < height[right] ? height[left] : height[right];
            leftIsBegger = height[left] > height[right] ? true : false;

            int waterRotation = (right - left) * tankHight;
            if(maxWatter < waterRotation){
                maxWatter = waterRotation;
            } else if(leftIsBegger){
                right --;
            } else {
                left ++;
            }
        }

        return maxWatter;
    }
}
```
Не трябва да се забравя че ни интересува в този случай дали лябата или дясната страна е по-голямата затова трябва да я следим с булева а не на сляпо да смалямаме от ляво или от дясно а да смяляваме от най-голямата страна.