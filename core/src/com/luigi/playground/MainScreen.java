package com.luigi.playground;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

/**
 * Created by CU47EK on 06/07/2017.
 */

public class MainScreen implements Screen {
    private final Playground game;
    private final Array<Rectangle> missileArray;
    private final Texture kimTexture;
    private final Texture missileTexture;
    private final Texture explossionTexture;
    private final Sound launchMissileSound;
    private final Music music;
    private long lastMissileLaunched;
    private OrthographicCamera camera;
    private Rectangle kim;
    private int deathsCount = 0;
    private int pointsCount = 0;

    public MainScreen(Playground playground) {
        this.game = playground;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        kim = new Rectangle(700, 200, 77, 100);

        kimTexture = new Texture("face.png");
        missileTexture = new Texture("missile.png");
        explossionTexture = new Texture("explosion.gif");

        missileArray = new Array<Rectangle>();
        lastMissileLaunched = TimeUtils.millis();

        launchMissileSound = Gdx.audio.newSound(Gdx.files.internal("Missile_Launch.wav"));
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music.setLooping(true);

        spamMissile();
    }

    private void spamMissile() {
        Rectangle missile = new Rectangle(20, MathUtils.random(40, 440), 110, 77);
        missileArray.add(missile);
        lastMissileLaunched = TimeUtils.millis();
        launchMissileSound.play(0.2f, 2f, 0f);

    }


    @Override
    public void show() {
        music.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        interact(delta);
        launchAnotherMissile();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(kimTexture, kim.x, kim.y);
        Iterator<Rectangle> iterator = missileArray.iterator();

        while (iterator.hasNext()) {
            Rectangle missile = iterator.next();
            moveMissile(delta, missile);
            if (missile.x - 110 > 800) {
                iterator.remove();
                pointsCount++;
            }
            if (missile.overlaps(kim)) {
                iterator.remove();
                game.batch.draw(explossionTexture, kim.x, kim.y);
                deathsCount++;
            }
            game.batch.draw(missileTexture, missile.x, missile.y);
        }
        game.font.draw(game.batch, "hits = " + deathsCount, 10, 470);
        game.font.draw(game.batch, "points = " + pointsCount, 300, 470);
        game.batch.end();

    }

    private void launchAnotherMissile() {
        if (TimeUtils.millis() - lastMissileLaunched > 1250 - pointsCount*10) {
            spamMissile();
        }
    }

    private void moveMissile(float delta, Rectangle missile) {
        missile.x += delta * 400;
    }

    private void interact(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            kim.x += delta * 200;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            kim.x -= delta * 200;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            kim.y += delta * 200;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            kim.y -= delta * 200;
        }
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            kim.y = touchPos.y - 100 / 2;
            kim.x = touchPos.x - 77 / 2;
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        music.pause();
    }

    @Override
    public void resume() {
        music.play();
    }

    @Override
    public void hide() {
        music.pause();
    }

    @Override
    public void dispose() {
        kimTexture.dispose();
        missileTexture.dispose();
        explossionTexture.dispose();
        launchMissileSound.dispose();
        music.dispose();
    }
}
