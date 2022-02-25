package com.smarte.darkbot;

import com.github.manolo8.darkbot.Main;
import com.github.manolo8.darkbot.config.types.Option;
import com.github.manolo8.darkbot.core.manager.HeroManager;
import eu.darkbot.api.config.ConfigSetting;
import eu.darkbot.api.config.annotations.Number;
import eu.darkbot.api.config.annotations.Percentage;
import eu.darkbot.api.extensions.Behavior;
import eu.darkbot.api.extensions.Configurable;
import eu.darkbot.api.extensions.Feature;

import static com.github.manolo8.darkbot.Main.API;

@Feature(name = "Ship Ability Helper",
        description = "It is responsible for activating the solace ship's ability in defense mode")
public class ShipsAbility implements Behavior, Configurable<ShipsAbility.Config> {
    private final HeroManager hero;
    private ConfigSetting<Config> config;
    private long lastActive = -1;

    /**
     * @param main Main bot instance
     */
    public ShipsAbility(Main main) {
        this.hero = main.hero;
    }

    @Option(value = "Ships Ability Config")
    public static class Config {
        @Option(value = "Solace Key")
        public Character SOLACE_KEY;
        @Option(value = "Solace Activate Percent")
        @Percentage
        public double SOLACE_ACTIVATE_PERCENT = 0.75;
        @Option(value = "Solace skill cooldown (sec)")
        @Number(min = 1, max = 90)
        public int SOLACE_SKILL_COOLDOWN = 90;
    }

    @Override
    public void onTickBehavior() {
        //
        if (this.config.getValue().SOLACE_KEY == null) return;

        if (this.shipNeedAbility() && (this.lastActive == -1 || this.isAbilityAvailable())) {
            lastActive = System.currentTimeMillis();
            API.keyboardClick(this.config.getValue().SOLACE_KEY);
        }
    }

    @Override
    public void setConfig(ConfigSetting<Config> configSetting) {
        this.config = configSetting;
    }

    /**
     * @return True if the percentage of health is less than stated
     */
    private boolean shipNeedAbility() {
        return this.hero.health.hpPercent() < this.config.getValue().SOLACE_ACTIVATE_PERCENT;
    }

    /**
     * @return True if the ability charge time has elapsed
     */
    private boolean isAbilityAvailable() {
        return System.currentTimeMillis() - lastActive > (
                this.config.getValue().SOLACE_SKILL_COOLDOWN * 1000L
        );
    }
}
