/*
 This file is part of Subsonic.

 Subsonic is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 Subsonic is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with Subsonic.  If not, see <http://www.gnu.org/licenses/>.

 Copyright 2009 (C) Sindre Mehus
 */
package net.sourceforge.subsonic.service;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

/**
 * Unit test of {@link SettingsService}.
 *
 * @author Sindre Mehus
 */
public class SettingsServiceTestCase
{

    @Test
    public void testSubsonicHome() {

        String initialValue = System.getProperty("subsonic.home");
        try {

            File subsonicHome = new File("test");
            System.setProperty("subsonic.home", subsonicHome.getPath());

            //should reload subsonichome fromm property
            new SettingsService();
            assertEquals("Wrong Subsonic home.", subsonicHome, SettingsService.getSubsonicHome());

        } finally {
            if (initialValue != null) {
                System.setProperty("subsonic.home", initialValue);
            }
            else {System.clearProperty("subsonic.home");}
        }
    }

    @Test
    public void testDefaultValues() {
        SettingsService settingsService = new SettingsService();

        assertEquals("Wrong default language.", "en", settingsService.getLocale().getLanguage());
        assertEquals("Wrong default index creation interval.", 1, settingsService.getIndexCreationInterval());
        assertEquals("Wrong default index creation hour.", 3, settingsService.getIndexCreationHour());
        assertTrue("Wrong default playlist folder.", settingsService.getPlaylistFolder().endsWith("playlists"));
        assertEquals("Wrong default theme.", "default", settingsService.getThemeId());
        assertNull("Wrong default license email.", settingsService.getLicenseEmail());
        assertNull("Wrong default license code.", settingsService.getLicenseCode());
        assertNull("Wrong default license date.", settingsService.getLicenseDate());
        assertEquals("Wrong default Podcast episode retention count.", 10, settingsService.getPodcastEpisodeRetentionCount());
        assertEquals("Wrong default Podcast episode download count.", 1, settingsService.getPodcastEpisodeDownloadCount());
        assertTrue("Wrong default Podcast folder.", settingsService.getPodcastFolder().endsWith("Podcast"));
        assertEquals("Wrong default Podcast update interval.", 24, settingsService.getPodcastUpdateInterval());
        assertEquals("Wrong default rewrite URL enabled.", true, settingsService.isRewriteUrlEnabled());
        assertEquals("Wrong default LDAP enabled.", false, settingsService.isLdapEnabled());
        assertEquals("Wrong default LDAP URL.", "ldap://host.domain.com:389/cn=Users,dc=domain,dc=com", settingsService.getLdapUrl());
        assertNull("Wrong default LDAP manager DN.", settingsService.getLdapManagerDn());
        assertNull("Wrong default LDAP manager password.", settingsService.getLdapManagerPassword());
        assertEquals("Wrong default LDAP search filter.", "(sAMAccountName={0})", settingsService.getLdapSearchFilter());
        assertEquals("Wrong default LDAP auto-shadowing.", false, settingsService.isLdapAutoShadowing());
    }

    @Test
    public void testChangeSettings() {
        SettingsService settingsService = new SettingsService();

        settingsService.setIndexString("indexString");
        settingsService.setIgnoredArticles("a the foo bar");
        settingsService.setShortcuts("new incoming \"rock 'n' roll\"");
        settingsService.setPlaylistFolder("playlistFolder");
        settingsService.setMusicFileTypes("mp3 ogg  aac");
        settingsService.setCoverArtFileTypes("jpeg gif  png");
        settingsService.setWelcomeMessage("welcomeMessage");
        settingsService.setLoginMessage("loginMessage");
        settingsService.setLocale(Locale.CANADA_FRENCH);
        settingsService.setThemeId("dark");
        settingsService.setIndexCreationInterval(4);
        settingsService.setIndexCreationHour(9);
        settingsService.setLicenseEmail("sindre@foo.bar.no");
        settingsService.setLicenseCode(null);
        settingsService.setLicenseDate(new Date(223423412351253L));
        settingsService.setPodcastEpisodeRetentionCount(5);
        settingsService.setPodcastEpisodeDownloadCount(-1);
        settingsService.setPodcastFolder("d:/podcasts");
        settingsService.setPodcastUpdateInterval(-1);
        settingsService.setRewriteUrlEnabled(false);
        settingsService.setLdapEnabled(true);
        settingsService.setLdapUrl("newLdapUrl");
        settingsService.setLdapManagerDn("admin");
        settingsService.setLdapManagerPassword("secret");
        settingsService.setLdapSearchFilter("newLdapSearchFilter");
        settingsService.setLdapAutoShadowing(true);

        verifySettings(settingsService);

        settingsService.save();
        verifySettings(settingsService);

        verifySettings(new SettingsService());
    }

