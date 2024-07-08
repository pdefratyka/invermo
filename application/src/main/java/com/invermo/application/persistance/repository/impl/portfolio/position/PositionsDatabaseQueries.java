package com.invermo.application.persistance.repository.impl.portfolio.position;

import com.invermo.application.persistance.entity.Position;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PositionsDatabaseQueries {

    static String addNewPosition(final Position position) {
        return "INSERT INTO positions (asset_id, user_id, position_type)\n" +
                "VALUES ('" +
                position.getAssetId() + "','" +
                position.getUserId() + "','" +
                position.getPositionType().toString() + "')";
    }

    static String getAllPositionsForUser(final Long userId) {
        return "SELECT * FROM positions where user_id='" + userId + "' ORDER BY position_id";
    }

    static String getPositionsWithAssetsForUser(final Long userId) {
        return "select \n" +
                "p.position_id as position_id, \n" +
                "p.asset_id as asset_id ,\n" +
                "p.position_type as position_type,\n" +
                "a.name as asset_name,\n" +
                "a.symbol as asset_symbol,\n" +
                "a.type as asset_type,\n" +
                "a.currency as currency\n" +
                "from positions p \n" +
                "inner join assets a ON p.asset_id=a.asset_id\n" +
                "where user_id='" + userId + "'";
    }
}
