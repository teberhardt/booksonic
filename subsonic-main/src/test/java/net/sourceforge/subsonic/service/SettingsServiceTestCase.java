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
    public void testIndexString()
    {
        SettingsService ss = new SettingsService();

        ss.setIndexString("indexString");

        assertEquals("Wrong index string.", "indexString", ss.getIndexString());
    }

    @Test
    public void testIgnoredArticles()
    {
        SettingsService ss = new SettingsService();

        ss.setIgnoredArticles("a the foo bar");

        assertEquals("Wrong ignored articles.", "a the foo bar", ss.getIgnoredArticles());
    }

    @Test
    public void testShortcuts()
    {
        SettingsService ss = new SettingsService();

        ss.setShortcuts("new incoming \"rock 'n' roll\"");

        assertEquals("Wrong shortcuts.", "new incoming \"rock 'n' roll\"", ss.getShortcuts());
    }

    @Test
    public void testPlaylistFolder()
    {
        SettingsService ss = new SettingsService();

        ss.setPlaylistFolder("playlistFolder");

        assertEquals("Wrong playlist folder.", "playlistFolder", ss.getPlaylistFolder());

    }

    @Test
    public void testMusicFileTypes()
    {
        SettingsService ss = new SettingsService();

        ss.setMusicFileTypes("mp3 ogg  aac");

        assertEquals("Wrong music mask.", "mp3 ogg  aac", ss.getMusicFileTypes());

    }

    @Test
    public void testCoverArtFileTypes()
    {
        SettingsService ss = new SettingsService();

        ss.setCoverArtFileTypes("jpeg gif  png");

        assertEquals("Wrong cover art mask.", "jpeg gif  png", ss.getCoverArtFileTypes());
    }

    @Test
    public void testWelcomeMessage()
    {
        SettingsService ss = new SettingsService();

        ss.setWelcomeMessage("welcomeMessage");

        assertEquals("Wrong welcome message.", "welcomeMessage", ss.getWelcomeMessage());
    }


    @Test
    public void testLoginMessage()
    {
        SettingsService ss = new SettingsService();

        ss.setLoginMessage("loginMessage");

        assertEquals("Wrong login message.", "loginMessage", ss.getLoginMessage());
    }

    @Test
    public void testLocale()
    {
        SettingsService ss = new SettingsService();

        ss.setLocale(Locale.CANADA_FRENCH);

        assertEquals("Wrong locale.", Locale.CANADA_FRENCH, ss.getLocale());
    }

    @Test
    public void testThemeId()
    {
        SettingsService ss = new SettingsService();

        ss.setThemeId("dark");

        assertEquals("Wrong theme.", "dark", ss.getThemeId());
    }

    @Test
    public void testIndexCreationInterval()
    {
        SettingsService ss = new SettingsService();

        ss.setIndexCreationInterval(4);

        assertEquals("Wrong index creation interval.", 4, ss.getIndexCreationInterval());
    }

    @Test
    public void testIndexCreationHour()
    {
        SettingsService ss = new SettingsService();

        ss.setIndexCreationHour(9);

        assertEquals("Wrong index creation hour.", 9, ss.getIndexCreationHour());
    }

    @Test
    public void testLicenseEmail()
    {
        SettingsService ss = new SettingsService();

        ss.setLicenseEmail("sindre@foo.bar.no");

        assertEquals("Wrong license email.", "sindre@foo.bar.no", ss.getLicenseEmail());
    }

    @Test
    public void testLicenseCode()
    {
        SettingsService ss = new SettingsService();

        ss.setLicenseCode(null);

        assertEquals("Wrong license code.", null, ss.getLicenseCode());
    }

    @Test
    public void testLicenseDate()
    {
        SettingsService ss = new SettingsService();

        ss.setLicenseDate(new Date(223423412351253L));
        assertEquals("Wrong license date.", new Date(223423412351253L), ss.getLicenseDate());

    }

    @Test
    public void testPodcastEpisodeRetentionCount()
    {
        SettingsService ss = new SettingsService();

        ss.setPodcastEpisodeRetentionCount(5);
        assertEquals("Wrong Podcast episode retention count.", 5, ss.getPodcastEpisodeRetentionCount());
    }

    @Test
    public void testPodcastEpisodeDownloadCount()
    {
        SettingsService ss = new SettingsService();

        ss.setPodcastEpisodeDownloadCount(-1);
        assertEquals("Wrong Podcast episode download count.", -1, ss.getPodcastEpisodeDownloadCount());

    }

    @Test
    public void testPodcastFolder()
    {
        SettingsService ss = new SettingsService();

        ss.setPodcastFolder("d:/podcasts");
        assertEquals("Wrong Podcast folder.", "d:/podcasts", ss.getPodcastFolder());
    }

    @Test
    public void testPodcastUpdateInterval()
    {
        SettingsService ss = new SettingsService();

        ss.setPodcastUpdateInterval(-1);
        assertEquals("Wrong Podcast update interval.", -1, ss.getPodcastUpdateInterval());
    }


    @Test
    public void testRewriteUrlEnabled()
    {
        SettingsService ss = new SettingsService();

        ss.setRewriteUrlEnabled(false);
        assertEquals("Wrong rewrite URL enabled.", false, ss.isRewriteUrlEnabled());
    }

    @Test
    public void testLdapEnabled()
    {
        SettingsService ss = new SettingsService();

        ss.setLdapEnabled(true);
        assertTrue("Wrong LDAP enabled.", ss.isLdapEnabled());
    }

    @Test
    public void testLdapManagerDn()
    {
        SettingsService ss = new SettingsService();

        ss.setLdapManagerDn("admin");
        assertEquals("Wrong LDAP manager DN.", "admin", ss.getLdapManagerDn());
    }

    @Test
    public void testLdapManagerPassword()
    {
        SettingsService ss = new SettingsService();

        ss.setLdapManagerPassword("secret");
        assertEquals("Wrong LDAP manager password.", "secret", ss.getLdapManagerPassword());
    }

    @Test
    public void testLdapSearchFilter()
    {
        SettingsService ss = new SettingsService();

        ss.setLdapSearchFilter("newLdapSearchFilter");
        assertEquals("Wrong LDAP search filter.", "newLdapSearchFilter", ss.getLdapSearchFilter());
    }

    @Test
    public void testLdapAutoShadowing()
    {
        SettingsService ss = new SettingsService();

        ss.setLdapAutoShadowing(true);
        assertTrue("Wrong LDAP auto-shadowing.", ss.isLdapAutoShadowing());
    }


    @Test
    public void testSaveWithIndexString() {
        SettingsService ss = new SettingsService();
        ss.setIndexString("indexString");

        ss.save();

        File f = new File(ss.getSubsonicHome().getAbsolutePath() + File.separator + "booksonic.properties" );
        assertTrue("Configuration file does not exists", f.exists() );
        assertEquals("Wrong index string.", "indexString", ss.getIndexString());
        f.delete();

    }

}
