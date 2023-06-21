package site.angker.bmicalculator;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText edtHeight,edtWeight;
    TextView txtResult;
    private final static String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupView();
        setupListener();
    }

    private void setupListener() {
        initTextListener(edtHeight);
        initTextListener(edtWeight);
    }

    private void setupView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        edtHeight = findViewById(R.id.txt_height);
        edtWeight = findViewById(R.id.txt_weight);
        txtResult = findViewById(R.id.txt_result);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
    }

    private void initTextListener(EditText editText){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                calculateBmi();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void calculateBmi() {
        if (isValidInput(edtHeight) && isValidInput(edtWeight)) {
            double bmi = calculateBmiAndCastIfNeeded(getTextAsDouble(edtHeight), getTextAsDouble(edtWeight));
            txtResult.setText("Berat badan "+ bmi);
        } else {
            txtResult.setText("");
        }
    }

    private double calculateBmiAndCastIfNeeded(double height, double weight) {
        height = isMetric() ? height : height / 39.37008;
        weight = isMetric() ? weight : weight / 2.204623;
        return calculateBmi(height, weight);
    }
    public static double calculateBmi(double height, double weight) {
        return Math.round(weight / Math.pow(height, 2) * 10d) / 10d;
    }
    private boolean isMetric() {
        boolean defaultToMetric = true;
        return defaultToMetric;
    }

    private boolean isValidInput(EditText editText) {
        return getTextAsDouble(editText) > 0;
    }

    private double getTextAsDouble(EditText editText) {
        String input = editText.getText().toString().replace(',', '.');
        try {
            return Double.valueOf(input);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}