package com.software.codetime.managers;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.project.Project;
import org.apache.commons.lang.StringUtils;
import swdc.java.ops.manager.EventTrackerManager;
import swdc.java.ops.manager.FileUtilManager;
import swdc.java.ops.manager.UtilManager;
import swdc.java.ops.model.*;

import java.util.*;

public class KeystrokeUtilManager {

    public static long elapsed_seconds = 0;
    public static String project_null_error = "";

    public static void processKeystrokes(CodeTime keystrokeCountInfo) {
        try {
            if (keystrokeCountInfo.hasData()) {

                swdc.java.ops.model.Project project = keystrokeCountInfo.getProject();

                // check to see if we need to find the main project if we don't have it
                if (project == null || StringUtils.isBlank(project.getDirectory()) ||
                        project.getDirectory().equals("Untitled")) {

                    Editor[] editors = EditorFactory.getInstance().getAllEditors();
                    if (editors != null && editors.length > 0) {
                        for (Editor editor : editors) {
                            Project editorProject = editor.getProject();
                            // update the code time project dir info
                            if (editorProject != null && StringUtils.isNotBlank(editorProject.getName())) {

                                String projDir = editorProject.getProjectFilePath();
                                String projName = editorProject.getName();
                                if (project == null) {
                                    project = new swdc.java.ops.model.Project(projName, projDir);
                                } else {
                                    project.setDirectory(projDir);
                                    project.setName(projName);
                                }
                                break;
                            }
                        }
                    }
                }

                ElapsedTime eTime = SessionDataManager.getTimeBetweenLastPayload();

                // end the file end times.
                preProcessKeystrokeData(keystrokeCountInfo, eTime.sessionSeconds, eTime.elapsedSeconds);

                // send the event to the event tracker
                EventTrackerManager.getInstance().trackCodeTimeEvent(keystrokeCountInfo);

                UtilManager.TimesData timesData = UtilManager.getTimesData();
                // set the latest payload timestamp utc so help with session time calculations
                FileUtilManager.setNumericItem("latestPayloadTimestampEndUtc", timesData.now);
            }
        } catch (Exception e) {
        }

        keystrokeCountInfo.resetData();
    }

    private static void validateAndUpdateCumulativeData(CodeTime keystrokeCountInfo, long sessionSeconds) {

        TimeData td = TimeDataManager.incrementSessionAndFileSeconds(keystrokeCountInfo.getProject(), sessionSeconds);

        if (UtilManager.isNewDay()) {

            // clear out data from the previous day
            WallClockManager.getInstance().newDayChecker();

            if (td != null) {
                td = null;
                project_null_error = "TimeData should be null as its a new day";
            }
        }

        // add the cumulative data
        keystrokeCountInfo.workspace_name = UtilManager.getWorkspaceName();
        keystrokeCountInfo.hostname = UtilManager.getHostname();
        keystrokeCountInfo.cumulative_session_seconds = 60;
        keystrokeCountInfo.cumulative_editor_seconds = 60;

        if (td != null) {
            keystrokeCountInfo.cumulative_editor_seconds = td.getEditor_seconds();
            keystrokeCountInfo.cumulative_session_seconds = td.getSession_seconds();
        }

        if (keystrokeCountInfo.cumulative_editor_seconds < keystrokeCountInfo.cumulative_session_seconds) {
            keystrokeCountInfo.cumulative_editor_seconds = keystrokeCountInfo.cumulative_session_seconds;
        }
    }

    // end unended file payloads and add the cumulative editor seconds
    public static void preProcessKeystrokeData(CodeTime keystrokeCountInfo, long sessionSeconds, long elapsedSeconds) {

        validateAndUpdateCumulativeData(keystrokeCountInfo, sessionSeconds);

        // set the elapsed seconds (last end time to this end time)
        elapsed_seconds = elapsedSeconds;

        UtilManager.TimesData timesData = UtilManager.getTimesData();
        Map<String, CodeTime.FileInfo> fileInfoDataSet = keystrokeCountInfo.getSource();
        for ( CodeTime.FileInfo fileInfoData : fileInfoDataSet.values() ) {
            // end the ones that don't have an end time
            if (fileInfoData.end == 0) {
                // set the end time for this file
                fileInfoData.end = timesData.now;
                fileInfoData.local_end = timesData.local_now;
            }
        }
    }
}
