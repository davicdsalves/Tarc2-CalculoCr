package br.com.unirio.cr.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import br.com.unirio.cr.Broadcaster;
import br.com.unirio.cr.Constants;
import br.com.unirio.cr.R;
import br.com.unirio.cr.adapter.MateriasAdapter;
import br.com.unirio.cr.data.facade.MateriaPeriodoFacade;
import br.com.unirio.cr.data.facade.PeriodoFacade;
import br.com.unirio.cr.data.model.Materia;
import br.com.unirio.cr.data.model.MateriaPeriodo;
import br.com.unirio.cr.data.model.Periodo;
import br.com.unirio.cr.model.MateriasRow;
import br.com.unirio.cr.utils.CalculationUtil;
import br.com.unirio.cr.utils.DialogUtil;


public class AddGradesActivity extends Activity {

    private TextView mTextViewMatricula;
    private TextView mTextViewPeriodoCr;

    private ListView mListViewMaterias;
    private MateriasAdapter adapter;

    private Button mButtonPrevious;
    private Button mButtonNext;
    private Button mButtonAddClass;

    private Long matricula;
    private Integer numeroPeriodo;
    private Double nota;
    private Long creditosPeriodo;

    private Periodo periodo;

    private GetMateriaInfoTask task;

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grades);

        mTextViewMatricula = (TextView) findViewById(R.id.textViewMatricula);
        mTextViewPeriodoCr = (TextView) findViewById(R.id.textViewSemester);
        mListViewMaterias = (ListView) findViewById(R.id.listViewMaterias);
        mButtonPrevious = (Button) findViewById(R.id.buttonPrevious);
        mButtonNext = (Button) findViewById(R.id.buttonNext);
        mButtonAddClass = (Button) findViewById(R.id.buttonAddClass);

        matricula = getIntent().getLongExtra(Constants.MATRICULA_KEY, 0L);
        numeroPeriodo = getIntent().getIntExtra(Constants.PERIODO_KEY, 0);
        nota = getIntent().getDoubleExtra(Constants.CR_KEY, 0.0);
        creditosPeriodo = getIntent().getLongExtra(Constants.CREDITOS_KEY, 0L);

        periodo = insertPeriodo();
        addContentInfo(periodo);

        mButtonPrevious.setOnClickListener(backListener);
        mButtonAddClass.setOnClickListener(addClassListener);
        mButtonNext.setOnClickListener(addGradesListener);

        this.task = new GetMateriaInfoTask();
        task.execute();

    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(Constants.BROADCAST_UPDATE_CR);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (task != null) {
            task.cancel(true);
        }
        if (dialog != null) {
            dialog.dismiss();
        }
        unregisterReceiver(broadcastReceiver);
    }

    private View.OnClickListener backListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    private View.OnClickListener addClassListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dialog = DialogUtil.createDialog(AddGradesActivity.this, matricula, numeroPeriodo);
            dialog.show();
        }
    };

    private View.OnClickListener addGradesListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(AddGradesActivity.this, AddGradesActivity.class);
            intent.putExtra(Constants.MATRICULA_KEY, matricula);
            intent.putExtra(Constants.PERIODO_KEY, (numeroPeriodo + 1));
            intent.putExtra(Constants.CR_KEY, 0.0);
            intent.putExtra(Constants.CREDITOS_KEY, 0L);
            startActivity(intent);
        }
    };

    /**
     *
     * @param periodo
     */
    private void addContentInfo(Periodo periodo) {
        mTextViewMatricula.setText(String.format(Constants.MATRICULA_MASK, periodo.getMatricula()));
        mTextViewPeriodoCr.setText(String.format(Constants.PERIODO_CR_MASK, periodo.getNumero(), periodo.getNota()));
    }

    /**
     *
     * @return
     */
    private Periodo insertPeriodo() {
        PeriodoFacade facade = new PeriodoFacade();
        //List<Periodo> periodos = facade.getByMatricula(this, matricula);
        Periodo _periodo = facade.insert(this, matricula, numeroPeriodo, nota, creditosPeriodo);
        //periodos.add(_periodo);
        return _periodo;
    }

    /**
     * Classe para obter as info da materia
     */
    private class GetMateriaInfoTask extends AsyncTask<Void, Void, List<MateriasRow>> {

        @Override
        protected List<MateriasRow> doInBackground(Void... voids) {
            List<MateriasRow> rows = new ArrayList<MateriasRow>();

            MateriaPeriodoFacade materiaPeriodoFacade = new MateriaPeriodoFacade();
            List<MateriaPeriodo> mPs = materiaPeriodoFacade.getByPeriodId(AddGradesActivity.this, periodo.getId());

            List<Long> creditos = new ArrayList<Long>();
            List<Double> notas = new ArrayList<Double>();

            for (MateriaPeriodo mp : mPs) {
                Materia materia = mp.getMateria();

                MateriasRow row = new MateriasRow();
                row.setNome(materia.getNome());
                row.setCreditos(materia.getCreditos());
                row.setNota(mp.getNota());
                rows.add(row);

                creditos.add(materia.getCreditos());
                notas.add(mp.getNota());
            }

            Double notaPeriodo = CalculationUtil.calculateCr(creditos.toArray(new Long[creditos.size()]), notas.toArray(new Double[notas.size()]));
            Long creditosPeriodo = CalculationUtil.calculateCreditos(creditos.toArray(new Long[creditos.size()]));
            periodo.setNota(notaPeriodo);
            periodo.setCreditosPeriodo(creditosPeriodo);

            PeriodoFacade periodoFacade = new PeriodoFacade();
            periodoFacade.update(AddGradesActivity.this, matricula, numeroPeriodo, notaPeriodo, creditosPeriodo);

            return rows;
        }

        @Override
        protected void onPostExecute(List<MateriasRow> materiasRows) {
            addContentInfo(periodo);
            adapter = new MateriasAdapter(AddGradesActivity.this, materiasRows);
            mListViewMaterias.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            Broadcaster.updateCra(AddGradesActivity.this);
        }
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            task = new GetMateriaInfoTask();
            task.execute();
        }
    };

}
