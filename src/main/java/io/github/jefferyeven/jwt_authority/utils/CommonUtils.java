package io.github.jefferyeven.jwt_authority.utils;

public class CommonUtils {
    public static String urlStandard(String s){
        int len = s.length();
        int st = 0;
        char[] val = s.toCharArray();    /* avoid getfield opcode */

        while ((st < len) && (val[st] <= ' ')) {
            st++;
        }
        while ((st < len) && (val[len - 1] <= ' '|| val[len-1]=='/')) {
            len--;
        }
        s = ((st > 0) || (len < val.length)) ? s.substring(st, len) : s;;
        StringBuilder stringBuilder = new StringBuilder();
        if(s.charAt(0)!='/'){
            stringBuilder.append('/');
        }
        stringBuilder.append(s);
        return stringBuilder.toString();
    }
}
