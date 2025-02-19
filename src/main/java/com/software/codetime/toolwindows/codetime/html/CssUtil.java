package com.software.codetime.toolwindows.codetime.html;

import com.intellij.openapi.editor.colors.EditorColorsManager;

public class CssUtil {

    public static String getGlobalStyle() {
        if (EditorColorsManager.getInstance().isDarkEditor()) {
            return getDarkStyle();
        }
        return getLightStyle();
    }

    private static String getDarkStyle() {
        return "  <style type=\"text/css\">\n" +
                "    body { background-color: #2e2e2e; color: #fafafa; line-height: 1; font-size: .9rem; margin: 0; padding: 0; }\n" +
                "    canvas { display: block; position: relative; zindex: 1; pointer-events: none; }\n" +
                "    #content { background-color: #2e2e2e; position: absolute; top: 0; bottom: 0; width: 100%; height: 100%; }\n" +
                "    .right-padding-4 { padding-right: 4px; }\n" +
                "    .card { background-color: #2e2e2e; border-radius: 0; }\n" +
                "    .card > .list-group { border-style: none; background-color: #2e2e2e; }\n" +
                "    .list-group, .list-group:active, .list-group:focus { background-color: #2e2e2e; }\n" +
                "    .list-group-item, .list-group-item:active, .list-group-item:focus { border: 0 none; background-color: #2e2e2e; color: #fafafa; }\n" +
                "    .list-group-item:hover { background-color: #3e3d3d; color: #fafafa; }\n" +
                "    .accordion { background-color: #2e2e2e; color: #fafafa; }\n" +
                "    .accordion-item, .accordion-item:active, .accordion-item:focus { border: 0 none; background-color: #2e2e2e; color: #fafafa; }\n" +
                "    .accordion-button, .accordion-button:active, .accordion-button:focus { font-size: inherit; background-color: #2e2e2e; color: #fafafa; }\n" +
                "    .accordion-button:not(.collapsed) { background-color: #2e2e2e; color: #fafafa }\n" +
                "    .accordion-button::after { background-image: url('data:image/svg+xml,%3Csvg xmlns=\"http://www.w3.org/2000/svg\" width=\"16\" height=\"16\" fill=\"%23fafafa\" class=\"bi bi-chevron-compact-down\" viewBox=\"0 0 16 16\"%3E%3Cpath fill-rule=\"evenodd\" d=\"M1.553 6.776a.5.5 0 0 1 .67-.223L8 9.44l5.776-2.888a.5.5 0 1 1 .448.894l-6 3a.5.5 0 0 1-.448 0l-6-3a.5.5 0 0 1-.223-.67z\"/%3E%3C/svg%3E'); }\n" +
                "    .accordion-button:not(.collapsed)::after { background-image: url('data:image/svg+xml,%3Csvg xmlns=\"http://www.w3.org/2000/svg\" width=\"16\" height=\"16\" fill=\"%23fafafa\" class=\"bi bi-chevron-compact-down\" viewBox=\"0 0 16 16\"%3E%3Cpath fill-rule=\"evenodd\" d=\"M1.553 6.776a.5.5 0 0 1 .67-.223L8 9.44l5.776-2.888a.5.5 0 1 1 .448.894l-6 3a.5.5 0 0 1-.448 0l-6-3a.5.5 0 0 1-.223-.67z\"/%3E%3C/svg%3E'); transform: rotate(-180deg); }\n" +
                "    .accordion-body { padding: 1px }\n" +
                "    .bb-text { font-size: small; font-weight: 600; }\n" +
                "    .getting-started-bg { background-color: #2196f3; color: #fafafa; }\n" +
                "    .getting-started-button { background-color: #fafafa; color: #2196f3; }\n" +
                "    .getting-started-button:hover { background-color: #efefef; color: #2196f3; }\n" +
                "    .pg-track-bg { height: 6px; background-color: #a1cbf5; }\n" +
                "    .pg-track { background-color: #fafafa; }\n" +
                "    button:focus, button:active { outline: none; border-style: none; background-color: #2e2e2e }\n" +
                "    a.white-link, a.white-link:active, a.white-link:hover {color: #fafafa; text-decoration: none; font-weight: bold; }\n" +
                "    .cursor-pointer { cursor: pointer; }\n" +
                "    .top-right { position: absolute; top: 18px; right: 16px; }\n" +
                "    .icon-button { padding: 0; background-color: transparent; border: 0; -webkit-appearance: none; cursor: pointer; }\n" +
                "    .fs-7 { font-size: .8rem !important; line-height: normal; }\n" +
                "  </style>\n";
    }

    private static String getLightStyle() {
        return "  <style type=\"text/css\">\n" +
                "    body { line-height: 1; font-size: .9rem; margin: 0; padding: 0; }\n" +
                "    canvas { display: block; position: relative; zindex: 1; pointer-events: none; }\n" +
                "    #content { position: absolute; top: 0; bottom: 0; width: 100%; height: 100%; }\n" +
                "    .right-padding-4 { padding-right: 4px; }\n" +
                "    .card { border-radius: 0; }\n" +
                "    .list-group-item { border: 0 none; }\n" +
                "    .accordion-item { border: 0 none; background-color: transparent; }\n" +
                "    .accordion-button { font-size: inherit; }\n" +
                "    .accordion-body { padding: 1px }\n" +
                "    .card > .list-group { border-style: none; }\n" +
                "    .bb-text { font-size: small; font-weight: 600; }\n" +
                "    .getting-started-bg { background-color: #2196f3; color: #ffffff; }\n" +
                "    .getting-started-button { background-color: #ffffff; color: #2196f3; }\n" +
                "    .pg-track-bg { height: 6px; background-color: #a1cbf5; }\n" +
                "    .pg-track { background-color: #ffffff; }\n" +
                "    button:focus, button:active { outline: none; border-style: none; }\n" +
                "    a.white-link, a.white-link:active, a.white-link:hover {color: #ffffff; text-decoration: none; font-weight: bold; }\n" +
                "    .cursor-pointer { cursor: pointer; }\n" +
                "    .top-right { position: absolute; top: 18px; right: 16px; }\n" +
                "    .icon-button { padding: 0; background-color: transparent; border: 0; -webkit-appearance: none; cursor: pointer; }\n" +
                "    .fs-7 { font-size: .8rem !important; line-height: normal; }\n" +
                "  </style>\n";
    }
}
