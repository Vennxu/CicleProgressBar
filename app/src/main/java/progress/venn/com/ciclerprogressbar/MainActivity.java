package progress.venn.com.ciclerprogressbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.venn.round.library.RoundProgressBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RoundProgressBar bar = findViewById(R.id.progress);
        bar.setCurrentProgress(100);
    }
}