    private void verifySettings(SettingsService ss) {
        assertEquals("Wrong index string.", "indexString", ss.getIndexString());
        assertEquals("Wrong ignored articles.", "a the foo bar", ss.getIgnoredArticles());
        assertEquals("Wrong shortcuts.", "new incoming \"rock 'n' roll\"", ss.getShortcuts());
        assertTrue("Wrong ignored articles array.", Arrays.equals(new String[] {"a", "the", "foo", "bar"}, ss.getIgnoredArticlesAsArray()));
        assertTrue("Wrong shortcut array.", Arrays.equals(new String[] {"new", "incoming", "rock 'n' roll"}, ss.getShortcutsAsArray()));
        assertEquals("Wrong playlist folder.", "playlistFolder", ss.getPlaylistFolder());
        assertEquals("Wrong music mask.", "mp3 ogg  aac", ss.getMusicFileTypes());
        assertTrue("Wrong music mask array.", Arrays.equals(new String[] {"mp3", "ogg", "aac"}, ss.getMusicFileTypesAsArray()));
        assertEquals("Wrong cover art mask.", "jpeg gif  png", ss.getCoverArtFileTypes());
        assertTrue("Wrong cover art mask array.", Arrays.equals(new String[] {"jpeg", "gif", "png"}, ss.getCoverArtFileTypesAsArray()));
        assertEquals("Wrong welcome message.", "welcomeMessage", ss.getWelcomeMessage());
        assertEquals("Wrong login message.", "loginMessage", ss.getLoginMessage());
        assertEquals("Wrong locale.", Locale.CANADA_FRENCH, ss.getLocale());
        assertEquals("Wrong theme.", "dark", ss.getThemeId());
        assertEquals("Wrong index creation interval.", 4, ss.getIndexCreationInterval());
        assertEquals("Wrong index creation hour.", 9, ss.getIndexCreationHour());
        assertEquals("Wrong license email.", "sindre@foo.bar.no", ss.getLicenseEmail());
        assertEquals("Wrong license code.", null, ss.getLicenseCode());
        assertEquals("Wrong license date.", new Date(223423412351253L), ss.getLicenseDate());
        assertEquals("Wrong Podcast episode retention count.", 5, ss.getPodcastEpisodeRetentionCount());
        assertEquals("Wrong Podcast episode download count.", -1, ss.getPodcastEpisodeDownloadCount());
        assertEquals("Wrong Podcast folder.", "d:/podcasts", ss.getPodcastFolder());
        assertEquals("Wrong Podcast update interval.", -1, ss.getPodcastUpdateInterval());
        assertEquals("Wrong rewrite URL enabled.", false, ss.isRewriteUrlEnabled());
        assertTrue("Wrong LDAP enabled.", ss.isLdapEnabled());
        assertEquals("Wrong LDAP URL.", "newLdapUrl", ss.getLdapUrl());
        assertEquals("Wrong LDAP manager DN.", "admin", ss.getLdapManagerDn());
        assertEquals("Wrong LDAP manager password.", "secret", ss.getLdapManagerPassword());
        assertEquals("Wrong LDAP search filter.", "newLdapSearchFilter", ss.getLdapSearchFilter());
        assertTrue("Wrong LDAP auto-shadowing.", ss.isLdapAutoShadowing());
    }

}
