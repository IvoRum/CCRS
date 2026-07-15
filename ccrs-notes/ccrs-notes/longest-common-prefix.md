# Белешки
Тука тябва да мислиш едновременно за вътрешния pointer и за loop на списъка. Объркващо е.

# Solution
```java
class Solution {

    public String longestCommonPrefix(String[] strs) {
        StringBuilder sb = new StringBuilder();
        int pointer = 0;
        boolean loopFlag =true;

        while(loopFlag){//pointer iteration
            if(strs.length == 0 || pointer > strs[0].length() -1){
                break;
            }
            char commonChar = strs[0].charAt(pointer);
            int checkBR = 0;
            for(int i = 0; i < strs.length; i++){//strs iteration
                if(pointer > strs[i].length() -1 || (pointer > 0 && sb.length() ==0)){//optiomisation
                    loopFlag = false;
                    break;
                }
                if(commonChar == strs[i].charAt(pointer)){
                    checkBR++;
                }
  
                if(checkBR == strs.length ){
                    sb.append(commonChar);
                } else if(i == strs.length -1){
                    loopFlag = false;
                    break;
                }
            }
            pointer++;
        }
        return sb.toString();
    }
}
```