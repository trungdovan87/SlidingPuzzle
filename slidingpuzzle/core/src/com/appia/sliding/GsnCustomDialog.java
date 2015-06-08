package com.appia.sliding;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

class GsnCustomDialog extends Group {

    public GsnCustomDialog(String title, BitmapFont font, Image background, Image... buttons) {
        addActor(background);
        setWidth(background.getWidth());
        setHeight(background.getHeight());

        for (Image button : buttons) addActor(button);

        if (buttons.length == 2) {
            ActorUtility.setRatio(buttons[0], 0.5f, 0f, getWidth() * 0.25f, getHeight() * 0.1f);
            ActorUtility.setRatio(buttons[1], 0.5f, 0f, getWidth() * 0.75f, getHeight() * 0.1f);
        } else {
            float tmp = getWidth() / (buttons.length + 1);
            for (int i = 0; i < buttons.length; i++)
                ActorUtility.setRatio(buttons[i], 0.5f, 0, tmp * (i + 1), getHeight() * 0.1f);
        }

        Label contentLabel = new Label(title, new Label.LabelStyle(font, new Color(1, 1, 1, 1)));
        contentLabel.setAlignment(Align.center);
        addActor(contentLabel);
        contentLabel.setWidth(0.8f * getWidth());
        contentLabel.setWrap(true);

        ActorUtility.setCenter(contentLabel, getWidth() / 2, getHeight() / 1.7f);

    }
}
