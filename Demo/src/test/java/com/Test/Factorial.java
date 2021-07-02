package com.Test;
   public class Factorial {
      public static void main(String args[]){
         int i, factorial=1, number;
        for (number =10; number<20; number++) {

         for(i = 1; i<=number; i++) {
            factorial = factorial * i;
         }
         System.out.println("Factorial of the given number is:: "+factorial);
      }
      }
   }