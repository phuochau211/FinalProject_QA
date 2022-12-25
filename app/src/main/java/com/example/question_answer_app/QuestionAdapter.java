package com.example.question_answer_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>{

    private Context context;
    private List<Question> questionList;

    public QuestionAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<Question> list){
        this.questionList = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_qa,parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        Question question = questionList.get(position);
        if (question == null){
            return;
        }
        holder.imgAvatar.setImageResource(question.getImgAvata());
        holder.tvQuestion.setText(question.getQuestion());
        holder.tvSubject.setText(question.getSubject());
        holder.tvNameP.setText(question.getNameP());
        holder.ratingBar.setRating((float) question.getRating());

    }

    @Override
    public int getItemCount() {
        if (questionList != null){
            return questionList.size();
        }
        return 0;
    }

    public class QuestionViewHolder extends RecyclerView.ViewHolder{

    private ImageView imgAvatar;
    private TextView tvNameP, tvSubject, tvQuestion;
    private RatingBar ratingBar;
    private Button btnAnswer;

        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);

            imgAvatar = itemView.findViewById(R.id.cimAvata);
            tvNameP = itemView.findViewById(R.id.tvNameP);
            tvSubject = itemView.findViewById(R.id.tvSubject);
            tvQuestion = itemView.findViewById(R.id.tvQuestion);
            ratingBar = itemView.findViewById(R.id.ratingbar);
            btnAnswer = itemView.findViewById(R.id.btnAnswer);

            btnAnswer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AnswerActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }
}
