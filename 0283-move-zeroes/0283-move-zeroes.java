class Solution {
    public void moveZeroes(int[] array) {

        int n = array.length;

        int k =0;
        for(int i =0 ; i < n ; i++){
            if(array[i] !=0){
                array[k]=array[i];
                k++;
            }
        }

        while(k < n){
            array[k]=0;
            k++;
        }
    
    }
}