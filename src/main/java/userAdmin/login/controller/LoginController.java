package userAdmin.login.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import userAdmin.login.service.LoginService;
import userAdmin.register.service.RegisterService;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


@Controller
public class LoginController {

    @Value("${google.http}")
    private String http;

    @Value("${google.scope}")
    private String scope;

    @Value("${google.state}")
    private String state;

    @Value("${google.redirectUri}")
    private String redirectUri;

    @Value("${google.responseType}")
    private String responseType;

    @Value("${google.clientId}")
    private String clientId;

    @Value("${google.clientSecret}")
    private String clientSecret;

    @Autowired
    private RegisterService registerService;

    @Autowired
    private LoginService loginService;

    @RequestMapping("/index")
    public String index() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {

        String token = loginService.getTokenRestTemplate(username, password);
        model.addAttribute("authorisation", "Bearer " + token);
        return "loginToHome";
    }

    @RequestMapping("/google/login")
    public String googleLogin(RedirectAttributes redirectAttributes) throws Exception {
//        redirectAttributes.addAttribute("scope", scope);
//        redirectAttributes.addAttribute("state", state);
//        redirectAttributes.addAttribute("redirect_uri", redirectUri);
//        redirectAttributes.addAttribute("response_type", responseType);
//        redirectAttributes.addAttribute("client_id", clientId);
        return "redirect:" + http;
    }

    @RequestMapping("/google/callBack")
    public String googleCallBack(HttpServletRequest req, Model model) throws IOException {
        // Google取得access_token的url
        URL urlObtainToken = new URL("https://accounts.google.com/o/oauth2/token");
        HttpURLConnection connectionObtainToken = (HttpURLConnection) urlObtainToken.openConnection();

        // 設定此connection使用POST
        connectionObtainToken.setRequestMethod("POST");
        connectionObtainToken.setDoOutput(true);

        // 開始傳送參數
        OutputStreamWriter writer = new OutputStreamWriter(connectionObtainToken.getOutputStream());
        writer.write("code=" + req.getParameter("code") + "&");   // 取得Google回傳的參數code
        writer.write("client_id=" + clientId + "&");
        writer.write("client_secret=" + clientSecret + "&");
        writer.write("redirect_uri=" + redirectUri + "&");
        writer.write("grant_type=authorization_code");
        writer.close();

        // 如果認證成功
        if (connectionObtainToken.getResponseCode() == HttpURLConnection.HTTP_OK) {
            StringBuilder sbLines = new StringBuilder("");

            // 取得Google回傳的資料(JSON格式)
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(connectionObtainToken.getInputStream(), "utf-8"));
            String strLine = "";
            while ((strLine = reader.readLine()) != null) {
                sbLines.append(strLine);
            }

            try {
                // 把上面取回來的資料，放進JSONObject中，以方便我們直接存取到想要的參數
                JSONObject jo = new JSONObject(sbLines.toString());

                URL urUserInfo =
                        new URL("https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + jo.getString("access_token"));
                HttpURLConnection connObtainUserInfo = (HttpURLConnection) urUserInfo.openConnection();


                //如果認證成功
                if (connObtainUserInfo.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    StringBuilder infoLines = new StringBuilder("");

                    // 取得Google回傳的資料(JSON格式)
                    BufferedReader infoReader =
                            new BufferedReader(new InputStreamReader(connObtainUserInfo.getInputStream(), "utf-8"));
                    String infoLine = "";
                    while ((infoLine = infoReader.readLine()) != null) {
                        infoLines.append(infoLine);
                    }
                    JSONObject userInfo = new JSONObject(infoLines.toString());

                    //存DB時,需先判斷帳號是否已存在
                    String token = registerService.register(new User(userInfo.getString("id"), userInfo.getString("email"), new ArrayList<>()));
                    model.addAttribute("authorisation", "Bearer " + token);
                    return "loginToHome";
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return "/login";
    }
}
