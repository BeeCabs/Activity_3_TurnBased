package mcm.edu.ph.act_3_ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Color;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    int turnNumber = 1;
    hero lillian = new hero();
    ghost boo = new ghost();
    //setup ui components
    TextView txtHP, txtDMG, txtghostHP, txtghostType;
    Button btnAttack;
    Button btnReset;

    // initialization
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ResetGame();
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view){
        Battle(view);
    }

    private void Battle(View view) {
        // get ui elements
        TextView txtmessage;
        txtmessage = findViewById(R.id.txtmessage);

        // generate new characters + randomizer
        Random random = new Random();

        // get ui elements
        TextView txtHP, txtDMG, txtghostHP;
        Button btnAttack;

        txtHP = findViewById(R.id.hpDisplay);
        txtDMG = findViewById(R.id.dmgDisplay);
        txtghostHP = findViewById(R.id.ghostHp);

        btnAttack = findViewById(R.id.btnAtk);

        // calculate damage using randomizer
        int holyspritz = random.nextInt(lillian.maxDMG - lillian.minDMG) + lillian.minDMG;
        int ghostattack = random.nextInt(boo.ghostMaxDMG - boo.ghostMinDMG) + boo.ghostMinDMG;

        //get character ui image
        ImageView lilliasmile = findViewById(R.id.lillia_smile);
        ImageView lilliasad = findViewById(R.id.lilliasad);

        //change button color
        TurnColors(turnNumber, btnAttack);

        //switch based on button id
        switch (view.getId()){
            case R.id.btnAtk:
                //attack logic
                // if odd turns
                if (turnNumber%2 ==1) {
                    // update ghost hp by deducting character damage
                    boo.ghostHP = boo.ghostHP - holyspritz;
                    // set values to ui
                    txtmessage.setText("Mister Boo dealt " + holyspritz + " damage!");
                    btnAttack.setText("Ghost's turn");
                    // update image ui
                    lilliasmile.setVisibility(View.VISIBLE);
                    lilliasad.setVisibility(View.INVISIBLE);
                    // next turn
                    turnNumber++;
                }
                else{
                    //if even number, deduct character hp with ghost damage
                    lillian.HP = lillian.HP - ghostattack;
                    //update ui
                    txtmessage.setText("Lilllia dealt " + ghostattack + " damage!");
                    btnAttack.setText("Lillia's turn");
                    lilliasmile.setVisibility(View.INVISIBLE);
                    lilliasad.setVisibility(View.VISIBLE);
                    //next turn
                    turnNumber++;
                }

                //update hp meters
                txtHP.setText(String.valueOf(lillian.HP));
                txtghostHP.setText(String.valueOf(boo.ghostHP));
                txtDMG.setText((lillian.minDMG)+ " ~ "+ (lillian.maxDMG));

                GameOver(txtmessage);
                break;
            case R.id.btnReset:
                ResetGame();
                break;
        }
    }

    private void GameOver(TextView txtmessage) {
        //game over state
        if (lillian.HP <= 0) {
            txtmessage.setText("The ghost was victorious!");
            txtHP.setText("0");
            btnAttack.setText("GAME OVER");
            ResetMode();
        }
        else if (boo.ghostHP <= 0){
            txtmessage.setText("The hero was victorious!");
            txtghostHP.setText("0");
            btnAttack.setText("VICTORY!");
            ResetMode();
        }
    }

    private void ResetGame() {
        // hero init
        lillian.minDMG =50;
        lillian.maxDMG =80;
        lillian.HP = 800;

        //ghost init
        boo.ghostMinDMG = 0;
        boo.ghostMaxDMG = 10;
        boo.ghostHP = 1000;

        //get components from view
        txtHP = findViewById(R.id.hpDisplay);
        txtDMG = findViewById(R.id.dmgDisplay);
        txtghostHP = findViewById(R.id.ghostHp);
        txtghostType = findViewById(R.id.ghostType);

        btnAttack = findViewById(R.id.btnAtk);
        btnReset = findViewById(R.id.btnReset);

        //assign values to UI
        txtHP.setText(String.valueOf(lillian.HP));
        txtDMG.setText((lillian.minDMG)+ " ~ "+ (lillian.maxDMG));
        txtghostHP.setText(String.valueOf(boo.ghostHP));
        txtghostType.setText("BOO");
        btnAttack.setText("START");

        AttackMode();

        // add button listener
        btnAttack.setOnClickListener(this);
        btnReset.setOnClickListener(this);

        // get ui image
        ImageView lilliasmile = findViewById(R.id.lillia_smile);
        ImageView lilliasad = findViewById(R.id.lilliasad);

        // show character
        lilliasmile.setVisibility(View.VISIBLE);
        lilliasad.setVisibility(View.INVISIBLE);
    }

    private void AttackMode() {
        //change button visiblities
        btnAttack.setVisibility(View.VISIBLE);
        btnAttack.setEnabled(true);
        btnReset.setVisibility(View.INVISIBLE);
        btnReset.setEnabled(false);
    }

    private void ResetMode() {
        //change button visiblities
        btnAttack.setVisibility(View.INVISIBLE);
        btnAttack.setEnabled(false);
        btnReset.setText("RESTART");
        btnReset.setVisibility(View.VISIBLE);
        btnReset.setEnabled(true);
    }

    private void TurnColors(int turn, Button button) {
        //enemy turn
        if (turn%2 == 1){
            button.setBackgroundColor(Color.RED);

        }else {
            button.setBackgroundColor(Color.WHITE);
        }
    }
}