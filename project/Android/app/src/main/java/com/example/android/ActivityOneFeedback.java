package com.example.android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class ActivityOneFeedback extends AppCompatActivity {


    private androidx.appcompat.widget.Toolbar buttonBack;
    private ImageButton buttonSpeak;
    private TextView oneTitle;
    private TextView oneContent;
    private TextView oneStatus;
    private TextView oneReply;
    private TextToSpeech tts;

    public static String title;
    public static String content;
    public static String status;
    public static String reply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_feedback);

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == tts.SUCCESS) {
                    int result = tts.setLanguage(Locale.CHINA);
                    if (result != TextToSpeech.LANG_COUNTRY_AVAILABLE
                            && result != TextToSpeech.LANG_AVAILABLE) {
                        Toast.makeText(ActivityOneFeedback.this, "TTS暂时不支持这种语音的朗读！",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        buttonBack =findViewById(R.id.button_one_feedback_back);
        buttonSpeak = (ImageButton) findViewById(R.id.feedback_detail_speak);
        oneTitle = (TextView) findViewById(R.id.feedback_detail_title);
        oneContent = (TextView) findViewById(R.id.feedback_detail_content);
        oneStatus = findViewById(R.id.feedback_detail_status);
        oneReply = findViewById(R.id.feedback_detail_reply);

        oneTitle.setText(title);
        oneContent.setText(content);
        oneStatus.setText(status);
        oneReply.setText(reply);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        buttonSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = title + "    " + content+"    " +reply;
                tts.setSpeechRate(0.5F);
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "");
            }
        });
    }
}