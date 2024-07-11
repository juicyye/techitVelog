package techit.velog.global.dto;

import java.util.Map;

public class GoogleResponse implements OAuth2Response{
    private Map<String, Object> attribute;

    public GoogleResponse(Map<String, Object> attribute) {
        this.attribute = attribute;
    }

    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getProviderId() {
        return attribute.get("provider").toString();
    }

    @Override
    public String getEmail() {
        return attribute.get("email").toString();
    }

    @Override
    public String getName() {
        return attribute.get("name").toString();
    }

    @Override
    public String getLoginId() {
        return getProvider() + " " + getProviderId();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attribute;
    }
}
