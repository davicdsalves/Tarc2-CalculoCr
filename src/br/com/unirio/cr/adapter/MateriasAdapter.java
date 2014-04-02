package br.com.unirio.cr.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.unirio.cr.Constants;
import br.com.unirio.cr.R;
import br.com.unirio.cr.model.MateriasRow;

public class MateriasAdapter extends ListAdapter<MateriasRow> {

    public MateriasAdapter(Activity activity, List<MateriasRow> list) {
        super(activity, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MateriasRow row = list.get(position);
        View view = null;
        if (convertView == null)
        {
            LayoutInflater inflater = activity.getLayoutInflater();
            view = inflater.inflate(R.layout.materias_row, null);

            final ViewHolder viewHolder = new ViewHolder();

            viewHolder.infoMateria = (TextView) view.findViewById(R.id.textViewMateriaInfo);

            view.setTag(viewHolder);
        }
        else
        {
            view = convertView;
        }

        ViewHolder holder = (ViewHolder) view.getTag();
        holder.infoMateria.setText(parseMateriaInfo(row));

        return view;
    }

    private String parseMateriaInfo(MateriasRow row) {
        return String.format(Constants.MATERIA_MASK, row.getNome(), row.getCreditos(), row.getNota());
    }

    private static class ViewHolder {
        protected TextView infoMateria;
    }

}
