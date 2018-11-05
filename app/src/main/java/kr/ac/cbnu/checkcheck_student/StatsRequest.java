package kr.ac.cbnu.checkcheck_student;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class StatsRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "http://18.234.170.219/stats_stu.php";
    private Map<String, String> params;

    public StatsRequest(String id, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<String, String>();
        params.put("studentnumber", id);
    }

    public Map<String, String> getParams() {
        return params;
    }

}