package com.github.luriel0228.autopickup;

import com.github.luriel0228.autopickup.listener.AutoPickupListener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class AutoPickup extends JavaPlugin {

    public FileConfiguration config;

    @Override
    public void onEnable() {
        config = getConfig();
        saveDefaultConfig();
        if (config != null && config.getBoolean("AutoPickup.enablePlugin")) {
            registerEvents();
        } else {
            getLogger().info("플러그인이 비활성화 상태입니다. 활성화하려면 config.yml에서 `enablePlugin: true`로 설정한 후 서버를 재시작 해주십시오.");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new AutoPickupListener(), this);
    }

}
