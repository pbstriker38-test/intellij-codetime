package com.software.codetime.main;


import com.software.codetime.managers.ReadmeManager;
import com.software.codetime.managers.SessionDataManager;
import com.software.codetime.managers.UserSessionManager;
import com.software.codetime.models.IntellijProject;
import com.software.codetime.toolwindows.codetime.CodeTimeWindowFactory;
import org.apache.commons.lang.StringUtils;
import swdc.java.ops.manager.AccountManager;
import swdc.java.ops.manager.ConfigManager;
import swdc.java.ops.manager.EventTrackerManager;
import swdc.java.ops.manager.FileUtilManager;
import swdc.java.ops.snowplow.events.UIInteractionType;
import swdc.java.ops.websockets.WebsocketClient;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Activator {

    public static final Logger log = Logger.getLogger("Activator");
    private static Activator instance = null;

    public static Activator getInstance() {
        if (instance == null) {
            instance = new Activator();
        }
        return instance;
    }

    private Activator() {
        init();
    }

    private void init() {
        ConfigManager.init(
                PluginInfo.api_endpoint,
                PluginInfo.launch_url,
                PluginInfo.pluginId,
                PluginInfo.getPluginName(),
                PluginInfo.getVersion(),
                PluginInfo.IDE_NAME,
                PluginInfo.IDE_VERSION,
                () -> CodeTimeWindowFactory.refresh(false),
                new SessionDataManager(),
                ConfigManager.IdeType.intellij);

        log.log(Level.INFO, ConfigManager.plugin_name + ": Loaded v" + ConfigManager.plugin_id);
        // create anon user if no user exists
        String jwt = FileUtilManager.getItem("jwt");
        if (StringUtils.isBlank(jwt)) {
            jwt = AccountManager.createAnonymousUser(false);
            if (StringUtils.isBlank(jwt)) {
                boolean serverIsOnline = UserSessionManager.isServerOnline();
                if (!serverIsOnline) {
                    UserSessionManager.showOfflinePrompt(true);
                }
            }
        }

        AccountManager.getUser();

        SessionDataManager.updateSessionSummaryFromServer();

        try {
            WebsocketClient.connect();
        } catch (Exception e) {
            log.warning("Websocket connect error: " + e.getMessage());
        }

        // initialize the tracker
        EventTrackerManager.getInstance().init(new IntellijProject());

        // send the activate event
        EventTrackerManager.getInstance().trackEditorAction("editor", "activate");

        String readmeDisplayed = FileUtilManager.getItem("intellij_CtReadme");
        if (readmeDisplayed == null || Boolean.valueOf(readmeDisplayed) == false) {
            // send an initial plugin payload
            ReadmeManager.openReadmeFile(UIInteractionType.keyboard);
            FileUtilManager.setItem("intellij_CtReadme", "true");
        }
    }
}
