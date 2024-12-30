package com.example.android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.utils.MyActivity;

import java.util.Locale;

public class ActivityOneNotice extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar buttonBack;
    private ImageButton buttonSpeak;
    private TextView oneTitle;
    private TextView oneContent;
    private TextToSpeech tts;

    public static String title;
    public static String content;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_notice);

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == tts.SUCCESS) {
                    int result = tts.setLanguage(Locale.CHINA);
                    if (result != TextToSpeech.LANG_COUNTRY_AVAILABLE
                            && result != TextToSpeech.LANG_AVAILABLE) {
                        Toast.makeText(ActivityOneNotice.this, "TTS暂时不支持这种语音的朗读！",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        buttonBack =findViewById(R.id.button_one_notice_back);
        buttonSpeak = (ImageButton) findViewById(R.id.notice_detail_speak);
        oneTitle = (TextView) findViewById(R.id.notice_detail_title);
        oneContent = (TextView) findViewById(R.id.notice_detail_content);
        oneTitle.setText(title);
        oneContent.setText(content);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        buttonSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = title + "    " + content;
                tts.setSpeechRate(0.5F);
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "");
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.shutdown();
        }
        super.onDestroy();
    }

    /*protected void speakText(){
        String text = title + "    " + content;
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "");
        if (tts!=null) {
            tts.setSpeechRate((float) 0.5);
        }
    }*/
}