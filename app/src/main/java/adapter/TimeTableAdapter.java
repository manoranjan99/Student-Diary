package adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.manoranjank.studentdiary.R;
import com.manoranjank.studentdiary.Subject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimeTableAdapter extends RecyclerView.Adapter<TimeTableAdapter.MyViewHolder> {

    private List<Subject> subjectList;


    public class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.name_of_subject)
        TextView name_of_subject;
        @BindView(R.id.time_of_subject)
        TextView time_of_subject;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public TimeTableAdapter(List<Subject> subjectList){
        this.subjectList = subjectList;
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
        Subject subject = subjectList.get(i);
        myViewHolder.name_of_subject.setText(subject.getSubjectName());
        myViewHolder.time_of_subject.setText(subject.getTime());

    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }
}
