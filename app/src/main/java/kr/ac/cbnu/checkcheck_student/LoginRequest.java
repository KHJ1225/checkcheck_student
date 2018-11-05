package kr.ac.cbnu.checkcheck_student;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class LoginRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "http://18.234.170.219/login_stu.php";
    private Map<String, String> params;

    public LoginRequest(String id, String password, Response.Listener<String> listener) {
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<String, String>();
        params.put("studentnumber", id);
        params.put("password", password);
    }

    public Map<String, String> getParams() {
        return params;
    }

}