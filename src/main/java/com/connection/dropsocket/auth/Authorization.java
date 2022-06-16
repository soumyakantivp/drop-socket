package com.connection.dropsocket.auth;


import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxAuthFinish;

import com.dropbox.core.json.JsonReader;
import com.dropbox.core.oauth.DbxCredential;

public class Authorization {
	public static void main(String[] args) throws IOException {
        // Only display important log messages.
        Logger.getLogger("").setLevel(Level.WARNING);

        /*
        if (args.length != 3) {
            System.err.println("Usage: COMMAND <app-info-file> <auth-file-output> <mode>");
            System.err.println("");
            System.err.println("<app-info-file>: a JSON file with information about your API app.  Example:");
            System.err.println("");
            System.err.println("  {");
            System.err.println("    \"key\": \"Your Dropbox API app key...\",");
            System.err.println("    \"secret\": \"Your Dropbox API app secret...\"");
            System.err.println("  }");
            System.err.println("");
            System.err.println("  Get an API app key by registering with Dropbox:");
            System.err.println("    https://dropbox.com/developers/apps");
            System.err.println("");
            System.err.println("<auth-file-output>: If authorization is successful, the resulting API");
            System.err.println("  credential will be saved to this file, which can then be used with");
            System.err.println("  other example programs, such as the one in \"examples/account-info\".");
            System.err.println("");
            System.err.println("<mode>: value can only be short_live_token, pkce, scope, or incremental.");
            System.err.println("  short_live_token: authorization will request short_lived_token");
            System.err.println("    together with refresh token and expiration time.");
            System.err.println("  pkce: authorization will run short_live_token without app secret");
            System.err.println("    use that when you have a client side only app without server.");
            System.err.println("  scope: authorization will request specific scope.");
            System.err.println("");
            System.exit(1);
            return;
        }
*/
        String argAppInfoFile = "src\\main\\java\\com\\connection\\dropsocket\\auth\\test.app";
        String argAuthFileOutput = "C:\\Users\\Soumya Kanti\\eclipse-workspace\\drop-socket\\src\\main\\java\\com\\connection\\dropsocket\\controller\\test.auth";

        // Read app info file (contains app key and app secret)
        DbxAppInfo appInfo;
        try {
            appInfo = DbxAppInfo.Reader.readFromFile(argAppInfoFile);
        } catch (JsonReader.FileLoadException ex) {
            System.err.println("Error reading <app-info-file>: " + ex.getMessage());
            System.exit(1); return;
        }

        // Run through Dropbox API authorization process
        DbxAuthFinish authFinish = null;
        String mode = "short_live_token";
        switch (mode) {
            case "short_live_token":
                authFinish = new ShortLiveTokenAuthorize().authorize(appInfo);
                break;
            case "pkce":
                authFinish = new PkceAuthorize().authorize(appInfo);
                break;
            case "scope":
                authFinish = new ScopeAuthorize().authorize(appInfo);
                break;
            default:
                System.err.println("Error reading <mode> : " + args[2]);
                System.exit(1);
        }

        System.out.println("Authorization complete.");
        System.out.println("- User ID: " + authFinish.getUserId());
        System.out.println("- Account ID: " + authFinish.getAccountId());
        System.out.println("- Access Token: " + authFinish.getAccessToken());
        System.out.println("- Expires At: " + authFinish.getExpiresAt());
        System.out.println("- Refresh Token: " + authFinish.getRefreshToken());
        System.out.println("- Scope: " + authFinish.getScope());

        // Save auth information the new DbxCredential instance. It also contains app_key and
        // app_secret which is required to do refresh call.
        DbxCredential credential = new DbxCredential(authFinish.getAccessToken(), authFinish
            .getExpiresAt(), authFinish.getRefreshToken(), appInfo.getKey(), appInfo.getSecret());
        File output = new File(argAuthFileOutput);
        try {
            DbxCredential.Writer.writeToFile(credential, output);
            System.out.println("Saved authorization information to \"" + output.getCanonicalPath() + "\".");
        } catch (IOException ex) {
            System.err.println("Error saving to <auth-file-out>: " + ex.getMessage());
            System.err.println("Dumping to stderr instead:");
            DbxCredential.Writer.writeToStream(credential, System.err);
            System.exit(1); return;
        }
    }
}
