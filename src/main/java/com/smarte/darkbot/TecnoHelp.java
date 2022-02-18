package com.smarte.darkbot;

import com.github.manolo8.darkbot.Main;
import com.github.manolo8.darkbot.config.types.Option;
import com.github.manolo8.darkbot.core.itf.Behaviour;
import com.github.manolo8.darkbot.core.itf.Configurable;
import com.github.manolo8.darkbot.extensions.features.Feature;

import java.util.Arrays;

@Feature(name = "Tecno Helper", description = "Activa automaticamente las tecno")
public class TecnoHelp implements Behaviour, Configurable<TecnoHelp.Config> {
    private Main main;
    private Config config;

    @Override
    public void install(Main main) {
        if (!Arrays.equals(VerifierChecker.class.getSigners(), getClass().getSigners())) return;
        VerifierChecker.checkAuthenticity();

        this.main = main;
    }

    @Override
    public void tick() {
        if (Character.isDefined(this.config.key)) {
            System.console().writer().print("Hola desde TecnoHelp");
        }
    }

    public static class Config {
        @Option("Presion de misiles")
        public Character key;
    }

    @Override
    public void setConfig(Config config) {
        this.config = config;
    }
}
