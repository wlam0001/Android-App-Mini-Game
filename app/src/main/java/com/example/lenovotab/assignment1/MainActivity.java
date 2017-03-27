package com.example.lenovotab.assignment1;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    final static int length = 16;
    final static String [] letters = "ABCDEFGHIJ".split("");
    final static int[] A = {0,1,2};
    final static int[] B = {3,7,9,11};
    final static int[] C = {4, 10, 14, 15};
    final static int[] D = {0, 4, 5, 6, 7};
    final static int[] E = {6, 7, 8, 10, 12};
    final static int[] F = {0, 2, 14, 15};
    final static int[] G = {3, 14, 15};
    final static int[] H = {4, 5, 7, 14, 15};
    final static int[] I = {1, 2, 3, 4, 5};
    final static int[] J = {3, 4, 5, 9, 13};

    int[][] combined = {A, B, C, D, E, F, G, H, I, J};
    GridLayout grid;
    GridLayout buttons;
    boolean[] boolArray;
    List<Integer[]> list;
    String sequence;
    int count;
    int tests;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        grid = (GridLayout) findViewById(R.id.grid_main);
        buttons = (GridLayout) findViewById(R.id.button_main);

        tests = 0;
        count = 0;
        sequence = "";

        int[] array = new int[10];
        list = new ArrayList<>();

        //True = white, False = black
        boolArray = new boolean[length];
        for (int i = 0; i < length; i++){
            boolArray[i] = randomBool();
        }

        for (int i = 0; i < array.length; i++){
            array[i] = i;
        }

        for (int i = 1; i <= array.length; i++){
            combine(array, i, list);
        }

        createGrid(grid);
        createButtons(buttons);
        setTextCountAndSequence (count, sequence);
    }

    private void createGrid (GridLayout grid){
        int cellPixels = (int) convertDpToPixel(56, getApplicationContext());
        for (int i = 0; i < 4; i++) {
            for (int j = 0;  j < 4; j++) {
                TextView cell = new TextView(this);
                GridLayout.LayoutParams param = new GridLayout.LayoutParams(GridLayout.spec(i), GridLayout.spec(j));
                param.rightMargin = 5;
                param.bottomMargin = 5;
                cell.setLayoutParams(param);
                if (!boolArray[(i*4)+j]) {
                    cell.setBackgroundColor(Color.BLACK);
                    cell.setTextColor(Color.WHITE);
                }
                else{
                    cell.setBackgroundColor(Color.WHITE);
                    cell.setTextColor(Color.BLACK);
                }
                cell.setHeight(cellPixels);
                cell.setWidth(cellPixels);
                cell.setId((i*4)+j);
                cell.setText(String.valueOf((i*4)+j));
                cell.setTextSize(getResources().getDimension(R.dimen.textsize));
                cell.setGravity(Gravity.CENTER);
                grid.addView(cell, param);
            }
        }
    }

    private void createButtons (GridLayout buttons){
        int widthPixels = (int) convertDpToPixel(64, getApplicationContext());
        int heightPixels = (int) convertDpToPixel(40, getApplicationContext());

        for (int i = 5; i < 7; i++) {
            for (int j = 0;  j < 5; j++) {
                Button switches = new Button(getApplicationContext());
                GridLayout.LayoutParams location = new GridLayout.LayoutParams(GridLayout.spec(i-5), GridLayout.spec(j));
                location.rightMargin = 5;
                location.bottomMargin = 5;
                switches.setLayoutParams(location);
                switches.setGravity(Gravity.CENTER);
                switches.setId(((i-5)*5)+j+ 16 + 1);
                switches.setText(letters[((i-5)*5)+j+1]);
                switches.setMinimumHeight(heightPixels);
                switches.setMinimumWidth(widthPixels);
                switches.setHeight(heightPixels);
                switches.setWidth(widthPixels);
                switches.setOnClickListener(this);
                buttons.addView(switches, location);
            }
        }
    }

    private double convertDpToPixel(double dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * ((double)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public void onClick(View view) {
        count++;
        if (!sequence.isEmpty()){
            sequence+=" > ";
        }

        String selectedButton = letters[view.getId()-16];

        switch (selectedButton){
            case "A": switchBools(A, boolArray, true);
                sequence+="A";
                break;
            case "B": switchBools(B, boolArray, true);
                sequence+="B";
                break;
            case "C": switchBools(C, boolArray, true);
                sequence+="C";
                break;
            case "D": switchBools(D, boolArray, true);
                sequence+="D";
                break;
            case "E": switchBools(E, boolArray, true);
                sequence+="E";
                break;
            case "F": switchBools(F, boolArray, true);
                sequence+="F";
                break;
            case "G": switchBools(G, boolArray, true);
                sequence+="G";
                break;
            case "H": switchBools(H, boolArray, true);
                sequence+="H";
                break;
            case "I": switchBools(I, boolArray, true);
                sequence+="I";
                break;
            case "J": switchBools(J, boolArray, true);
                sequence+="J";
                break;

        }

        setTextCountAndSequence (count, sequence);
        if (checkSolution(boolArray)){
            CharSequence text = "Completed!";
            showToast(text);
        }
    }

    private void setTextCountAndSequence (int count, String sequence){
        TextView countView =(TextView)findViewById(R.id.count);
        TextView sequenceView =(TextView)findViewById(R.id.sequence);
        Resources res = getResources();

        countView.setText(String.format(res.getString(R.string.count), count ));
        sequenceView.setText(String.format(res.getString(R.string.sequence), sequence));
    }

    public void newGame(View view){
        for (int i = 0; i < length; i++){
            boolArray[i] = randomBool();
            TextView v =(TextView)findViewById(i);
            if (boolArray[i]){
                v.setBackgroundColor(Color.WHITE);
                v.setTextColor(Color.BLACK);
            }
            else{
                v.setBackgroundColor(Color.BLACK);
                v.setTextColor(Color.WHITE);

            }
        }

        count = 0;
        sequence = "";
        setTextCountAndSequence (count, sequence);
    }

    public void solution(View view){
        String solution = "";
        CharSequence text = "";
        Integer [] sol;

        if (checkSolution(boolArray)){
            text = "Already Completed!";
        }else {
            if ((sol = getShortestSolution()) != null) {
                for (Integer letter : sol) {
                    Button selected = (Button) findViewById(letter + 16 + 1);

                    if (!solution.isEmpty()) {
                        solution += " > ";
                    }
                    switchBools(combined[letter], boolArray, true);
                    solution += letters[letter + 1];

                    selected.setTextColor(Color.BLACK);
                }
                if (checkSolution(boolArray)) {
                    setTextCountAndSequence(sol.length, solution);
                    text = "Completed!";
                }


            } else {
                text = "No Solution";
            }
        }
        showToast(text);
    }

    public void testCase(View view){
        if (tests > 4){
            tests = 0;
        }
        switch (tests){
            case 0: boolArray = new boolean[]
                            {true, true, true, false,
                            false, false, false, false,
                            false, false, false, false,
                            false, false, false, false};
                break;
            case 1: boolArray = new boolean[]
                            {false, false, false, true,
                            true, true, true, true,
                            true, true, true, true,
                            true, true, true, true};
                break;
            case 2: boolArray = new boolean[]
                            {false, false, false, true,
                            false, false, false, true,
                            false, true, false, true,
                            false, false, false, false};
                break;
            case 3: boolArray = new boolean[]
                            {false, true, false, true,
                            true, true, false, false,
                            false, true, false, false,
                            false, true, true, true};
                break;
            case 4: boolArray = new boolean[]
                    {true, false, true, false,
                            true, false, false, false,
                            true, false, false, true,
                            true, true, false, false};
                break;
        }
        tests++;

        for (int i = 0; i < length; i++){
            TextView v =(TextView)findViewById(i);
            if (boolArray[i]){
                v.setBackgroundColor(Color.WHITE);
                v.setTextColor(Color.BLACK);
            }
            else{
                v.setBackgroundColor(Color.BLACK);
                v.setTextColor(Color.WHITE);

            }
        }
        count = 0;
        sequence = "";
        setTextCountAndSequence (count, sequence);
    }

    private void showToast(CharSequence text){
        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Nullable
    private Integer[] getShortestSolution (){
        for (Integer[] combo: list){
            boolean[] temp = boolArray.clone();
            for (Integer letter : combo){
                switchBools(combined[letter], temp, false);
            }
            if (checkSolution(temp)){
                return combo;
            }
        }
        return null;
    }

    private boolean randomBool(){
        return Math.random() < 0.5;
    }

    private void switchBools(int [] switchingArray, boolean[] givenBoolArray, boolean updateView){

        for (int aSwitch: switchingArray){
            //update background color to switched color
            if (updateView) {
                TextView view = (TextView) findViewById(aSwitch);

                if (givenBoolArray[aSwitch]) {
                    view.setBackgroundColor(Color.BLACK);
                    view.setTextColor(Color.WHITE);
                } else {
                    view.setBackgroundColor(Color.WHITE);
                    view.setTextColor(Color.BLACK);
                }
            }
            givenBoolArray[aSwitch] = !givenBoolArray[aSwitch];
        }
    }

    public boolean checkSolution(boolean[] givenBoolArray){
        boolean first = givenBoolArray[0];
        for (int i = 1; i < givenBoolArray.length; i++){
            if (first != givenBoolArray[i]){
                return false;
            }
        }
        return true;
    }

    private void update (int[] combine, int[] max, int[] cLoc, List<Integer[]> list ){
        for (int j = combine.length-1; j>=0; j--){
            if (combine [j] < max[j]){
                combine[j]=cLoc[j]++;
                int temp = combine[j];
                for (int i = j; i < combine.length;i++){
                    if (++temp <= max[i]){
                        combine[i] = temp;
                        cLoc[i] = temp;
                    }
                }
                break;
            }
        }
        list.add(convert(combine));
    }

    private void combine(int[] array, int r, List<Integer[]> list) {
        int[] comb = new int[r];
        int[] cLoc = new int[r];
        int[] max = new int[r];
        int c = r-1;

        if (r == 1) {
            for (int i = 0; i < array.length; i++){
                Integer[] temp = {new Integer(array[i])};
                list.add(temp);
            }
        }else{
            for (int i = 0; i < r; i++ ){
                comb[i] = array[i];
                cLoc[i] = i+1;
                max[i] = array[array.length - r + i];
            }
            list.add(convert(comb));

            while (cLoc[0] < max[0]){
                while (comb[c] < max[c]){
                    comb[c] = comb[c] + 1;
                    list.add(convert(comb));
                }
                if (cLoc[0] < max[0]){
                    update (comb, max, cLoc, list);
                }

            }
        }
    }

    private Integer[] convert(int[] arr){
        Integer[] newArray = new Integer[arr.length];
        int i = 0;
        for (int value : arr) {
            newArray[i++] = Integer.valueOf(value);
        }
        return newArray;
    }
}
