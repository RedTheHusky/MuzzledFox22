package web.jexpress.models.collectdata;

public interface iAuth {
    interface Option{
        String
                code="code",
                auth="auth",
                token="token",
                includeRoles="includeRoles",
                includePermissions="includePermissions",
                includeGuilds="includeGuilds";
    }
    interface Cookies{
        String
                user="user",
                token="token";
    }
    interface  Keys{
        String
                accessToken ="accessToken",
                refreshToken ="refreshToken",
                userId ="user_id",
                code ="code",
                token = "token";
    }



}
