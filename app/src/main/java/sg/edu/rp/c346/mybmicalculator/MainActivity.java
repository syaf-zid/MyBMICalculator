package sg.edu.rp.c346.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etWeight, etHeight;
    Button btnCalc, btnReset;
    TextView tvDate, tvBMI, tvOutcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.editTextWeight);
        etHeight = findViewById(R.id.editTextHeight);
        btnCalc = findViewById(R.id.buttonCalculate);
        btnReset = findViewById(R.id.buttonReset);
        tvDate = findViewById(R.id.textViewDate);
        tvBMI = findViewById(R.id.textViewBMI);
        tvOutcome = findViewById(R.id.textViewOutcome);

        etWeight.requestFocus();

        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float weight = Float.parseFloat(etWeight.getText().toString());
                float height = Float.parseFloat(etHeight.getText().toString());
                float bmi = weight / (height * height);
                String message = "";

                String result = String.format("%.3f", bmi);

                Calendar now = Calendar.getInstance();
                String dateTime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);

                if(bmi >= 30) {
                    message = "You are obese";
                } else if(bmi >= 25 && bmi <= 29.9) {
                    message = "You are overweight";
                } else if(bmi >= 18.5 && bmi <= 24.9) {
                    message = "Your BMI is normal";
                } else if(bmi < 18.5) {
                    message = "You are underweight";
                }

                tvDate.setText(dateTime);
                tvBMI.setText(result);
                tvOutcome.setText(message);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etHeight.setText("");
                etWeight.setText("");
                tvDate.setText("");
                tvBMI.setText("");
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        String bmi = tvBMI.getText().toString();
        String date = tvDate.getText().toString();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEdit = prefs.edit();

        prefEdit.putString("date", date);
        prefEdit.putString("bmi", bmi);


        prefEdit.commit();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        String defaultDate = prefs.getString("date", "");
        String defaultBMI = prefs.getString("bmi", "");

        tvDate.setText(defaultDate);
        tvBMI.setText("" + defaultBMI);
    }
}
