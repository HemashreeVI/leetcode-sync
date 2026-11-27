class Solution {
    public boolean canAliceWin(int n) {

        if(n < 10){
            return false;
        }
        int m = 10;
        int count = 0 ;

        while( n > 0){
            n = n-m;

            if(n >= 0){
            count++;
            }
            else{
                break;
            }

            m=m-1;
        }

        if(count%2 == 0 ){
            return false;
        }
        return true;


    
        
    }
}

// 20
// 10  1 true
// 1   2 false
//


