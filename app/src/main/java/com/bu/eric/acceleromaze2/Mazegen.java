package com.bu.eric.acceleromaze2;

import android.app.Activity;

public class Mazegen extends Activity {

    public static Acceleromaze getMaze(int mazeNo) {
        Acceleromaze maze = null;
        if(mazeNo == 1) {
            maze = new Acceleromaze();
            boolean[][] border = new boolean[][]{
                    {true ,true ,true ,true ,true ,true ,true ,true ,true ,true ,true ,true ,true ,true ,true ,true ,true ,true },//0
                    {true ,false,true ,false,false,false,false,false,true ,false,false,false,false,false,false,false,false,true },//1
                    {true ,false,true ,false,false,false,false,false,true ,false,false,false,false,false,false,false,false,true },//2
                    {true ,false,true ,false,false,false,false,false,true ,false,false,false,false,false,false,false,false,true },//3
                    {true ,false,true ,false,false,false,false,false,true ,false,false,false,true ,false,false,false,false,true },//4
                    {true ,false,false,false,false,true ,false,false,true ,false,false,false,true ,false,false,false,false,true },//5
                    {true ,false,false,false,false,true ,false,false,true ,false,false,false,true ,false,false,false,false,true },//6
                    {true ,true ,true ,true ,true ,true ,false,false,true ,false,false,false,true ,false,false,false,false,true },//7
                    {true ,false,false,false,false,false,false,false,true ,false,false,false,true ,true ,true ,true ,true ,true },//8
                    {true ,false,false,false,false,false,false,false,true ,false,false,false,false,false,false,false,false,true },//9
                    {true ,false,false,false,false,false,false,false,true ,false,false,false,false,false,false,false,false,true },//10
                    {true ,false,false,false,false,true ,true ,true ,true ,true ,true ,true ,true ,true ,true ,false,false,true },//11
                    {true ,false,false,false,false,true ,false,false,false,false,false,false,false,false,false,false,false,true },//12
                    {true ,false,false,false,false,true ,false,false,false,false,false,false,true ,false,false,false,false,true },//13
                    {true ,true ,true ,true ,false,true ,false,false,false,false,false,false,true ,false,false,false,false,true },//14
                    {true ,false,false,false,false,true ,false,false,false,false,false,false,true ,false,false,false,false,true },//15
                    {true ,false,false,false,false,true ,false,false,false,false,false,false,true ,false,false,false,false,true },//16
                    {true ,false,false,false,false,true ,false,false,false,false,false,false,false,false,false,false,false,true },//17
                    {true ,false,true ,true ,true ,true ,false,false,true ,true ,true ,true ,true ,true ,true ,true ,true ,true },//18
                    {true ,false,false,false,false,true ,false,false,false,false,false,false,false,false,false,false,false,true },//19
                    {true ,false,false,false,false,true ,false,false,false,false,false,false,true ,false,false,false,false,true },//20
                    {true ,false,false,false,false,true ,true ,true ,true ,true ,true ,true ,true ,false,false,false,false,true },//21
                    {true ,true ,true ,true ,false,true ,false,false,false,false,false,false,true ,false,false,false,false,true },//22
                    {true ,false,false,false,false,true ,false,false,false,false,false,false,true ,false,false,false,false,true },//23
                    {true ,false,false,false,false,true ,false,false,false,false,false,false,false,false,false,false,false,true },//24
                    {true ,false,false,false,false,true ,false,false,true ,true ,true ,true ,true ,true ,true ,true ,true ,true },//25
                    {true ,false,true ,true ,true ,true ,false,false,false,false,false,false,false,false,false,false,false,true },//26
                    {true ,false,false,false,false,true ,false,false,false,false,false,false,false,false,false,false,false,true },//27
                    {true ,false,false,false,false,true ,true ,true ,true ,true ,true ,true ,false ,false,false,false,false,true },//28
                    {true ,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true },//29
                    {true ,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true },//30
                    {true ,true ,true ,true ,true ,true ,true ,true ,true ,true ,true ,true ,true ,true ,true ,true ,true ,true },//31
            };       //0   //1   //2   //3   //4   //5   //6   //7   //8   //9   //10  //11  //12  //13  //14  //15  //16  //17

            boolean[][] wholes = new boolean[][]{
                    {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},//0
                    {false,false,false,false,false,false,false,false,false,true ,false,false,false,false,false,true ,false,false},//1
                    {false,false,false,false,true ,false,false,false,false,false,false,false,false,false,false,false,false,false},//2
                    {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},//3
                    {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},//4
                    {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},//5
                    {false,false,false,false,false,false,false,false,false,false,false,false,false,true ,false,false,false,false},//6
                    {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},//7
                    {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},//8
                    {false,false,false,false,false,true ,false,false,false,false,false,false,false,false,false,false,false,false},//9
                    {false,false,false,false,false,false,false,false,false,true ,false,false,false,false,false,false,false,false},//10
                    {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},//11
                    {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},//12
                    {false,false,false,false,false,false,false,false,false,false,false,false,false,true ,false,false,false,false},//13
                    {false,false,false,false,false,false,false,false,false,false,true ,false,false,false,false,false,false,false},//14
                    {false,false,true ,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},//15
                    {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true ,false},//16
                    {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},//17
                    {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},//18
                    {false,false,false,false,true ,false,false,false,false,false,false,false,false,false,false,false,false,false},//19
                    {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},//20
                    {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},//21
                    {false,false,false,false,false,false,false,true ,false,false,false,false,false,false,false,false,false,false},//22
                    {false,true ,false,false,false,false,false,false,false,false,false,false,false,false,false,true ,false,false},//23
                    {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},//24
                    {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},//25
                    {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},//26
                    {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},//27
                    {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},//28
                    {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true ,false,false},//29
                    {false,true ,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},//30
                    {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},//31
            };        //0   //1   //2   //3   //4   //5   //6   //7   //8   //9   //10  //11  //12  //13  //14  //15  //16  //17
            maze.setBorders(border);
            maze.setHoles(wholes);
            maze.setStartPosition(1, 2);
            maze.setFinalPosition(15, 6);
        }
        //other mazes
        return maze;
    }
}
