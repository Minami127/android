package com.minami.quizapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.minami.quizapp.model.Quiz;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView txtQuiz;
    ProgressBar progressBar;
    TextView txtResult;
    Button btnTrue;
    Button btnFalse;

    ArrayList<Quiz> quizArrayList = new ArrayList<>();

    int currentIndex = 0;

    int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtQuiz = findViewById(R.id.txtQuiz);
        progressBar = findViewById(R.id.progressBar);
        txtResult = findViewById(R.id.txtResult);
        btnTrue = findViewById(R.id.btnTrue);
        btnFalse = findViewById(R.id.btnFalse);

        // 퀴즈는, 문제와 정답으로 되어있다.
        // 문제는 문자열이고, 정답은  true / false 로 할거다.

        // 퀴즈 클래스를 설계한다.

        // 설계한 클래스에 데이터를 넣어준다.
        //  => 객체를 생성한다. => 메모리에 공간 확보
        setQuiz();

        // 1번 퀴즈 문제를 화면에 보여준다.
        Quiz quiz = quizArrayList.get(currentIndex);
        txtQuiz.setText( quiz.question  );

        // 프로그레스바를 하나 증가시켜준다.
        progressBar.setProgress(currentIndex+1);



        // 참 버튼 눌렀을때 처리
        btnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 현재 문제의 정답을 가져온다.
                Quiz quiz = quizArrayList.get(currentIndex);

                if( quiz.answer == true){
                    // 화면에 "정답입니다" 보여준다.
                    txtResult.setText("정답입니다.");
                    count = count + 1;
                }else{
                    // 화면에 "오답입니다" 보여준다.
                    txtResult.setText("오답입니다.");
                }

                getNextQuestion();

            }
        });

        // 거짓 버튼 눌렀을때 처리
        btnFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Quiz quiz = quizArrayList.get(currentIndex);
                if(quiz.answer == false){
                    txtResult.setText("정답입니다.");
                    count = count + 1;
                }else{
                    txtResult.setText("오답입니다.");
                }

                getNextQuestion();



            }
        });


    }

    private void getNextQuestion() {
        // 그 다음 문제 제출

        if(currentIndex == quizArrayList.size()-1){
            showAlertDialog();
            return;
        }

        currentIndex = currentIndex + 1;


        Quiz quiz = quizArrayList.get(currentIndex);
        txtQuiz.setText(quiz.question);

        // 프로그레스바도 하나 증가
        progressBar.setProgress(currentIndex+1);


    }


    void setQuiz(){


        Quiz q = new Quiz(R.string.q1, true);
        quizArrayList.add(q);

        q = new Quiz(R.string.q2, false);
        quizArrayList.add(q);

        q = new Quiz(R.string.q3, true);
        quizArrayList.add(q);

        q = new Quiz(R.string.q4, false);
        quizArrayList.add(q);

        q = new Quiz(R.string.q5, true);
        quizArrayList.add(q);

        q = new Quiz(R.string.q6, false);
        quizArrayList.add(q);

        q = new Quiz(R.string.q7, true);
        quizArrayList.add(q);

        q = new Quiz(R.string.q8, false);
        quizArrayList.add(q);

        q = new Quiz(R.string.q9, true);
        quizArrayList.add(q);

        q = new Quiz(R.string.q10, false);
        quizArrayList.add(q);
    }

    private void  showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        // 이 다이얼 로그의 외곽부분을 눌렀을때, 사라지지 않도록 하는 코드
        builder.setCancelable(false);
        builder.setTitle("퀴즈 끝");
        builder.setMessage("퀴즈가 모두 끝났습니다. 맞춘 정답은 " + count + "개 입니다. 다시 시작하시겠습니까?");
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                currentIndex = 0;
                count = 0;
                Quiz quiz = quizArrayList.get(currentIndex);
                txtQuiz.setText(quiz.question);
                progressBar.setProgress(currentIndex+1);
                txtResult.setText("결과");
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 현재 이 액티비트를 종료한다 => 액티비티가 1개면 앱이 종료
                finish();
            }
        });
        builder.show();
    }

}