package com.mygdx.ipop_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class SceneController {

    public static Stage mainMenuStage = new Stage();
    public static Stage playerShowRoomStage = new Stage();
    public static Stage rankginsStage = new Stage();
    public static Stage singlePlayerStage = new Stage();
    public static Stage multiPlayerStage = new Stage();
    public static Stage selectNamePlayerStage = new Stage();
    public static Stage selectCharacterPlayerStage = new Stage();
    public static Stage selectGradePlayerStage = new Stage();

    public static int scene = 0;

    public SceneController() {
        mainMenuStage = new Stage();
        playerShowRoomStage = new Stage();
        rankginsStage = new Stage();
        singlePlayerStage = new Stage();
        multiPlayerStage = new Stage();
        selectNamePlayerStage = new Stage();
        selectCharacterPlayerStage = new Stage();
        selectGradePlayerStage = new Stage();
    }

    public static void goToMainMenu() {
        Gdx.input.setInputProcessor(mainMenuStage);
    }

    public static void goToRankings() {
        Gdx.input.setInputProcessor(rankginsStage);
    }

    public static void goToSelectName() {
        Gdx.input.setInputProcessor(selectNamePlayerStage);
        SceneController.scene = 10;
    }

    public static void goToSinglePlayer() {
        Gdx.input.setInputProcessor(singlePlayerStage);
    }

    public static void goToMultiplayer() {
        Gdx.input.setInputProcessor(multiPlayerStage);
    }

    public static void goToSelectCharacter() {
        Gdx.input.setInputProcessor(selectCharacterPlayerStage);
    }

    public static void goToSelectGrade() {
        Gdx.input.setInputProcessor(selectGradePlayerStage);
    }


    public void render(float delta) {
        Stage stageActual = getStageActual();
        stageActual.act(delta);
        stageActual.draw();
    }

    public static Stage getStageActual() {
        if (scene == 1) {
            return playerShowRoomStage;
        } else if (scene == 2) {
            return rankginsStage;
        } else if (scene == 3) {
            return singlePlayerStage;
        } else if (scene == 4) {
            return rankginsStage;
        } else if (scene == 5) {
            return selectNamePlayerStage;
        } else if (scene == 6) {
            return selectCharacterPlayerStage;
        } else if (scene == 7) {
            return selectGradePlayerStage;
        } else if (scene == 8) {
            return mainMenuStage;
        }else {
            return mainMenuStage;
        }
    }

}
