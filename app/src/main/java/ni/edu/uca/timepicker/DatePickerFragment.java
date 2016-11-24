package ni.edu.uca.timepicker;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {

    private DatePickerDialog.OnDateSetListener onDateSetListener;


    public DatePickerFragment() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        final DatePickerDialog pickerDialog = new DatePickerDialog(getActivity(), null, year, month, day);
        pickerDialog.setCancelable(true);
        pickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString( android.R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        pickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, getString( android.R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                getOnDateSetListener().onDateSet(pickerDialog.getDatePicker(), pickerDialog.getDatePicker().getYear(), pickerDialog.getDatePicker().getMonth(), pickerDialog.getDatePicker().getDayOfMonth());
            }
        });
        return pickerDialog;

    }

    public DatePickerDialog.OnDateSetListener getOnDateSetListener() {
        return onDateSetListener;
    }

    public void setOnDateSetListener(DatePickerDialog.OnDateSetListener onDateSetListener) {
        this.onDateSetListener = onDateSetListener;
    }

}
