package edu.xjtu.social.dao;

import edu.xjtu.social.domain.node.Share;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Repository
public interface ShareDao extends Neo4jRepository<Share, Long> {

    @Query("match (user:User) where user.account={account}\n" +
            "create (share:Share{publishaccount:{account},publisher:{publisher},publisherimg:{publisherimg}," +
            "title:{title},content:{content},related_hobby:{related_hobby},hobbyid:{hobbyid},imgurl:{imgurl},time:{time},address:{address}})\n" +
            "create (user)-[publish:Publish]->(share) return share")
    Share publishShare(String account, String publisher, String publisherimg,
                       String title, String content, String related_hobby, Long hobbyid, String imgurl, String address, String time);

    @Query("match (user:User) where user.account={account}\n" +
            "match (share:Share) where ID(share)={shareid}\n" +
            "match (user)-[publish:Publish]->(share) delete share,publish return count(publish)")
    Integer deleteShareById(String account, Long shareid);

    @Query("match (user:User) where user.account={account} \n" +
            "match ((user)-[:Publish]->(share)) return share")
    List<Share> getShareByAccount(@Param("account") String account);

    @Query("match (user:User) where user.account={account}\n" +
            "match (user)-[:Follow]->(friends)-[:Publish]->(shares) return shares")
    ArrayList<Share> getFriendShares(@Param("account") String account);

    @Query("match (user:User) where user.account={account}\n" +
            "match (user)-[:Like]->(hobbies)\n" +
            "match (share:Share) where share.related_hobby in hobbies.hname return share")
    ArrayList<Share> recommendByHobby(@Param("account") String account);

    @Query("MATCH (user:User), (share:Share) WHERE user.account={account} AND ID(share)={shareid}\n" +
            "MERGE (user)-[p:Praised]->(share) RETURN count(p)")
    Integer likeShare(String account, Long shareid);

    @Query("MATCH (user:User)-[:Publish]->(share:Share) WHERE user.account={account}\n" +
            "OPTIONAL MATCH (praiser:User)-[p:Praised]->(share)\n" +
            "RETURN share, count(praiser) AS likes")
    List<Map<String, Object>> getShareByAccountWithLikes(@Param("account") String account);
}
