package com.jnu.student;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jnu.student.view.GameView;

public class GameViewFragment extends Fragment {

    private GameView gameView;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gameView = view.findViewById(R.id.gameView); // 获取 ClockView 实例
        gameView.startGame(); // 开始游戏
    }

    @Override
    public void onPause() {
        super.onPause();
        if (gameView != null) {
            gameView.stopGame(); // 停止游戏
        }
    }

}


