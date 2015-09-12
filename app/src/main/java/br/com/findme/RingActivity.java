package br.com.findme;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.eftimoff.androipathview.PathView;

public class RingActivity extends AppCompatActivity {

    private PathView mPathView;
    private MediaPlayer mPlayer;
    private View mRingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupWindow();
        setContentView(R.layout.activity_ring);
        setupWally();

        mRingView = findViewById(R.id.ring_view);
        mRingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        AudioManager audioManager = (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);

        int streamMaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, streamMaxVolume, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);

        Uri defaultRingtoneUri = RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_RINGTONE);
        mPlayer = MediaPlayer.create(this, defaultRingtoneUri);
        mPlayer.setLooping(true);
        mPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPlayer.stop();
    }

    private void setupWindow() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void setupWally() {
        mPathView = (PathView) findViewById(R.id.pathView);
        animateWally();
    }

    private void animateWally() {
        mPathView.getPathAnimator().
                delay(2000).
                duration(4000).
                listenerEnd(endAnimationListener()).
                interpolator(new AccelerateDecelerateInterpolator()).
                start();
    }

    private PathView.AnimatorBuilder.ListenerEnd endAnimationListener() {
        return new PathView.AnimatorBuilder.ListenerEnd() {
            @Override
            public void onAnimationEnd() {
                animateWally();
            }
        };
    }
}
