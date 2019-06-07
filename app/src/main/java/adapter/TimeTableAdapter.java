package adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.manoranjank.studentdiary.HomeActivity;
import com.manoranjank.studentdiary.R;
import com.manoranjank.studentdiary.Subject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimeTableAdapter extends RecyclerView.Adapter<TimeTableAdapter.MyViewHolder> {

    private List<Subject> subjectList;
    private Context context;
    private myDbAdapter dbAdapter;


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.row_layout)
        RelativeLayout row_layout;
        @BindView(R.id.name_of_subject)
        TextView name_of_subject;
        @BindView(R.id.time_of_subject)
        TextView time_of_subject;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        @Override
        public void onClick(View v) {

        }
    }

    public TimeTableAdapter(List<Subject> subjectList, Context context) {
        this.subjectList = subjectList;
        this.context = context;
        dbAdapter = new myDbAdapter(context);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_recyclerview, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final Subject subject = subjectList.get(i);
        final int pos = i;
        myViewHolder.name_of_subject.setText(subject.getSubjectName());
        myViewHolder.time_of_subject.setText(subject.getTime());
        myViewHolder.row_layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context
                );

                builder.setTitle("Confirm");
                builder.setMessage("Are you sure want to delete?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dbAdapter.delete(subjectList.get(pos).getSubjectName(), subjectList.get(pos).getDayOfWeek());
                        if (subjectList.get(pos).getDayOfWeek().equals("MONDAY")) {
                            HomeActivity.fragmentListeners.get(8).updateList(pos);
                        }else if(subjectList.get(pos).getDayOfWeek().equals("SUNDAY")){}
                        else if (subjectList.get(pos).getDayOfWeek().equals("SUNDAY")) {
                            HomeActivity.fragmentListeners.get(0).updateList(pos);
                        }
                        subjectList.remove(pos);

                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
                return false;
            }
        });

    }


    @Override
    public int getItemCount() {
        return subjectList.size();
    }
}