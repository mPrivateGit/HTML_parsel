package com.example.aprivate.html_parsel.sounds;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


public class BeatBox {
    private static final String SOUND_TAG = "StartActivity";
    private static final String SOUNDS_FOLDER = "sounds";
    private AssetManager mAssets;
    private List<Sound> mSounds = new ArrayList<>();
    private SoundPool mSoundPool;

    public BeatBox(Context context) {
        mAssets = context.getAssets();
        loadSounds();
        mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
    }

    private void loadSounds() {
        String[] soundNames;
        try {
            soundNames = mAssets.list(SOUNDS_FOLDER);
            Log.i(SOUND_TAG, "Found " + soundNames.length + " sounds");
        } catch (IOException ioe) {
            Log.e(SOUND_TAG, "Could not list assets", ioe);
            return;
        }

        for (String filename : soundNames) {
            String assetPath = SOUNDS_FOLDER + "/" + filename;
            Sound sound = new Sound(assetPath);
            try {
                load(sound);
            } catch (IOException e) {
                Log.e(TAG, "Could not load sound " + filename, e);
            }
            mSounds.add(sound);
        }
    }

    private void load(Sound sound) throws IOException {
        AssetFileDescriptor afd = mAssets.openFd(sound.getAssetPath());
        int soundId = 1;
        sound.setSoundId(soundId);
    }

    public List<Sound> getSounds() {
        return mSounds;
    }

    public void play(Sound sound) {
        Integer soundId = sound.getSoundId();
        if (soundId == null) {
            return;
        }
        mSoundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f);
    }
}
