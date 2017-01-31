package NinetyMin.core.FootBallMatch;

import org.apache.commons.lang3.EnumUtils;

import java.util.Optional;

/**
 * Created by orlavy on 1/31/17.
 */
public enum MatchStatus {
    UPCOMING, PLAYED;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

    public static Optional<MatchStatus> optionalFromString(String value){
        if (value != null && EnumUtils.isValidEnum(MatchStatus.class, value.toUpperCase())){
            return Optional.of(MatchStatus.valueOf(value.toUpperCase()));
        } else {
            return Optional.empty();
        }
    }
}

