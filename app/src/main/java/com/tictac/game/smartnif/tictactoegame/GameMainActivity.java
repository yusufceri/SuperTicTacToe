package com.tictac.game.smartnif.tictactoegame;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.smartnif.game.tictacai.AIPlayer;
import com.smartnif.game.tictacai.GameMain;
import com.smartnif.game.tictacai.StartSolve;

import java.util.ArrayList;

public class GameMainActivity extends AppCompatActivity implements GameInterface{

    public static final String THIS_FILE = "GameMainActivity";

    StartSolve solve;
    GameMain.Seed userChoice, oppPlayer;

    /////////////////////////////////////////////////////////////
    /////
    int skin_cross = R.drawable.default_cross;	// default values.
    int skin_dot = R.drawable.default_dot;		// default values.

    int btn_user_choice, btn_comp_choice;
    int userWinNumber=0, compWinNumber=0;

    private StartSolve.GameLEVEL gameLevel = StartSolve.GameLEVEL.MEDIUM;

    View.OnClickListener button_listener = new View.OnClickListener() {
        public void onClick(View v) {
            ImageButton ibutton = (ImageButton) v;
            int x=0, y=0;

            if(v == findViewById(R.id.b1)){
                //Log.d(THIS_FILE, "1. Button ==============") ;
                solve.nextMoveForUser(x,y);
            }else  if(v == findViewById(R.id.b2)){
               // Log.d(THIS_FILE, "2. Button ==============") ;
                solve.nextMoveForUser(x, y + 1);
            }else  if(v == findViewById(R.id.b3)){
                //Log.d(THIS_FILE, "3. Button ==============") ;
                solve.nextMoveForUser(x, y + 2);
            }
            else  if(v == findViewById(R.id.b4)){
                //Log.d(THIS_FILE, "4. Button ==============") ;
                solve.nextMoveForUser(x + 1, y);
            }
            else  if(v == findViewById(R.id.b5)){
                //Log.d(THIS_FILE, "5. Button ==============") ;
                solve.nextMoveForUser(x + 1, y + 1);
            }
            else  if(v == findViewById(R.id.b6)){
                //Log.d(THIS_FILE, "6. Button ==============") ;
                solve.nextMoveForUser(x + 1, y + 2);
            }
            else  if(v == findViewById(R.id.b7)){
                //Log.d(THIS_FILE, "7. Button ==============") ;
                solve.nextMoveForUser(x + 2, y);
            }
            else  if(v == findViewById(R.id.b8)){
                //Log.d(THIS_FILE, "8. Button ==============") ;
                solve.nextMoveForUser(x + 2, y + 1);
            }
            else  if(v == findViewById(R.id.b9)){
                //Log.d(THIS_FILE, "9. Button ==============") ;
                solve.nextMoveForUser(x+2,y+2);
            }

            ibutton.setClickable(false);
            ibutton.setImageResource(btn_user_choice);
        }
    };

    public void choiceType(int type){
        if(type == skin_cross){
            btn_user_choice = skin_cross;
            userChoice = GameMain.Seed.CROSS ;

            btn_comp_choice = skin_dot;
            oppPlayer = GameMain.Seed.NOUGHT;

        }else  if(type == skin_dot){
            btn_user_choice = skin_dot;
            userChoice = GameMain.Seed.NOUGHT;

            btn_comp_choice = skin_cross;
            oppPlayer = GameMain.Seed.CROSS;
        }
    }

    void printEntryScreen(){
        setContentView(R.layout.activity_game_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        final String[] gameLevels= {"EASY", "MEDIUM", "HARD"}; //} {String.valueOf(StartSolve.GameLEVEL.EASY), String.valueOf(StartSolve.GameLEVEL.MEDIUM), String.valueOf((StartSolve.GameLEVEL.HARD)};

        Spinner spin1=(Spinner) findViewById(R.id.spinnerLevel);
        ArrayAdapter adapter=new ArrayAdapter<>(GameMainActivity.this, R.layout.spinner_item_level, gameLevels);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_level);
        spin1.setAdapter(adapter);
        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(), "You have Chosen :" + gameLevels[position], Toast.LENGTH_LONG).show();

                switch (position) {
                    case 0:
                        gameLevel = StartSolve.GameLEVEL.EASY;
                        break;
                    case 1:
                        gameLevel = StartSolve.GameLEVEL.MEDIUM;
                        break;
                    case 2:
                        gameLevel = StartSolve.GameLEVEL.HARD;
                        break;
                    default:
                        Log.d(THIS_FILE, "Game Level error !!!!!!! ");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        final String[] userTypes= {"X", "O"};
        Spinner spinUserType=(Spinner) findViewById(R.id.spinnerUserType);
        ArrayAdapter adapterUserType=new ArrayAdapter<>(GameMainActivity.this, R.layout.spinner_item_level, userTypes);
        adapterUserType.setDropDownViewResource(R.layout.spinner_dropdown_item_level);
        spinUserType.setAdapter(adapterUserType);

        spinUserType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(), "You have Chosen :" + userTypes[position], Toast.LENGTH_LONG).show();

