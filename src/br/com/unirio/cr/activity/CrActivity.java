package br.com.unirio.cr.activity;

import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import br.com.unirio.cr.Constants;
import br.com.unirio.cr.R;
import br.com.unirio.cr.data.facade.EstudanteFacade;
import br.com.unirio.cr.data.facade.PeriodoFacade;
import br.com.unirio.cr.data.model.Estudante;
import br.com.unirio.cr.data.model.Periodo;
import br.com.unirio.cr.utils.StringUtils;


public class CrActivity extends Activity {

    private EditText mEditTextCourseCrTotal;

    private TextView mTextViewCargaCumprida;
    private TextView mTextViewCrAcumulado;

    private Button mButtonSave;
    private Button mButtonPrevious;
    private Button mButtonAddGrades;

    private Long matricula;
    private Estudante estudante;

    private GetDataTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cr);

        mEditTextCourseCrTotal = (EditText) findViewById(R.id.editTextCourseCrTotal);

        mTextViewCargaCumprida = (TextView) findViewById(R.id.textViewCargaCumprida);
        mTextViewCrAcumulado = (TextView) findViewById(R.id.textViewCrAcumulado);

        mButtonSave = (Button) findViewById(R.id.buttonSave);
        mButtonPrevious = (Button) findViewById(R.id.buttonPrevious);
        mButtonAddGrades = (Button) findViewById(R.id.buttonAddGrades);

        matricula = Long.valueOf(getIntent().getStringExtra(Constants.MATRICULA_KEY));

        estudante = addStudent();

        mButtonSave.setOnClickListener(saveListener);
        mButtonPrevious.setOnClickListener(backListener);
        mButtonAddGrades.setOnClickListener(addGradesListener);

        task = new GetDataTask();
        task.execute();

    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(Constants.BROADCAST_UPDATE_CRA);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (task != null) {
            task.cancel(true);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    private View.OnClickListener saveListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            EstudanteFacade estudanteFacade = new EstudanteFacade();
            if (mEditTextCourseCrTotal.getText() != null && StringUtils.hasText(mEditTextCourseCrTotal.getText().toString())) {
                Long cargaDoCurso = Long.valueOf(mEditTextCourseCrTotal.getText().toString());
                estudante.setCargaDoCurso(cargaDoCurso);
                estudanteFacade.update(CrActivity.this, matricula, estudante.getCargaAcumulada(), estudante.getCargaCumprida(), estudante.getCargaDoCurso());
                Toast.makeText(CrActivity.this, "Carga do curso salva com sucesso.", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private View.OnClickListener backListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    private View.OnClickListener addGradesListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(CrActivity.this, AddGradesActivity.class);
            intent.putExtra(Constants.MATRICULA_KEY, matricula);
            intent.putExtra(Constants.PERIODO_KEY, Constants.PERIODO_UM);
            intent.putExtra(Constants.CR_KEY, 0.0);
            intent.putExtra(Constants.CREDITOS_KEY, 0L);
            startActivity(intent);
        }
    };

    /**
     *
     * @param estudante
     */
    private void fillCredits(Estudante estudante) {
        mEditTextCourseCrTotal.setText(String.valueOf(estudante.getCargaDoCurso()));
        mTextViewCargaCumprida.setText(String.valueOf(estudante.getCargaCumprida()));
        mTextViewCrAcumulado.setText(String.valueOf(estudante.getCargaAcumulada()));
    }

    /**
     *
     * @return
     */
    private Estudante addStudent() {
        EstudanteFacade estudanteFacade = new EstudanteFacade();
        return estudanteFacade.insert(this, matricula);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            task = new GetDataTask();
            task.execute();
        }
    };

    private final class GetDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            PeriodoFacade periodoFacade = new PeriodoFacade();
            List<Periodo> periodos = periodoFacade.getByMatricula(CrActivity.this, matricula);
            estudante.setCargaCumprida(0L);

            int numPeriodos = periodos.size();
            Double crAcumulado = 0.0;

            for (Periodo periodo : periodos) {
                estudante.setCargaCumprida((estudante.getCargaCumprida() + periodo.getCreditosPeriodo()));
                Double crPeriodo = periodo.getNota();
                crAcumulado += crPeriodo;
            }
            estudante.setCargaAcumulada( (numPeriodos == 0 ? 0.0 : crAcumulado / numPeriodos) );
            EstudanteFacade estudanteFacade = new EstudanteFacade();
            estudanteFacade.update(CrActivity.this, matricula, estudante.getCargaAcumulada(), estudante.getCargaCumprida(), estudante.getCargaDoCurso());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            fillCredits(estudante);
            super.onPostExecute(aVoid);
        }
    }

}
