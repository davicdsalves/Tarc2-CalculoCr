package br.com.unirio.cr.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.unirio.cr.Broadcaster;
import br.com.unirio.cr.R;
import br.com.unirio.cr.data.facade.MateriaFacade;
import br.com.unirio.cr.data.facade.MateriaPeriodoFacade;
import br.com.unirio.cr.data.facade.PeriodoFacade;
import br.com.unirio.cr.data.model.Materia;
import br.com.unirio.cr.data.model.Periodo;

public class DialogUtil {

    public static Dialog createDialog(final Activity activity, final Long matricula, final Integer numeroPeriodo)
    {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_materia);

        Button cancel = (Button) dialog.findViewById(R.id.buttonCancel);
        Button save = (Button) dialog.findViewById(R.id.buttonSave);

        EditText mEditTextNome = (EditText) dialog.findViewById(R.id.editTextMateriaNome);
        EditText mEditTextCreditos = (EditText) dialog.findViewById(R.id.editTextMateriaCreditos);
        EditText mEditTextNota = (EditText) dialog.findViewById(R.id.editTextMateriaNota);

        final Editable eNome = mEditTextNome.getText();
        final Editable eCreditos = mEditTextCreditos.getText();
        final Editable eNota = mEditTextNota.getText();

        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (eNome != null && eCreditos != null && eNota != null) {
                    String nome = eNome.toString();
                    String creditos = eCreditos.toString();
                    String nota = eNota.toString();
                    if (StringUtils.hasText(nome) && StringUtils.hasText(creditos) && StringUtils.hasText(nota)) {
                        Long lCreditos = Long.valueOf(creditos);
                        Double dNota = Double.valueOf(nota);
                        insertMateria(activity, matricula, nome, lCreditos, dNota, numeroPeriodo);
                        dialog.dismiss();
                        Broadcaster.updateCr(activity);
                        return;
                    }
                }
                Toast.makeText(activity, "Informações incompletas", Toast.LENGTH_SHORT).show();
            }
        });
        return dialog;
    }

    /**
     *
     * @param context
     * @param matricula
     * @param nome
     * @param creditos
     * @param nota
     * @param numeroPeriodo
     */
    private static void insertMateria(Context context, Long matricula, String nome, Long creditos, Double nota, Integer numeroPeriodo) {
        MateriaFacade facade = new MateriaFacade();
        Materia materia = facade.insert(context, creditos, nome.toUpperCase());

        PeriodoFacade periodoFacade = new PeriodoFacade();
        Periodo periodo = periodoFacade.getByMatriculaAndNumero(context, matricula, numeroPeriodo);

        MateriaPeriodoFacade materiaPeriodoFacade = new MateriaPeriodoFacade();
        materiaPeriodoFacade.insert(context, materia.getId(), periodo.getId(), nota);
    }
}