                switch(position){
                    case 0:
                        choiceType(skin_cross);
                        break;
                    case 1:
                        choiceType(skin_dot);
                        break;
                    default:
                        Log.d(THIS_FILE, "Cross Type error !!!!!!! ");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ImageButton btnNewGame = (ImageButton)findViewById(R.id.btnNewGame);
        // ImageButton btnOptions = (ImageButton)findViewById(R.id.btnOptions);

        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d(THIS_FILE, "New Game");
//                setContentView(R.layout.content_game_main);

                userWinNumber = 0;
                compWinNumber = 0;
                //
                //choiceType(skin_cross);
                //
                solve = new StartSolve(GameMainActivity.this, userChoice, gameLevel);
                new_game();
            }
        });
    }

    /////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        printEntryScreen();

       // btnOptions.setOnClickListener(new View.OnClickListener() {
       //     @Override
       //     public void onClick(View v) {
       //         Log.d(THIS_FILE, "Options");
       //         startActivity(new Intent(getApplication(), GameSettingsActivity.class));
       //     }
       // });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    /////////////////////////////
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            //do whatever you need for the hardware 'back' button
//            printEntryScreen();
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
    /////////////////////////////

    public void new_game() {

        // reset the game view. (this must be the first line in this function)
        setContentView(R.layout.content_main);

        ImageButton btnStartNewGame = (ImageButton)findViewById(R.id.btnStartNewGame);
        btnStartNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userWinNumber = 0;
                compWinNumber = 0;
                //
                //choiceType(skin_cross);
                //
                solve = new StartSolve(GameMainActivity.this, userChoice, gameLevel);
                new_game();
            }
        });

        TextView txtGameLevel = (TextView)findViewById(R.id.txtvwGameLevel);
        txtGameLevel.setText("Game Level: "+gameLevel);

        final ImageButton b3 = (ImageButton) findViewById(R.id.b3);
        final ImageButton b2 = (ImageButton) findViewById(R.id.b2);
        final ImageButton b1 = (ImageButton) findViewById(R.id.b1);

        final ImageButton b6 = (ImageButton) findViewById(R.id.b6);
        final ImageButton b5 = (ImageButton) findViewById(R.id.b5);
        final ImageButton b4 = (ImageButton) findViewById(R.id.b4);

        final ImageButton b9 = (ImageButton) findViewById(R.id.b9);
        final ImageButton b8 = (ImageButton) findViewById(R.id.b8);
        final ImageButton b7 = (ImageButton) findViewById(R.id.b7);

        // set the OnClickListeners.
        b1.setOnClickListener(button_listener);
        b2.setOnClickListener(button_listener);
        b3.setOnClickListener(button_listener);
        b4.setOnClickListener(button_listener);
        b5.setOnClickListener(button_listener);
        b6.setOnClickListener(button_listener);
        b7.setOnClickListener(button_listener);
        b8.setOnClickListener(button_listener);
        b9.setOnClickListener(button_listener);

        // Re-enable the Click-able property of buttons.
        b1.setClickable(true);
        b2.setClickable(true);
        b3.setClickable(true);
        b4.setClickable(true);
        b5.setClickable(true);
        b6.setClickable(true);
        b7.setClickable(true);
        b8.setClickable(true);
        b9.setClickable(true);

        TextView txtUserWinNumber = (TextView)findViewById(R.id.txtvwUserWinNumber);
        TextView txtCompWinNumber = (TextView)findViewById(R.id.txtvwCompWinNumber);

        txtUserWinNumber.setText("You: "+String.valueOf(userWinNumber));
        txtCompWinNumber.setText("Android: "+String.valueOf(compWinNumber));

        if(userChoice == GameMain.Seed.NOUGHT){
            solve.startMoveComp();
        }
    }

    /**
     * Shows the result of the game in a separate dialog.
     * */
    public boolean show_result(String message)		//function to select the game mode
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // reset the game environment.
                        solve = new StartSolve(GameMainActivity.this, userChoice, gameLevel);
                        new_game();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
        return true;
    }

    public void comp_play (int x) {
        int tmp_id = 0;

        switch(x){
            case 0:
                tmp_id = R.id.b1;
                break;
            case 1:
                tmp_id = R.id.b2;
                break;
            case 2:
                tmp_id = R.id.b3;
                break;
            case 3:
                tmp_id = R.id.b4;
                break;
            case 4:
                tmp_id = R.id.b5;
                break;
            case 5:
                tmp_id = R.id.b6;
                break;
            case 6:
                tmp_id = R.id.b7;
                break;
            case 7:
                tmp_id = R.id.b8;
                break;
            case 8:
                tmp_id = R.id.b9;
                break;
            default:
                Log.d(THIS_FILE, "===>>>>>> Buton YOK !!!!!! ");
        }

        final ImageButton ib = (ImageButton) findViewById (tmp_id);

        // draw the symbol on the button
        ib.setImageResource(btn_comp_choice);

        // make the button un-clickable.
        ib.setClickable(false);
    }

    @Override
    public void nextMoveforComputer(ArrayList<Integer> move) {
        //Log.d(THIS_FILE, "Next move: Computer !!!!!!!!!!!");
        //(x*3)+(y+1)
        comp_play((move.get(0) * 3) + (move.get(1)));
    }

    @Override
    public void gameResult(boolean result, GameMain.Seed player) {
        if(result){
            if(player == userChoice){
                //Log.d(THIS_FILE, "You Won :)");
                userWinNumber++;
                show_result("       You Won :)");
            }else if(player == oppPlayer){
                //Log.d(THIS_FILE, "You Lost!!!!!!!! ");
                compWinNumber++;
                show_result("   You Lost !!!");
            }
        }else{
            //Beraber
            show_result("   Draw !!!!");
        }
    }
}
