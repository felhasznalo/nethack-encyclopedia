package org.morsi.android.nethack.redux;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.morsi.android.nethack.redux.trackers.ItemTracker;
import org.morsi.android.nethack.redux.trackers.LevelTracker;
import org.morsi.android.nethack.redux.trackers.NoteTracker;
import org.morsi.android.nethack.redux.trackers.PlayerTracker;
import org.morsi.android.nethack.redux.util.AndroidMenu;
import org.morsi.android.nethack.redux.util.UI;

public class GameTrackerActivity extends Activity {

    // actual trackers
    public PlayerTracker player_tracker;
    public LevelTracker level_tracker;
    public ItemTracker item_tracker;
    public NoteTracker note_tracker;

    // Override menu / about_dialog dialog handlers
    @Override
    public boolean onSearchRequested() {
        return AndroidMenu.onSearchRequested(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return AndroidMenu.onCreateOptionsMenu(this, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return AndroidMenu.onOptionsItemSelected(this, item);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        return AndroidMenu.onCreateDialog(this, id);
    }

    private Spinner spinner() {
        return (Spinner) findViewById(R.id.game_tracker_spinner);
    }

    private ArrayAdapter<CharSequence> spinnerAdapter() {
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(this, R.array.quick_stats_array,
                        android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_tracker);

        spinner().setAdapter(spinnerAdapter());
        handleSpinnerSelection();

        player_tracker = new PlayerTracker(this);
        level_tracker = new LevelTracker(this);
        item_tracker = new ItemTracker(this);
        note_tracker = new NoteTracker(this);

        player_tracker.onCreate();
        level_tracker.onCreate();
        item_tracker.onCreate();
        note_tracker.onCreate();
    }

    public void onClickNewTrackerButton(View target) {
        if (playerSelected(this))
            player_tracker.newTrackerPopup();
        else if (levelSelected(this))
            level_tracker.newTrackerPopup();
        else if (itemSelected(this))
            item_tracker.newTrackerPopup();
        else if (notesSelected(this))
            note_tracker.newTrackerPopup();
    }

    public void onResetNewTrackerButton(View target) {
        confirmTrackerReset();
    }

    ///

    String selected;

    private void setSelected(AdapterView<?> parent, int pos) {
        selected = parent.getItemAtPosition(pos).toString();
    }

    private boolean playerSelected(Context context) {
        return selected.equals(context.getString(R.string.player_tracker));
    }

    private boolean levelSelected(Context context) {
        return selected.equals(context.getString(R.string.level_tracker));
    }

    private boolean itemSelected(Context context) {
        return selected.equals(context.getString(R.string.item_tracker));
    }

    private boolean notesSelected(Context context) {
        return selected.equals(context.getString(R.string.notes_tracker));
    }

    private void handleSpinnerSelection() {
        final Activity activity = this;
        spinner().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                setSelected(parent, pos);
                boolean player_selected = playerSelected(view.getContext());
                boolean level_selected = playerSelected(view.getContext());
                boolean item_selected = playerSelected(view.getContext());
                boolean notes_selected = playerSelected(view.getContext());

                if (player_selected) {
                    UI.showView(activity, R.id.player_tracker);
                    UI.hideView(activity, R.id.level_tracker);
                    UI.hideView(activity, R.id.item_tracker);
                    UI.hideView(activity, R.id.notes_tracker);
                } else if (level_selected) {
                    UI.showView(activity, R.id.level_tracker);
                    UI.hideView(activity, R.id.player_tracker);
                    UI.hideView(activity, R.id.item_tracker);
                    UI.hideView(activity, R.id.notes_tracker);
                } else if (item_selected) {
                    UI.showView(activity, R.id.item_tracker);
                    UI.hideView(activity, R.id.player_tracker);
                    UI.hideView(activity, R.id.level_tracker);
                    UI.hideView(activity, R.id.notes_tracker);
                } else if (notes_selected) {
                    UI.showView(activity, R.id.notes_tracker);
                    UI.hideView(activity, R.id.player_tracker);
                    UI.hideView(activity, R.id.level_tracker);
                    UI.hideView(activity, R.id.item_tracker);
                } else {
                    UI.hideView(activity, R.id.notes_tracker);
                    UI.hideView(activity, R.id.player_tracker);
                    UI.hideView(activity, R.id.level_tracker);
                    UI.hideView(activity, R.id.item_tracker);
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    ///

    private void confirmTrackerReset() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage(R.string.reset_tracker)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        resetTracker();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create();
        dialog.show();
    }

    private void resetTracker() {
        player_tracker.reset();
        level_tracker.reset();
        item_tracker.reset();
        note_tracker.reset();
    }
}