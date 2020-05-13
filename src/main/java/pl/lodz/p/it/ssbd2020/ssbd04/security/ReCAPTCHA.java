package pl.lodz.p.it.ssbd2020.ssbd04.security;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * Klasa reprezentująca odpowiedź od Google.
 */
public class ReCAPTCHA implements Serializable {

    private boolean success;

    @SerializedName("challenge_ts")
    private String challengeTs;

    private String hostname;

    private float score;

    private String action;

    @SerializedName("error-codes")
    private ErrorCode[] errorCodes;

    public boolean hasClientError() {
        ErrorCode[] errors = getErrorCodes();
        if (errors == null) {
            return false;
        }

        for (ErrorCode er : errors) {
            switch(er) {
                case InvalidResponse:
                case MissingResponse:
                    return true;
            }
        }
        return false;
    }

    public ErrorCode[] getErrorCodes() {
        return this.errorCodes;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getChallengeTs() {
        return challengeTs;
    }

    public void setChallengeTs(String challengeTs) {
        this.challengeTs = challengeTs;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setErrorCodes(ErrorCode[] errorCodes) {
        this.errorCodes = errorCodes;
    }

    @Override
    public String toString() {
        return "ReCAPTCHA{" +
                "success=" + success +
                ", challengeTs='" + challengeTs + '\'' +
                ", hostname='" + hostname + '\'' +
                ", score=" + score +
                ", action='" + action + '\'' +
                ", errorCodes=" + Arrays.toString(errorCodes) +
                '}';
    }

    static enum ErrorCode {
        MissingSecret,
        InvalidSecret,
        MissingResponse,
        InvalidResponse;

        private static Map<String, ErrorCode> errorsMap = new HashMap<>();

        static {
            errorsMap.put("missing-input-secret", MissingSecret);
            errorsMap.put("invalid-input-secret", InvalidSecret);
            errorsMap.put("missing-input-response", MissingResponse);
            errorsMap.put("invalid-input-response", InvalidResponse);
        }

        public static ErrorCode forValue(String value) {
            return errorsMap.get(value.toLowerCase());
        }
    }
}
