package com.example.andrea.braintrainer;

import java.util.Random;

public class MathExpression {
   private int answer;
   private String expression;
   public MathExpression(int maxNum)
   {
      Random rand = new Random();
      int i1 = rand.nextInt(maxNum);
      int i2 = rand.nextInt(maxNum);
      int basicExpressionChoice = rand.nextInt(4);
      switch (basicExpressionChoice)
      {
         case 0:
            expression = Integer.toString(i1) + " + " + Integer.toString(i2);
            answer = i1 + i2;
            break;
         case 1:
            if (i1 > i2)
            {
               expression = Integer.toString(i1) + " - " + Integer.toString(i2);
               answer = i1 - i2;
            }
            else
            {
               expression = Integer.toString(i2) + " - " + Integer.toString(i1);
               answer = i2 - i1;
            }
            break;
         case 2:
            expression = Integer.toString(i1) + " * " + Integer.toString(i2);
            answer = i1 * i2;
            break;
         default:
            while (i2 == 0)
            {
               i2 = rand.nextInt(maxNum);
            }
            expression = Integer.toString(i1) + " / " + Integer.toString(i2);
            answer = (int) Math.floor(i1 / i2);

      }
   }
   public int getAnswer()
   {
      return answer;
   }
   public String getExpression()
   {
      return expression;
   }
}

