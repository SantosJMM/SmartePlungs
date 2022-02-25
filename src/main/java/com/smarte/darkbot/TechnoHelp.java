package com.smarte.darkbot;

import com.github.manolo8.darkbot.Main;
import com.github.manolo8.darkbot.config.types.Option;
import com.github.manolo8.darkbot.core.itf.Behaviour;
import com.github.manolo8.darkbot.core.itf.Configurable;
import com.github.manolo8.darkbot.core.manager.HeroManager;
import com.github.manolo8.darkbot.core.utils.Drive;
import com.github.manolo8.darkbot.extensions.features.Feature;

import java.util.Arrays;

import static com.github.manolo8.darkbot.Main.API;

@Feature(name = "Techno Helper", description = "Automatically activates tech factories")
public class TechnoHelp implements Behaviour, Configurable<TechnoHelp.Config> {
    private final TechnoStatus state;
    private Config config;

    public TechnoHelp(TechnoStatus state) {
        this.state = state;
    }

    @Override
    public void install(Main main) {
        if (!Arrays.equals(VerifierChecker.class.getSigners(), getClass().getSigners())) return;
        VerifierChecker.checkAuthenticity();

    }

    @Override
    public void tick() {
        // Maneja la activacion de la presicion de misiles.
        if (!this.config.key.toString().isEmpty()) {
//            System.out.println("Hola desde TechnoHelp");

            if (!this.state.runMissileAccuracy) {
//                this.hero.getLaser();
                API.keyboardClick(this.config.key);
                this.state.runMissileAccuracy = true;
                this.state.runTimeMissileAccuracy = System.currentTimeMillis();
            } else {
                this.state.runMissileAccuracy = false;
                this.state.runTimeMissileAccuracy = Long.parseLong(String.valueOf(0));
            }
        }
    }

    public static class TechnoStatus {
        protected boolean runMissileAccuracy;
        protected long runTimeMissileAccuracy;
    }

    public static class Config {
        @Option(value = "Presicion de misiles", description = "Inidica la tecla a presionar para la presicion de misiles.")
        public Character key;
    }

    @Override
    public void setConfig(Config config) {
        this.config = config;
    }

    private boolean checkIfTheTimeElapsed(long start, float minuteToWait) {
        // finding the time after the operation is executed
        long end = System.currentTimeMillis();
        // finding the time difference
        float msec = end - start;
        // converting it into seconds
        float sec= msec/1000F;
        // converting it into minutes
        float minutes=sec/60F;
        return minutes >= minuteToWait;
    }
}
