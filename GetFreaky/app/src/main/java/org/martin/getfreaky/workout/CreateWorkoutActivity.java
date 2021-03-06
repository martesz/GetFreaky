package org.martin.getfreaky.workout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.martin.getfreaky.GlobalVariables;
import org.martin.getfreaky.LoginActivity;
import org.martin.getfreaky.R;
import org.martin.getfreaky.dataObjects.Exercise;
import org.martin.getfreaky.dataObjects.Workout;
import org.martin.getfreaky.network.GetFreakyService;
import org.martin.getfreaky.network.RetrofitClient;
import org.martin.getfreaky.network.WorkoutResponse;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by martin on 2016. 05. 08..
 */
public class CreateWorkoutActivity extends AppCompatActivity implements CreateExerciseFragment.ICreateExerciseFragment {
    public static final String KEY_WORKOUT = "KEY_WORKOUT";
    public static final String KEY_EDIT_ID = "KEY_EDIT_ID";
    public static final String KEY_EDIT_WORKOUT = "KEY_EDIT_WORKOUT";
    public static final String KEY_EDIT_INDEX = "KEY_EDIT_INDEX";

    public static final int CONTEXT_ACTION_DELETE_EXERCISE = 10;
    public static final int CONTEXT_ACTION_EDIT_EXERCISE = 11;

    private EditText etName;
    private Workout workoutToEdit;
    private String workoutToEditId;
    private int position;
    private boolean inEditMode = false;
    private Realm realm;

    private ListView listView;
    private ExerciseAdapter adapter;
    private FloatingActionButton fab;

    // Write changes to DB only when the user presses the save button
    List<Exercise> added;
    List<Exercise> deleted;

    RealmList<Exercise> exercisesInDB;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_workout);

        added = new ArrayList<>();
        deleted = new ArrayList<>();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_create);
        setSupportActionBar(toolbar);

        RealmConfiguration config = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(config);
        realm = Realm.getDefaultInstance();

        etName = (EditText) findViewById(R.id.etWorkoutName);

        if (getIntent().getExtras() != null &&
                getIntent().getExtras().containsKey(KEY_EDIT_ID)) {
            inEditMode = true;
            workoutToEditId = getIntent().getStringExtra(KEY_EDIT_ID);
            position = getIntent().getIntExtra(KEY_EDIT_INDEX, -1);
            realm.beginTransaction();
            workoutToEdit = realm.where(Workout.class).equalTo("id", workoutToEditId).findFirst();
            realm.commitTransaction();

            etName.setText(workoutToEdit.getName());
        } else {
            workoutToEdit = new Workout();
        }

        Button btnSave = (Button) findViewById(R.id.btnSaveWorkout);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inEditMode) {
                    updateWorkout();
                } else {
                    saveWorkout();
                }
            }
        });

        exercisesInDB = workoutToEdit.getExercises();
        List<Exercise> exercisesList = new ArrayList<Exercise>();
        exercisesList.addAll(exercisesInDB);
        listView = (ListView) findViewById(R.id.listExercise);
        adapter = new ExerciseAdapter(this, exercisesList);
        listView.setAdapter(adapter);
        listView.setEmptyView(findViewById(R.id.emptyExercise));
        registerForContextMenu(listView);

        fab =
                (FloatingActionButton) findViewById(R.id.addExerciseButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNewExerciseDialog();
            }
        });

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(CreateWorkoutActivity.this);
        userId = preferences.getString(LoginActivity.USER_ID_KEY, "DefaultUser");
    }

    private void showNewExerciseDialog() {
        CreateExerciseFragment createFragment = new CreateExerciseFragment();
        FragmentManager fm = getSupportFragmentManager();
        createFragment.show(fm, CreateExerciseFragment.TAG);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private void saveWorkout() {
        Intent intentResult = new Intent();

        realm.beginTransaction();
        workoutToEdit.setName(etName.getText().toString());
        workoutToEdit.getExercises().addAll(added);
        realm.copyToRealm(workoutToEdit);
        realm.commitTransaction();

        putWorkout(workoutToEdit);

        intentResult.putExtra(KEY_EDIT_ID, workoutToEdit.getId());
        setResult(RESULT_OK, intentResult);
        finish();
    }

    private void updateWorkout() {
        realm.beginTransaction();
        workoutToEdit.setName(etName.getText().toString());
        workoutToEdit.getExercises().addAll(added);
        workoutToEdit.getExercises().removeAll(deleted);
        realm.commitTransaction();

        putWorkout(workoutToEdit);

        Intent intentResult = new Intent();
        intentResult.putExtra(KEY_EDIT_INDEX, position);
        intentResult.putExtra(KEY_EDIT_ID, workoutToEdit.getId());
        setResult(RESULT_OK, intentResult);
        finish();
    }

    @Override
    public void onExerciseCreated(Exercise exercise) {
        adapter.addExercise(exercise);
        adapter.notifyDataSetChanged();
        added.add(exercise);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Menu");
        menu.add(0, CONTEXT_ACTION_DELETE_EXERCISE, 0, "Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == CONTEXT_ACTION_DELETE_EXERCISE) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            Exercise selectedExercise = (Exercise) adapter.getItem(info.position);

            if (added.contains(selectedExercise)) {
                added.remove(selectedExercise);
            } else {
                // We only want to delete it when the user presses the save button
                deleted.add(selectedExercise);
            }

            adapter.removeItem(info.position);
            adapter.notifyDataSetChanged();
        } else {
            return false;
        }
        return true;
    }

    private void putWorkout(Workout workout) {
        Workout copy = new Workout(workout);
        RetrofitClient client = new RetrofitClient((GlobalVariables) this.getApplication());
        GetFreakyService service = client.createService();
        Call<WorkoutResponse> call = service.putWorkout(copy, userId);
        call.enqueue(new Callback<WorkoutResponse>() {
            @Override
            public void onResponse(Call<WorkoutResponse> call, Response<WorkoutResponse> response) {
                if (response.body() != null) {
                    Toast.makeText(CreateWorkoutActivity.this, response.body().getMessage().toString(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(CreateWorkoutActivity.this, "Could not upload workout", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<WorkoutResponse> call, Throwable t) {
                Toast.makeText(CreateWorkoutActivity.this, "Could not upload workout", Toast.LENGTH_LONG).show();
            }
        });
    }
}
