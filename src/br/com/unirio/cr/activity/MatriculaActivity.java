package br.com.unirio.cr.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import br.com.unirio.cr.Constants;
import br.com.unirio.cr.R;
import br.com.unirio.cr.utils.StringUtils;


public class MatriculaActivity extends Activity {

    private EditText mEditTextMatricula;
    private Button mButtonOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matricula);

        mEditTextMatricula = (EditText) findViewById(R.id.editTextMatricula);
        mButtonOk = (Button) findViewById(R.id.buttonOk);
        mButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Editable editable = mEditTextMatricula.getText();
                if (editable != null && StringUtils.hasText(editable.toString())) {
                    String matricula = editable.toString();
                    Intent intent = new Intent(MatriculaActivity.this, CrActivity.class);
                    intent.putExtra(Constants.MATRICULA_KEY, matricula);
                    startActivity(intent);
                } else {
                    Toast.makeText(MatriculaActivity.this, "Digite a matricula", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
