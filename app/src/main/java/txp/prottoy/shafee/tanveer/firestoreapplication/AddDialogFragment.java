package txp.prottoy.shafee.tanveer.firestoreapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class AddDialogFragment extends DialogFragment {
    private EditText nameEditText, numberEditText;
    private OnFragmentInteractionListener interactionListener;

    public AddDialogFragment() {
    }

    @NonNull
    public static AddDialogFragment newInstance() {
        return new AddDialogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initialize(inflater, container);
    }

    private View initialize(LayoutInflater inflater, ViewGroup container) {
        View addDialogView = inflater.inflate(R.layout.fragment_dialog_add, container, false);
        nameEditText = addDialogView.findViewById(R.id.fragment_dialog_name);
        numberEditText = addDialogView.findViewById(R.id.fragment_dialog_number);
        addDialogView.findViewById(R.id.fragment_dialog_button).setOnClickListener(addOcl);
        return addDialogView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnFragmentInteractionListener) {
            interactionListener = (OnFragmentInteractionListener) context;
        }
        else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        interactionListener = null;
    }

    private View.OnClickListener addOcl = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String[] values = new String[2];
            values[0] = nameEditText.getText().toString();
            values[1] = numberEditText.getText().toString();
            if(!TextUtils.isEmpty(values[0]) && !TextUtils.isEmpty(values[1])) {
                interactionListener.addData(values);
                AddDialogFragment.this.getDialog().dismiss();
            }
        }
    };

    public interface OnFragmentInteractionListener {
        void addData(String[] values);
    }
}
