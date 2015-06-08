package com.appia.sliding;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class RectActor extends Actor {
    private float x, y, width, height;
    private ShapeRenderer shapeRenderer;

    public RectActor(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        Color color = getColor();
        shapeRenderer.setColor(color.r * parentAlpha, color.g * parentAlpha, color.b * parentAlpha, color.a * parentAlpha);
        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.end();
        batch.begin();
    }
}