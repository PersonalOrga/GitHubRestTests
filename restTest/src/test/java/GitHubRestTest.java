import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class GitHubRestTest {

    /**
     *
     */
    public String githutUrl = "https://github.com";
    public String githutUrlApi = "https://api.github.com";

    /**
     *
     */
    public Response jsonResponse;

    /**
     *
     */
    public static Client restclient;
    //private UsernamePasswordCredentials authentication ;//=""

    private Response getReponseForUri(String uri) {
        WebTarget target = restclient.target(uri);
        
        jsonResponse = target.request().header("User-Agent", "JUnit").get();
        return jsonResponse;
    }

    private boolean userExists(String username) throws Exception {

        jsonResponse = getReponseForUri(githutUrlApi + "/users/" + username);
        String jsonstring = jsonResponse.readEntity(String.class);
        System.out.println("Username: " +username);
        System.out.println("JSON string: " +jsonstring);
        
        if (jsonstring.contains("API rate limit exceeded")) {
            System.out.println("Will go on error because Api Rate Limit has exceeded " + username);
            return false;
        }
        if (jsonstring.contains("Not Found")) {
            System.out.println("User not found: " + username);
            return false;
        }
        assertEquals("Status code:", Response.Status.OK.getStatusCode(), jsonResponse.getStatus());
        jsonResponse.close();

        return userJsonCompliant(jsonstring, username);
    }

    private boolean userHasRepo(String username) throws Exception {

        jsonResponse = getReponseForUri(githutUrlApi + "/users/" + username + "/repos");
        String jsonstring = jsonResponse.readEntity(String.class);
        System.out.println("Username: " +username);
        System.out.println("JSON string: " +jsonstring);
        
        if (jsonstring.contains("API rate limit exceeded")) {
            System.out.println("Will go on error because Api Rate Limit has exceeded " + username);
            return false;
        }
        if (jsonstring.contains("Not Found")) {
            System.out.println("User's repo not found: " + username);
            return false;
        }
        assertEquals("Status code", Response.Status.OK.getStatusCode(), jsonResponse.getStatus());
        return repoJsonCompliant(jsonstring, username);
    }

    private boolean userJsonCompliant(String jsonString, String username) {
        assertTrue("Login:", jsonString.contains("\"login\":\"" + username + "\","));
        String urlsPattern = "\"url\":\"" + githutUrlApi + "/users/" + username + "\","
                + "\"html_url\":\"" + githutUrl + "/" + username + "\","
                + "\"followers_url\":\"" + githutUrlApi + "/users/" + username + "/followers\","
                + "\"following_url\":\"" + githutUrlApi + "/users/" + username + "/following{/other_user}\","
                + "\"gists_url\":\"" + githutUrlApi + "/users/" + username + "/gists{/gist_id}\","
                + "\"starred_url\":\"" + githutUrlApi + "/users/" + username + "/starred{/owner}{/repo}\","
                + "\"subscriptions_url\":\"" + githutUrlApi + "/users/" + username + "/subscriptions\","
                + "\"organizations_url\":\"" + githutUrlApi + "/users/" + username + "/orgs\","
                + "\"repos_url\":\"" + githutUrlApi + "/users/" + username + "/repos\","
                + "\"events_url\":\"" + githutUrlApi + "/users/" + username + "/events{/privacy}\","
                + "\"received_events_url\":\"" + githutUrlApi + "/users/" + username + "/received_events\"";
        System.out.println("Urls Pattern: " + urlsPattern);
        assertTrue("Urls Pattern", jsonString.contains(urlsPattern));
        assertTrue("Type User", jsonString.contains("\"type\":\"User\","));
        return true;
    }

    private boolean repoJsonCompliant(String jsonString, String username) {
        userJsonCompliant(jsonString, username);
        assertTrue("full_name:", jsonString.contains("\"full_name\":\"" + username));
        assertTrue("url:", jsonString.contains("\"url\":\"" + githutUrlApi + "/repos/" + username));
        assertTrue("forks_url:", jsonString.contains("\"forks_url\":\"" + githutUrlApi + "/repos/" + username));
        assertTrue("keys_url:", jsonString.contains("\"keys_url\":\"" + githutUrlApi + "/repos/" + username));
        assertTrue("collaborators_url:", jsonString.contains("\"collaborators_url\":\"" + githutUrlApi + "/repos/" + username));
        assertTrue("teams_url:", jsonString.contains("\"teams_url\":\"" + githutUrlApi + "/repos/" + username));
        assertTrue("hooks_url:", jsonString.contains("\"hooks_url\":\"" + githutUrlApi + "/repos/" + username));
        assertTrue("issue_events_url:", jsonString.contains("\"issue_events_url\":\"" + githutUrlApi + "/repos/" + username));
        assertTrue("events_url:", jsonString.contains("\"events_url\":\"" + githutUrlApi + "/repos/" + username));
        assertTrue("assignees_url:", jsonString.contains("\"assignees_url\":\"" + githutUrlApi + "/repos/" + username));
        assertTrue("branches_url:", jsonString.contains("\"branches_url\":\"" + githutUrlApi + "/repos/" + username));
        assertTrue("tags_url:", jsonString.contains("\"tags_url\":\"" + githutUrlApi + "/repos/" + username));
        assertTrue("blobs_url:", jsonString.contains("\"blobs_url\":\"" + githutUrlApi + "/repos/" + username));
        assertTrue("git_tags_url:", jsonString.contains("\"git_tags_url\":\"" + githutUrlApi + "/repos/" + username));
        assertTrue("git_refs_url:", jsonString.contains("\"git_refs_url\":\"" + githutUrlApi + "/repos/" + username));
        assertTrue("trees_url:", jsonString.contains("\"trees_url\":\"" + githutUrlApi + "/repos/" + username));
        assertTrue("statuses_url:", jsonString.contains("\"statuses_url\":\"" + githutUrlApi + "/repos/" + username));
        assertTrue("languages_url:", jsonString.contains("\"languages_url\":\"" + githutUrlApi + "/repos/" + username));
        assertTrue("stargazers_url:", jsonString.contains("\"stargazers_url\":\"" + githutUrlApi + "/repos/" + username));
        assertTrue("contributors_url:", jsonString.contains("\"contributors_url\":\"" + githutUrlApi + "/repos/" + username));
        assertTrue("subscribers_url:", jsonString.contains("\"subscribers_url\":\"" + githutUrlApi + "/repos/" + username));
        assertTrue("subscription_url:", jsonString.contains("\"subscription_url\":\"" + githutUrlApi + "/repos/" + username));
        assertTrue("commits_url:", jsonString.contains("\"commits_url\":\"" + githutUrlApi + "/repos/" + username));
        assertTrue("git_commits_url:", jsonString.contains("\"git_commits_url\":\"" + githutUrlApi + "/repos/" + username));
        assertTrue("comments_url:", jsonString.contains("\"comments_url\":\"" + githutUrlApi + "/repos/" + username));
        assertTrue("issue_comment_url:", jsonString.contains("\"issue_comment_url\":\"" + githutUrlApi + "/repos/" + username));
        assertTrue("contents_url:", jsonString.contains("\"contents_url\":\"" + githutUrlApi + "/repos/" + username));
        assertTrue("compare_url:", jsonString.contains("\"compare_url\":\"" + githutUrlApi + "/repos/" + username));
        assertTrue("merges_url:", jsonString.contains("\"merges_url\":\"" + githutUrlApi + "/repos/" + username));
        assertTrue("archive_url:", jsonString.contains("\"archive_url\":\"" + githutUrlApi + "/repos/" + username));
        assertTrue("downloads_url:", jsonString.contains("\"downloads_url\":\"" + githutUrlApi + "/repos/" + username));
        assertTrue("issues_url:", jsonString.contains("\"issues_url\":\"" + githutUrlApi + "/repos/" + username));
        assertTrue("pulls_url:", jsonString.contains("\"pulls_url\":\"" + githutUrlApi + "/repos/" + username));
        assertTrue("milestones_url:", jsonString.contains("\"milestones_url\":\"" + githutUrlApi + "/repos/" + username));
        assertTrue("notifications_url:", jsonString.contains("\"notifications_url\":\"" + githutUrlApi + "/repos/" + username));
        assertTrue("labels_url:", jsonString.contains("\"labels_url\":\"" + githutUrlApi + "/repos/" + username));
        assertTrue("releases_url:", jsonString.contains("\"releases_url\":\"" + githutUrlApi + "/repos/" + username));
        assertTrue("deployments_url:", jsonString.contains("\"deployments_url\":\"" + githutUrlApi + "/repos/" + username));

        assertTrue("git_url:", jsonString.contains("\"git_url\":\"git://github.com/" + username));
        assertTrue("ssh_url:", jsonString.contains("\"ssh_url\":\"git@github.com:" + username));
        assertTrue("clone_url:", jsonString.contains("\"clone_url\":\"https://github.com/" + username));
        assertTrue("svn_url:", jsonString.contains("\"svn_url\":\"https://github.com/" + username));

        return true;
    }

    /**
     *
     * @throws Exception
     */
    @BeforeClass
    public static void beforeClass() throws Exception {
        restclient = ClientBuilder.newBuilder().build();
    }

    /**
     *
     * @throws Exception
     */
    @AfterClass
    public static void afterClass() throws Exception {
        restclient.close();
    }

    /**
     * Test user existence + Check Json jsonResponse syntax
     *
     * @throws Exception
     */
    @Test
    public void testUser() throws Exception {
        //This user is expected to exist
        String username = "nealstewart";
        assertTrue(username + " exists", userExists(username));
        //This user is expected to fail
        username = "whateverfakeusername";
        assertFalse(username + " doesn't exist", userExists(username));
    }

    /**
     * Test user's repo existence - will first check user exists + Check Json
 jsonResponse syntax
     *
     * @throws Exception
     */
    @Test
    public void testUserRepo() throws Exception {
        //This user is expected to exist and to have a repo
        String username = "nealstewart";
        assertTrue(username + "'s github repo exists", userHasRepo(username));
        //This user is expected to fail
        username = "whateverfakeusername";
        assertFalse(username + "'s github repo doesn't exist", userHasRepo(username));

    }

}
