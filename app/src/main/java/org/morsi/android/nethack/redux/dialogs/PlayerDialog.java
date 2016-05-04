package org.morsi.android.nethack.redux.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import org.morsi.android.nethack.redux.GameTrackerActivity;
import org.morsi.android.nethack.redux.R;
import org.morsi.android.nethack.redux.trackers.PlayerTracker;

public class PlayerDialog {
    Activity activity;

    GameTrackerActivity game_tracker(){
        return (GameTrackerActivity) activity;
    }

    PlayerTracker player_tracker(){
        return game_tracker().player_tracker;
    }

    private int strength(){
        return player_tracker().strength;
    }

    private void strength(int s){
        player_tracker().strength = s;
    }

    private int dexterity(){
        return player_tracker().dexterity;
    }

    private void dexterity(int d){
        player_tracker().dexterity = d;
    }

    private int constitution(){
        return player_tracker().constitution;
    }

    private void constitution(int c){
        player_tracker().constitution = c;
    }

    private int intelligence(){
        return player_tracker().intelligence;
    }

    private void intelligence(int i){
        player_tracker().intelligence = i;
    }

    private int wisdom(){
        return player_tracker().wisdom;
    }

    private void wisdom(int w){
        player_tracker().wisdom = w;
    }

    private int charisma(){
        return player_tracker().charisma;
    }

    private void charisma(int c){
        player_tracker().charisma = c;
    }

    Dialog dialog;

    private Button closeButton(){
        return (Button) dialog.findViewById(R.id.player_close);
    }

    private EditText strengthInput(){
        return (EditText) dialog.findViewById(R.id.strengthInput);
    }

    private String strengthInputValueString(){
        return strengthInput().getText().toString();
    }

    private int strengthInputValue(){
        return Integer.parseInt(strengthInputValueString());
    }

    private EditText dexterityInput(){
        return (EditText) dialog.findViewById(R.id.dexterityInput);
    }

    private String dexterityInputValueString(){
        return dexterityInput().getText().toString();
    }

    private int dexterityInputValue(){
        return Integer.parseInt(dexterityInputValueString());
    }

    private EditText constitutionInput(){
        return (EditText) dialog.findViewById(R.id.constitutionInput);
    }

    private String constitutionInputValueString(){
        return constitutionInput().getText().toString();
    }

    private int constitutionInputValue(){
        return Integer.parseInt(constitutionInputValueString());
    }

    private EditText intelligenceInput(){
        return (EditText) dialog.findViewById(R.id.intelligenceInput);
    }

    private String intelligenceInputValueString(){
        return intelligenceInput().getText().toString();
    }

    private int intelligenceInputValue(){
        return Integer.parseInt(intelligenceInputValueString());
    }

    private EditText wisdomInput(){
        return (EditText) dialog.findViewById(R.id.wisdomInput);
    }

    private String wisdonInputValueString(){
        return wisdomInput().getText().toString();
    }

    private int wisdomInputValue(){
        return Integer.parseInt(wisdonInputValueString());
    }

    private EditText charismaInput(){
        return (EditText) dialog.findViewById(R.id.charismaInput);
    }

    private String charismaInputValueString(){
        return charismaInput().getText().toString();
    }

    private int charismaInputValue(){
        return Integer.parseInt(charismaInputValueString());
    }

    ///

    private PlayerDialog(Activity activity){
        this.activity = activity;
        dialog = new Dialog(activity);
        dialog.setContentView(R.layout.player_dialog);
        dialog.setTitle("Player Attributes");

        input_listener = new InputChangedListener();

        // wire up inputs
        closeButton().setOnClickListener(new DialogListener(dialog));
        strengthInput().addTextChangedListener(input_listener);
        dexterityInput().addTextChangedListener(input_listener);
        constitutionInput().addTextChangedListener(input_listener);
        intelligenceInput().addTextChangedListener(input_listener);
        wisdomInput().addTextChangedListener(input_listener);
        charismaInput().addTextChangedListener(input_listener);

        // restore prefs
        restoreUIPrefs();
    }


    public static Dialog create(Activity activity) {
        return new PlayerDialog(activity).dialog;
    }

    ///

    private void restoreUIPrefs(){
        strengthInput().setText(Integer.toString(strength()));
        dexterityInput().setText(Integer.toString(dexterity()));
        constitutionInput().setText(Integer.toString(constitution()));
        intelligenceInput().setText(Integer.toString(intelligence()));
        wisdomInput().setText(Integer.toString(wisdom()));
        charismaInput().setText(Integer.toString(charisma()));
    }

    ///


    private void updateFields(){
        strength(strengthInputValue());
        constitution(constitutionInputValue());
        intelligence(intelligenceInputValue());
        wisdom(wisdomInputValue());
        charisma(charismaInputValue());
    }

    InputChangedListener input_listener;

    class InputChangedListener implements TextWatcher {
        public InputChangedListener() {}

        public void afterTextChanged(Editable s) {
            updateFields();
            player_tracker().storeFields();
            player_tracker().updateOutput();
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    }
}